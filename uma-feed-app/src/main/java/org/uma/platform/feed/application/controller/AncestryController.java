package org.uma.platform.feed.application.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.Ancestry;
import org.uma.platform.feed.application.repository.impl.JvStoredAncestryRepository;
import org.uma.platform.common.utils.lang.DateUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static org.uma.platform.common.utils.lang.DateUtil.tolocalDateTime;

@RestController
@RequestMapping("/api/bt")
public class AncestryController {

    private final JvStoredAncestryRepository jvRepository;

    public AncestryController(JvStoredAncestryRepository jvRepository) {
        this.jvRepository = jvRepository;
    }

    @GetMapping("/old/ancestry")
    public List<Ancestry> findAllOnThisWeek() {
        // たぶんこんくらいで、大丈夫なはず。
        LocalDateTime dateTime = LocalDateTime.now().minusWeeks(1L).minusDays(1L);
        return jvRepository.findAll(dateTime, Option.THIS_WEEK);
    }

    @GetMapping("/old/ancestry/{epochSecond}")
    public List<Ancestry> findAllOnStandard(@PathVariable(value = "epochSecond") Long epochSecond) {
        // 上限、下限を設定しましょう。バリデーション
        return jvRepository.findAll(tolocalDateTime(epochSecond), Option.STANDARD);
    }

    @GetMapping("/ancestry")
    public Flux<Ancestry> readFluxOnThisWeek() {
        return Mono
                .defer(() -> Mono.just(DateUtil.lastWeek()))
                .flatMapMany(i -> jvRepository
                        .readFlux(i, Option.THIS_WEEK));
    }

    @GetMapping("/ancestry/{epochSecond}")
    public Flux<Ancestry> readFluxOnStandard(@PathVariable(value = "epochSecond") Long epochSecond) {
        return Mono
                .just(tolocalDateTime(epochSecond))
                .map(DateUtil::within3years)
                .flatMapMany(i -> jvRepository
                        .readFlux(i, Option.STANDARD));
    }

//    @GetMapping("/test/ancestry/{epochSecond}")
//    public Mono<String> test_error(@PathVariable(value = "epochSecond") Long epochSecond) {
//        // 500 で error message返る
//        return Mono
//                .just(tolocalDateTime(epochSecond))
//                .map(DateUtil::within3years)
//                .flatMap(i -> Mono.just(i.toString()));
//    }

}
