package org.uma.vodka.response;

import java.util.Objects;

public abstract class JvResult {

    private final Integer returnCode;

    public abstract static class Builder<T extends Builder<T>> {

        private final Integer returnCode;

        Builder(Integer returnCode) {
            this.returnCode = Objects.requireNonNull(returnCode);
        }

        protected abstract T self();

        abstract public JvResult build();

    }

    JvResult(Builder<?> builder) {
        returnCode = builder.returnCode;
    }

    public Integer getReturnCode() {
        return this.returnCode;
    }
}
