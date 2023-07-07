package utilities;

import java.util.Random;

public class AccountHelper {

    private AccountHelper(){

    }
    public static int generateAccountNumber(){
        int randomFourDigitNumber = generateRandomFourDigitNumber();
        int checkSum = getSumOfAllDigits(randomFourDigitNumber);
        String accountNumberString = String.valueOf(randomFourDigitNumber) + checkSum;
        return Integer.parseInt(accountNumberString);
    }

    private static int generateRandomFourDigitNumber() {
        Random random = new Random();
        int min = 1000;
        int max = 9999;

        return random.nextInt(max - min + 1) + min;
    }

    private static int getSumOfAllDigits(int number) {
        int sum = 0;

        while (number != 0) {
            int digit = number % 10;
            sum += digit;
            number /= 10;
        }

        return sum;
    }
}
