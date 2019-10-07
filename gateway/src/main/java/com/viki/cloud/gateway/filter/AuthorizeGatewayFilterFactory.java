package com.viki.cloud.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {
    private static final String KEY = "enabled";
    private static final String AUTHORIZE_TOKEN = "token";

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        log.info("Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(KEY);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String token = headers.getFirst(AUTHORIZE_TOKEN);
            ServerHttpResponse response = exchange.getResponse();

            if (token == null) {
                token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
            }

            if (StringUtils.isEmpty(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
        /**
         * 是否开启认证
         */
        private boolean enabled;

        public Config() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
