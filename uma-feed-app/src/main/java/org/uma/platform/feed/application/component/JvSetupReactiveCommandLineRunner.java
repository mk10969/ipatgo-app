package org.uma.platform.feed.application.component;


import com.google.common.collect.Sets;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.uma.platform.common.model.*;
import org.uma.platform.common.model.odds.Quinella;
import org.uma.platform.common.model.odds.WinsPlaceBracketQuinella;
import org.uma.platform.feed.application.repository.impl.*;
import org.uma.platform.feed.application.util.JvLinkUtil;
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

    private final SetUpConfiguration setUpConfiguration;

    /**
     * {@link JvSetupRunnerConfiguration}
     */
    private final Map<String, JvSetupReactiveRunner<?>> jvSetupRunners;


    @Override
    public void run(String... args) throws Exception {
        if (setUpConfiguration.getDataTypes() == null) {
            throw new IllegalStateException("データ種別が選択されていません。");
        }
        log.info("セットアップ開始");

        Flux.fromStream(jvSetupRunners.entrySet().stream())
                .filter(entry -> setUpConfiguration.getDataTypes().contains(entry.getKey()))
                .doOnNext(entry -> log.info("実行: {}", entry.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(JvSetupReactiveRunner::run)
                .subscribeOn(Schedulers.single()) //single thread で回す
                .subscribe(
                        i -> {},
                        e -> log.error("ERROR: ", e),
                        () -> log.info("完了")
                );
    }


    @Data
    @Configuration
    @ConfigurationProperties(prefix = "setup")
    private static class SetUpConfiguration {

        /**
         * 現在から設定した日付までのデータをセットアップする。
         */
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime yyyyMMddHHmmss;

        /**
         * セットアップデータ種類（選択可能）
         */
        private Set<String> dataTypes = Sets.newHashSet();
    }


    @FunctionalInterface
    private interface JvSetupReactiveRunner<T> {

        Flux<T> run();
    }


    @Configuration
    @RequiredArgsConstructor
    private static class JvSetupRunnerConfiguration {

        private final SetUpConfiguration setUpConfiguration;

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

        private static <T> Flux<T> logAndNullCheck(T anyModel) {
            return Flux.just(anyModel)
                    .doOnNext(model -> log.info("{}", model))
                    .doOnNext(JvLinkUtil::fieldNotNull);
        }

        @Bean("RacingDetails")
        public JvSetupReactiveRunner<RacingDetails> jvSetupRunnerRacingDetails(
                JvStoredRacingDetailsRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "racingDetails"))
                    .flatMap(this::insert);
        }

        @Bean("HorseRacingDetails")
        public JvSetupReactiveRunner<HorseRacingDetails> jvSetupRunnerHorseRacingDetails(
                JvStoredHorseRacingDetailsRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(500)
                    .map(chunk -> Tuples.of(chunk, "horseRacingDetails"))
                    .flatMap(this::insert);
        }

        @Bean("RaceRefund")
        public JvSetupReactiveRunner<RaceRefund> jvSetupRunnerRaceRefund(
                JvStoredRaceRefundRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(500)
                    .map(chunk -> Tuples.of(chunk, "raceRefund"))
                    .flatMap(this::insert);
        }

        /**
         * dataDiv=9 (レース中止)のデータを入れておくか？ あらかじめフィルターしておくか？
         * →RacingDetailsには、データとして格納しておくが、VoteCountにはデータを入れない。
         * つまりフィルターしておく！
         */
        @Bean("VoteCount")
        public JvSetupReactiveRunner<VoteCount> jvSetupRunnerVoteCount(
                JvStoredVoteCountRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .filter(model -> !"9".equals(model.getDataDiv()))
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(30)
                    .map(chunk -> Tuples.of(chunk, "voteCount"))
                    .flatMap(this::insert);
        }

        /**
         * VoteCountと同様の思想
         */
        @Bean("WinsPlaceBracketQuinella")
        public JvSetupReactiveRunner<WinsPlaceBracketQuinella> jvSetupRunnerWinsPlaceBracketQuinella(
                JvStoredOddsWinsPlaceBracketQuinellaRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .filter(model -> !"9".equals(model.getDataDiv()))
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "winsPlaceBracketQuinella"))
                    .flatMap(this::insert);
        }

        /**
         * VoteCountと同様の思想
         */
        @Bean("Quinella")
        public JvSetupReactiveRunner<Quinella> jvSetupRunnerQuinella(
                JvStoredOddsQuinellaRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .filter(model -> !"9".equals(model.getDataDiv()))
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "quinella"))
                    .flatMap(this::insert);
        }


        @Bean("Ancestry")
        public JvSetupReactiveRunner<Ancestry> jvSetupRunnerAncestry(
                JvStoredAncestryRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "ancestry"))
                    .flatMap(this::insert);
        }

        @Bean("BreedingHorse")
        public JvSetupReactiveRunner<BreedingHorse> jvSetupRunnerBreedingHorse(
                JvStoredBreedingHorseRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "breedingHorse"))
                    .flatMap(this::insert);
        }

        @Bean("Offspring")
        public JvSetupReactiveRunner<Offspring> jvSetupRunnerOffspring(
                JvStoredOffspringRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "offspring"))
                    .flatMap(this::insert);
        }


        @Bean("RaceHorse")
        public JvSetupReactiveRunner<RaceHorse> jvSetupRunnerRaceHorse(
                JvStoredRaceHorseRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "raceHorse"))
                    .flatMap(this::insert);
        }

        @Bean("Jockey")
        public JvSetupReactiveRunner<Jockey> jvSetupRunnerJockey(
                JvStoredJockeyRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "jockey"))
                    .flatMap(this::insert);
        }

        @Bean("Trainer")
        public JvSetupReactiveRunner<Trainer> jvSetupRunnerTrainer(
                JvStoredTrainerRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "trainer"))
                    .flatMap(this::insert);
        }

        @Bean("Owner")
        public JvSetupReactiveRunner<Owner> jvSetupRunnerOwner(
                JvStoredOwnerRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "owner"))
                    .flatMap(this::insert);
        }

        @Bean("Breeder")
        public JvSetupReactiveRunner<Breeder> jvSetupRunnerBreeder(
                JvStoredBreederRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(300)
                    .map(chunk -> Tuples.of(chunk, "breeder"))
                    .flatMap(this::insert);
        }

        @Bean("Course")
        public JvSetupReactiveRunner<Course> jvSetupRunnerCourse(
                JvStoredCourseRepository repository) {
            return () -> repository.readFlux(setUpConfiguration.getYyyyMMddHHmmss())
                    .flatMap(JvSetupRunnerConfiguration::logAndNullCheck)
                    .buffer(100)
                    .map(chunk -> Tuples.of(chunk, "course"))
                    .flatMap(this::insert);
        }

    }

}
