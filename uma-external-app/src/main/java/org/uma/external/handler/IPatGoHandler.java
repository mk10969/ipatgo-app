package org.uma.external.handler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.uma.external.ipatgo.IPatGoClient;
import org.uma.external.ipatgo.IPatGoException;
import org.uma.external.ipatgo.IPatGoExe;
import org.uma.external.ipatgo.IPatGoProperties;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@EnableConfigurationProperties(IPatGoProperties.class)
@RequiredArgsConstructor
public class IPatGoHandler extends BaseHandler {

    private static final String VOTE_DATA = "voteData";

    private static final String MONEY = "money";


    private final IPatGoProperties iPatGoProperties;

    /**
     * IPATGOの機能一覧
     * （内部でhttp client使っているみたいやけど。httpなんよね。sちゃうっていうねw）
     * <p>
     * 1.【dataモード】 投票データ1件分を投票 TODO:入力チェック
     * 2.【fileモード】 投票ファイルから複数件一括投票 （全馬券式 / WIN5）
     * 3.【statモード】 購入状況照会
     * 4.【historyモード】 投票履歴照会 （当日分 / 前日分 / 最終受付分）
     * 5.【depositモード】 即パット入金指示 TODO:入力チェック
     * 6.【withdrawモード】 即パット出金指示
     * <p>
     * <p>
     * statとhistoryは、ファイルできるらしいから
     * ファイルができたら、そのファイルを丸ごと送りつけるか！
     */
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions
                .route(RequestPredicates.POST("/data/{voteData}"), this::vote)
                .andRoute(RequestPredicates.GET("/stat"), this::stat)
                .andRoute(RequestPredicates.GET("/history"), this::history)
                .andRoute(RequestPredicates.GET("/history/before"), this::historyBefore)
                .andRoute(RequestPredicates.GET("/history/latest"), this::historyLatest)
                .andRoute(RequestPredicates.POST("/deposit/{money}"), this::deposit)
                .andRoute(RequestPredicates.POST("/withdraw"), this::withdraw)
                .filter(IPatGoHandler::iPatGoErrorHandle);
    }


    @NonNull
    private static Mono<ServerResponse> iPatGoErrorHandle(
            ServerRequest request, HandlerFunction<ServerResponse> next) {
        try {
            return next.handle(request);

        } catch (IPatGoException e) {
            log.error("IPatGo Error: ", e);
            return errorPublisher(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
    }

    @NonNull
    private static Mono<ServerResponse> okIPatGoPublisher(Integer exitCode) {
        Mono<ExternalResponse> mono = Mono.just(ExternalResponse.builder()
                .data(String.valueOf(exitCode)).message(OK).build());

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(mono, ExternalResponse.class));
    }

    @NonNull
    private Mono<ServerResponse> vote(ServerRequest request) {
        String voteData = request.pathVariable(VOTE_DATA);
        return Mono.fromFuture(IPatGoClient.execute(
                new IPatGoExe.CommandBuilder(iPatGoProperties)
                        .setMode(IPatGoExe.Mode.data)
                        .setArgument(voteData)
                        .setNoSplash()
                        .build()))
                .flatMap(IPatGoHandler::okIPatGoPublisher);
    }

    @NonNull
    private Mono<ServerResponse> stat(ServerRequest request) {
        return Mono.fromFuture(IPatGoClient.execute(
                new IPatGoExe.CommandBuilder(iPatGoProperties)
                        .setMode(IPatGoExe.Mode.stat)
                        .build()))
                .flatMap(IPatGoHandler::okIPatGoPublisher);
    }

    @NonNull
    private Mono<ServerResponse> history(ServerRequest request) {
        return Mono.fromFuture(IPatGoClient.execute(
                new IPatGoExe.CommandBuilder(iPatGoProperties)
                        .setMode(IPatGoExe.Mode.history)
                        .build()))
                .flatMap(IPatGoHandler::okIPatGoPublisher);
    }

    @NonNull
    private Mono<ServerResponse> historyBefore(ServerRequest request) {
        return Mono.fromFuture(IPatGoClient.execute(
                new IPatGoExe.CommandBuilder(iPatGoProperties)
                        .setMode(IPatGoExe.Mode.history)
                        .setTimeSeries(IPatGoExe.TimeSeries.before)
                        .build()))
                .flatMap(IPatGoHandler::okIPatGoPublisher);
    }

    @NonNull
    private Mono<ServerResponse> historyLatest(ServerRequest request) {
        return Mono.fromFuture(IPatGoClient.execute(
                new IPatGoExe.CommandBuilder(iPatGoProperties)
                        .setMode(IPatGoExe.Mode.history)
                        .setTimeSeries(IPatGoExe.TimeSeries.latest)
                        .build()))
                .flatMap(IPatGoHandler::okIPatGoPublisher);
    }

    @NonNull
    private Mono<ServerResponse> deposit(ServerRequest request) {
        String money = request.pathVariable(MONEY);
        return Mono.fromFuture(IPatGoClient.execute(
                new IPatGoExe.CommandBuilder(iPatGoProperties)
                        .setMode(IPatGoExe.Mode.deposit)
                        .setArgument(money)
                        .build()))
                .flatMap(IPatGoHandler::okIPatGoPublisher);
    }

    @NonNull
    private Mono<ServerResponse> withdraw(ServerRequest request) {
        return Mono.fromFuture(IPatGoClient.execute(
                new IPatGoExe.CommandBuilder(iPatGoProperties)
                        .setMode(IPatGoExe.Mode.withdraw)
                        .build()))
                .flatMap(IPatGoHandler::okIPatGoPublisher);
    }

}
