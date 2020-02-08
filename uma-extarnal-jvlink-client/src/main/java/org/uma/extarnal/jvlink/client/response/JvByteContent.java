package org.uma.extarnal.jvlink.client.response;

import java.util.Objects;

public class JvByteContent extends JvResult implements JvContent<byte[]> {

    /**
     * JV-Data Byte配列
     */
    private final byte[] line;

    /**
     * データ保存ファイル名
     */
    private final String fileName;

    public static class Builder extends JvResult.Builder<Builder> {
        private byte[] line;
        private String fileName;

        public Builder(Integer returnCode) {
            super(returnCode);
        }

        public Builder line(byte[] val) {
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
        public JvByteContent build() {
            return new JvByteContent(this);
        }
    }


    private JvByteContent(Builder builder) {
        super(builder);
        this.line = builder.line;
        this.fileName = builder.fileName;
    }

    @Override
    public int getReturnCode() {
        return super.getReturnCode();
    }

    @Override
    public byte[] getLine() {
        return this.line;
    }

    public String getFileName() {
        return this.fileName;
    }

}
