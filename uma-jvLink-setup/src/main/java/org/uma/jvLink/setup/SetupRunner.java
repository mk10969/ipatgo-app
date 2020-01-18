package org.uma.jvLink.setup;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.uma.jvLink.core.JvLinkClient;
import org.uma.jvLink.core.response.JvStringContent;
import org.uma.jvLink.core.util.ByteUtil;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.uma.jvLink.core.JvStored.RACE_ALL;

@Slf4j
@Component
public class SetupRunner {

    /**
     * 現在から設定した日付までのデータをセットアップする。
     */
    @Value("setup.yyyyMMddHHmmss")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime yyyyMMddHHmmss;


    public void test() {

        try (Stream<JvStringContent> stream = JvLinkClient
                .readForSetup(RACE_ALL.get(), yyyyMMddHHmmss)) {

            stream.map(JvStringContent::getLine)
                    .peek(log::info)
                    .map(ByteUtil::toByte);


        }finally {
            log.info("完了");
        }


    }


}
