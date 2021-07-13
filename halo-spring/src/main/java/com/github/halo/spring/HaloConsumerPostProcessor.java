package com.github.halo.spring;

import com.github.halo.annotation.HaloRpcReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * haloConsumer后置处理器
 * @author mason.lu 2021/7/11
 */
public class HaloConsumerPostProcessor implements ApplicationContextAware, BeanClassLoaderAware, BeanFactoryPostProcessor {

    private ClassLoader classLoader;

    private ApplicationContext context;

    private final Map<String,BeanDefinition> rpcRefBeanDefinition =
            new LinkedHashMap<>();


    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if (beanClassName != null){
                Class<?> clazz = ClassUtils.resolveClassName(beanClassName,classLoader);
                ReflectionUtils.doWithFields(clazz,this::parseRpcReference);
            }
        }

        BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
        this.rpcRefBeanDefinition.forEach((beanName,beanDefinition)->{
            if (context.containsBean(beanName)){
                throw new IllegalArgumentException("spring context already has a bean named " + beanName);
            }
            registry.registerBeanDefinition(beanName,beanDefinition);
        });
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void parseRpcReference(Field field){
        HaloRpcReference annotation = AnnotationUtils.getAnnotation(field, HaloRpcReference.class);
        if (annotation != null){
            BeanDefinitionBuilder builder =
                    BeanDefinitionBuilder.genericBeanDefinition(RpcReferenceBean.class);
            builder.setInitMethodName(RpcReferenceBean.INIT_METHOD_NAME);
            builder.addPropertyValue("interfaceClass",field.getType());
            builder.addPropertyValue("serviceVersion",annotation.serviceVersion());
            builder.addPropertyValue("timeout",annotation.timeout());

            BeanDefinition beanDefinition = builder.getBeanDefinition();
            rpcRefBeanDefinition.put(field.getName(),beanDefinition);
        }
    }
}
