package com.dydev.mitd.test;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

class CodeTest {

    @Test
    void test() {
        long a = new Date().getTime();
        long b = (new Date()).getTime();

        System.out.println(a);
        System.out.println(b);

        Assertions.assertThat(a).isEqualTo(b);
    }

}
