package org.uma.external.jvlink;

import org.junit.jupiter.api.Test;

public class TestUseClass {


    @Test
    void test(){

        TestGeneric1 gene1 = new TestGeneric1();
        TestGeneric2 gene2 = new TestGeneric2();
        TestGeneric3 gene3 = new TestGeneric3();

        use(gene1);
        use(gene2);
        // interfaceで、Genericが有効
//        use(gene3);

    }

    public <T extends TestGeneric> void use(T test) {
        System.out.println(test.getName());

    }

}
