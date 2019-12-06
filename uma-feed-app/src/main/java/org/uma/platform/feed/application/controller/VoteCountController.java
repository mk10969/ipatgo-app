package org.uma.platform.feed.application.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.VoteCount;
import org.uma.platform.common.utils.lang.DateUtil;
import org.uma.platform.feed.application.repository.impl.JvStoredVoteCountRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.uma.platform.common.utils.lang.DateUtil.lastWeek;
import static org.uma.platform.common.utils.lang.DateUtil.tolocalDateTime;

@RestController
@RequestMapping("/api/bt")
public class VoteCountController {
    private final JvStoredVoteCountRepository jvRepository;

    public VoteCountController(JvStoredVoteCountRepository jvRepository) {
        this.jvRepository = jvRepository;
    }


    @GetMapping("/voteCount")
    public Flux<VoteCount> readFluxOnThisWeek() {
        return Mono
                .defer(() -> Mono.just(lastWeek()))
                .flatMapMany(i -> jvRepository.readFlux(i, Option.THIS_WEEK));
    }

    @GetMapping("/voteCount/{epochSecond}")
    public Flux<VoteCount> readFluxOnStandard(@PathVariable(value = "epochSecond") Long epochSecond) {
        return Mono
                .just(tolocalDateTime(epochSecond))
                .map(DateUtil::within3years)
                .flatMapMany(i -> jvRepository.readFlux(i, Option.STANDARD));

    }

}

