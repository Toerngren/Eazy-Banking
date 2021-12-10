package Utility;

public class Utilities {

    public static double truncateDouble(double value, int decimalsNumber) {
        return (double) ((int) (value * Math.pow(10, decimalsNumber))) / Math.pow(10, decimalsNumber);
    }
}
