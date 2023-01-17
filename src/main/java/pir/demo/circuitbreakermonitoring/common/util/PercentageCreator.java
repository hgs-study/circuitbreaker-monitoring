package pir.demo.circuitbreakermonitoring.common.util;

import java.util.Random;

public class PercentageCreator {

    public static boolean half(){
        Random random = new Random();
        return random.nextBoolean();
    }
}
