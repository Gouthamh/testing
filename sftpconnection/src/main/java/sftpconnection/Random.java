package sftpconnection;

public class Random {

    private static final int MAX_VALUE = 1000000;

    public static void main(String[] args) {
        int minValue = 0;
        int randomNumber = generateRandomNumber(minValue, MAX_VALUE);
        System.out.println("random " + randomNumber);
    }

    private static int generateRandomNumber(int minValue, int maxValue) {
        return (int)((Math.random() * (maxValue - minValue)) + minValue);
    }
}
