package org.uma.vodka.config.condition;

import org.uma.vodka.config.Option;
import org.uma.vodka.config.spec.RecordSpec;
import org.uma.vodka.config.spec.StoredDataSpec;

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


}
