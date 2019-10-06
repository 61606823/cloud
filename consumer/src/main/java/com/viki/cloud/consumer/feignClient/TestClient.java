package com.viki.cloud.consumer.feignClient;

import com.viki.cloud.consumer.common.ConstantClass;
import com.viki.cloud.consumer.feignClient.fallbackFactory.TestClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ConstantClass.APPLICATION_NAME_SERVER, path = "/test", fallbackFactory = TestClientFallbackFactory.class)
public interface TestClient {
    @GetMapping("/hello")
    String hello(@RequestParam(name = "name") String name) throws Throwable;
}
