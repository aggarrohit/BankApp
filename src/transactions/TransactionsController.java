package transactions;

import base.BaseController;
import services.TransactionServiceImpl;
import services.ITransactionServices;
import user.UserController;
import fileHelper.AccountsFileHelper;
import model.Account;
import utilities.StringHelper;
import model.TransactionType;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class TransactionsController extends BaseController implements ITransactionServices {
    TransactionsModel transactionsModel;
    TransactionsView transactionsView;
    UserController userController;

    public TransactionsController(TransactionsModel transactionsModel,TransactionsView transactionsView, UserController userController) {

        super(transactionsModel,transactionsView);
        this.transactionsModel = transactionsModel;
        this.transactionsView = transactionsView;
        this.userController=userController;

    }

    public TransactionsView getView(){
        return transactionsView;
    }

    public TransactionsModel getModel(){
        return transactionsModel;
    }
    public UserController getUserController(){
        return userController;
    }
    public void handleTransaction(TransactionType transactionType){
        try {
            switch (transactionType){

                case    DEPOSIT         -> depositMoney();
                case    WITHDRAWAL      -> withdrawMoney();
                case    MONEY_TRANSFER  -> sendMoney();
                default                 -> System.out.println("default");

            }
            userController.showUserMenu();
        } catch (IOException  e) {
            throw new RuntimeException(e);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            userController.showUserMenu();
        }
    }

    @Override
    public void depositMoney() throws IOException {
        transactionsView.askForAmount();
        requestAmount();
        BigDecimal enteredAmount =transactionsModel.getEnteredAmount();
        if(enteredAmount.compareTo(BigDecimal.ZERO)<=0) throw new IllegalArgumentException("Invalid amount entered.");

        TransactionServiceImpl.addMoneyToSelfAccount(enteredAmount,this);
    }

    @Override
    public void withdrawMoney() throws IOException {
        transactionsView.askForAmount();
        requestAmount();
        BigDecimal enteredAmount =transactionsModel.getEnteredAmount();
        if(enteredAmount.compareTo(BigDecimal.ZERO)<=0) throw new IllegalArgumentException("Invalid amount entered.");
        TransactionServiceImpl.withdrawMoneyFromSelfAccount(enteredAmount,this);
    }

    @Override
    public void sendMoney() throws IOException {

        String loggedInUserSsn = transactionsModel.getLoggedInUser().getSsn();
        Account selfAccount = AccountsFileHelper.getAccountWithSsn(loggedInUserSsn);

        transactionsView.askForAccountNumberOption();

        Integer[] accounts = AccountsFileHelper
                .getAccounts()
                .stream()
                //extracting the account numbers
                .map(Account::accountNumber)
                //removing own account number
                .filter(accountNumber -> {
                    assert selfAccount != null;
                    return accountNumber !=selfAccount.accountNumber();
                })
                .toArray(Integer[]::new);

        transactionsView.drawMenuOptions(StringHelper.convertToStringArray(accounts));
        readNumberInput();
        try {
            Integer receiverAccountNumber = accounts[transactionsModel.getSelectedOption() - 1];
            TransactionServiceImpl.sendMoneyFromSelfAccount(receiverAccountNumber,this);
        }catch (IndexOutOfBoundsException e){
            transactionsView.invalidOption();
            sendMoney();
        }

    }

    public void requestAmount(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        try {
            BigDecimal amountEntered = new BigDecimal(input.trim());
            transactionsModel.setEnteredAmount(amountEntered);
        }catch (NumberFormatException exception){
            transactionsModel.setEnteredAmount(new BigDecimal(-1));
        }
    }

}
