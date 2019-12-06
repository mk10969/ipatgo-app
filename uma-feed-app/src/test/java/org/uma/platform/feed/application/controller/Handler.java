//package org.uma.platform.feed.application.controller;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.lang.NonNull;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.ServerRequest;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.uma.platform.feed.application.repository.impl.JvStoredAncestryRepository;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//import static org.springframework.web.reactive.function.server.ServerResponse.ok;
//
//@Configuration
//public class Handler {
//
//    @Bean
//    public RouterFunction<ServerResponse> routes(JvStoredAncestryRepository repository) {
//        return route(GET("/"), this::hello)
//                .andRoute(GET("/Ancestry/{}"), );
//    }
//
//    @NonNull
//    private Mono<ServerResponse> hello(ServerRequest req) {
//        return ok().body(Flux.just("Hello", "World!"), String.class);
//    }
//
//
//}
