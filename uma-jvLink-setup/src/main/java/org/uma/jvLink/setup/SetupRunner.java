package org.uma.jvLink.setup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Slf4j
@Component
@RequiredArgsConstructor
public class SetupRunner implements CommandLineRunner {

    private final SetupConfiguration setupConfiguration;


    @Override
    public void run(String... args) throws Exception {
        Mono.just("OK")
                .doOnNext(i -> log.info("セットアップ開始"))
                .flatMap(i -> setupConfiguration.setupRACE())
                .flatMap(i -> setupConfiguration.setupBLOD())
                .flatMap(i -> setupConfiguration.setupDIFF())
                .subscribe(
                        i -> {},
                        e -> log.error("ERROR: ", e),
                        () -> log.info("セットアップ完了")
                );
    }
}
