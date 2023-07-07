package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public record Transactions(List<Transaction> transactions) {


    public void printTable(int accountNumber) {

        printHeader(accountNumber);
        String transactionsTableFormat = "%-7s %-5s %-12s %-12s %-8s %-8s %-15s%n";
        System.out.printf(transactionsTableFormat, "Sr.No."," ID", "From Acc.", "To Acc.", "Debit.", "Credit", "Type");
        printSeparatorLine();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = replaceIfTransferredFromAnotherAccount(transactions.get(i),accountNumber);
            System.out.printf(transactionsTableFormat,
                    i+1,
                    transaction.transactionId(),
                    replaceIfSelfAccount(transaction.fromAccountNumber(),accountNumber),
                    replaceIfSelfAccount(transaction.toAccountNumber(),accountNumber),
                    transaction.debit(),
                    transaction.credit(),
                    transaction.type());
        }
        printSeparatorLine();
    }

    //when money is transferred from another account then it is a debit for sender but credit for receiver, so changing debit amount to credit amount when receiving money
    private Transaction replaceIfTransferredFromAnotherAccount(Transaction transaction,int accountNumber){
        boolean isTransactionTypeMoneyTransfer = transaction.type().equals(TransactionType.MONEY_TRANSFER.getTransactionType());
        boolean isToAccountSelfAccount = accountNumber==transaction.toAccountNumber();
        if(isTransactionTypeMoneyTransfer && isToAccountSelfAccount){
            return new Transaction(
                    transaction.transactionId(),
                    transaction.fromAccountNumber(),
                    transaction.toAccountNumber(),
                    new BigDecimal(BigInteger.ZERO),
                    transaction.debit(),
                    transaction.timestamp(),
                    transaction.type()
            );
        }
        return transaction;
    }

    //replacing own account number with "self" keyword for ease of user
    private String replaceIfSelfAccount(int accountNumber,int selfAccountNumber){
        if(accountNumber==selfAccountNumber) return "self";
        return String.valueOf(accountNumber);
    }

    private void printSeparatorLine(){
        System.out.println("----------------------------------------------------------------------");
    }

    private void printHeader(int accountNumber){
        printSeparatorLine();
        System.out.println("******************* Transactions (Account #"
                            +accountNumber
                            +") *******************");
        printSeparatorLine();
    }
}
