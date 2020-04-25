package org.uma.external.jvlink;

import com.jacob.com.Variant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JvLinkEventCallback {

    private final JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler;

    public JvLinkEventCallback(JvLinkDataLab.JvLinkEventHandler jvLinkEventHandler) {
        this.jvLinkEventHandler = jvLinkEventHandler;
    }

    /**
     * JVWatchEventで定義されている「イベントメソッド名」と同じシグネチャを用意する。
     * <p>
     * 渡されるパラメータ： "YYYYMMDDJJRR"
     */
    public void JVEvtPay(Variant[] variants) {
        log.debug("caught JV-Link event 'JVEvtPay'!");
        jvLinkEventHandler.handlePay(getParameter(variants));
    }

    public void JVEvtWeight(Variant[] variants) {
        log.debug("caught JV-Link event 'JVEvtWeight'!");
        jvLinkEventHandler.handleWeight(getParameter(variants));
    }


    /**
     * 渡されるパラメータ： "TTYYYYMMDDJJRRNNNNNNNNNNNNNN"
     */
    public void JVEvtJockeyChange(Variant[] variants) {
        log.debug("caught JV-Link event 'JVEvtJockeyChange'!");
        jvLinkEventHandler.handleJockeyChange(getParameter(variants));
    }

    public void JVEvtWeather(Variant[] variants) {
        log.debug("caught JV-Link event 'JVEvtWeather'!");
        jvLinkEventHandler.handleWeather(getParameter(variants));
    }

    public void JVEvtCourseChange(Variant[] variants) {
        log.debug("caught JV-Link event 'JVEvtCourseChange'!");
        jvLinkEventHandler.handleCourseChange(getParameter(variants));
    }

    public void JVEvtAvoid(Variant[] variants) {
        log.debug("caught JV-Link event 'JVEvtAvoid'!");
        jvLinkEventHandler.handleAvoid(getParameter(variants));
    }

    public void JVEvtTimeChange(Variant[] variants) {
        log.debug("caught JV-Link event 'JVEvtTimeChange'!");
        jvLinkEventHandler.handleTimeChange(getParameter(variants));
    }

    private String getParameter(Variant[] variants) {
        return variants[0].getString();
    }
}
