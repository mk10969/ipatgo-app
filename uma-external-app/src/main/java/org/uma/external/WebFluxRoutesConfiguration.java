package org.uma.external;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.uma.external.handler.IPatGoHandler;
import org.uma.external.handler.JvLinkRealTimeHandler;
import org.uma.external.handler.JvLinkStoreHandler;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebFluxRoutesConfiguration {

    @Autowired
    private JvLinkStoreHandler jvLinkStoreHandler;

    @Autowired
    private JvLinkRealTimeHandler jvLinkRealTimeHandler;

    @Autowired
    private IPatGoHandler iPatGoHandler;


    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/"), this::ping)
                .and(jvLinkStoreHandler.routes())
                .and(jvLinkRealTimeHandler.routes())
                .and(iPatGoHandler.routes())
                .filter((request, next) -> {
                    // routes共通
                    try {
                        return next.handle(request);
                    } catch (Exception e) {
                        log.error("Server Error: ", e);
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                });
    }

    @NonNull
    private Mono<ServerResponse> ping(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromPublisher(Mono.just("OK"), String.class));
    }

}
