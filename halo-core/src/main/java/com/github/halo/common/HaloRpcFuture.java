package com.github.halo.common;

import com.github.halo.common.packet.HaloRpcRequest;
import com.github.halo.common.packet.HaloRpcResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 管理异步模型的Future实现 <br>
 * get阻塞的实现可以使用传统的synchronized 的lock搭配 <br>
 * wait() / signalAll() 来实现，这边不使用这种做法，而是扩展使用AQS
 * @author mason.lu 2021/6/27
 */
public class HaloRpcFuture implements Future {

    private long requestId;

    private HaloRpcResponse response;

    private long startTimestamp;

    private ReentrantLock lock = new ReentrantLock();

    private final List<RpcCallback> peddingCallbacks = new ArrayList<>();

    private StateLock stateLock = new StateLock();

    private HaloRpcRequest request;

    /**
     * Future线程池，负责运行Future的任务
     * */
    private static final ExecutorService executor =
            Executors.newFixedThreadPool(16);


    public HaloRpcFuture(long requestId,HaloRpcRequest request){
        this.requestId = requestId;
        this.request = request;
        this.startTimestamp = System.currentTimeMillis();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDone() {
        return stateLock.isDone();
    }

    @Override
    public Object get() throws InterruptedException, ExecutionException {
        stateLock.acquire(0);
        if (Objects.nonNull(response)){
            return response.getData();
        }else {
            return null;
        }
    }

    @Override
    public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        boolean success = stateLock.tryAcquireNanos(-1, unit.toNanos(timeout));
        if (success){
            if (this.response!=null){
                return this.response.getData();
            }else {
                return null;
            }
        }else {
            throw new RuntimeException("timeout execution requestId: " + requestId
                    + ",className: " + this.request.getClassName()
                    + ",methodName: " + this.request.getMethodName());
        }
    }

    public void done(HaloRpcResponse response){
        this.response = response;
        //release lock
        boolean success = stateLock.release(0);
        if (success){
            executeCallBacks();
        }
        long constTime = System.currentTimeMillis() - startTimestamp;
        //TODO 增加花费时间的处理，如进行统计或者打印WARNING信息
    }

    /**
     * 简单的基于状态的lock
     * */
    class StateLock extends AbstractQueuedSynchronizer{

        private static final int DONE = 1;

        private static final int WAITING = 0;

        /**
         * tryAcquire失败进入等待队列
         * */
        @Override
        protected boolean tryAcquire(int arg) {
            return getState() == DONE;
        }

        /**
         * 尝试释放
         * */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == WAITING){
                return compareAndSetState(WAITING, DONE);
            }
            return false;
        }

        public boolean isDone(){
            return getState() == DONE;
        }
    }

    public HaloRpcFuture addCallBack(RpcCallback callback){
        if (Objects.isNull(callback)){
            throw new NullPointerException("rpc call back must not null!");
        }
        lock.lock();
        try{
            if (isDone()){
                executeCallBack(callback);
            }else {
                this.peddingCallbacks.add(callback);
            }
        }finally {
            lock.unlock();
        }
        return this;
    }

    private void executeCallBack(RpcCallback callback){
        final HaloRpcResponse resp = this.response;
        executor.submit(new Runnable() {
            @Override
            public void run() {
                if (resp.getThrowable() == null){
                    callback.handleSuccess(resp);
                }else {
                    callback.handleFailure(resp.getThrowable());
                }
            }
        });
    }

    private void executeCallBacks(){
        lock.lock();
        try{
            for (final RpcCallback peddingCallback : peddingCallbacks) {
                executeCallBack(peddingCallback);
            }
        }finally {
            lock.unlock();
        }
    }
}
