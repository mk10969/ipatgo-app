package org.uma.jvLink.setup;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.uma.jvLink.client.JvLinkClient;
import org.uma.jvLink.client.response.JvStringContent;
import org.uma.jvLink.client.util.ByteUtil;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.stream.Stream;

import static org.uma.jvLink.client.property.JvStored.BLOD_ALL;
import static org.uma.jvLink.client.property.JvStored.DIFF_ALL;
import static org.uma.jvLink.client.property.JvStored.RACE_ALL;


@Slf4j
@Configuration
@ConfigurationProperties(prefix = "setup")
public class SetupConfiguration {

    /**
     * 現在から設定した日付までのデータをセットアップする。
     */
    @Setter
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime yyyyMMddHHmmss;


    /**
     * セットアップデータをひたすらファイルに書き込む。
     */
    public void setupRACE() {
        try (Stream<JvStringContent> stream = JvLinkClient
                .readForSetup(RACE_ALL.get(), yyyyMMddHHmmss)) {
            Flux.fromStream(stream)
                    .doOnNext(i -> log.info("<< START"))
                    .publishOn(Schedulers.single())
                    .map(JvStringContent::getLine)
                    .map(ByteUtil::toByte)
                    .map(bytes -> Base64.getEncoder()
                            .encodeToString(bytes))
                    .subscribeOn(Schedulers.parallel())
                    .subscribe(
                            log::info,
                            e -> log.error("ERROR: ", e),
                            () -> log.info("<< EOL")
                    );
        }
    }

    public void setupBLOD() {
        try (Stream<JvStringContent> stream = JvLinkClient
                .readForSetup(BLOD_ALL.get(), yyyyMMddHHmmss)) {
            Flux.fromStream(stream)
                    .doOnNext(i -> log.info("<< START"))
                    .publishOn(Schedulers.single())
                    .map(JvStringContent::getLine)
                    .map(ByteUtil::toByte)
                    .map(bytes -> Base64.getEncoder()
                            .encodeToString(bytes))
                    .subscribeOn(Schedulers.parallel())
                    .subscribe(
                            log::info,
                            e -> log.error("ERROR: ", e),
                            () -> log.info("<< EOL")
                    );
        }
    }

    public void setupDIFF() {
        try (Stream<JvStringContent> stream = JvLinkClient
                .readForSetup(DIFF_ALL.get(), yyyyMMddHHmmss)) {
            Flux.fromStream(stream)
                    .doOnNext(i -> log.info("<< START"))
                    .publishOn(Schedulers.single())
                    .map(JvStringContent::getLine)
                    .map(ByteUtil::toByte)
                    .map(bytes -> Base64.getEncoder()
                            .encodeToString(bytes))
                    .subscribeOn(Schedulers.parallel())
                    .subscribe(
                            log::info,
                            e -> log.error("ERROR: ", e),
                            () -> log.info("<< EOL")
                    );
        }
    }

}
