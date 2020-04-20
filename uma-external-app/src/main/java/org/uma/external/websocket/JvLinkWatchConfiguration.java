package org.uma.external.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.uma.external.jvlink.JvLinkDataLab;
import org.uma.external.jvlink.JvLinkWatch;
import reactor.core.publisher.UnicastProcessor;

@Configuration
public class JvLinkWatchConfiguration {

    @Bean
    public JvLinkWatch jvLinkWatch(JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler) {
        return new JvLinkWatch(jvLinkEventHandler);
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
