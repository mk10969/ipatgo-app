package org.uma.platform.feed.application.jvlink.response;


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

    @Override
    public Integer getReturnCode() {
        return super.getReturnCode();
    }

}
