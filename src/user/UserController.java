package user;

import base.BaseController;
import fileHelper.AccountsFileHelper;
import fileHelper.TransactionsFileHelper;
import transactions.TransactionsModel;
import transactions.TransactionsView;
import model.Account;
import model.TransactionType;
import model.Transactions;
import services.IUserServices;
import transactions.TransactionsController;

import java.io.FileNotFoundException;
import java.io.IOException;


public class UserController extends BaseController implements IUserServices {
    UserModel userModel;
    UserView userView;

    public UserController(UserModel userModel, UserView userView) {
        super(userModel,userView);
        this.userModel = userModel;
        this.userView = userView;
    }

    @Override
    public void showUserMenu(){
        Account account;
        try {
            account = AccountsFileHelper.getAccountWithSsn(userModel.getLoggedInUser().getSsn());

            assert account != null;
            userView.printAccountNumber(account);
        userView.drawMenuOptions(userModel.userMenuOptions);

        handleUserMenuOptions();
        } catch (IOException e) {
            userView.showDatabaseError();
            showFirstMenu();
        }
    }

    @Override
    public void handleUserMenuOptions() throws IOException {

        readNumberInput();
        switch (userModel.getSelectedOption()){
            case 1 -> showTransactions();
            case 2 -> transactionAction(TransactionType.DEPOSIT);
            case 3 -> transactionAction(TransactionType.WITHDRAWAL);
            case 4 -> transactionAction(TransactionType.MONEY_TRANSFER);
            case 5 -> changePassword();
            case 6 -> logout();
            default -> {
                userView.enteredWrongOption();
                handleUserMenuOptions();
            }
        }

    }

    @Override
    public void changePassword() {
        AccountAccess accountAccess = new AccountAccess(userModel,userView,this);
        accountAccess.changePassword();
    }

    private void transactionAction(TransactionType transactionType){
        TransactionsModel transactionsModel = new TransactionsModel();
        TransactionsView transactionsView = new TransactionsView();

        TransactionsController transactionsController = new TransactionsController(transactionsModel,transactionsView,this);
        transactionsController.handleTransaction(transactionType);
    }

    @Override
    public void showTransactions() throws FileNotFoundException {
        Account account = AccountsFileHelper.getAccountWithSsn(userModel.getLoggedInUser().getSsn());
        assert account != null;
        Transactions transactions = new Transactions(TransactionsFileHelper.getAccountTransactions(account.accountNumber()));
        transactions.printTable(account.accountNumber());
        showUserMenu();
    }


    @Override
    public void login() {
        AccountAccess accountAccess = new AccountAccess(userModel,userView,this);
        accountAccess.proceedToLogin();
    }

    @Override
    public void signup() {
        AccountAccess accountAccess = new AccountAccess(userModel,userView,this);
        accountAccess.startSignup();
    }

    @Override
    public void logout(){
        userModel.setLoggedInUser(null);
        showFirstMenu();
    }
}
