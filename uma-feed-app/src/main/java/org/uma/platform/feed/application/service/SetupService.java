//package org.uma.platform.feed.application.service;
//
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.stereotype.Service;
//import org.uma.platform.feed.application.configuration.SetupConfiguration;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.util.function.Tuple2;
//import reactor.util.function.Tuples;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.function.Function;
//
//
//@Slf4j
//@Service("SETUP")
//@RequiredArgsConstructor
//@Profile("setup")
//public class SetupService implements CommandLineRunner {
//
//    @Value("${setup.property.setupDataTypes}")
//    private Set<String> setupDataTypes;
//
//    @Value("${setup.property.yyyyMMddHHmmss}")
//    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//    private LocalDateTime dateTime;
//
//    private final Map<String, Function<LocalDateTime, Mono<SetupConfiguration.Status>>> runners;
//
//    private final Function<LocalDateTime, Mono<SetupConfiguration.Status>> empty = dateTime ->
//            Mono.just(SetupConfiguration.Status.EMPTY);
//
//
////    private void execute2() {
////        runners.entrySet().stream()
////                .peek(runner -> log.info("セットアップ開始 {}", runner.getKey()))
////                .peek(runner -> log.info("期間：現在から、{}まで", dateTime))
////                .filter(i -> setupDataTypes.contains(i.getKey()))
////                .peek(runner -> log.info("{}:{}", runner.getKey(), Status.RUNNING))
////                .forEach(runner -> runner.getValue()
////                        .apply(dateTime)
////                        .subscribe(
////                                status -> log.info("{}:{}", runner.getKey(), status),
////                                e -> log.error("ERROR {}:{}", runner.getKey(), e),
////                                () -> log.info("セットアップ終了 {}", runner.getKey())
////                        )
////                );
////    }
//
//
//    @Override
//    public void run(String... args) throws Exception {
////        execute();
//
//        Flux.fromIterable(setupDataTypes)
//                .doOnNext(dataType ->
//                        log.info("セットアップ開始"))
//                .doOnNext(dataType ->
//                        log.info("データ種別：{} 期間：現在から、{}まで", dataType, dateTime))
//                .map(dateType -> runners.getOrDefault(dateType, empty)
//                        .apply(dateTime)
//                        .map(status -> Tuples.of(dateType, status)))
//                .subscribe(
//                        i -> log.info("{}", i),
//                        e -> log.error("SETUP ERROR:", e),
//                        () -> log.info("セットアップ終了")
//                );
//
//    }
//
//    private void execute() {
//        Mono.just(Status.RUNNING)
//                .doOnNext(i -> log.info("セットアップ開始"))
//                .doOnNext(i -> log.info("期間：現在から、{}まで", dateTime))
////                .doOnNext(i -> log.info("setup RacingDetails {}", Status.RUNNING))
////                .flatMap(this::setupRacingDetails)
////                .doOnNext(i -> log.info("setup HorseRacingDetails {}", Status.RUNNING))
////                .flatMap(this::setupHorseRacingDetails)
////                .doOnNext(i -> log.info("setup RaceRefund {}", Status.RUNNING))
////                .flatMap(this::setupRaceRefund)
////                .doOnNext(i -> log.info("setup VoteCount {}", Status.RUNNING))
////                .flatMap(this::setupVoteCount)
////                .doOnNext(i -> log.info("setup Ancestry {}", Status.RUNNING))
////                .flatMap(this::setupAncestry)
////                .doOnNext(i -> log.info("setup BreedingHorse {}", Status.RUNNING))
////                .flatMap(this::setupBreedingHorse)
////                .doOnNext(i -> log.info("setup Offspring {}", Status.RUNNING))
////                .flatMap(this::setupOffspring)
////                .doOnNext(i -> log.info("setup Course {}", Status.RUNNING))
////                .flatMap(this::setupCourse)
//                .doOnNext(i -> log.info("setup RaceHorse {}", Status.RUNNING))
//                .flatMap(this::setupRaceHorse)
//                .doOnNext(i -> log.info("setup Jockey {}", Status.RUNNING))
//                .flatMap(this::setupJockey)
//                .doOnNext(i -> log.info("setup Trainer {}", Status.RUNNING))
//                .flatMap(this::setupTrainer)
//                .doOnNext(i -> log.info("setup Owner {}", Status.RUNNING))
//                .flatMap(this::setupOwner)
//                .doOnNext(i -> log.info("setup Breeder {}", Status.RUNNING))
//                .flatMap(this::setupBreeder)
//                .subscribe(
//                        i -> log.info("{}", i),
//                        e -> log.error("SETUP ERROR:", e),
//                        () -> log.info("セットアップ終了")
//                );
//    }
//
//
//    private final ReactiveMongoTemplate reactiveTemplate;
//
//    private final JvRaceService raceService;
//
//    private final JvBloodService bloodService;
//
//    private final JvCommonService commonService;
//
//    private final JvDiffService diffService;
//
//    public enum Status {RUNNING, COMPLETE, ERROR, EMPTY}
//
//
//    private <T> Flux<T> insertOnTransaction(Tuple2<List<T>, String> tuples) {
//        // 一つのドキュメントに対して、transaction別いらない。
//        return reactiveTemplate
//                .insert(tuples.getT1(), tuples.getT2());
////                .inTransaction()
////                .execute(action -> action.insert(tuples.getT1(), tuples.getT2()))
//    }
//
//    public Mono<Status> setupRacingDetails(Status status) {
//        return raceService.findRacingDetailsOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "RacingDetails"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupHorseRacingDetails(Status status) {
//        return raceService.findHorseRacingDetailsOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "HorseRacingDetails"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupRaceRefund(Status status) {
//        return raceService.findRaceRefundOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "RaceRefund"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupVoteCount(Status status) {
//        return raceService.findVoteCountOnSetUp(dateTime)
//                .buffer(100)
//                .map(chunk -> Tuples.of(chunk, "VoteCount"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupAncestry(Status status) {
//        return bloodService.findAncestryOnSetUp(dateTime)
//                .buffer(200)
//                .map(chunk -> Tuples.of(chunk, "Ancestry"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupBreedingHorse(Status status) {
//        return bloodService.findBreedingHorseOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "BreedingHorse"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupOffspring(Status status) {
//        return bloodService.findOffspringOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Offspring"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupCourse(Status status) {
//        return commonService.findCourseOnSetUp(dateTime)
//                .buffer(200)
//                .map(chunk -> Tuples.of(chunk, "Course"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupRaceHorse(Status status) {
//        return diffService.findRaceHorseOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "RaceHorse"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupJockey(Status status) {
//        return diffService.findJockeyOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Jockey"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupTrainer(Status status) {
//        return diffService.findTrainerOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Trainer"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupOwner(Status status) {
//        return diffService.findOwnerOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Owner"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    public Mono<Status> setupBreeder(Status status) {
//        return diffService.findBreederOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Breeder"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//}
