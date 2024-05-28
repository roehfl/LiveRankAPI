package com.kakaopaysec.liverankapi.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CommonUtilsTest {
    Random random;
    CommonUtils commonUtils;

    @BeforeEach
    public void setUp() {
        random = new Random();
        commonUtils = new CommonUtils();
    }

    @Test
    public void testGetTickSize() {
        assertEquals(1, commonUtils.getTickSize(1000));
        assertEquals(5, commonUtils.getTickSize(40000));
        assertEquals(10, commonUtils.getTickSize(70000));
        assertEquals(100, commonUtils.getTickSize(700000));
        assertThrows(IllegalArgumentException.class, () -> commonUtils.getTickSize(2000000));
    }

    @Test
    public void testGenerateRandomInt() {
        int min = 10000;
        int max = 50000;

        for (int i = 0; i < 500; i++) {
            int tickSize = commonUtils.getTickSize(max);
            int randomInt = commonUtils.generateRandomInt(min, max, tickSize);
            assertTrue(randomInt >= min);
            assertTrue(randomInt <= max);
            assertEquals(0, randomInt % tickSize);
        }
    }
}
