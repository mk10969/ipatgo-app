package org.uma.jvLink.core.config.condition;

import org.uma.jvLink.core.config.DataEnum;
import org.uma.jvLink.core.config.spec.RecordSpec;

public interface OpenCondition<T extends Enum<T> & DataEnum<String, T>> {

    T getDataSpec();

    RecordSpec getRecordType();

}
