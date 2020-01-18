package org.uma.jvLink.core.config.condition;

import org.uma.jvLink.core.config.spec.RecordSpec;
import org.uma.jvLink.core.config.spec.StoredDataSpec;

public class StoredOpenCondition implements OpenCondition<StoredDataSpec> {

    private final StoredDataSpec dataSpec;
    private final RecordSpec recordType;

    public StoredOpenCondition(StoredDataSpec dataSpec, RecordSpec recordType) {
        this.dataSpec = dataSpec;
        this.recordType = recordType;
    }

    @Override
    public StoredDataSpec getDataSpec() {
        return this.dataSpec;
    }

    @Override
    public RecordSpec getRecordType() {
        return this.recordType;
    }

    @Override
    public String toString() {
        return "StoredOpenCondition{" +
                "dataSpec=" + dataSpec +
                ", recordType=" + recordType +
                '}';
    }

}
