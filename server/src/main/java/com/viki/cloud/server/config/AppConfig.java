package com.viki.cloud.server.config;

import com.viki.cloud.common.mq.OutputBinder;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(value = {OutputBinder.class})
public class AppConfig {

}
