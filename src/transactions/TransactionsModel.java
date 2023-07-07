package transactions;

import user.UserModel;

import java.math.BigDecimal;

public class TransactionsModel extends UserModel {


    public void setEnteredAmount(BigDecimal enteredAmount) {
        this.enteredAmount = enteredAmount;
    }

    public BigDecimal getEnteredAmount() {
        return enteredAmount;
    }

    private BigDecimal enteredAmount;

}
