package utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

public class PasswordHelper {

    private PasswordHelper(){}

    public static Map<String,String> generateHashedPasswordAndSalt(String password){
        String salt = generateSalt(); // Generate a random salt

        String hashedPassword = hashPassword(password, salt);

        Map<String,String> hashedValues = new HashMap<>();
        hashedValues.put(Constants.SALT,salt);
        hashedValues.put(Constants.HASHED_PASSWORD,hashedPassword);

        return hashedValues;
    }

    public static String generateHashedPassword(String password, String salt){
        return hashPassword(password, salt);
    }

    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16]; // 16 bytes = 128 bits
        random.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    private static String hashPassword(String password, String salt) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Add the salt to the password
            String saltedPassword = password + salt;

            // Compute the hash value
            byte[] hashBytes = md.digest(saltedPassword.getBytes());

            // Convert the byte array to a hexadecimal string representation
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    public static List<String> getPasswordShortcomings(String password){

        List<String> passwordShortComings = new ArrayList<>();

        if(password.length()<6) passwordShortComings.add("Password must be at least 6 characters long.");
        if(StringHelper.countNumbersInString(password)==0) passwordShortComings.add("Password must have at lease one numeric.");
        if(StringHelper.countLowerCaseLetters(password)==0) passwordShortComings.add("Password must have at lease one lower case letter.");
        if(StringHelper.countUpperCaseLetters(password)==0) passwordShortComings.add("Password must have at lease one upper case letter.");
        if(StringHelper.countSpecialCharacters(password)==0) passwordShortComings.add("Password must have at lease one special character.");
        if(StringHelper.countSpaces(password)!=0) passwordShortComings.add("Password must not have any spaces.");

        for (String issue:passwordShortComings){
            System.out.println(issue);
        }

        return passwordShortComings;
    }

}
