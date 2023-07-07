package base;

import user.UserController;
import user.UserModel;
import user.UserView;
import model.AccessActions;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class BaseController {
    BaseModel baseModel;
    BaseView baseView;
    Scanner scanner;

    public BaseController(BaseModel baseModel,BaseView baseView) {
        this.baseModel = baseModel;
        this.baseView = baseView;
        this.scanner= new Scanner(System.in);
    }

    public void showFirstMenu(){
        baseView.clearTerminal();
        baseView.welcomeHeading();
        baseView.drawMenuOptions(baseModel.firstMenuOptions);
        readNumberInput();
        handleFirstMenuOptions();
    }

    private void handleFirstMenuOptions(){
        try {
            switch (baseModel.getSelectedOption()){
                case 1  -> proceedWithAction(AccessActions.LOGIN);
                case 2  -> proceedWithAction(AccessActions.SIGNUP);
                case 3  -> baseView.sayGoodBye();
                default -> throw new IllegalArgumentException();
            }
        } catch (FileNotFoundException e) {
            baseView.showDatabaseError();
            showFirstMenu();
        }catch (IllegalArgumentException e) {
            baseView.showWrongOptionSelected();
            showFirstMenu();
        }
    }

    private void proceedWithAction(AccessActions accessActions) throws FileNotFoundException {
        UserModel userModel = new UserModel();
        UserView userView = new UserView();
        UserController userController = new UserController(userModel,userView);
        switch (accessActions){
            case LOGIN  -> userController.login();
            case SIGNUP -> userController.signup();
        }

    }

    public void readNumberInput(){
        String input = scanner.nextLine();
        try {
            int optionSelected = Integer.parseInt(input.trim());
            baseModel.setSelectedOption(optionSelected);
        }catch (NumberFormatException exception){
            baseModel.setSelectedOption(-1);
        }
    }

    public void readTextInput() throws IllegalArgumentException{
        String input = scanner.nextLine();
        try {
            if(input.trim().isBlank()) throw new IllegalArgumentException("Nothing Entered");
            baseModel.setEnteredText(input.trim());
        }catch (IllegalArgumentException exception){
            System.out.println(exception.getMessage());
            System.out.println("Exiting.");
            baseModel.setEnteredText("");
        }
    }


}
