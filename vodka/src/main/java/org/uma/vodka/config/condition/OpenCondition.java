package org.uma.vodka.config.condition;

import org.uma.vodka.config.spec.DataEnum;
import org.uma.vodka.config.spec.RecordSpec;

public interface OpenCondition<T extends Enum<T> & DataEnum> {

    T getDataSpec();

    RecordSpec getRecordType();

}
