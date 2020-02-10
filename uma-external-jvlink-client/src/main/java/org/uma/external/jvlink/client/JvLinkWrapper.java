package org.uma.external.jvlink.client;

import com.jacob.com.ComThread;
import lombok.extern.slf4j.Slf4j;
import org.uma.external.jvlink.client.config.condition.OpenCondition;
import org.uma.external.jvlink.client.config.condition.RealTimeOpenCondition;
import org.uma.external.jvlink.client.config.condition.StoredOpenCondition;
import org.uma.external.jvlink.client.config.option.Option;
import org.uma.external.jvlink.client.config.option.RealTimeKey;
import org.uma.external.jvlink.client.response.JvSimpleResult;
import org.uma.external.jvlink.client.response.JvStringContent;
import org.uma.external.jvlink.client.util.DateUtil;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;


@Slf4j
public class JvLinkWrapper {
    /**
     * UserAgentの最大バイト長
     */
    private static final int MAX_BYTE_LENGTH_USER_AGENT = 64;

    /**
     * open時に指定するための時間フォーマット
     */
    private static final String JV_DATE_FORMAT = "yyyyMMddHHmmss";

    private static final String userAgent = "UNKNOWN";

    private JvLinkDataLab jvLinkDataLab;

    // TODO: もうちょっとなんとかならんか。。
    JvLinkWrapper() {
    }


    /**
     * COMを使うごとに、JvLinkDataLabImplを都度生成する
     */
    private void create() {
        ComThread.InitSTA();
        this.jvLinkDataLab = new JvLinkDataLabImpl();

    }

    private void destroy() {
        ComThread.Release();
        this.jvLinkDataLab = null;
    }


    public JvLinkWrapper init() {
        this.create();
        log.info("init");
        JvLinkHandler.handle(() -> jvLinkDataLab.jvInit(userAgent));
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

    public JvLinkWrapper open(StoredOpenCondition condition, LocalDateTime fromTime, Option option) {
        Objects.requireNonNull(fromTime, "データ読み出し開始ポイントがnullになっています。");
        return open(condition, DateUtil.format(JV_DATE_FORMAT, fromTime), option);
    }

    public JvLinkWrapper open(StoredOpenCondition condition, String fromTime, Option option) {
        Objects.requireNonNull(condition, "データ検索条件がnullになっています。");
        log.info("open: {}", condition);
        JvLinkHandler.handle(() ->
                jvLinkDataLab.jvOpen(condition.getDataSpec().getCode(), fromTime, option.getCode())
        );
        return this;
    }

    public JvLinkWrapper rtOpen(RealTimeOpenCondition condition, RealTimeKey rtKey) {
        Objects.requireNonNull(condition, "データ検索条件がnullになっています。");
        Objects.requireNonNull(rtKey, "keyがnullになっています。");
        log.info("rtOpen: {}", condition);
        JvLinkHandler.handle(() ->
                jvLinkDataLab.jvRtOpen(condition.getDataSpec().getCode(), rtKey.get())
        );
        return this;
    }

    public JvLinkReader<JvStringContent> read(OpenCondition<?> condition) {
        log.info("read: {}", condition);
        return new JvLinkReader<>(() ->
                jvLinkDataLab.jvRead(condition.getRecordType().getLength())
        );
    }

    public void cancel() {
        jvLinkDataLab.jvCancel();
    }

    public void skip() {
        jvLinkDataLab.jvSkip();
    }

    public void close() {
        log.info("close");
        jvLinkDataLab.jvClose();
        this.destroy();
    }

}