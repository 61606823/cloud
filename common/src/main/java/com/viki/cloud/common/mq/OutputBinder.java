package com.viki.cloud.common.mq;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * 发送绑定器
 */
public interface OutputBinder {
    String OUTPUT_NAME = "topic-test";

    /**
     * 输出通道
     *
     * @return
     */
    @Output(OUTPUT_NAME)
    MessageChannel testChannel();
}
