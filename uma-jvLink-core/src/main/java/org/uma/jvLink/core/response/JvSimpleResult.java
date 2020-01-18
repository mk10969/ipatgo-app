package org.uma.jvLink.core.response;


public class JvSimpleResult extends JvResult {

    public static class Builder extends JvResult.Builder<Builder> {

        public Builder(Integer returnCode) {
            super(returnCode);
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public JvSimpleResult build() {
            return new JvSimpleResult(this);
        }
    }

    private JvSimpleResult(Builder builder) {
        super(builder);
    }

    public static Builder create(Integer returnCode) {
        return new Builder(returnCode);
    }

    @Override
    public int getReturnCode() {
        return super.getReturnCode();
    }

}
