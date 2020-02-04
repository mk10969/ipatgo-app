package org.uma.jvLink.setup;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.uma.jvLink.client.JvLinkClient;
import org.uma.jvLink.client.response.JvStringContent;
import org.uma.jvLink.client.util.ByteUtil;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Base64;

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


    public Mono<String> setupRACE() {
        return JvLinkClient.readForSetup(RACE_ALL.get(), yyyyMMddHHmmss)
                .doOnNext(i -> log.info("<< RACE START"))
                .map(JvStringContent::getLine)
                .map(ByteUtil::toByte)
                .map(bytes -> Base64.getEncoder().encodeToString(bytes))
                .doOnNext(log::info)
                .then(Mono.just("OK"));
    }

    public Mono<String> setupBLOD() {
        return JvLinkClient.readForSetup(BLOD_ALL.get(), yyyyMMddHHmmss)
                .doOnNext(i -> log.info("<< BLOD START"))
                .map(JvStringContent::getLine)
                .map(ByteUtil::toByte)
                .map(bytes -> Base64.getEncoder().encodeToString(bytes))
                .doOnNext(log::info)
                .then(Mono.just("OK"));
    }

    public Mono<String> setupDIFF() {
        return JvLinkClient.readForSetup(DIFF_ALL.get(), yyyyMMddHHmmss)
                .doOnNext(i -> log.info("<< DIFF START"))
                .map(JvStringContent::getLine)
                .map(ByteUtil::toByte)
                .map(bytes -> Base64.getEncoder().encodeToString(bytes))
                .doOnNext(log::info)
                .then(Mono.just("OK"));
    }

}
