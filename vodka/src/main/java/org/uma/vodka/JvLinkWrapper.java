package org.uma.vodka;

import org.uma.vodka.common.lang.DateUtil;
import org.uma.vodka.common.lang.StringUtil;
import org.uma.vodka.config.Option;
import org.uma.vodka.config.RealTimeKey;
import org.uma.vodka.config.condition.OpenCondition;
import org.uma.vodka.config.condition.RealTimeOpenCondition;
import org.uma.vodka.config.condition.StoredOpenCondition;
import org.uma.vodka.response.JvByteContent;
import org.uma.vodka.response.JvSimpleResult;
import org.uma.vodka.response.JvStringContent;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.regex.Pattern;

public class JvLinkWrapper {
    /**
     * UserAgentの最大バイト長です。
     */
    private static final int MAX_BYTE_LENGTH_USER_AGENT = 64;

    /**
     * open時に指定するための時間フォーマットです。
     */
    private static final String JV_DATE_FORMAT = "yyyyMMddHHmmss";

    private String userAgent = "UNKNOWN";

    private final JvLinkDataLab jvLinkDataLab;

    JvLinkWrapper() {
        this.jvLinkDataLab = new JvLinkDataLabImpl();
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public JvLinkWrapper setUserAgent(String userAgent) {
        Objects.requireNonNull(userAgent, "ユーザエージェントがnullになっています。");
        int length = StringUtil.getBytesLength(userAgent, Charset.forName("SJIS"));

        if (length > MAX_BYTE_LENGTH_USER_AGENT) {
            throw new IllegalArgumentException(
                    String.format("UserAgentは64バイト以下でなければいけません。: %s バイト", length));
        }
        if (!Pattern.matches("^[\\w\\s./]*$", userAgent)) {
            throw new IllegalArgumentException(String.format("%s", userAgent));
        }

        this.userAgent = userAgent;
        return this;
    }

    public JvLinkWrapper init() {
        JvLinkHandler.handle(() -> jvLinkDataLab.jvInit(getUserAgent()));
        return this;
    }

    public JvLinkWrapper setUIProperties() {
        JvLinkHandler.handle(jvLinkDataLab::jvSetUIProperties);
        return this;
    }

    public JvSimpleResult status() {
        return JvLinkHandler.handle(jvLinkDataLab::jvStatus);
    }

    public JvLinkWrapper setSaveFlag(boolean saveFlag) {
        JvLinkHandler.handle(() -> jvLinkDataLab.jvSetSaveFlag(saveFlag ? 1 : 0));
        return this;
    }

    public JvLinkWrapper setServiceKey(String serviceKey) {
        Objects.requireNonNull(serviceKey, "サービスキーがnullになっています。");
        JvLinkHandler.handle(() -> jvLinkDataLab.jvSetServiceKey(serviceKey));
        return this;
    }

    public JvLinkWrapper setSavePath(String savePath) {
        Objects.requireNonNull(savePath, "保存パスがnullになっています。");
        if (Files.notExists(Paths.get(savePath))) {
            throw new IllegalArgumentException("保存パスは実際に存在するディレクトリを指定する必要があります。");
        }
        JvLinkHandler.handle(() -> jvLinkDataLab.jvSetSavePath(savePath));
        return this;
    }

    public JvLinkWrapper open(StoredOpenCondition condition, ZonedDateTime fromTime, Option option) {
        return open(condition, DateUtil.format(JV_DATE_FORMAT, fromTime), option);
    }

    public JvLinkWrapper open(StoredOpenCondition condition, String fromTime, Option option) {
        Objects.requireNonNull(condition, "データ検索条件がnullになっています。");
        Objects.requireNonNull(fromTime, "データ読み出し開始ポイントがnullになっています。");

        JvLinkHandler.handle(
                () -> jvLinkDataLab.jvOpen(condition.getDataSpec().getCode(), fromTime, option.getCode())
        );
        return this;
    }

    public JvLinkWrapper rtOpen(RealTimeOpenCondition condition, RealTimeKey rtKey) {
        Objects.requireNonNull(condition, "データ検索条件がnullになっています。");
        Objects.requireNonNull(rtKey, "keyがnullになっています。");
        JvLinkHandler.handle(
                () -> jvLinkDataLab.jvRtOpen(condition.getDataSpec().getCode(), rtKey.toString())
        );
        return this;
    }

    public JvLinkReader<JvByteContent> gets(OpenCondition condition) {
        return new JvLinkReader<>(
                () -> jvLinkDataLab.jvGets(condition.getRecordType().getLength())
        );
    }

    public JvLinkReader<JvStringContent> read(OpenCondition condition) {
        return new JvLinkReader<>(
                () -> jvLinkDataLab.jvRead(condition.getRecordType().getLength())
        );
    }

    public void cancel() {
        jvLinkDataLab.jvCancel();
    }

    public void skip() {
        jvLinkDataLab.jvSkip();
    }

    public void close() {
        JvLinkHandler.handle(jvLinkDataLab::jvClose);
    }

}
