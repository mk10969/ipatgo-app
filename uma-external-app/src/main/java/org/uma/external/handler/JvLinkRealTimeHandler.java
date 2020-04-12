package org.uma.external.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.uma.external.jvlink.JvLinkClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

import static org.uma.external.jvlink.property.JvRealTime.OB15_HR;
import static org.uma.external.jvlink.property.JvRealTime.OB15_RA;
import static org.uma.external.jvlink.property.JvRealTime.OB15_SE;
import static org.uma.external.jvlink.property.JvRealTime.OB20_H1;
import static org.uma.external.jvlink.property.JvRealTime.OB31_O1;
import static org.uma.external.jvlink.property.JvRealTime.OB32_O2;
import static org.uma.external.jvlink.property.JvRealTime.OB33_O3;
import static org.uma.external.jvlink.property.JvRealTime.OB34_O4;
import static org.uma.external.jvlink.property.JvRealTime.OB35_O5;
import static org.uma.external.jvlink.property.JvRealTime.OB36_O6;
import static org.uma.external.jvlink.property.JvRealTime.OB41_O1;
import static org.uma.external.jvlink.property.JvRealTime.OB42_O2;

@Slf4j
@Component
public class JvLinkRealTimeHandler extends BaseHandler {

    private static final String RACE_ID = "raceId";


    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route(RequestPredicates.GET("/racingDetails"), this::racingDetails)
                .andRoute(RequestPredicates.GET("/raceRefund"), this::raceRefund)
                .andRoute(RequestPredicates.GET("/voteCount"), this::voteCount)
                .andRoute(RequestPredicates.GET("/winsPlaceBracketQuinella"), this::winsPlaceBracketQuinella)
                .andRoute(RequestPredicates.GET("/quinella"), this::quinella)
                .andRoute(RequestPredicates.GET("/quinellaPlace"), this::quinellaPlace)
                .andRoute(RequestPredicates.GET("/exacta"), this::exacta)
                .andRoute(RequestPredicates.GET("/trio"), this::trio)
                .andRoute(RequestPredicates.GET("/trifecta"), this::trifecta)
                .andRoute(RequestPredicates.GET("/horseRacingDetails"), this::horseRacingDetails)
                .andRoute(RequestPredicates.GET("/timeseries/winsPlaceBracketQuinella"), this::timeseriesWinsPlaceBracketQuinella)
                .andRoute(RequestPredicates.GET("/timeseries/quinella"), this::timeseriesQuinella)
                .filter(JvLinkRealTimeHandler::JvLinkRealTimeErrorHandle)
                .filter(BaseHandler::jvLinkErrorHandle);
    }


    @NonNull
    private static Mono<ServerResponse> JvLinkRealTimeErrorHandle(
            ServerRequest request, HandlerFunction<ServerResponse> next) {
        try {
            return next.handle(request);

        } catch (NoSuchElementException e) {
            log.error("Client Error: ", e);
            return errorPublisher(HttpStatus.BAD_REQUEST, "raceIdを指定してください。");
        }
    }

    @NonNull
    private Mono<ServerResponse> racingDetails(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB15_RA.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> raceRefund(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB15_HR.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> voteCount(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB20_H1.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> winsPlaceBracketQuinella(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB31_O1.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> quinella(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB32_O2.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> quinellaPlace(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB33_O3.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> exacta(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB34_O4.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> trio(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB35_O5.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> trifecta(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB36_O6.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> horseRacingDetails(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB15_SE.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> timeseriesWinsPlaceBracketQuinella(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB41_O1.get(), () -> raceId));
    }

    @NonNull
    private Mono<ServerResponse> timeseriesQuinella(ServerRequest request) {
        String raceId = request.queryParam(RACE_ID).orElseThrow(NoSuchElementException::new);
        return okPublisher(() ->
                JvLinkClient.readLines(OB42_O2.get(), () -> raceId));
    }

}
