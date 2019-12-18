package org.uma.platform.feed.application.repository;

import org.uma.platform.common.config.Option;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface JvLinkStoredRepository<T> {

    /**
     * バッチ用
     * @param dateTime
     * @param option
     * @return
     */
    List<T> readLines(LocalDateTime dateTime, Option option);

    /**
     * セットアップ用
     * @param dateTime
     * @return Streamを返却するので、closeとthreadロックを忘れないこと。
     */
    Stream<T> readStream(LocalDateTime dateTime);



}
