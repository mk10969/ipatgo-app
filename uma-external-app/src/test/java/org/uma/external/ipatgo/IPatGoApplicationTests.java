package org.uma.external.ipatgo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


class IPatGoApplicationTests {

    @Test
    void test_cmd() throws ExecutionException, InterruptedException {
        List<String> cmd = Arrays.asList("java", "-version");
        CompletableFuture<Integer> result = IPatGoCommandRunner.execute(cmd);
        Assertions.assertEquals(result.get(), 0);
    }

//    @Test
//    void test_cmd2() throws ExecutionException, InterruptedException {
//        List<String> cmd = Arrays.asList("/tmp/jarfile/once.sh");
//        CompletableFuture<Integer> result = IPatGoCommandRunner.execute(cmd);
//        Assertions.assertEquals(result.get(), 0);
//    }


}
