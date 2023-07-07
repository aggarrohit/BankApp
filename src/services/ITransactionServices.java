package services;

import java.io.IOException;

public interface ITransactionServices {
    void depositMoney() throws IOException;
    void withdrawMoney() throws IOException;
    void sendMoney() throws IOException;
}
