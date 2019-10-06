package com.viki.cloud.consumer.aop;

import com.viki.cloud.common.CommonConstantClass;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthAspect {
    @Around("@annotation(com.viki.cloud.common.annotation.AuthToken)")
    public Object checkToken(ProceedingJoinPoint point) throws Throwable {
        checkToken();
        return point.proceed();
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        return attributes.getRequest();
    }

    private void checkToken() {
        //1. 从header里面获取token
        HttpServletRequest request = getHttpServletRequest();
        String token = request.getHeader(CommonConstantClass.HEAD_KEY_TOKEN);

        if (StringUtils.isBlank(token)) {
            throw new SecurityException("Token不合法！");
        }
    }
}
