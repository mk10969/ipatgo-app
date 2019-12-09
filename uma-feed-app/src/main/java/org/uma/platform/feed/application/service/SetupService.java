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
import reactor.util.Logger;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("setup")
public class SetupService implements CommandLineRunner {

    @Value("${data.yyyyMMdd}")
    private String yyyyMMdd;

    private final ReactiveMongoTemplate reactiveTemplate;

//    private final MongoClient client;

    private final JvRaceService raceService;


    @Override
    public void run(String... args) throws Exception {

        Mono.just(yyyyMMdd)
                .doOnNext(i -> System.out.println("現在から" + i + "までの期間のセットアップを開始します。"))
                .map(DateUtil::of)
                .flatMap(this::setupRacingDetails)
                .subscribe(
                        i -> System.out.println("成功"),
                        e -> System.out.println("失敗"),
                        () -> System.out.println("セットアップを終わります。")
                );

    }

    private enum Status {
        WAIT, RUNNNIG, COMPLETE, ERROR
    }

    @SafeVarargs
    private final void executeAll(Function<LocalDateTime, Mono<Status>>... func) {
        LocalDateTime dateTime = DateUtil.of(yyyyMMdd);
        // 直列に実行する
        Stream.of(func).filter(Objects::nonNull)
                .forEach(function -> function.apply(dateTime).subscribe());
    }


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


    private <T> Flux<T> insertOnTransaction(Tuple2<T, String> tuples) {
        return reactiveTemplate
                .inTransaction()
                .execute(action -> action.insert(tuples.getT1(), tuples.getT2()))
                .log((Logger) log)
                .doOnError(e -> log.error(e.getMessage()));
    }

    private Mono<Status> setupRacingDetails(LocalDateTime dateTime) {
        return raceService.findRacingDetailsOnSetUp(dateTime)
                .buffer(5000)
                .map(chunk -> Tuples.of(chunk, "RacingDetails"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    private Mono<Status> setupHorseRacingDetails(LocalDateTime dateTime) {
        return raceService.findHorseRacingDetailsOnSetUp(dateTime)
                .buffer(2000)
                .map(chunk -> Tuples.of(chunk, "HorseRacingDetails"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    private Mono<Status> setupRaceRefund(LocalDateTime dateTime) {
        return raceService.findRaceRefundOnSetUp(dateTime)
                .buffer(5000)
                .map(chunk -> Tuples.of(chunk, "RaceRefund"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    private Mono<Status> setupVoteCount(LocalDateTime dateTime) {
        return raceService.findVoteCountOnSetUp(dateTime)
                .buffer(1000)
                .map(chunk -> Tuples.of(chunk, "VoteCount"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

}
