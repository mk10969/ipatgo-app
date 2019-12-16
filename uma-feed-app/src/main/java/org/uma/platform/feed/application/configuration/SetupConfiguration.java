//package org.uma.platform.feed.application.configuration;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
//import org.springframework.stereotype.Component;
//import org.uma.platform.feed.application.service.JvBloodService;
//import org.uma.platform.feed.application.service.JvCommonService;
//import org.uma.platform.feed.application.service.JvDiffService;
//import org.uma.platform.feed.application.service.JvRaceService;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.util.function.Tuple2;
//import reactor.util.function.Tuples;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.function.Function;
//
//
//@Component
//@Configuration
//@RequiredArgsConstructor
//@Profile("setup")
//public class SetupConfiguration {
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
//    @Bean("RacingDetails")
//    public Function<LocalDateTime, Mono<Status>> setupRacingDetails() {
//        return dateTime -> raceService.findRacingDetailsOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "RacingDetails"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("HorseRacingDetails")
//    public Function<LocalDateTime, Mono<Status>> setupHorseRacingDetails() {
//        return dateTime -> raceService.findHorseRacingDetailsOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "HorseRacingDetails"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("RaceRefund")
//    public Function<LocalDateTime, Mono<Status>> setupRaceRefund() {
//        return dateTime -> raceService.findRaceRefundOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "RaceRefund"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("VoteCount")
//    public Function<LocalDateTime, Mono<Status>> setupVoteCount() {
//        return dateTime -> raceService.findVoteCountOnSetUp(dateTime)
//                .buffer(100)
//                .map(chunk -> Tuples.of(chunk, "VoteCount"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("Ancestry")
//    public Function<LocalDateTime, Mono<Status>> setupAncestry() {
//        return dateTime -> bloodService.findAncestryOnSetUp(dateTime)
//                .buffer(200)
//                .map(chunk -> Tuples.of(chunk, "Ancestry"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("BreedingHorse")
//    public Function<LocalDateTime, Mono<Status>> setupBreedingHorse() {
//        return dateTime -> bloodService.findBreedingHorseOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "BreedingHorse"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("Offspring")
//    public Function<LocalDateTime, Mono<Status>> setupOffspring() {
//        return dateTime -> bloodService.findOffspringOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Offspring"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("Course")
//    public Function<LocalDateTime, Mono<Status>> setupCourse() {
//        return dateTime -> commonService.findCourseOnSetUp(dateTime)
//                .buffer(200)
//                .map(chunk -> Tuples.of(chunk, "Course"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("RaceHorse")
//    public Function<LocalDateTime, Mono<Status>> setupRaceHorse() {
//        return dateTime -> diffService.findRaceHorseOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "RaceHorse"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("Jockey")
//    public Function<LocalDateTime, Mono<Status>> setupJockey() {
//        return dateTime -> diffService.findJockeyOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Jockey"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("Trainer")
//    public Function<LocalDateTime, Mono<Status>> setupTrainer() {
//        return dateTime -> diffService.findTrainerOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Trainer"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("Owner")
//    public Function<LocalDateTime, Mono<Status>> setupOwner() {
//        return dateTime -> diffService.findOwnerOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Owner"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
//    @Bean("Breeder")
//    public Function<LocalDateTime, Mono<Status>> setupBreeder() {
//        return dateTime -> diffService.findBreederOnSetUp(dateTime)
//                .buffer(500)
//                .map(chunk -> Tuples.of(chunk, "Breeder"))
//                .flatMap(this::insertOnTransaction)
//                .then(Mono.just(Status.COMPLETE));
//    }
//
////    private void insertOnTransaction2() {
////        Mono.from(client.startSession()).flatMap(session -> {
////            session.startTransaction();
////            return Mono.from(collection.insertOne(session, documentOne))
////                    .then(Mono.from(collection.insertOne(session, documentTwo)))
////                    .onErrorResume(e -> Mono.from(session.abortTransaction())
////                            .then(Mono.error(e)))
////                    .flatMap(val -> Mono.from(session.commitTransaction())
////                            .then(Mono.just(val)))
////                    .doFinally(signal -> session.close());
////        });
////    }
//
//}