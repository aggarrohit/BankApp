package base;

import model.User;

public class BaseModel {

    private static String enteredText;
    private static User loggedInUser;
    private static int selectedOption;

    public String[] firstMenuOptions = new String[]
            {
                    "Login",
                    "Sign Up",
                    "Exit"
            };

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

    public String getEnteredText() {
        return enteredText;
    }

    public void setEnteredText(String enteredText) {
        this.enteredText = enteredText;
    }


    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        BaseModel.loggedInUser = loggedInUser;
    }



}
