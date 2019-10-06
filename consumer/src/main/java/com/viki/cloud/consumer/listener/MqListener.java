package com.viki.cloud.consumer.listener;

import com.alibaba.fastjson.JSON;
import com.viki.cloud.common.mq.InputBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 消息监听器
 */
@Component
@Slf4j
public class MqListener {
    private int count = 1;

    /**
     * 接收消息
     *
     * @param headers 消息头
     * @param payload 消息体
     */
    @StreamListener(value = InputBinder.INPUT_TEST, condition = "headers['version']=='1.0'")
    public void testReceiver(@Headers Map headers, @Payload Object payload) {
        log.info("Received {}-headers:{},payload:{}", count, JSON.toJSONString(headers), payload);

//        if (count == 3) {
//            count = 1;
//            //当抛出这个异常的时候，会将消息放入DLQ队列，从而不会造成严重的堆积问题。
//            throw new AmqpRejectAndDontRequeueException("tried 3 times failed, send to dlq!");
//        } else {
//            count++;
//            throw new RuntimeException("Message consumer failed!");
//        }
    }

    /**
     * 处理局部异常的方法
     *
     * @param errorMessage
     */
    //@ServiceActivator(inputChannel = InputBinder.INPUT_TEST + ".com-viki-cloud-consumer-group.errors")
//    public void testReceiverFallbackHandler(ErrorMessage errorMessage) {
//        // 获取异常对象
//        Throwable errorMessagePayload = errorMessage.getPayload();
//        log.error("发生异常", errorMessagePayload);
//
//        //获取消息体
//        Message<?> originalMessage = errorMessage.getOriginalMessage();
//
//        if (originalMessage != null) {
//            String message = new String((byte[]) originalMessage.getPayload());
//            log.info("消息体: {}", message);
//        } else {
//            log.info("消息体为空");
//        }
//    }

    /**
     * 处理全局异常的方法
     *
     * @param errorMessage
     */
//    @StreamListener("errorChannel")
//    public void handleError(ErrorMessage errorMessage) {
//        log.error("发生异常. errorMessage = {}", errorMessage);
//    }
}
