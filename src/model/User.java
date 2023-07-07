package model;

import utilities.Constants;
import utilities.PasswordHelper;
import utilities.StringHelper;
import java.util.Map;

public class User {

    private final String ssn;
    private final String fullName;
    private final String salt;
    private final String hashedPassword;

    //constructor for accessing existing users with salt and hashed passwords
    public User(String ssn, String fullName, String salt, String hashedPassword){
        this.fullName=fullName;
        this.ssn=ssn;
        this.salt=salt;
        this.hashedPassword=hashedPassword;
    }

    //Constructor for creating new user with hashed password and salt
    public User(String ssn, String fullName, String password) throws IllegalArgumentException{

            if (!StringHelper.isSsnFormatCorrect(ssn)) throw new IllegalArgumentException("SSN is not valid!");
            if (StringHelper.getFullNameShortcomings(fullName).size() > 0)
                throw new IllegalArgumentException("Name not valid!");
            if (PasswordHelper.getPasswordShortcomings(password).size() > 0) {
                throw new IllegalArgumentException("Password not valid!");
            }


            Map<String, String> hashedPasswordAndSalt = PasswordHelper.generateHashedPasswordAndSalt(password);
            this.fullName=fullName;
            this.ssn=ssn;
            this.salt = hashedPasswordAndSalt.get(Constants.SALT);
            this.hashedPassword = hashedPasswordAndSalt.get(Constants.HASHED_PASSWORD);


    }

    public String toString(){
        StringBuffer stringBuffer = new StringBuffer();
         stringBuffer.append(ssn).append(",")
                     .append(fullName).append(",")
                     .append(salt).append(",")
                     .append(hashedPassword);
        return stringBuffer.toString();
    }

    public String getSsn() {
        return ssn;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getSalt() {
        return salt;
    }

    public String getFullName() {
        return fullName;
    }

}
