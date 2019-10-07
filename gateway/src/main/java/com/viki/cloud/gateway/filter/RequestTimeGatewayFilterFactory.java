package com.viki.cloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class RequestTimeGatewayFilterFactory extends AbstractGatewayFilterFactory<RequestTimeGatewayFilterFactory.Config> {
    private static final String KEY = "withParams";
    private static final String REQUEST_TIME_BEGIN = "requestTimeBegin";

    public RequestTimeGatewayFilterFactory() {
        super(Config.class);
        log.info("Loaded GatewayFilterFactory [RequestTime]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            exchange.getAttributes().put(REQUEST_TIME_BEGIN, System.currentTimeMillis());

            return chain.filter(exchange).then(new Mono<Void>() {
                @Override
                public void subscribe(CoreSubscriber<? super Void> coreSubscriber) {
                    Mono.fromRunnable(() -> {
                        Long startTime = exchange.getAttribute(REQUEST_TIME_BEGIN);
                        if (startTime != null) {
                            StringBuilder sb = new StringBuilder(exchange.getRequest().getURI().getRawPath())
                                    .append(": ")
                                    .append(System.currentTimeMillis() - startTime)
                                    .append("ms");
                            if (config.isWithParams()) {
                                sb.append(" params:").append(exchange.getRequest().getQueryParams());
                            }
                            log.info(sb.toString());
                        }
                    });
                }
            });
        };
    }

    public static class Config {
        private boolean withParams;

        public Config() {

        }

        public boolean isWithParams() {
            return withParams;
        }

        public void setWithParams(boolean withParams) {
            this.withParams = withParams;
        }
    }
}
