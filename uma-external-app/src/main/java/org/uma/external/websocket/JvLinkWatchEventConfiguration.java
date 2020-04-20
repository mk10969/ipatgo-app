package org.uma.external.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.uma.external.jvlink.JvLinkDataLab;
import org.uma.external.jvlink.JvLinkWatch;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@Configuration
public class JvLinkWatchEventConfiguration {

    /**
     * JvLinkイベント通知
     */
    private JvLinkWatch jvLinkWatch;

    @Autowired
    private JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler;


    /**
     * アプリ起動時、JvLinkからのイベント通知を始める。
     */
    @PostConstruct
    void init() {
        this.jvLinkWatch = new JvLinkWatch(jvLinkEventHandler);
        this.jvLinkWatch.start();
    }

    /**
     * アプリ終了時、JvLinkからのイベント通知を終わる。
     */
    @PreDestroy
    void end() {
        this.jvLinkWatch.stop();
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> log.info("JvLink Watch Event start!!!");
    }


    @Component
    public static class JvLinkEventHandlerImpl implements JvLinkDataLab.JvLinkEventHandler {

        @Autowired
        private UnicastProcessor<String> hotSource;

        @Override
        public void handlePay(String yyyymmddjjrr) {
            hotSource.onNext("HR:" + yyyymmddjjrr);
        }

        @Override
        public void handleWeight(String yyyymmddjjrr) {
            hotSource.onNext("WH:" + yyyymmddjjrr);
        }

        @Override
        public void handleJockeyChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            hotSource.onNext("JC:" + ttyyyymmddjjrrnnnnnnnnnnnnnn);
        }

        @Override
        public void handleWeather(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            hotSource.onNext("WE:" + ttyyyymmddjjrrnnnnnnnnnnnnnn);
        }

        @Override
        public void handleCourseChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            hotSource.onNext("CC:" + ttyyyymmddjjrrnnnnnnnnnnnnnn);
        }

        @Override
        public void handleAvoid(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            hotSource.onNext("AV:" + ttyyyymmddjjrrnnnnnnnnnnnnnn);
        }

        @Override
        public void handleTimeChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            hotSource.onNext("TC:" + ttyyyymmddjjrrnnnnnnnnnnnnnn);
        }
    }

}
