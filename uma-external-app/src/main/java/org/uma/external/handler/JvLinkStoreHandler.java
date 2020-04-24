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
import org.uma.external.jvlink.config.option.Option;
import org.uma.external.jvlink.util.DateUtil;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.uma.external.jvlink.property.JvStored.BLOD_BT;
import static org.uma.external.jvlink.property.JvStored.BLOD_HN;
import static org.uma.external.jvlink.property.JvStored.BLOD_SK;
import static org.uma.external.jvlink.property.JvStored.COMM_CS;
import static org.uma.external.jvlink.property.JvStored.DIFF_BN;
import static org.uma.external.jvlink.property.JvStored.DIFF_BR;
import static org.uma.external.jvlink.property.JvStored.DIFF_CH;
import static org.uma.external.jvlink.property.JvStored.DIFF_KS;
import static org.uma.external.jvlink.property.JvStored.DIFF_UM;
import static org.uma.external.jvlink.property.JvStored.RACE_H1;
import static org.uma.external.jvlink.property.JvStored.RACE_HR;
import static org.uma.external.jvlink.property.JvStored.RACE_JG;
import static org.uma.external.jvlink.property.JvStored.RACE_O1;
import static org.uma.external.jvlink.property.JvStored.RACE_O2;
import static org.uma.external.jvlink.property.JvStored.RACE_O3;
import static org.uma.external.jvlink.property.JvStored.RACE_O4;
import static org.uma.external.jvlink.property.JvStored.RACE_O5;
import static org.uma.external.jvlink.property.JvStored.RACE_O6;
import static org.uma.external.jvlink.property.JvStored.RACE_RA;
import static org.uma.external.jvlink.property.JvStored.RACE_SE;

@Slf4j
@Component
public class JvLinkStoreHandler extends BaseHandler {

    private static final String EPOCH_SECOND = "epochSecond";


    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                // racing
                .route(RequestPredicates.GET("/racingDetail/{epochSecond}"), this::racingDetail)
                .andRoute(RequestPredicates.GET("/racingHorseDetail/{epochSecond}"), this::racingHorseDetail)
                .andRoute(RequestPredicates.GET("/racingRefund/{epochSecond}"), this::racingRefund)
                .andRoute(RequestPredicates.GET("/racingVote/{epochSecond}"), this::racingVote)
                .andRoute(RequestPredicates.GET("/racingHorseExclusion/{epochSecond}"), this::racingHorseExclusion)
                // odds
                .andRoute(RequestPredicates.GET("/winsShowBracketQ/{epochSecond}"), this::winsShowBracketQ)
                .andRoute(RequestPredicates.GET("/quinella/{epochSecond}"), this::quinella)
                .andRoute(RequestPredicates.GET("/quinellaPlace/{epochSecond}"), this::quinellaPlace)
                .andRoute(RequestPredicates.GET("/exacta/{epochSecond}"), this::exacta)
                .andRoute(RequestPredicates.GET("/trio/{epochSecond}"), this::trio)
                .andRoute(RequestPredicates.GET("/trifecta/{epochSecond}"), this::trifecta)
                // blood
                .andRoute(RequestPredicates.GET("/bloodAncestry/{epochSecond}"), this::bloodAncestry)
                .andRoute(RequestPredicates.GET("/bloodBreeding/{epochSecond}"), this::bloodBreeding)
                .andRoute(RequestPredicates.GET("/bloodLine/{epochSecond}"), this::bloodLine)
                // uma
                .andRoute(RequestPredicates.GET("/raceHorse/{epochSecond}"), this::raceHorse)
                .andRoute(RequestPredicates.GET("/jockey/{epochSecond}"), this::jockey)
                .andRoute(RequestPredicates.GET("/trainer/{epochSecond}"), this::trainer)
                .andRoute(RequestPredicates.GET("/owner/{epochSecond}"), this::owner)
                .andRoute(RequestPredicates.GET("/breeder/{epochSecond}"), this::breeder)
                .andRoute(RequestPredicates.GET("/course/{epochSecond}"), this::course)
//               @Deprecated
//                .andRoute(RequestPredicates.GET("/racingDetails/thisWeek"), this::racingDetailsOnThisWeek)
//                .andRoute(RequestPredicates.GET("/horseRacingDetails/thisWeek"), this::horseRacingDetailsOnThisWeek)
                .filter(JvLinkStoreHandler::JvLinkStoreErrorHandle)
                .filter(BaseHandler::jvLinkErrorHandle);
    }

    @NonNull
    private static Mono<ServerResponse> JvLinkStoreErrorHandle(
            ServerRequest request, HandlerFunction<ServerResponse> next) {
        try {
            return next.handle(request);

        } catch (IllegalArgumentException e) {
            log.error("Client Error: ", e);
            return errorPublisher(HttpStatus.BAD_REQUEST, "データ取得基準日を正しく設定してください。");
        }
    }


    /**
     * @throws NumberFormatException    parse long.
     * @throws IllegalArgumentException toLocalDateTime.
     */
    private static LocalDateTime Validate(String pathValue) {
        return DateUtil.toLocalDateTime(Long.parseLong(pathValue));
    }

//    @Deprecated
//    private Mono<ServerResponse> racingDetailsOnThisWeek(ServerRequest request) {
//        LocalDateTime lastWeek = DateUtil.lastWeek();
//        return okFlux(() -> JvLinkClient.readLines(RACE_RA.get(), lastWeek, Option.THIS_WEEK));
//    }
//
//    @Deprecated
//    private Mono<ServerResponse> horseRacingDetailsOnThisWeek(ServerRequest request) {
//        LocalDateTime lastWeek = DateUtil.lastWeek();
//        return okFlux(() -> JvLinkClient.readLines(RACE_SE.get(), lastWeek, Option.THIS_WEEK));
//    }

    private Mono<ServerResponse> racingDetail(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_RA.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> racingHorseDetail(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_SE.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> racingRefund(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_HR.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> racingVote(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_H1.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> racingHorseExclusion(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_JG.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> winsShowBracketQ(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_O1.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> quinella(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_O2.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> quinellaPlace(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_O3.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> exacta(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_O4.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> trio(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_O5.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> trifecta(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(RACE_O6.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> bloodAncestry(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(BLOD_BT.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> bloodBreeding(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(BLOD_HN.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> bloodLine(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(BLOD_SK.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> raceHorse(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(DIFF_UM.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> jockey(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(DIFF_KS.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> trainer(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(DIFF_CH.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> owner(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(DIFF_BN.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> breeder(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(DIFF_BR.get(), baseDate, Option.STANDARD));
    }

    private Mono<ServerResponse> course(ServerRequest request) {
        LocalDateTime baseDate = Validate(request.pathVariable(EPOCH_SECOND));
        return okFlux(() -> JvLinkClient.readLines(COMM_CS.get(), baseDate, Option.STANDARD));
    }

}
