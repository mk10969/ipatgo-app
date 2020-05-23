package org.uma.external.websocket;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Component("jvWatchEvent")
public class JvLinkWebSocketHandler implements WebSocketHandler {

    private static final long DELAY_INTERVAL = 200;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    @Getter
    private final UnicastProcessor<JvLinkWatchConfiguration.EventMessage> hotSource =
            UnicastProcessor.create(new ConcurrentLinkedQueue<>());

    private Flux<JvLinkWatchConfiguration.EventMessage> hotPublisher;


    @PostConstruct
    void init() {
        this.hotPublisher = hotSource.publish().autoConnect();
    }


    @NonNull
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(hotPublisher
                .delayElements(Duration.ofMillis(DELAY_INTERVAL))
                .map(JvLinkWebSocketHandler::toJson)
                .doOnNext(message -> log.info("Event Message: {}", message))
                .map(session::textMessage))
                .doOnSubscribe(i -> log.info("Subscribe On WebSocket SessionId: {}",
                        session.getId()))
                .doOnTerminate(() -> log.info("UnSubscribe On WebSocket SessionId: {}",
                        session.getId()))
                .doOnError(ex -> log.error("WebSocket Error: ", ex));
    }

    private static String toJson(JvLinkWatchConfiguration.EventMessage eventMessage) {
        try {
            return objectMapper.writeValueAsString(eventMessage);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

