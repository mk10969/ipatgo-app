package org.uma.platform.feed.application.component;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;
import org.uma.platform.common.config.Option;
import org.uma.platform.feed.application.repository.JvLinkStoredRepository;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("setup")
public class JvDataSetup {

    @Value("${data.yearsOld}")
    private long yearsOld;

    @Value("{data.filters}")
    private List<String> fliters;

    private final List<JvLinkStoredRepository> jvLinkStoredRepositories;

    private final ReactiveMongoTemplate template;

    @PostConstruct
    public void setup() {
        LocalDateTime dateTime = LocalDateTime.now()
                .minusYears(yearsOld);

//        Flux.fromIterable(jvLinkStoredRepositories)
//                .publishOn(Schedulers.single())  // 訳ありシングルスレッド
//                .flatMap(repository -> repository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG)
//                        .flatMap(i ->));
    }


}
