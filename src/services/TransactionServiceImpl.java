package services;

import fileHelper.AccountsFileHelper;
import fileHelper.TransactionsFileHelper;
import model.Account;
import model.Transaction;
import transactions.TransactionsController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import static model.TransactionType.*;

public class TransactionServiceImpl {
    public static void addMoneyToSelfAccount(BigDecimal amount, TransactionsController transactionsController) throws IOException {
        String loggedInUserSsn = transactionsController.getModel().getLoggedInUser().getSsn();
        Account account = AccountsFileHelper.getAccountWithSsn(loggedInUserSsn);
        assert account != null;

        BigDecimal updatedBalance = account.balance().add(amount);
        Account updatedAccount = new Account(account.accountNumber(),account.ssn(), updatedBalance);

        //replacing account to update balance
        AccountsFileHelper.updateAccount(account,updatedAccount);
        Transaction transaction = new Transaction(
                null,
                0,
                account.accountNumber(),
                new BigDecimal("0"),
                amount,
                Calendar.getInstance().getTimeInMillis(),
                DEPOSIT.getTransactionType()
        );
        //adding transaction entry
        TransactionsFileHelper.addTransaction(transaction);
    }

    public static void withdrawMoneyFromSelfAccount(BigDecimal amount, TransactionsController transactionsController) throws IOException {
        Account account = AccountsFileHelper.getAccountWithSsn(transactionsController.getModel().getLoggedInUser().getSsn());
        assert account != null;

        BigDecimal newBalance = account.balance().subtract(amount);
        if(newBalance.compareTo(BigDecimal.ZERO)<0){
            insufficientBalance(transactionsController);
        }else{
            Account updatedAccount = new Account(account.accountNumber(),account.ssn(),newBalance);
            AccountsFileHelper.updateAccount(account,updatedAccount);
            Transaction transaction = new Transaction(
                    null,
                    account.accountNumber(),
                    0,
                    amount,
                    new BigDecimal("0"),
                    Calendar.getInstance().getTimeInMillis(),
                    WITHDRAWAL.getTransactionType()
            );
            TransactionsFileHelper.addTransaction(transaction);
        }
    }

    private static void insufficientBalance(TransactionsController transactionsController) {
        transactionsController.getView().clearTerminal();
        transactionsController.getView().showInsufficientBalance();
        transactionsController.getUserController().showUserMenu();
    }


    public static void sendMoneyFromSelfAccount(int toAccountNumber,TransactionsController transactionsController) throws IOException {

        transactionsController.getView().askForAmount();
        transactionsController.requestAmount();
        BigDecimal enteredAmount =transactionsController.getModel().getEnteredAmount();
        if(enteredAmount.compareTo(BigDecimal.ZERO)<=0) throw new IllegalArgumentException("Invalid amount entered.");

        Account account = AccountsFileHelper.getAccountWithSsn(transactionsController.getModel().getLoggedInUser().getSsn());
        assert account != null;
        BigDecimal newBalance = account.balance().subtract(enteredAmount);
        if(newBalance.compareTo(BigDecimal.ZERO)<0){
            insufficientBalance(transactionsController);
        }else{
            transferMoney(toAccountNumber, enteredAmount, account);
        }
    }

    //after all checks this method transfers balance between accounts
    private static void transferMoney(int toAccountNumber, BigDecimal enteredAmount, Account fromAccount) throws IOException {
        BigDecimal newBalance = fromAccount.balance().subtract(enteredAmount);
        //update sender's account balance
        Account updatedAccount = new Account(fromAccount.accountNumber(), fromAccount.ssn(), newBalance);
        AccountsFileHelper.updateAccount(fromAccount,updatedAccount);

        //update receiver's account balance
        Account receiverAccount = AccountsFileHelper.getAccountWithAccountNumber(toAccountNumber);
        assert receiverAccount != null;
        Account updatedReceiverAccount = new Account(toAccountNumber,receiverAccount.ssn(),
                receiverAccount.balance().add(enteredAmount));
        AccountsFileHelper.updateAccount(receiverAccount,updatedReceiverAccount);

        //add transaction for sending and receiving
        Transaction transaction = new Transaction(
                null,
                fromAccount.accountNumber(),
                toAccountNumber,
                enteredAmount,
                new BigDecimal("0"),
                Calendar.getInstance().getTimeInMillis(),
                MONEY_TRANSFER.getTransactionType()
        );
        TransactionsFileHelper.addTransaction(transaction);
    }
}
