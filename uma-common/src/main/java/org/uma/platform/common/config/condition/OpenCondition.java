package org.uma.platform.common.config.condition;

import org.uma.platform.common.config.spec.DataEnum;
import org.uma.platform.common.config.spec.RecordSpec;

public interface OpenCondition<T extends Enum<T> & DataEnum> {

    T getDataSpec();

    RecordSpec getRecordType();

}
