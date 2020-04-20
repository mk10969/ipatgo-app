package org.uma.external.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class WebSocketConfiguration {

    @Autowired
    private Map<String, WebSocketHandler> webSocketHandlers;


    @Bean
    public HandlerMapping webSocketHandlerMapping() {
        // bean名で、Mappingする
        Map<String, WebSocketHandler> map = webSocketHandlers.entrySet().stream()
                .collect(Collectors.toMap(d -> "/" + d.getKey(), Map.Entry::getValue));

        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        handlerMapping.setOrder(1); // beanの優先順位
        handlerMapping.setUrlMap(map);
        return handlerMapping;
    }

    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }


    @Slf4j
    @Component("jvWatchEvent")
    public static class JvLinkWebSocketHandler implements WebSocketHandler {

        @Autowired
        private Flux<String> hotPublisher;

        /**
         * 遅延時間: second
         */
        private static final Integer DELAY_INTERVAL = 1;

        @NonNull
        @Override
        public Mono<Void> handle(WebSocketSession session) {
            return session.send(hotPublisher
                    .delayElements(Duration.ofSeconds(DELAY_INTERVAL))
                    .doOnNext(message -> log.info("Event Message: {}", message))
                    .map(session::textMessage))
                    .doOnSubscribe(i -> log.info("Subscribe On WebSocket SessionId: {}",
                            session.getId()))
                    .doOnTerminate(() -> log.info("UnSubscribe On WebSocket SessionId: {}",
                            session.getId()))
                    .doOnError(ex -> log.error("WebSocket Error: ", ex));
        }
    }

}
