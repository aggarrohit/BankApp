package fileHelper;

import model.Account;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utilities.Constants.PATH_TO_ASSETS;

public class AccountsFileHelper {

    private static final String PATH_TO_ACCOUNTS_FILE = PATH_TO_ASSETS + "/accounts.txt";

    private AccountsFileHelper(){

    }

    public static List<Account> getAccounts() throws FileNotFoundException {
        Scanner sc = new Scanner(new File(PATH_TO_ACCOUNTS_FILE));
        List<Account> accounts = new ArrayList<>();
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            if(!nextLine.isEmpty()) {
                String[] accountFields = nextLine.split(",");
                Account account = new Account(
                        Integer.parseInt(accountFields[0]),
                        accountFields[1],
                        new BigDecimal(accountFields[2])
                );
                accounts.add(account);
            }
        }
        sc.close();
        return accounts;
    }

    public static void addAccount(Account account) throws IllegalArgumentException {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_ACCOUNTS_FILE, true));
            writer.write(account.toString()+System.lineSeparator());

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void updateAccount(Account oldAccount,Account updatedAccount) throws IOException {

        Scanner sc = new Scanner(new File(PATH_TO_ACCOUNTS_FILE));
        StringBuffer buffer = new StringBuffer();
        //Reading lines of the file and appending them to StringBuffer
        while (sc.hasNextLine()) {
            buffer.append(sc.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();
        //closing the Scanner object
        sc.close();

        //Replacing the old line with new line
        fileContents = fileContents.replaceAll(oldAccount.toString(), updatedAccount.toString());
        //instantiating the FileWriter class
        FileWriter writer = new FileWriter(PATH_TO_ACCOUNTS_FILE);
        writer.append(fileContents);
        writer.flush();

    }


    public static Account getAccountWithSsn(String ssn) throws FileNotFoundException {

        List<Account> accounts = getAccounts();

        for (Account account : accounts) {
            if (account.ssn().equals(ssn)) {
                return account;
            }
        }
        return null;
    }

    public static Account getAccountWithAccountNumber(int accountNumber) throws FileNotFoundException {

        List<Account> accounts = getAccounts();

        for (Account account : accounts) {
            if (account.accountNumber()==accountNumber) {
                return account;
            }
        }
        return null;
    }

}
