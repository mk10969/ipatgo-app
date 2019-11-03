package org.uma.daiwaScarlet.repository;

import org.uma.vodka.config.Option;

import java.time.ZonedDateTime;
import java.util.List;

public interface JvLinkStoredRepository<T> {

    List<T> findAll(ZonedDateTime time, Option option);


}
