package user;

import base.BaseController;
import fileHelper.AccountsFileHelper;
import fileHelper.UsersFileHelper;
import utilities.*;
import model.Account;
import model.SignupStage;
import model.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import static model.SignupStage.*;

public class AccountAccess extends BaseController {
    UserModel userModel;
    UserView userView;
    UserController userController;
    private String ssn;
    private String name;
    private String password;
    private String password_repeat;

    public AccountAccess(UserModel userModel, UserView userView, UserController userController) {
        super(userModel,userView);
        this.userModel = userModel;
        this.userView = userView;
        this.userController=userController;
    }

    public void startSignup(){

        implementSignupMethods(SSN_INPUT);
        checkSsnForSignup();
        implementSignupMethods(NEW_PASSWORD_INPUT);
        implementSignupMethods(NEW_PASSWORD_RE_INPUT);
        implementSignupMethods(MATCH_NEW_PASSWORDS);
        implementSignupMethods(FULL_NAME_INPUT);
        implementSignupMethods(CREATE_ACCOUNT);
        proceedToLogin();

    }
    public void changePassword(){
        userView.askCurrentPassword();
        readTextInput();
        if(doesCurrentPasswordMatches(userModel.getEnteredText())){

            implementChangePasswordMethods(NEW_PASSWORD_INPUT);
            implementChangePasswordMethods(NEW_PASSWORD_RE_INPUT);
            implementChangePasswordMethods(MATCH_NEW_PASSWORDS);
            implementChangePasswordMethods(UPDATE_ACCOUNT);

        }else{
            userView.wrongPassword();
            userController.showUserMenu();
        }
    }
    public void proceedToLogin() {
        implementSignupMethods(SSN_INPUT);
        checkSsnForLogin();
        requestPassword();
        loginWithSsnPassword();
    }

    private void implementSignupMethods(SignupStage signupStage){

        try {
            switch (signupStage){
                case SSN_INPUT              -> requestSsn();
                case NEW_PASSWORD_INPUT     -> requestNewPassword();
                case NEW_PASSWORD_RE_INPUT  -> reRequestNewPassword();
                case MATCH_NEW_PASSWORDS    -> matchPasswords();
                case FULL_NAME_INPUT        -> requestFullName();
                case CREATE_ACCOUNT         -> createNewUserAndAccount();
                default                     -> userController.showUserMenu();
            }
        } catch (IllegalArgumentException e) {
            if(e.getMessage()==null) {
                userController.showFirstMenu();
            }else{
                implementSignupMethods(Objects.requireNonNull(getByValue(e.getMessage())));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    private void implementChangePasswordMethods(SignupStage signupStage){

        try {
            switch (signupStage){
                case NEW_PASSWORD_INPUT     -> requestNewPassword();
                case NEW_PASSWORD_RE_INPUT  -> reRequestNewPassword();
                case MATCH_NEW_PASSWORDS    -> matchPasswords();
                case UPDATE_ACCOUNT         -> updateAccount();
                default                     -> userController.showUserMenu();
            }
        } catch (IllegalArgumentException e) {
            if(e.getMessage()==null) {
                userController.showFirstMenu();
            }else{
                implementChangePasswordMethods(Objects.requireNonNull(getByValue(e.getMessage())));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private void requestSsn() throws FileNotFoundException {
        userView.askSsn();
        readTextInput();
        String enteredSsn = userModel.getEnteredText();
        StringHelper.isBlank(enteredSsn);
        if(!StringHelper.isSsnFormatCorrect(enteredSsn)) throw new IllegalArgumentException(SSN_INPUT.getSignupStage());
    }

    private void checkSsnForSignup(){
        String enteredSsn = userModel.getEnteredText();
        if(UsersFileHelper.isSsnPresent(enteredSsn)) {
            userView.ssnAlreadyPresentError();
            throw new IllegalArgumentException(SSN_INPUT.getSignupStage());
        }
        ssn=enteredSsn;
    }

    private void requestNewPassword() {
        userView.askNewPassword();
        readTextInput();
        String enteredPassword = userModel.getEnteredText();
        StringHelper.isBlank(enteredPassword);
        boolean passwordHasShortcomings = PasswordHelper.getPasswordShortcomings(enteredPassword).size()>0;
        if(passwordHasShortcomings) throw new IllegalArgumentException(NEW_PASSWORD_INPUT.getSignupStage());
        password=enteredPassword;
    }

    private void reRequestNewPassword() throws FileNotFoundException {
        userView.askRePassword();
        readTextInput();
        String enteredPassword = userModel.getEnteredText();
        StringHelper.isBlank(enteredPassword);
        if(PasswordHelper.getPasswordShortcomings(userModel.getEnteredText()).size()>0) throw new IllegalArgumentException(NEW_PASSWORD_RE_INPUT.getSignupStage());
        password_repeat=enteredPassword;
    }

    private void matchPasswords() throws FileNotFoundException {
        if(!password_repeat.equals(password)){
            userView.passwordMismatchError();
            throw new IllegalArgumentException(NEW_PASSWORD_INPUT.getSignupStage());
        }
    }

    private void requestFullName() throws FileNotFoundException {
        userView.askName();
        readTextInput();
        String enteredName = userModel.getEnteredText();
        StringHelper.isBlank(enteredName);
        if(StringHelper.getFullNameShortcomings(enteredName).size()>0) throw new IllegalArgumentException(FULL_NAME_INPUT.getSignupStage());
        name=userModel.getEnteredText();
    }

    private void createNewUserAndAccount() throws FileNotFoundException {
        User user = new User(ssn,name,password);
        UsersFileHelper.addUser(user);
        Account account = new Account(AccountHelper.generateAccountNumber(),ssn,new BigDecimal(0));
        AccountsFileHelper.addAccount(account);
        userView.accountCreated();
    }

    private void checkSsnForLogin(){
        String enteredSsn =userModel.getEnteredText();
        if(!UsersFileHelper.isSsnPresent(enteredSsn)) {
            userView.ssnNotRegisteredError();
            userController.showFirstMenu();
            return;
        }
        ssn=enteredSsn;
    }

    private void loginWithSsnPassword(){
        User user = UsersFileHelper.getUserBySsnPassword(ssn,password);
        if(user==null){
            userView.invalidLogin();
            userController.showFirstMenu();
        }else{
            userModel.setLoggedInUser(user);
            userView.welcomeWithName(user.getFullName());
            userController.showUserMenu();
        }
    }

    private void requestPassword()  {
        userView.askPassword();
        readTextInput();
        StringHelper.isBlank(userModel.getEnteredText());
        password = userModel.getEnteredText();
    }

    private boolean doesCurrentPasswordMatches(String currentPassword){
        String oldHashedPassword =  PasswordHelper.generateHashedPassword(currentPassword,userModel.getLoggedInUser().getSalt());
        return oldHashedPassword.equals(userModel.getLoggedInUser().getHashedPassword());
    }

    private void updateAccount() throws IOException {
        String currentUserSsn = userModel.getLoggedInUser().getSsn();
        String currentUserFullName = userModel.getLoggedInUser().getFullName();
        User updatedUser = new User(currentUserSsn, currentUserFullName,password);

        UsersFileHelper.updateUser(userModel.getLoggedInUser(),updatedUser);
        userModel.setLoggedInUser(updatedUser);
        userView.passwordChanged();
        userController.showUserMenu();
    }

}
