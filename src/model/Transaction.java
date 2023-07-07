package model;

import utilities.StringHelper;

import java.math.BigDecimal;

public record Transaction(
        String transactionId,
        int fromAccountNumber,
        int toAccountNumber,
        BigDecimal debit,
        BigDecimal credit,
        long timestamp,
        String type
    ) {

    public Transaction{
        if(transactionId==null) transactionId= StringHelper.generateRandomId(5);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(transactionId).append(",")
                     .append(fromAccountNumber).append(",")
                     .append(toAccountNumber).append(",")
                     .append(debit).append(",")
                     .append(credit).append(",")
                     .append(timestamp).append(",")
                     .append(type);
        return stringBuilder.toString();
    }
}
