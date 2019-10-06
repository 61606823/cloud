package com.viki.cloud.server.controller;

import com.viki.cloud.common.annotation.AuthToken;
import com.viki.cloud.common.mq.OutputBinder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    private final OutputBinder outputBinder;

    public TestController(OutputBinder outputBinder) {
        this.outputBinder = outputBinder;
    }

    @GetMapping("/hello")
    @AuthToken
    public String hello(@RequestParam String name) {
        log.debug(String.format("hello=%s", name));
        log.info(String.format("hello=%s", name));

        return String.format("com.viki.cloud.server=%s", name);
    }

    /**
     * 发生消息
     *
     * @param message
     * @return
     */
    @GetMapping("/sendmsg")
    public String sendMsg(@RequestParam String message) {
        log.info("Send:{}", message);

        Map<String, Object> headers = new HashMap<>();
        headers.put("charset-encoding", "UTF-8");
        headers.put("content-type", MediaType.TEXT_PLAIN_VALUE);
        outputBinder.testChannel().send(new GenericMessage<>(message, headers));

        return "ok";
    }

    /**
     * 发生消息
     * 在RabbitMQ中，消息的过期时间必须是非负 32 位整数，即：0 <= n <= 2^32-1，以毫秒为单位。 其中，2^32-1 = 4294967295。
     *
     * @param message
     * @param delay
     * @return
     */
    @GetMapping("/sendmsgwithdelay")
    public String sendMsgWithDelay(@RequestParam String message, @RequestParam int delay) {
        log.info("Send:{},delay:{}", message, delay);

        outputBinder.testChannel().send(
                MessageBuilder.withPayload(message)
                        .setHeader("x-delay", delay)
                        .build()
        );

        return "ok";
    }

    /**
     * 发生消息
     *
     * @param message
     * @return
     */
    @GetMapping("/sendmsgwithcondition")
    public String sendMsgWithCondition(@RequestParam String message) {
        log.info("Send:{}", message);

        outputBinder.testChannel().send(
                MessageBuilder.withPayload(message + "-v1")
                        .setHeader("version", "1.0")
                        .build()
        );
        outputBinder.testChannel().send(
                MessageBuilder.withPayload(message + "-v2")
                        .setHeader("version", "2.0")
                        .build()
        );

        return "ok";
    }
}
