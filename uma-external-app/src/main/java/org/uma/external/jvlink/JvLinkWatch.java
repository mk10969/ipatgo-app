package org.uma.external.jvlink;

public class JvLinkWatch {

    private final JvLinkWrapper JvLink;

    private final JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler;


    public JvLinkWatch(JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler) {
        this.JvLink = new JvLinkWrapper();
        this.jvLinkEventHandler = jvLinkEventHandler;
    }

    public void start() {
        this.JvLink.init().watchEvent(this.jvLinkEventHandler);
    }

    public void stop() {
        this.JvLink.closeWatchEvent();
    }
}
