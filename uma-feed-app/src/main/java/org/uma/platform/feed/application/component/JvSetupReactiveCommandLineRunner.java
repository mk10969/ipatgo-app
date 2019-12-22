package org.uma.platform.feed.application.component;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.uma.platform.common.model.HorseRacingDetails;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.repository.impl.JvStoredHorseRacingDetailsRepository;
import org.uma.platform.feed.application.repository.impl.JvStoredRacingDetailsRepository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Profile("setup")
@Slf4j
@Component
@RequiredArgsConstructor
public class JvSetupReactiveCommandLineRunner implements CommandLineRunner {

    /**
     * セットアップデータ種類（選択可能）
     */
    @Value("${setup.dataTypes}")
    private Set<String> dataTypes;

    /**
     * {@link JvSetupRunnerConfiguration}
     */
    private final Map<String, JvSetupReactiveRunner<?>> jvSetupRunners;


    @Override
    public void run(String... args) throws Exception {
        if (dataTypes == null) {
            throw new IllegalStateException("データ種別が選択されていません。");
        }
        log.info("セットアップ開始");

        Flux.fromStream(jvSetupRunners.entrySet().stream())
                .doOnNext(entry -> log.info("実行中: {}", entry.getKey()))
                .filter(entry -> dataTypes.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(JvSetupReactiveRunner::run)
                .subscribeOn(Schedulers.single()) //single thread で回す
                .subscribe(
                        i -> {},
                        e -> log.error("ERROR: ", e),
                        () -> log.info("完了")
                );

        log.info("セットアップ完了");
    }

//    private void execute() {
//        jvSetupRunners.entrySet().stream()
//                .filter(e -> dataTypes.contains(e.getKey()))
//                .peek(e -> log.info("セットアップ中: {}", e.getKey()))
//                .forEach(runner -> runner.getValue().run()
//                        .publishOn(Schedulers.immediate()) // use current thread
//                        .subscribe(
//                                i -> {},
//                                e -> log.error("エラー: [" + runner.getKey() + "]", e),
//                                () -> log.info("完了 :[{}]", runner.getKey())
//                        )
//                );
//    }


    @FunctionalInterface
    private interface JvSetupReactiveRunner<T> {

        Flux<T> run();
    }


    @Configuration
    @RequiredArgsConstructor
    private static class JvSetupRunnerConfiguration {

        /**
         * 過去データ日付
         * 現在から設定した日付までのデータをセットアップする。
         */
        @Value("${setup.yyyyMMddHHmmss}")
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime dateTime;

        /**
         * Reactive Mongo Client
         */
        private final ReactiveMongoTemplate reactiveTemplate;


        private <T> Flux<T> insert(Tuple2<List<T>, String> tuples) {
            // 一つのドキュメントに対して、transaction別いらない。
            return reactiveTemplate.insert(tuples.getT1(), tuples.getT2());
//                .inTransaction()
//                .execute(action -> action.insert(tuples.getT1(), tuples.getT2()))
        }

        @Bean("RacingDetails")
        public JvSetupReactiveRunner<RacingDetails> jvSetupRunnerRacingDetails(
                JvStoredRacingDetailsRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .doOnNext(model -> log.info("RacingDetails: {}", model))
                    .buffer(500)
                    .map(chunk -> Tuples.of(chunk, "racingDetails"))
                    .flatMap(this::insert);
        }

        @Bean("HorseRacingDetails")
        public JvSetupReactiveRunner<HorseRacingDetails> jvSetupRunnerHorseRacingDetails(
                JvStoredHorseRacingDetailsRepository repository) {
            return () -> repository.readFlux(dateTime)
                    .doOnNext(model -> log.info("HorseRacingDetails: {}", model))
                    .buffer(500)
                    .map(chunk -> Tuples.of(chunk, "horseRacingDetails"))
                    .flatMap(this::insert);
        }

//        @Bean("RaceRefund")
//        public JvSetupReactiveRunner<RaceRefund> jvSetupRunnerRaceRefund(
//                JvStoredRaceRefundRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("RaceRefund: {}", model))
//                    .buffer(500)
//                    .map(chunk -> Tuples.of(chunk, "raceRefund"))
//                    .flatMap(this::insert);
//        }
//
//
//        @Bean("VoteCount")
//        public JvSetupReactiveRunner<VoteCount> jvSetupRunnerVoteCount(
//                JvStoredVoteCountRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("VoteCount: {}", model))
//                    .buffer(200)
//                    .map(chunk -> Tuples.of(chunk, "voteCount"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("Ancestry")
//        public JvSetupReactiveRunner<Ancestry> jvSetupRunnerAncestry(
//                JvStoredAncestryRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("Ancestry: {}", model))
//                    .buffer(500)
//                    .map(chunk -> Tuples.of(chunk, "ancestry"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("BreedingHorse")
//        public JvSetupReactiveRunner<BreedingHorse> jvSetupRunnerBreedingHorse(
//                JvStoredBreedingHorseRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("BreedingHorse: {}", model))
//                    .buffer(500)
//                    .map(chunk -> Tuples.of(chunk, "breedingHorse"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("Offspring")
//        public JvSetupReactiveRunner<Offspring> jvSetupRunnerOffspring(
//                JvStoredOffspringRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("Offspring: {}", model))
//                    .buffer(500)
//                    .map(chunk -> Tuples.of(chunk, "offspring"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("RaceHorse")
//        public JvSetupReactiveRunner<RaceHorse> jvSetupRunnerRaceHorse(
//                JvStoredRaceHorseRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("RaceHorse: {}", model))
//                    .buffer(300)
//                    .map(chunk -> Tuples.of(chunk, "raceHorse"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("Jockey")
//        public JvSetupReactiveRunner<Jockey> jvSetupRunnerJockey(
//                JvStoredJockeyRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("Jockey: {}", model))
//                    .buffer(300)
//                    .map(chunk -> Tuples.of(chunk, "jockey"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("Trainer")
//        public JvSetupReactiveRunner<Trainer> jvSetupRunnerTrainer(
//                JvStoredTrainerRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("Trainer: {}", model))
//                    .buffer(300)
//                    .map(chunk -> Tuples.of(chunk, "trainer"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("Owner")
//        public JvSetupReactiveRunner<Owner> jvSetupRunnerOwner(
//                JvStoredOwnerRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("Owner: {}", model))
//                    .buffer(300)
//                    .map(chunk -> Tuples.of(chunk, "owner"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("Breeder")
//        public JvSetupReactiveRunner<Breeder> jvSetupRunnerBreeder(
//                JvStoredBreederRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("Breeder: {}", model))
//                    .buffer(300)
//                    .map(chunk -> Tuples.of(chunk, "breeder"))
//                    .flatMap(this::insert);
//        }
//
//        @Bean("Course")
//        public JvSetupReactiveRunner<Course> jvSetupRunnerCourse(
//                JvStoredCourseRepository repository) {
//            return () -> repository.readFlux(dateTime)
//                    .doOnNext(model -> log.info("Course: {}", model))
//                    .buffer(300)
//                    .map(chunk -> Tuples.of(chunk, "course"))
//                    .flatMap(this::insert);
//        }

    }

}
