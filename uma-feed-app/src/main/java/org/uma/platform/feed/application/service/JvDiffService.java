package org.uma.platform.feed.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.uma.platform.common.config.Option;
import org.uma.platform.common.model.*;
import org.uma.platform.feed.application.repository.impl.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Service("DIFF")
@RequiredArgsConstructor
public class JvDiffService {

    /**
     * 競走馬
     */
    private final JvStoredRaceHorseRepository raceHorseRepository;

    /**
     * 騎手
     */
    private final JvStoredJockeyRepository jockeyRepository;

    /**
     * 調教師
     */
    private final JvStoredTrainerRepository trainerRepository;

    /**
     * 馬主
     */
    private final JvStoredOwnerRepository ownerRepository;

    /**
     * 生産者
     */
    private final JvStoredBreederRepository breederRepository;


    public Flux<RaceHorse> findRaceHorse(LocalDateTime dateTime) {
        return raceHorseRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<RaceHorse> findRaceHorseOnSetUp(LocalDateTime dateTime) {
        return raceHorseRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<Jockey> findJockey(LocalDateTime dateTime) {
        return jockeyRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<Jockey> findJockeyOnSetUp(LocalDateTime dateTime) {
        return jockeyRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<Trainer> findTrainer(LocalDateTime dateTime) {
        return trainerRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<Trainer> findTrainerOnSetUp(LocalDateTime dateTime) {
        return trainerRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<Owner> findOwner(LocalDateTime dateTime) {
        return ownerRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<Owner> findOwnerOnSetUp(LocalDateTime dateTime) {
        return ownerRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }

    public Flux<Breeder> findBreeder(LocalDateTime dateTime) {
        return breederRepository.readFlux(dateTime, Option.STANDARD);
    }

    public Flux<Breeder> findBreederOnSetUp(LocalDateTime dateTime) {
        return breederRepository.readFlux(dateTime, Option.SETUP_WITHOUT_DIALOG);
    }


}
