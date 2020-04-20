package org.uma.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
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
import org.uma.external.jvlink.JvLinkWatch;
import reactor.core.publisher.Mono;

import javax.annotation.PreDestroy;

@Slf4j
@Configuration
public class ExternalConfiguration {

    /////  HTTP Routes Configuration /////

    @Configuration
    public static class RoutesConfiguration {

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
                            // 500
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


    ///// WebSocket Watch Event Configuration /////

    @Configuration
    public static class WatchEventConfiguration {

        @Autowired
        private JvLinkWatch jvLinkWatch;

        @Bean
        @Profile(value = "prd")
        public CommandLineRunner commandLineRunner() {
            return args -> this.start();
        }

        private void start() {
            log.info("JvLink Watch Event start!!!");
            jvLinkWatch.start();
        }

        @PreDestroy
        void finish() {
            this.jvLinkWatch.stop();
        }
    }

}
