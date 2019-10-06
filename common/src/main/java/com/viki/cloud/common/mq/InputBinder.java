package com.viki.cloud.common.mq;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * 订阅绑定器
 */
public interface InputBinder {
    String INPUT_TEST = "topic-test";

    /**
     * 输入通道
     *
     * @return
     */
    @Input(INPUT_TEST)
    SubscribableChannel testChannel();
}
