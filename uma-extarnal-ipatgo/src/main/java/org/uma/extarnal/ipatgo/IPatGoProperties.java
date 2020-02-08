package org.uma.extarnal.ipatgo;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;


@ConfigurationProperties
public class IPatGoProperties {

    private final String INetId;
    private final String subscriberNo;
    private final String password;
    private final String pArsNo;

    public IPatGoProperties(String INetId, String subscriberNo, String password, String pArsNo) {
        this.INetId = Objects.requireNonNull(INetId);
        this.subscriberNo = Objects.requireNonNull(subscriberNo);
        this.password = Objects.requireNonNull(password);
        this.pArsNo = Objects.requireNonNull(pArsNo);
    }

    public String getINetId() {
        return this.INetId;
    }

    public String getSubscriberNo() {
        return this.subscriberNo;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPArsNo() {
        return this.pArsNo;
    }
}
