package org.uma.external.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Profile("dev")
@Configuration
public class TestEventHandler {

    @Autowired
    private UnicastProcessor<String> hotSource;

    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    void init() {
        service.scheduleAtFixedRate(() ->
                hotSource.onNext("TEST:AAAAAAAAA"), 10, 1, TimeUnit.SECONDS);
    }

}
