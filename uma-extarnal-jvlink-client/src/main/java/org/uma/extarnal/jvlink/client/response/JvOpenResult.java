package org.uma.extarnal.jvlink.client.response;

import java.util.Objects;

public class JvOpenResult extends JvResult {
    /**
     * 指定した条件に該当する全ファイル数
     */
    private final int readCount;

    /**
     * ダウンロードが必要なファイル数
     */
    private final int downloadCount;

    /**
     * 全ファイルのうち最も新しいファイルのタイムスタンプ
     */
    private final String lastFileTimeStamp;


    public static class Builder extends JvResult.Builder<Builder> {
        // デフォルトパラメータ
        private int readCount;
        private int downloadCount;
        private String lastFileTimeStamp;

        public Builder(int returnCode) {
            // 必須パラメータ
            super(returnCode);
        }

        public Builder readCount(int val) {
            this.readCount = Objects.requireNonNull(val);
            return self();
        }

        public Builder downloadCount(int val) {
            this.downloadCount = Objects.requireNonNull(val);
            return self();
        }

        public Builder lastFileTimeStamp(String val) {
            this.lastFileTimeStamp = Objects.requireNonNull(val);
            return self();
        }

        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public JvOpenResult build() {
            return new JvOpenResult(this);
        }
    }

    private JvOpenResult(Builder builder) {
        super(builder);
        this.readCount = builder.readCount;
        this.downloadCount = builder.downloadCount;
        this.lastFileTimeStamp = builder.lastFileTimeStamp;
    }

    public static Builder create(int returnCode) {
        return new Builder(returnCode);
    }

    @Override
    public int getReturnCode() {
        return super.getReturnCode();
    }

    public int getReadCount() {
        return this.readCount;
    }

    public int getDownloadCount() {
        return this.downloadCount;
    }

    public String getLastFileTimeStamp() {
        return this.lastFileTimeStamp;
    }
}
