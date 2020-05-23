package org.uma.external.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class WebSocketConfiguration {

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }


    @Bean
    public HandlerMapping webSocketHandlerMapping(Map<String, WebSocketHandler> webSocketHandlers) {
        // bean名で、Mappingする
        Map<String, WebSocketHandler> map = webSocketHandlers.entrySet().stream()
                .collect(Collectors.toMap(d -> "/" + d.getKey(), Map.Entry::getValue));

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1); // beanの優先順位
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }
}
