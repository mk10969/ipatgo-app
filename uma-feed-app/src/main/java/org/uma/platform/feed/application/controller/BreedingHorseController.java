package org.uma.platform.feed.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.BreedingHorse;
import org.uma.platform.common.utils.lang.DateUtil;
import org.uma.platform.feed.application.repository.impl.JvStoredBreedingHorseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.uma.platform.common.utils.lang.DateUtil.lastWeek;
import static org.uma.platform.common.utils.lang.DateUtil.tolocalDateTime;

@RestController
@RequestMapping("/api/bt")
public class BreedingHorseController {
    private final JvStoredBreedingHorseRepository jvRepository;

    public BreedingHorseController(JvStoredBreedingHorseRepository jvRepository) {
        this.jvRepository = jvRepository;
    }


    @GetMapping("/breedingHorse")
    public Flux<BreedingHorse> readFluxOnThisWeek() {
        return Mono
                .defer(() -> Mono.just(lastWeek()))
                .flatMapMany(i -> jvRepository.readFlux(i, Option.THIS_WEEK));
    }

    @GetMapping("/breedingHorse/{epochSecond}")
    public Flux<BreedingHorse> readFluxOnStandard(@PathVariable(value = "epochSecond") Long epochSecond) {
        return Mono
                .just(tolocalDateTime(epochSecond))
                .map(DateUtil::within3years)
                .flatMapMany(i -> jvRepository.readFlux(i, Option.STANDARD));

    }

}

