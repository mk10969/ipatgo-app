package org.uma.jvLink.setup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SetupRunner implements CommandLineRunner {

    private final SetupConfiguration setupConfiguration;


    @Override
    public void run(String... args) throws Exception {
        setupConfiguration.setupRACE();
        setupConfiguration.setupBLOD();
        setupConfiguration.setupDIFF();
    }

}
