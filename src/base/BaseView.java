package base;

public class BaseView {

    public void drawMenuOptions(String[] menuOptions){
        int i=0;
        for (String menuOption:menuOptions){
            System.out.println("["+(i+1)+"] "+menuOption);
            i++;
        }
        System.out.print("Enter Option: ");
    }

    public void welcomeHeading(){
        System.out.println("Welcome to Sweden Bank");
    }
    public void welcomeWithName(String name){
        System.out.println("Welcome "+name);
    }
    public void enteredWrongOption(){
        System.out.println("Entered wrong option, Please Enter Valid Option: ");
    }

    public void askSsn(){
        System.out.print("Enter SSN(YYYY-ABC): ");
    }

    public void askPassword(){
        System.out.print("Enter Password: ");
    }

    public void askCurrentPassword(){
        System.out.print("Enter Current Password: ");
    }

    public void askNewPassword(){
        System.out.print("Enter New Password: ");
    }

    public void askRePassword(){
        System.out.print("Re-Enter New Password: ");
    }

    public void askName(){
        System.out.print("Enter Full Name: ");
    }

    public void ssnAlreadyPresentError(){
        System.out.println("Cannot signup with this SSN as it is already registered with us.");
    }

    public void showDatabaseError(){
        System.out.println("Database file not found.");
    }

    public void showWrongOptionSelected(){
        System.out.println("Wrong option selected.");
    }

    public void ssnNotRegisteredError(){
        System.out.println("Entered SSN is not registered with us.");
    }

    public void invalidLogin(){
        System.out.println("Wrong ssn or password.");
    }

    public void passwordMismatchError(){
        System.out.println("Entered passwords do not match.");
    }
    public void clearTerminal(){
        try {
            String terminal = System.getenv("SHELL");
            if (terminal == null) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                throw new Exception("Found the shell terminal.");
            }
        } catch (Exception e) {
            String clearScreenASCIICode = "\033[H\033[2J";
            System.out.print(clearScreenASCIICode);
            System.out.flush();
        }
    }

    public void sayGoodBye(){
        System.out.println("Bye bye..\nHave a nice day!!\n");
    }

}
