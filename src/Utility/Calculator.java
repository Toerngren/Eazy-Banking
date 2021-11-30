package Utility;

import java.util.Random;

public class Calculator {

    public static int randomNumberGenerator(int max, int min){
        Random number = new Random();
        int generatedNumber = number.nextInt((max-min)+1);
        return generatedNumber;
    }
}
