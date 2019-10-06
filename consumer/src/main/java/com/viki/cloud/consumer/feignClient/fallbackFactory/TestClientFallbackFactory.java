package com.viki.cloud.consumer.feignClient.fallbackFactory;

import com.viki.cloud.consumer.feignClient.TestClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestClientFallbackFactory implements FallbackFactory<TestClient> {
    @Override
    public TestClient create(Throwable ex) {
        return name -> {
            //log.error(String.format("远程调用被限流/降级了-%s", name), ex);
            String logStr = String.format("远程调用被限流/降级了-%s", name);
            log.info(logStr);

            return logStr;
        };
    }
}
