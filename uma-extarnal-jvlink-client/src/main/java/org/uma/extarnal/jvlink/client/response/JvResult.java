package org.uma.extarnal.jvlink.client.response;

public abstract class JvResult {

    private final int returnCode;

    protected abstract static class Builder<T extends Builder<T>> {

        private final int returnCode;

        Builder(int returnCode) {
            this.returnCode = returnCode;
        }

        protected abstract T self();

        abstract public JvResult build();

    }

    JvResult(Builder<?> builder) {
        returnCode = builder.returnCode;
    }

    public int getReturnCode() {
        return this.returnCode;
    }
}
