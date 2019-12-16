package org.uma.platform.feed.application.repository;

import org.uma.platform.common.config.Option;

import java.time.LocalDateTime;
import java.util.List;

public interface JvLinkStoredRepository<T> {

    List<T> readLine(LocalDateTime dateTime, Option option);

}
