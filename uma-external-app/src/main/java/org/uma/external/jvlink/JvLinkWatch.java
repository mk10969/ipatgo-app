package org.uma.external.jvlink;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class JvLinkWatch {

    private static final String userAgent = "UNKNOWN";

    private final JvLinkDataLab jvLinkDataLab;

    private final JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler;


    public JvLinkWatch(JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler) {
        this.jvLinkDataLab = new JvLinkDataLabImpl();
        this.jvLinkEventHandler = Objects.requireNonNull(jvLinkEventHandler);
    }

    public void start() {
        this.jvInit();
        this.jvWatchEvent();
    }

    public void stop() {
        this.jvWatchEventClose();
    }

    public void jvInit() {
        JvLinkHandler.handle(() -> jvLinkDataLab.jvInit(userAgent));
        log.info("init");
    }

    private void jvWatchEvent() {
        JvLinkHandler.handle(() -> jvLinkDataLab.jvWatchEvent(this.jvLinkEventHandler));
        log.info("watch event!");
    }

    private void jvWatchEventClose() {
        JvLinkHandler.handle(jvLinkDataLab::jvWatchEventClose);
        log.info("close event!");
    }

}
