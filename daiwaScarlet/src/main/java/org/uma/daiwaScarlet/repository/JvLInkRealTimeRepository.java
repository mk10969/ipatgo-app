package org.uma.daiwaScarlet.repository;

import org.uma.vodka.config.RealTimeKey;

import java.util.List;

public interface JvLInkRealTimeRepository<T> {

    List<T> findAll(RealTimeKey realTimeKey);


}
