package org.uma.platform.common.config.spec;

import org.uma.platform.common.utils.constants.CodeEnum;

public interface RecordEnum<S, R extends Enum<R>> extends CodeEnum<S, R> {

    /**
     * JV-Data文字列の長さ
     */
    int getLength();

}
