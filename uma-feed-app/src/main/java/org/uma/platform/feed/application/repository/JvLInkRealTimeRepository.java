package org.uma.platform.feed.application.repository;

import org.uma.platform.common.config.RealTimeKey;

import java.util.List;

public interface JvLInkRealTimeRepository<T> {

    List<T> findAll(RealTimeKey realTimeKey);


}
