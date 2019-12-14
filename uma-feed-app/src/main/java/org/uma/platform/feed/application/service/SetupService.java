package org.uma.platform.feed.application.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import org.uma.platform.common.utils.lang.DateUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
@Profile("setup")
public class SetupService implements CommandLineRunner {

    @Value("${data.yyyyMMdd}")
    private String yyyyMMdd;

    @Value("${data.storedDataType}")
    private List<String> storedDataTypes;

    private final ReactiveMongoTemplate reactiveTemplate;

    private final JvRaceService raceService;

    private enum Status {RUNNNIG, COMPLETE, ERROR}

    @Override
    public void run(String... args) throws Exception {
        Mono.just(yyyyMMdd)
                .publishOn(Schedulers.elastic())
                .doOnNext(i -> log.info("現在から" +
                        i + "までの期間のセットアップを開始します。"))
                .map(DateUtil::of)
                .flatMap(this::setupRacingDetails)
                .flatMap(this::setupHorseRacingDetails)
                .flatMap(this::setupRaceRefund)
                .flatMap(this::setupVoteCount)
                .subscribe(
                        i -> log.info("成功"),
                        e -> log.error("失敗"),
                        () -> log.info("セットアップを終わります。")
                );
    }


//    private Mono<LocalDateTime> start() {
//        return Mono.just(yyyyMMdd)
//                .publishOn(Schedulers.elastic())
//                .doOnNext(i -> System.out.println("現在から" +
//                        i + "までの期間のセットアップを開始します。"))
//                .map(DateUtil::of)
//                .flatMap(dateTime -> Flux.fromIterable(storedDataTypes)
//                        .filter()
//                        .flatMap())
//
//    }


    private <T> Flux<T> insertOnTransaction(Tuple2<List<T>, String> tuples) {
        // 一つのドキュメントに対して、transaction別いらない。
        return reactiveTemplate
                .insert(tuples.getT1(), tuples.getT2())
//                .inTransaction()
//                .execute(action -> action.insert(tuples.getT1(), tuples.getT2()))
                .doOnError(e -> log.error("Setup ERROR: ", e));
    }


    private Mono<LocalDateTime> setupRacingDetails(LocalDateTime dateTime) {
        return raceService.findRacingDetailsOnSetUp(dateTime)
                .buffer(1000)
                .map(chunk -> Tuples.of(chunk, "RacingDetails"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(dateTime));
    }

    private Mono<LocalDateTime> setupHorseRacingDetails(LocalDateTime dateTime) {
        return raceService.findHorseRacingDetailsOnSetUp(dateTime)
                .buffer(1000)
                .map(chunk -> Tuples.of(chunk, "HorseRacingDetails"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(dateTime));
    }

    private Mono<LocalDateTime> setupRaceRefund(LocalDateTime dateTime) {
        return raceService.findRaceRefundOnSetUp(dateTime)
                .buffer(1000)
                .map(chunk -> Tuples.of(chunk, "RaceRefund"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(dateTime));
    }

    private Mono<LocalDateTime> setupVoteCount(LocalDateTime dateTime) {
        return raceService.findVoteCountOnSetUp(dateTime)
                .buffer(1000)
                .map(chunk -> Tuples.of(chunk, "VoteCount"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(dateTime));
    }


//    private final void executeAll(Function<LocalDateTime, Mono<Status>>... func) {
//        LocalDateTime dateTime = DateUtil.of(yyyyMMdd);
//        // 直列に実行する
//        Stream.of(func).filter(Objects::nonNull)
//                .forEach(function -> function.apply(dateTime).subscribe());
//    }


//    private void insertOnTransaction2() {
//        Mono.from(client.startSession()).flatMap(session -> {
//            session.startTransaction();
//            return Mono.from(collection.insertOne(session, documentOne))
//                    .then(Mono.from(collection.insertOne(session, documentTwo)))
//                    .onErrorResume(e -> Mono.from(session.abortTransaction())
//                            .then(Mono.error(e)))
//                    .flatMap(val -> Mono.from(session.commitTransaction())
//                            .then(Mono.just(val)))
//                    .doFinally(signal -> session.close());
//        });
//    }

}
