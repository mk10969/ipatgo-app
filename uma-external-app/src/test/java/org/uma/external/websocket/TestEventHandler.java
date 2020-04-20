package org.uma.external.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.UnicastProcessor;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Configuration
@ConditionalOnProperty(prefix = "spring.testEvent", name = "enabled", havingValue = "true")
public class TestEventHandler {

    @Autowired
    private UnicastProcessor<String> hotSource;

    private final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    void init() {
        service.scheduleAtFixedRate(() ->
                hotSource.onNext("AAAAAAAAA"), 2, 1, TimeUnit.SECONDS);
    }

}
