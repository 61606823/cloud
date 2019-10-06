package com.viki.cloud.consumer.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TestService {
    /**
     * 限流
     *
     * @param name
     * @return
     */
    @SentinelResource(value = "currentlimiting", blockHandler = "currentLimitingFallbackHandler")
    public String currentLimiting(String name) {
        log.info(name);
        return name;
    }

    /**
     * 限流
     * 实现处理函数，该函数的传参必须与资源点的传参一样，并且最后加上BlockException异常参数；同时，返回类型也必须一样。
     *
     * @param name
     * @param ex
     * @return
     */
    public String currentLimitingFallbackHandler(String name, BlockException ex) {
        //log.error(String.format("currentLimitingFallbackHandler-%s", name), ex);
        String logStr = String.format("currentLimitingFallbackHandler-%s", name);
        log.info(logStr);

        return logStr;
    }

    /**
     * 降级
     * 方法的调用QPS >= 5，如果异常率超过50%，那么后续2秒内的调用将直接出发熔断降级，默认情况会直接抛出DegradeException异常
     * 这时候就会执行fallback方法
     *
     * @param name
     * @return
     */
    @SentinelResource(value = "downgrade", fallback = "downgradeFallbackHandler")
    public String downgrade(String name) {
        log.info(name);
        throw new RuntimeException("发生异常");
    }

    /**
     * 降级
     *
     * @param name
     * @param ex
     * @return
     */
    public String downgradeFallbackHandler(String name, Throwable ex) {
        //log.error(String.format("downgradeFallbackHandler-%s", name), ex);
        String logStr = String.format("downgradeFallbackHandler-%s", name);
        log.info(logStr);

        return logStr;
    }
}
