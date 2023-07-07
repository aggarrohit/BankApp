package model;

import java.math.BigDecimal;

public record Account(int accountNumber, String ssn, BigDecimal balance) {

    //custom toString method to save account info in text file
    @Override
    public String toString() {
        return accountNumber + "," +
                ssn + "," +
                balance;
    }
}
