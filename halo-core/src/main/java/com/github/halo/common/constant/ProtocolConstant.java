package com.github.halo.common.constant;

/**
 * @author mason.lu 2021/6/22
 */
public interface ProtocolConstant {
    short MAGIC = 'H'&'A' << 8 | ('L'|'O');
    short VERSION = 0x1;
    short HEADER_LENGTH = 18;
    String ENCODER_NAME = "halo-inner-encoder";
    String DECODER_NAME = "halo-inner-decoder";
    String SERVER_HANDLER_NAME = "halo-server-handler";
    String CLIENT_HANDLER_NAME = "halo-client-handler";
}
