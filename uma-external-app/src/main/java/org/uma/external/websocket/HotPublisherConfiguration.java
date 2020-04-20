package org.uma.external.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;

import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
public class HotPublisherConfiguration {

    /**
     * hot source => publish()することで、Dynamicにデータを配信することができる
     * ただし、unicastなので、publish()できるのは、１つだけ。
     */
    @Bean
    public UnicastProcessor<String> hotSource() {
        return UnicastProcessor.create(new ConcurrentLinkedQueue<>());
    }

    /**
     * シングルトン、HotPublisher
     *
     * @param processor hotSource (unicast)
     * @return Flux<String>
     */
    @Bean
    public Flux<String> hotPublisher(UnicastProcessor<String> processor) {
        return processor.publish().autoConnect();
    }

}
