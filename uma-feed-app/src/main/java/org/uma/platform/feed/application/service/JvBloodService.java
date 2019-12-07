package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.Ancestry;
import org.uma.platform.common.model.BreedingHorse;
import org.uma.platform.common.model.Offspring;
import org.uma.platform.feed.application.repository.impl.JvStoredAncestryRepository;
import org.uma.platform.feed.application.repository.impl.JvStoredBreedingHorseRepository;
import org.uma.platform.feed.application.repository.impl.JvStoredOffspringRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service("BLOOD")
@RequiredArgsConstructor
public class JvBloodService {

    /**
     * 系統情報
     */
    private final JvStoredAncestryRepository ancestryRepository;

    /**
     * 繁殖馬情報
     */
    private final JvStoredBreedingHorseRepository breedingHorseRepository;

    /**
     * 産駒情報
     */
    private final JvStoredOffspringRepository offspringRepository;


    public Flux<Ancestry> findAncestry(LocalDateTime dateTime) {
        return ancestryRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<Ancestry> findAncestryOnSetUp(LocalDateTime dateTime) {
        return ancestryRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<BreedingHorse> findBreedingHorse(LocalDateTime dateTime) {
        return breedingHorseRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<BreedingHorse> findBreedingHorseOnSetUp(LocalDateTime dateTime) {
        return breedingHorseRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<Offspring> findOffspring(LocalDateTime dateTime) {
        return offspringRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<Offspring> findOffspringOnSetUp(LocalDateTime dateTime) {
        return offspringRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }


}
