package com.viki.cloud.consumer.interceptor;

import com.viki.cloud.common.CommonConstantClass;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class TokenRelayRequestIntecepor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        //1. 获取到token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader(CommonConstantClass.HEAD_KEY_TOKEN);

        //2. 将token传递
        if (StringUtils.isNotBlank(token)) {
            requestTemplate.header(CommonConstantClass.HEAD_KEY_TOKEN, token);
        }
    }
}
