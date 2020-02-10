package org.uma.external.jvlink.client.config.condition;

import org.uma.external.jvlink.client.config.spec.RecordSpec;
import org.uma.external.jvlink.client.config.spec.StoredDataSpec;

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
