package com.viki.cloud.consumer.controller;

import com.viki.cloud.consumer.common.ConstantClass;
import com.viki.cloud.consumer.feignClient.TestClient;
import com.viki.cloud.consumer.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
@RefreshScope
@Slf4j
public class TestController {
    private final RestTemplate restTemplate;
    private final TestClient testClient;
    private final TestService testService;
    @Value("${k1:}")
    private String k1;

    public TestController(RestTemplate restTemplate, TestClient testClient, TestService testService) {
        this.restTemplate = restTemplate;
        this.testClient = testClient;
        this.testService = testService;
    }

    /**
     * restTemplate
     *
     * @param name
     * @return
     */
    @GetMapping("/resttemplate")
    public String testWithRestTemplate(@RequestParam String name) {
        return restTemplate.getForObject(String.format("http://%s/test/hello?name=%s", ConstantClass.APPLICATION_NAME_SERVER, name),
                String.class);
    }

    /**
     * feign
     *
     * @param name
     * @return
     */
    @GetMapping("/feign")
    //@AuthToken
    public String testWithFeign(@RequestParam String name) throws Throwable {
        return testClient.hello(name);
    }

    /**
     * config
     *
     * @return
     */
    @GetMapping("/config")
    public String testWithConfig() {
        return k1;
    }

    @GetMapping("/sentineljson")
    public String testWithSentinelJson() {
        return "SentinelJson";
    }

    /**
     * 限流
     *
     * @param name
     * @return
     */
    @GetMapping("/currentlimiting")
    public String testWithCurrentLimiting(@RequestParam String name) {
        return testService.currentLimiting(name);
    }

    /**
     * 降级
     *
     * @param name
     * @return
     */
    @GetMapping("/downgrade")
    public String testWithDowngrade(@RequestParam String name) {
        return testService.downgrade(name);
    }
}
