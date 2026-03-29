package com.ecommerce.category.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    @org.springframework.lang.NonNull
    public ClientHttpResponse intercept(@org.springframework.lang.NonNull HttpRequest request, @org.springframework.lang.NonNull byte[] body, @org.springframework.lang.NonNull ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();
        headers.add(UserContext.CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(UserContext.AUTH_TOKEN, UserContextHolder.getContext().getAccessToken());

        return execution.execute(request, body);
    }
}
