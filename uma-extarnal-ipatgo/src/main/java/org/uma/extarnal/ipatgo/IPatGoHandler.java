package org.uma.extarnal.ipatgo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.uma.extarnal.ipatgo.IPatGoConfiguration.CommandBuilder;
import org.uma.extarnal.ipatgo.IPatGoConfiguration.Mode;
import org.uma.extarnal.ipatgo.IPatGoConfiguration.TimeSeries;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class IPatGoHandler {

    private final IPatGoProperties iPatGoProperties;


    /**
     * IPATGOの機能一覧
     * （内部でhttp client使っているみたいやけど。httpなんよね。sちゃうっていうねw）
     * <p>
     * 1.【dataモード】 投票データ1件分を投票
     * 2.【fileモード】 投票ファイルから複数件一括投票 （全馬券式 / WIN5）
     * 3.【statモード】 購入状況照会
     * 4.【historyモード】 投票履歴照会 （当日分 / 前日分 / 最終受付分）
     * 5.【depositモード】 即パット入金指示
     * 6.【withdrawモード】 即パット出金指示
     */
    @Bean
    RouterFunction<ServerResponse> routes(IPatGoHandler iPatGoHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/"), iPatGoHandler::ping)
                .andRoute(RequestPredicates.POST("/data/{voteData}"), iPatGoHandler::vote)
                .andRoute(RequestPredicates.GET("/stat"), iPatGoHandler::stat)
                .andRoute(RequestPredicates.GET("/history"), iPatGoHandler::history)
                .andRoute(RequestPredicates.GET("/history/before"), iPatGoHandler::historyBefore)
                .andRoute(RequestPredicates.GET("/history/latest"), iPatGoHandler::historyLatest)
                .andRoute(RequestPredicates.GET("/deposit/{money}"), iPatGoHandler::deposit)
                .andRoute(RequestPredicates.GET("/withdraw"), iPatGoHandler::withdraw)
                ;
    }


    private static Mono<ServerResponse> errorHandle(Throwable throwable) {
        log.error("ERROR: ", throwable);
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(throwable.getMessage()));
    }

    private static Mono<ServerResponse> exitCodeHandle(Integer exitCode) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(exitCode));
    }

    @NonNull
    public Mono<ServerResponse> ping(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("OK"));
    }

    @NonNull
    public Mono<ServerResponse> vote(ServerRequest request) {
        String voteData = request.pathVariable("voteData");
        return Mono.fromFuture(IPatGoCommandRunner
                .execute(new CommandBuilder(iPatGoProperties)
                        .setMode(Mode.data)
                        .setArgument(voteData)
                        .setNoSplash()
                        .build()))
                .flatMap(IPatGoHandler::exitCodeHandle)
                .onErrorResume(IPatGoHandler::errorHandle);
    }

    @NonNull
    public Mono<ServerResponse> stat(ServerRequest request) {
        return Mono.fromFuture(IPatGoCommandRunner
                .execute(new CommandBuilder(iPatGoProperties)
                        .setMode(Mode.stat)
                        .build()))
                .flatMap(IPatGoHandler::exitCodeHandle)
                .onErrorResume(IPatGoHandler::errorHandle);
    }

    @NonNull
    public Mono<ServerResponse> history(ServerRequest request) {
        return Mono.fromFuture(IPatGoCommandRunner
                .execute(new CommandBuilder(iPatGoProperties)
                        .setMode(Mode.history)
                        .build()))
                .flatMap(IPatGoHandler::exitCodeHandle)
                .onErrorResume(IPatGoHandler::errorHandle);
    }

    @NonNull
    public Mono<ServerResponse> historyBefore(ServerRequest request) {
        return Mono.fromFuture(IPatGoCommandRunner
                .execute(new CommandBuilder(iPatGoProperties)
                        .setMode(Mode.history)
                        .setTimeSeries(TimeSeries.before)
                        .build()))
                .flatMap(IPatGoHandler::exitCodeHandle)
                .onErrorResume(IPatGoHandler::errorHandle);
    }

    @NonNull
    public Mono<ServerResponse> historyLatest(ServerRequest request) {
        return Mono.fromFuture(IPatGoCommandRunner
                .execute(new CommandBuilder(iPatGoProperties)
                        .setMode(Mode.history)
                        .setTimeSeries(TimeSeries.latest)
                        .build()))
                .flatMap(IPatGoHandler::exitCodeHandle)
                .onErrorResume(IPatGoHandler::errorHandle);
    }

    @NonNull
    public Mono<ServerResponse> deposit(ServerRequest request) {
        String money = request.pathVariable("money");
        return Mono.fromFuture(IPatGoCommandRunner
                .execute(new CommandBuilder(iPatGoProperties)
                        .setMode(Mode.deposit)
                        .setArgument(money)
                        .build()))
                .flatMap(IPatGoHandler::exitCodeHandle)
                .onErrorResume(IPatGoHandler::errorHandle);
    }

    @NonNull
    public Mono<ServerResponse> withdraw(ServerRequest request) {
        return Mono.fromFuture(IPatGoCommandRunner
                .execute(new CommandBuilder(iPatGoProperties)
                        .setMode(Mode.withdraw)
                        .build()))
                .flatMap(IPatGoHandler::exitCodeHandle)
                .onErrorResume(IPatGoHandler::errorHandle);
    }

}
