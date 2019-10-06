package com.viki.cloud.consumer.config;

import com.viki.cloud.common.mq.InputBinder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBinding(value = {InputBinder.class})
public class AppConfig {
    @Bean
    @LoadBalanced
    //在定义RestTemplate的时候，
    // 增加了@LoadBalanced注解，而在真正调用服务接口的时候，原来host部分是通过手工拼接ip和端口的，
    // 直接采用服务名的时候来写请求路径即可。
    // 在真正调用的时候，Spring Cloud会将请求拦截下来，然后通过负载均衡器选出节点，
    // 并替换服务名部分为具体的ip和端口，从而实现基于服务名的负载均衡调用。
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
