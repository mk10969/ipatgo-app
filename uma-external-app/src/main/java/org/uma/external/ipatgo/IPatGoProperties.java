package org.uma.external.ipatgo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ipatgo.authentication")
public class IPatGoProperties {

    @Getter
    @Setter
    private String INetId;

    @Getter
    @Setter
    private String subscriberNo;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String parsNo;

}
