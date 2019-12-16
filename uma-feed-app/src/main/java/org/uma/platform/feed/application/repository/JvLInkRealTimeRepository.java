package org.uma.platform.feed.application.repository;

import org.uma.platform.common.config.RealTimeKey;

import java.util.List;

public interface JvLInkRealTimeRepository<T> {

    List<T> readLine(RealTimeKey realTimeKey);

}
