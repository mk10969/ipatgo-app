package org.uma.extarnal.ipatgo;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
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
