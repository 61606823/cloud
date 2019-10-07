package com.viki.cloud.gateway.predicate;

import com.viki.cloud.gateway.bean.TimeBetweenConfigBean;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfigBean> {
    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfigBean.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfigBean config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();

        return exchange -> {
            LocalTime now = LocalTime.now();
            return now.isAfter(start) && now.isBefore(end);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("start", "end");
    }
}
