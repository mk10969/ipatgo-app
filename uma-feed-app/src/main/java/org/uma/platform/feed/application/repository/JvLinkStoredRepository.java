package org.uma.platform.feed.application.repository;

import org.uma.platform.common.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

public interface JvLinkStoredRepository<T> {

    List<T> findAll(ZonedDateTime dateTime, Option option);


}
