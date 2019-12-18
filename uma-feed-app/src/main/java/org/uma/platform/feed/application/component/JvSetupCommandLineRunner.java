package org.uma.platform.feed.application.component;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.uma.platform.common.model.*;
import org.uma.platform.feed.application.repository.impl.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Profile("setup")
@Slf4j
@Component
@RequiredArgsConstructor
public class JvSetupCommandLineRunner implements CommandLineRunner {

    /**
     * セットアップデータ種類（選択可能）
     */
    @Value("${setup.property.setupDataTypes}")
    private List<String> setupDataTypes;

    /**
     * {@link JvSetupCommandLineRunner.JvSetupRunnerConfiguration}
     */
    private final Map<String, JvSetupRunner> jvSetupRunners;


    @Override
    public void run(String... args) throws Exception {
        log.info("セットアップ開始");

        jvSetupRunners.entrySet().stream()
                .filter(e -> setupDataTypes.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .forEach(JvSetupRunner::run);

        log.info("セットアップ完了");

    }


    @FunctionalInterface
    private interface JvSetupRunner {

        void run();
    }


    @Configuration
    private static class JvSetupRunnerConfiguration {

        /**
         * 過去データ日付
         * 現在から設定した日付までのデータをセットアップする。
         */
        @Value("${setup.property.yyyyMMddHHmmss}")
        @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
        private LocalDateTime dateTime;


        @Bean("RacingDetails")
        private JvSetupRunner jvSetupRunner(JvStoredRacingDetailsRepository repository) {
            return () -> {
                try (Stream<RacingDetails> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("RacingDetails: {}", model)).forEach(i -> {

                    });
                }
            };
        }

        @Bean("HorseRacingDetails")
        private JvSetupRunner jvSetupRunner(JvStoredHorseRacingDetailsRepository repository) {
            return () -> {
                try (Stream<HorseRacingDetails> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("HorseRacingDetails: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("RaceRefund")
        private JvSetupRunner jvSetupRunner(JvStoredRaceRefundRepository repository) {
            return () -> {
                try (Stream<RaceRefund> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("RaceRefund: {}", model)).forEach(i -> {

                    });
                }
            };
        }

        @Bean("VoteCount")
        private JvSetupRunner jvSetupRunner(JvStoredVoteCountRepository repository) {
            return () -> {
                try (Stream<VoteCount> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("VoteCount: {}", model)).forEach(i -> {

                    });
                }
            };
        }

        @Bean("Ancestry")
        private JvSetupRunner jvSetupRunner(JvStoredAncestryRepository repository) {
            return () -> {
                try (Stream<Ancestry> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("Ancestry: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("BreedingHorse")
        private JvSetupRunner jvSetupRunner(JvStoredBreedingHorseRepository repository) {
            return () -> {
                try (Stream<BreedingHorse> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("BreedingHorse: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("Offspring")
        private JvSetupRunner jvSetupRunner(JvStoredOffspringRepository repository) {
            return () -> {
                try (Stream<Offspring> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("Offspring: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("RaceHorse")
        private JvSetupRunner jvSetupRunner(JvStoredRaceHorseRepository repository) {
            return () -> {
                try (Stream<RaceHorse> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("RaceHorse: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("Jockey")
        private JvSetupRunner jvSetupRunner(JvStoredJockeyRepository repository) {
            return () -> {
                try (Stream<Jockey> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("Jockey: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("Trainer")
        private JvSetupRunner jvSetupRunner(JvStoredTrainerRepository repository) {
            return () -> {
                try (Stream<Trainer> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("Trainer: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("Owner")
        private JvSetupRunner jvSetupRunner(JvStoredOwnerRepository repository) {
            return () -> {
                try (Stream<Owner> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("Owner: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("Breeder")
        private JvSetupRunner jvSetupRunner(JvStoredBreederRepository repository) {
            return () -> {
                try (Stream<Breeder> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("Breeder: {}", model)).forEach(i -> {


                    });
                }
            };
        }

        @Bean("Course")
        private JvSetupRunner jvSetupRunner(JvStoredCourseRepository repository) {
            return () -> {
                try (Stream<Course> stream = repository.readStream(dateTime)) {
                    stream.peek(model -> log.info("Course: {}", model)).forEach(i -> {


                    });
                }
            };
        }


    }


}
