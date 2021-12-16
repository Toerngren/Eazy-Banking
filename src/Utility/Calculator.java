package Utility;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;


public class Calculator {
    private static ArrayList<String> ids = new ArrayList<>();

    // source: https://stackoverflow.com/questions/66836228/java-uuid-use-only-numeric-characters-to-generate-random-uuid
    public static String randomNumberGenerator(){
        UUID uuid = UUID.randomUUID();
        long lo = uuid.getLeastSignificantBits();
        long hi = uuid.getMostSignificantBits();
        lo = (lo >> (64 - 44)) ^ lo;
        hi = (hi >> (64 - 44)) ^ hi;
        String s = String.format("%06d", Math.abs(hi) + Math.abs(lo));
        s = s.substring(s.length() - 6);
        for(String id : ids) {
            if (id.equals(s)) {
                randomNumberGenerator();
            }

        }
        ids.add(s);
        return s;

    }
}
