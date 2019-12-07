package org.uma.platform.feed.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.RaceHorse;
import org.uma.platform.common.utils.lang.DateUtil;
import org.uma.platform.feed.application.repository.impl.JvStoredRaceHorseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.uma.platform.common.utils.lang.DateUtil.lastWeek;
import static org.uma.platform.common.utils.lang.DateUtil.tolocalDateTime;

@RestController
@RequestMapping("/api/bt")
public class RaceHorseController {
    private final JvStoredRaceHorseRepository jvRepository;

    public RaceHorseController(JvStoredRaceHorseRepository jvRepository) {
        this.jvRepository = jvRepository;
    }


    @GetMapping("/raceHorse")
    public Flux<RaceHorse> readFluxOnThisWeek() {
        return Mono
                .defer(() -> Mono.just(lastWeek()))
                .flatMapMany(i -> jvRepository.readFlux(i, Option.THIS_WEEK));
    }

    @GetMapping("/raceHorse/{epochSecond}")
    public Flux<RaceHorse> readFluxOnStandard(@PathVariable(value = "epochSecond") Long epochSecond) {
        return Mono
                .just(tolocalDateTime(epochSecond))
                .map(DateUtil::within3years)
                .flatMapMany(i -> jvRepository.readFlux(i, Option.STANDARD));

    }

}
