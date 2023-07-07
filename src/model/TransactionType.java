package model;

public enum TransactionType {
    DEPOSIT("deposit"),
    WITHDRAWAL("withdrawal"),
    MONEY_TRANSFER("transfer");

    private final String transactionType;

    TransactionType(String transactionType) {
        this.transactionType=transactionType;
    }

    public String getTransactionType() {
        return transactionType;
    }
}
