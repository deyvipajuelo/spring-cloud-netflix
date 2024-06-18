package com.dpajuelo.spring.gateway.spring_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.error("Pre filtro");
        exchange.getRequest().mutate().headers(h -> h.add("token", "123"));

        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            log.error("Post filtro");
            log.info("TOKEN: " + exchange.getRequest().getHeaders().getFirst("token"));
//            exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
            exchange.getResponse().getCookies().add("color", ResponseCookie.from("color","Rojo").build());
            exchange.getResponse().getCookies().add("token", ResponseCookie.from("token",exchange.getRequest().getHeaders().getFirst("token")).build());
        }));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
