package org.uma.jvLink.client.response;

import java.util.Objects;

public class JvStringContent extends JvResult implements JvContent<String> {
    /**
     * JV-Data文字列
     */
    private final String line;

    /**
     * データ保存ファイル名
     */
    private final String fileName;

    public static class Builder extends JvResult.Builder<Builder> {
        private String line;
        private String fileName;

        // 必須パラメータ
        public Builder(Integer returnCode) {
            super(returnCode);
        }

        public Builder line(String val) {
            this.line = Objects.requireNonNull(val);
            return self();
        }

        public Builder fileName(String val) {
            this.fileName = Objects.requireNonNull(val);
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public JvStringContent build() {
            return new JvStringContent(this);
        }
    }

    private JvStringContent(Builder builder) {
        super(builder);
        this.line = builder.line;
        this.fileName = builder.fileName;
    }

    public static Builder create(Integer returnCode) {
        return new Builder(returnCode);
    }
    
    @Override
    public int getReturnCode() {
        return super.getReturnCode();
    }

    @Override
    public String getLine() {
        return this.line;
    }

    public String getFileName() {
        return this.fileName;
    }

}
