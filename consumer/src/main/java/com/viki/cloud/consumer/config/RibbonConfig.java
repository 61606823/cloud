package com.viki.cloud.consumer.config;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;
import ribbonconfiguration.GlobalRibbonConfig;

@Configuration
@RibbonClients(defaultConfiguration = GlobalRibbonConfig.class)
public class RibbonConfig {

}
