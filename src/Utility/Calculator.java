package Utility;

import java.math.BigInteger;
import java.util.Random;
import java.util.UUID;


public class Calculator {

    // source: https://stackoverflow.com/questions/66836228/java-uuid-use-only-numeric-characters-to-generate-random-uuid
    public static String randomNumberGenerator(){

        UUID uuid = UUID.randomUUID();
        long lo = uuid.getLeastSignificantBits();
        long hi = uuid.getMostSignificantBits();
        lo = (lo >> (64 - 44)) ^ lo;
        hi = (hi >> (64 - 44)) ^ hi;
        String s = String.format("%06d", Math.abs(hi) + Math.abs(lo));
        return s.substring(s.length() - 6);
    }
}
