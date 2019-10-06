package ribbonconfiguration;

import com.netflix.loadbalancer.IRule;
import com.viki.cloud.consumer.config.NacosSameClusterWeightedRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalRibbonConfig {
    @Bean
    public IRule ribbonRule() {
        //由于Feign是基于Ribbon实现的，所以它自带了客户端负载均衡功能，也可以通过Ribbon的IRule进行策略扩展
        return new NacosSameClusterWeightedRule();
    }
}
