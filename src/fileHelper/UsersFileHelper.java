package fileHelper;

import utilities.PasswordHelper;
import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utilities.Constants.PATH_TO_ASSETS;

public class UsersFileHelper {

    private static final String PATH_TO_USERS_FILE = PATH_TO_ASSETS + "/users.txt";

    private UsersFileHelper() {
    }

    public static List<User> getUsers() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(PATH_TO_USERS_FILE));
        List<User> users = new ArrayList<>();
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            if(!nextLine.isEmpty()) {
                String[] userFields = nextLine.split(",");
                User user = new User(userFields[0], userFields[1], userFields[2], userFields[3]);
                users.add(user);
            }
        }
        sc.close();
        return users;
    }

    public static void addUser(User user) throws IllegalArgumentException {
        try {
            if (isSsnPresent(user.getSsn())) throw new IllegalArgumentException("SSN already present!");

            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_USERS_FILE, true));
            writer.write(user.toString()+System.lineSeparator());

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static User getUserBySsnPassword(String ssn, String password)  {

        List<User> users = null;
        try {
            users = getUsers();
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found.");
        }
        assert users != null;
        User user = getUserWithSsn(ssn,users);
        if(user==null) return null;

        String hashedPassword = PasswordHelper.generateHashedPassword(password,user.getSalt());
        if(hashedPassword.equals(user.getHashedPassword())) return user;

        return null;
    }

    public static void updateUser(User oldUser,User updatedUser) throws IOException {

        try {
            // Read the original file
            File inputFile = new File(PATH_TO_USERS_FILE);
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            // Store the modified content in a list
            List<String> modifiedContent = new ArrayList<>();

            // Replace the desired user entry
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.startsWith(oldUser.getSsn())) {
                    modifiedContent.add(updatedUser.toString());
                } else {
                    modifiedContent.add(currentLine);
                }
            }

            // Close the resources
            reader.close();

            // Write the modified content back to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile));
            for (String line : modifiedContent) {
                writer.write(line);
                writer.newLine();
            }

            // Close the writer
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean isSsnPresent(String ssn) {

        List<User> users = null;
        try {
            users = UsersFileHelper.getUsers();
        } catch (FileNotFoundException e) {
            System.out.println("Users file not found.");
        }

        assert users != null;
        for (User user : users) {
            if (user.getSsn().equals(ssn))  return true;
        }

        return false;
    }

    private static User getUserWithSsn(String ssn,List<User> users){

        for (User user : users) {
            if (user.getSsn().equals(ssn)) return user;
        }
        return null;
    }



}
