package transactions;

import user.UserView;

public class TransactionsView extends UserView {

    public void invalidOption(){
        System.out.println("Invalid option selected");
    }
    public void askForAmount(){
        System.out.print("Enter Amount: ");
    }

    public void askForAccountNumberOption(){
        System.out.println("Choose account serial number from below list");
    }

    public void showInsufficientBalance(){
        System.out.println("Sorry, your account does not have sufficient balance to complete this transaction");
    }

}
