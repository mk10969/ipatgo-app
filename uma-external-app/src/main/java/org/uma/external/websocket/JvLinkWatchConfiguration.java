package org.uma.external.websocket;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.uma.external.jvlink.JvLinkDataLab;
import org.uma.external.jvlink.JvLinkWatch;
import org.uma.external.jvlink.config.spec.RecordSpec;

@Configuration
public class JvLinkWatchConfiguration {

    @Bean
    public JvLinkWatch jvLinkWatch(JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler) {
        return new JvLinkWatch(jvLinkEventHandler);
    }

    @Component
    public static class JvLinkEventHandlerImpl implements JvLinkDataLab.JvLinkEventHandler {

        @Autowired
        private JvLinkWebSocketHandler jvLinkWebSocketHandler;

        @Override
        public void handlePay(String yyyymmddjjrr) {
            jvLinkWebSocketHandler.getHotSource().onNext(EventMessage.of(RecordSpec.HR, yyyymmddjjrr));
        }

        @Override
        public void handleWeight(String yyyymmddjjrr) {
            jvLinkWebSocketHandler.getHotSource().onNext(EventMessage.of(RecordSpec.WH, yyyymmddjjrr));
        }

        @Override
        public void handleJockeyChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            jvLinkWebSocketHandler.getHotSource().onNext(EventMessage.of(RecordSpec.JC, ttyyyymmddjjrrnnnnnnnnnnnnnn));
        }

        @Override
        public void handleWeather(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            jvLinkWebSocketHandler.getHotSource().onNext(EventMessage.of(RecordSpec.WE, ttyyyymmddjjrrnnnnnnnnnnnnnn));
        }

        @Override
        public void handleCourseChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            jvLinkWebSocketHandler.getHotSource().onNext(EventMessage.of(RecordSpec.CC, ttyyyymmddjjrrnnnnnnnnnnnnnn));
        }

        @Override
        public void handleAvoid(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            jvLinkWebSocketHandler.getHotSource().onNext(EventMessage.of(RecordSpec.AV, ttyyyymmddjjrrnnnnnnnnnnnnnn));
        }

        @Override
        public void handleTimeChange(String ttyyyymmddjjrrnnnnnnnnnnnnnn) {
            jvLinkWebSocketHandler.getHotSource().onNext(EventMessage.of(RecordSpec.TC, ttyyyymmddjjrrnnnnnnnnnnnnnn));
        }
    }

    @Getter
    public static class EventMessage {

        private final RecordSpec recordSpec;

        private final String eventId;

        private EventMessage(RecordSpec recordSpec, String eventId) {
            this.recordSpec = recordSpec;
            this.eventId = eventId;
        }

        public static EventMessage of(RecordSpec recordSpec, String eventId) {
            return new EventMessage(recordSpec, eventId);
        }
    }

}
