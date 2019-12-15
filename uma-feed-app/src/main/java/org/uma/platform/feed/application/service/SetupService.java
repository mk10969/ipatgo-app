package org.uma.platform.feed.application.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
@Component
@RequiredArgsConstructor
@Profile("setup")
public class SetupService implements CommandLineRunner {

    @Value("${setup.property.yyyyMMddHHmmss}")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime dateTime;

    @Value("${setup.property.setupDataTypes}")
    private Set<String> setupDataTypes;

    private final Map<String, Mono<Status>> runners;

    private final ReactiveMongoTemplate reactiveTemplate;

    private final JvRaceService raceService;

    private final JvBloodService bloodService;

    private final JvCommonService commonService;

    private final JvDiffService diffService;

    private enum Status {RUNNING, COMPLETE, ERROR}

    @Override
    public void run(String... args) throws Exception {
        execute();
//        Mono.just(yyyyMMdd)
//                .publishOn(Schedulers.elastic())
//                .doOnNext(i -> log.info("現在から" +
//                        i + "までの期間のセットアップを開始します。"))
//                .flatMap(this::setupRacingDetails)
//                .flatMap(this::setupHorseRacingDetails)
//                .flatMap(this::setupRaceRefund)
//                .flatMap(this::setupVoteCount)
//                .subscribe(
//                        i -> log.info("成功"),
//                        e -> log.error("SETUP ERROR: ", e),
//                        () -> log.info("セットアップを終わります。")
//                );
    }


    private void execute() {
        Flux.fromIterable(setupDataTypes)
                .doOnNext(dataType -> log.info("セットアップ開始"))
                .doOnNext(dataType -> log.info("データ種別：{} 期間：現在から、{}まで", dataType, dateTime))
                .flatMap(dateType -> runners.getOrDefault(dateType, Mono.empty())
                        .map(status -> Tuples.of(dateType, status)))
                .subscribe(
                        i -> log.info("{}", i),
                        e -> log.error("SETUP ERROR:", e),
                        () -> log.info("セットアップ終了")
                );
    }

//    // こっちの方が、良さそう。
//    private final void streamVersion() {
//        storedDataTypes.stream()
//                .peek(dataType -> log.info("セットアップ開始"))
//                .peek(dataType -> log.info("データ種別：{} 期間：現在から、{}まで", dataType, dateTime))
//                .flatMap(dataType -> runners.entrySet()
//                        .stream()
//                        .filter(key -> key.getKey().equals(dataType))
//                        .map(key -> key.getValue())
//                );
//    }


    private <T> Flux<T> insertOnTransaction(Tuple2<List<T>, String> tuples) {
        // 一つのドキュメントに対して、transaction別いらない。
        return reactiveTemplate
                .insert(tuples.getT1(), tuples.getT2());
//                .inTransaction()
//                .execute(action -> action.insert(tuples.getT1(), tuples.getT2()))
    }

    @Bean("RacingDetails")
    private Mono<Status> setupRacingDetails() {
        return raceService.findRacingDetailsOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "RacingDetails"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("HorseRacingDetails")
    private Mono<Status> setupHorseRacingDetails() {
        return raceService.findHorseRacingDetailsOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "HorseRacingDetails"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("RaceRefund")
    private Mono<Status> setupRaceRefund() {
        return raceService.findRaceRefundOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "RaceRefund"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("VoteCount")
    private Mono<Status> setupVoteCount() {
        return raceService.findVoteCountOnSetUp(dateTime)
                .buffer(100)
                .map(chunk -> Tuples.of(chunk, "VoteCount"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("Ancestry")
    private Mono<Status> setupAncestry() {
        return bloodService.findAncestryOnSetUp(dateTime)
                .buffer(200)
                .map(chunk -> Tuples.of(chunk, "Ancestry"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("BreedingHorse")
    private Mono<Status> setupBreedingHorse() {
        return bloodService.findBreedingHorseOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "BreedingHorse"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("Offspring")
    private Mono<Status> setupOffspring() {
        return bloodService.findOffspringOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "Offspring"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("Course")
    private Mono<Status> setupCourse() {
        return commonService.findCourseOnSetUp(dateTime)
                .buffer(200)
                .map(chunk -> Tuples.of(chunk, "Course"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("RaceHorse")
    private Mono<Status> setupRaceHorse() {
        return diffService.findRaceHorseOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "RaceHorse"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("Jockey")
    private Mono<Status> setupJockey() {
        return diffService.findJockeyOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "Jockey"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("Trainer")
    private Mono<Status> setupTrainer() {
        return diffService.findTrainerOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "Trainer"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("Owner")
    private Mono<Status> setupOwner() {
        return diffService.findOwnerOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "Owner"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
    }

    @Bean("Breeder")
    private Mono<Status> setupBreeder() {
        return diffService.findBreederOnSetUp(dateTime)
                .buffer(500)
                .map(chunk -> Tuples.of(chunk, "Breeder"))
                .flatMap(this::insertOnTransaction)
                .then(Mono.just(Status.COMPLETE));
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
