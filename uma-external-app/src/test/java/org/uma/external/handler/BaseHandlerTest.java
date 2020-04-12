package org.uma.external.handler;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BaseHandlerTest {

    /**
     * single()は、一つ以外throwしてくれる！便利！
     * @throws InterruptedException
     */
    @Test
    void test_flux_single() throws InterruptedException {

        String one = Flux.fromIterable(Arrays.asList("1"))
                .single()
                .block();
        assertEquals("1", one);
        Thread.sleep(1000);
    }

    @Test
    void test_flux_single_error() throws InterruptedException {
        assertThrows(IndexOutOfBoundsException.class, () -> Flux.fromIterable(Arrays.asList("1", "2"))
                .single()
                .block());
        Thread.sleep(1000);
    }

    @Test
    void test_flux_single_error2() throws InterruptedException {
        assertThrows(NoSuchElementException.class, () -> Flux.fromIterable(Arrays.asList())
                .single()
                .block());
        Thread.sleep(1000);
    }


}