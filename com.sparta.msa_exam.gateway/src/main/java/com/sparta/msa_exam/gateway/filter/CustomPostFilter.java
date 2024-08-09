package com.sparta.msa_exam.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
public class CustomPostFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            log.info("Post Filter: Response status code is {}", response.getStatusCode());
            String requestPath = exchange.getRequest().getPath().toString();
            log.info("Request Path: {}", requestPath);

            // ex > 이전 필터에서 권한 에러를 발생시키고 setStatus() 를 한 경우
            if (!response.isCommitted()) {
                // 호출된 서비스의 실제 uri 추출
                var serviceInstance = (URI) exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);

                // 서버 포트 헤더에 같이 출력
                response.getHeaders().add(
                        "Server-Port", String.valueOf(serviceInstance.getPort())
                );
            }

        }));
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}