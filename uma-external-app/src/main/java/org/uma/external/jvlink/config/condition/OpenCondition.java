package org.uma.external.jvlink.config.condition;

import org.uma.external.jvlink.config.DataEnum;
import org.uma.external.jvlink.config.spec.RecordSpec;

public interface OpenCondition<T extends Enum<T> & DataEnum<String, T>> {

    T getDataSpec();

    RecordSpec getRecordType();

}
