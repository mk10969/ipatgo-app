package org.uma.platform.feed.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.RacingDetails;
import org.uma.platform.feed.application.repository.impl.JvStoredRacingDetailsRepository;
import org.uma.platform.common.utils.lang.DateUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.uma.platform.common.utils.lang.DateUtil.tolocalDateTime;

@RestController
@RequestMapping("/api/bt")
public class RacingDetailsController {

    private final JvStoredRacingDetailsRepository jvRepository;

    public RacingDetailsController(JvStoredRacingDetailsRepository jvRepository) {
        this.jvRepository = jvRepository;
    }

    @GetMapping("/old/racingDetails")
    public List<RacingDetails> dindAllOnThisWeek() {
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L).minusDays(1L);
        return jvRepository.findAll(dateTime, Option.THIS_WEEK);
    }


    @GetMapping("/racingDetails")
    public Flux<RacingDetails> readFluxOnThisWeek() {
        return Mono
                .defer(DateUtil::lastWeek)
                .flatMapMany(i -> jvRepository
                        .readFlux(i, Option.THIS_WEEK));
    }

    @GetMapping("/racingDetails/{epochSecond}")
    public Flux<RacingDetails> readFluxOnStandard(@PathVariable(value = "epochSecond") Long epochSecond) {
        return Mono
                .just(tolocalDateTime(epochSecond))
                .map(DateUtil::within3years)
                .flatMapMany(i -> jvRepository
                        .readFlux(i, Option.STANDARD));
    }


}
