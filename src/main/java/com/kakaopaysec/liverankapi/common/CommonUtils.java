package com.kakaopaysec.liverankapi.common;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CommonUtils {
    private static final Random random = new Random();

    public int getTickSize(int price) {
        if (price < 10000) {
            return 1;
        } else if (price < 50000) {
            return 5;
        } else if (price < 100000) {
            return 10;
        } else if (price < 500000) {
            return 50;
        } else if (price < 1000000) {
            return 100;
        } else {
            throw new IllegalArgumentException("Amount is out of supported range");
        }
    }

    public int generateRandomInt(int min, int max, int tickSize) {
        int minTick = (int) Math.ceil((double) min / tickSize);
        int maxTick = (int) Math.floor((double) max / tickSize);
        int randomTick = random.nextInt((maxTick - minTick) + 1) + minTick;
        return randomTick * tickSize;
    }
}
