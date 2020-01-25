package org.uma.jvLink.setup;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.uma.jvLink.client.JvLinkClient;
import org.uma.jvLink.client.response.JvStringContent;
import org.uma.jvLink.client.util.ByteUtil;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.uma.jvLink.client.JvStored.RACE_ALL;

@Slf4j
@Component
public class SetupRunner {

    /**
     * 現在から設定した日付までのデータをセットアップする。
     */
    @Value("setup.yyyyMMddHHmmss")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime yyyyMMddHHmmss;


    /**
     * あまりにも遅かったら、Flux使うか。
     * TODO : RSocketにしようかと。
     */
    public void setupRACE() {

        try (Stream<JvStringContent> stream = JvLinkClient
                .readForSetup(RACE_ALL.get(), yyyyMMddHHmmss)) {

            stream.map(JvStringContent::getLine)
                    .map(ByteUtil::toByte)
                    .peek(i -> log.info(Arrays.toString(i)))
                    .forEach(data -> {
                        //Kafkaに突っ込むか。。。

                        log.info("OK");


                    });
        } catch (RuntimeException e) {
            log.error("セットアップ中に、エラーが発生しました。", e);

        }

    }


}
