package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {

    private StringHelper(){}
    public static int countNumbersInString(String input) {
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                count++;
            }
        }

        return count;
    }

    public static int countLowerCaseLetters(String input) {
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLowerCase(c)) {
                count++;
            }
        }

        return count;
    }

    public static int countUpperCaseLetters(String input) {
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                count++;
            }
        }

        return count;
    }

    public static int countSpaces(String input) {
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                count++;
            }
        }

        return count;
    }

    public static int countSpecialCharacters(String input) {
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                count++;
            }
        }

        return count;
    }

    public static boolean isSsnFormatCorrect(String input) {
        String pattern = "\\d{4}-[A-Za-z]{3}";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);

        if(!matcher.matches()) System.out.println("Invalid SSN.");

        return matcher.matches();
    }

    public static List<String> getFullNameShortcomings(String fullName){

        List<String> fullNameShortComings = new ArrayList<>();

        if(fullName.length()>25) fullNameShortComings.add("Name can be maximum 25 letters.");
        if(countNumbersInString(fullName)!=0) fullNameShortComings.add("Name can not have number.");
        if(countSpecialCharacters(fullName)!=0) fullNameShortComings.add("Name can not have any special character.");

        for (String issue:fullNameShortComings){
            System.out.println(issue);
        }

        return fullNameShortComings;
    }

    public static String[] convertToStringArray(Integer[] numbers) {
        String[] strings = new String[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            strings[i] = Integer.toString(numbers[i]);
        }

        return strings;
    }

    public static String generateRandomId(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public static void isBlank(String input){
        if(input.isBlank()) throw new IllegalArgumentException();
    }

}
