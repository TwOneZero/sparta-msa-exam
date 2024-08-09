package com.sparta.msa_exam.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class RouteConfig {

    private List<Route> routes;

    @Setter
    @Getter
    public static class Route {
        private String id;
        private String uri;
        private List<String> predicates;
    }
}