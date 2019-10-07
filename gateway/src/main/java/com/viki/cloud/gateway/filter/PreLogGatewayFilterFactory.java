package com.viki.cloud.gateway.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class PreLogGatewayFilterFactory extends AbstractGatewayFilterFactory<PreLogGatewayFilterFactory.Config> {
    public PreLogGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("name", "value");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            log.info("请求进来了...{},{}", config.getName(), config.getValue());
            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .build();
            ServerWebExchange modifiedExchange = exchange.mutate()
                    .request(modifiedRequest)
                    .build();

            return chain.filter(modifiedExchange);
        });
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Config {
        private String name;
        private String value;
    }
}
