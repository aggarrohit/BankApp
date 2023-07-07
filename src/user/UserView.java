package user;

import base.BaseView;
import model.Account;

public class UserView extends BaseView {


    public void wrongPassword(){
        System.out.println("You have entered a wrong password.");
    }

    public void printAccountNumber(Account account){
        System.out.println("Account Number: "+account.accountNumber()+" (balance:"+account.balance()+")");
    }

    public void accountCreated(){
        System.out.println("Account created successfully!");
    }

    public void passwordChanged(){System.out.println("Password changed successfully.");}
}
