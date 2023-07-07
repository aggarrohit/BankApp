package fileHelper;

import model.Transaction;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static utilities.Constants.PATH_TO_ASSETS;

public class TransactionsFileHelper {

    private static final String PATH_TO_transactions_FILE = PATH_TO_ASSETS + "/transactions.txt";

    private TransactionsFileHelper() {
    }

    public static List<Transaction> getAccountTransactions(int accountNumber) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(PATH_TO_transactions_FILE));
        List<Transaction> transactions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if(!nextLine.isEmpty()) {
                populateWithAccountTransaction(accountNumber, transactions, nextLine);

            }
        }
        scanner.close();
        return transactions;
    }

    public static void addTransaction(Transaction transaction) throws IllegalArgumentException {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(PATH_TO_transactions_FILE, true));
            writer.write(transaction.toString()+System.lineSeparator());

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void populateWithAccountTransaction(int accountNumber, List<Transaction> transactions, String transactionString) {
        String[] transactionFields = transactionString.split(",");

        int fromAccountNumber = Integer.parseInt(transactionFields[1]);
        int toAccountNumber = Integer.parseInt(transactionFields[2]);

        if(fromAccountNumber== accountNumber || toAccountNumber== accountNumber) {
            Transaction transaction = new Transaction(
                    transactionFields[0],
                    Integer.parseInt(transactionFields[1]),
                    Integer.parseInt(transactionFields[2]),
                    new BigDecimal(transactionFields[3]),
                    new BigDecimal(transactionFields[4]),
                    Long.parseLong(transactionFields[5]),
                    transactionFields[6]);
            transactions.add(transaction);
        }
    }



}
