package com.whistle6.api.common.component.interceptor;

import java.util.stream.Stream;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.whistle6.api.common.component.exception.AuthException;
import com.whistle6.api.common.component.security.JwtProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return CorsUtils.isPreFlightRequest(request)
        ? true
        : Stream.of(request)
        .map(req -> jwtProvider.parseBearerToken(req, "Authorization"))
        .filter(i -> !i.equals("undefined"))
        .filter(i -> jwtProvider.validateToken(i, "access"))
        .map(i -> true)
        .findAny()
        .orElseThrow(() -> new AuthException());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
