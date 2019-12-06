package org.uma.platform.feed.application.repository;

import org.uma.platform.common.config.RealTimeKey;
import reactor.core.publisher.Flux;

import java.util.List;

public interface JvLInkRealTimeRepository<T> {

    Flux<T> readFlux(RealTimeKey realTimeKey);

}
