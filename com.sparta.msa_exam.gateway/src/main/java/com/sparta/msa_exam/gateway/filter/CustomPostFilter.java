package com.sparta.msa_exam.gateway.filter;

import com.sparta.msa_exam.gateway.config.RouteConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomPostFilter implements GlobalFilter, Ordered {

    private final DiscoveryClient discoveryClient;
    private final RouteConfig routeConfig;


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            log.info("Post Filter: Response status code is {}", response.getStatusCode());

            // 요청 URI에서 경로 부분 추출
            String requestPath = exchange.getRequest().getPath().toString();
            log.info("Request Path: {}", requestPath);

            // 라우트 설정을 순회하며 비교
            routeConfig.getRoutes().forEach(route -> {
                String routeId = route.getId();
                route.getPredicates().forEach(predicate -> {
                    // 예: Path=/products/** -> '/products'로 변환
                    String predicateServicePath = extractFromPredicatesPath(predicate);

                    // 서비스와 일치하는 instance url port를 헤더로 넘기기
                    if (requestPath.equals(predicateServicePath)) {
                        ServiceInstance serviceInstance = discoveryClient.getInstances(routeId).get(0);
                        log.info("Server-Port : {}", serviceInstance.getPort());
                        response.getHeaders().add(
                                "Server-Port", String.valueOf(serviceInstance.getPort())
                        ); ;
                    }
                });
            });
        }));
    }

    // 경로에서 요청 서비스 path 만 추출
    private String extractFromPredicatesPath(String path) {
        // Path= 제거
        if (path.startsWith("Path=")) {
            path = path.substring(5);
        }

        // 예를 들어 '/products/**' -> '/products'
        int idx = path.indexOf("/**");
        if (idx != -1) {
            return path.substring(0, idx);
        }
        return path;
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }


}