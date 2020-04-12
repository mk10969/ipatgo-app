package org.uma.external;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import reactor.core.publisher.Mono;

@Configuration
public class WebFluxRoutesConfiguration {


    @Autowired
    private JvLinkRealTimeHandler jvLinkRealTimeHandler;

    @Autowired
    private IPatGoHandler iPatGoHandler;


    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/"), this::ping)
                .and(jvLinkRealTimeHandler.routes())
                .and(iPatGoHandler.routes());
    }

    @NonNull
    private Mono<ServerResponse> ping(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromPublisher(Mono.just("OK"), String.class));
    }

}
