package services;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IUserServices {
    void showUserMenu();
    void handleUserMenuOptions() throws IOException;
    void changePassword() throws IOException;
    void login();
    void signup();
    void logout();
    void showTransactions() throws FileNotFoundException;
}
