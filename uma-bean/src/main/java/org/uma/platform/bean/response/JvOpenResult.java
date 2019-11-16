package org.uma.platform.bean.response;

import java.util.Objects;

public class JvOpenResult extends JvResult {
    /**
     * 指定した条件に該当する全ファイル数
     */
    private final Integer readCount;

    /**
     * ダウンロードが必要なファイル数
     */
    private final Integer downloadCount;

    /**
     * 全ファイルのうち最も新しいファイルのタイムスタンプ
     */
    private final String lastFileTimeStamp;


    public static class Builder extends JvResult.Builder<Builder> {
        // デフォルトパラメータ
        private Integer readCount;
        private Integer downloadCount;
        private String lastFileTimeStamp;

        public Builder(Integer returnCode) {
            // 必須パラメータ
            super(returnCode);
        }

        public Builder readCount(Integer val) {
            this.readCount = Objects.requireNonNull(val);
            return self();
        }

        public Builder downloadCount(Integer val) {
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

    @Override
    public Integer getReturnCode() {
        return super.getReturnCode();
    }

    public Integer getReadCount() {
        return this.readCount;
    }

    public Integer getDownloadCount() {
        return this.downloadCount;
    }

    public String getLastFileTimeStamp() {
        return this.lastFileTimeStamp;
    }
}
