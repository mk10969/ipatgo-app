package org.uma.platform.feed.application.repository;

import org.uma.platform.common.config.Option;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface JvLinkStoredRepository<T> {

    Flux<T> readFlux(LocalDateTime dateTime, Option option);
}
