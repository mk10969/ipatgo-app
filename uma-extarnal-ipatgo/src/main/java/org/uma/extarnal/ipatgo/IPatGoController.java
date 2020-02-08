package org.uma.extarnal.ipatgo;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.uma.extarnal.ipatgo.IPatGoConfiguration.Builder;
import org.uma.extarnal.ipatgo.IPatGoConfiguration.Mode;
import org.uma.extarnal.ipatgo.IPatGoConfiguration.TimeSeries;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class IPatGoController {

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


    private final IPatGoProperties iPatGoProperties;

    private static Mono<ServerResponse> errorHandle(Throwable throwable) {
        log.error("ERROR: ", throwable);
        return ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue(throwable.getMessage()));
    }

    private static Mono<ServerResponse> exitCodeHandle(Integer exitCode) {
        if (exitCode == 0) {
            return ServerResponse.ok().build();
        } else {
            log.error("ERROR: exitCode [{}]", exitCode);
            return ServerResponse
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }


    @GetMapping("/data/{voteData}")
    public Mono<ServerResponse> vote(@Validated @PathVariable String voteData) {
        List<String> command = new Builder(iPatGoProperties)
                .setMode(Mode.data)
                .setArgument(voteData)
                .setNoSplash()
                .build();

        return Mono.fromFuture(IPatGoCommandRunner.execute(command))
                .flatMap(IPatGoController::exitCodeHandle)
                .onErrorResume(IPatGoController::errorHandle);
    }

    @GetMapping("/stat")
    public Mono<ServerResponse> stat() {
        List<String> command = new Builder(iPatGoProperties)
                .setMode(Mode.stat)
                .build();

        return Mono.fromFuture(IPatGoCommandRunner.execute(command))
                .flatMap(IPatGoController::exitCodeHandle)
                .onErrorResume(IPatGoController::errorHandle);
    }

    @GetMapping("/history")
    public Mono<ServerResponse> history() {
        List<String> command = new Builder(iPatGoProperties)
                .setMode(Mode.history)
                .build();

        return Mono.fromFuture(IPatGoCommandRunner.execute(command))
                .flatMap(IPatGoController::exitCodeHandle)
                .onErrorResume(IPatGoController::errorHandle);
    }

    @GetMapping("/history/before")
    public Mono<ServerResponse> historyBefore() {
        List<String> command = new Builder(iPatGoProperties)
                .setMode(Mode.history)
                .setTimeSeries(TimeSeries.before)
                .build();

        return Mono.fromFuture(IPatGoCommandRunner.execute(command))
                .flatMap(IPatGoController::exitCodeHandle)
                .onErrorResume(IPatGoController::errorHandle);
    }


    @GetMapping("/history/latest")
    public Mono<ServerResponse> historyLatest() {
        List<String> command = new Builder(iPatGoProperties)
                .setMode(Mode.history)
                .setTimeSeries(TimeSeries.latest)
                .build();

        return Mono.fromFuture(IPatGoCommandRunner.execute(command))
                .flatMap(IPatGoController::exitCodeHandle)
                .onErrorResume(IPatGoController::errorHandle);
    }

    @GetMapping("/deposit/{money}")
    public Mono<ServerResponse> deposit(@Validated @PathVariable int money) {
        List<String> command = new Builder(iPatGoProperties)
                .setMode(Mode.deposit)
                .setArgument(String.valueOf(money))
                .build();

        return Mono.fromFuture(IPatGoCommandRunner.execute(command))
                .flatMap(IPatGoController::exitCodeHandle)
                .onErrorResume(IPatGoController::errorHandle);
    }

    @GetMapping("/withdraw")
    public Mono<ServerResponse> withdraw() {
        List<String> command = new Builder(iPatGoProperties)
                .setMode(Mode.withdraw)
                .build();

        return Mono.fromFuture(IPatGoCommandRunner.execute(command))
                .flatMap(IPatGoController::exitCodeHandle)
                .onErrorResume(IPatGoController::errorHandle);
    }

}
