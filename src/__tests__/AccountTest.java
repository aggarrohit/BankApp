package __tests__;

import fileHelper.AccountsFileHelper;
import model.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;

class AccountTest {

    @Test
    void checkIfAccountBalanceUpdates() throws IOException {
        String testSsn = "1990-roh";
        Account account = AccountsFileHelper.getAccountWithSsn(testSsn);
        assert account != null;
        Account updatedAccount = new Account(account.accountNumber(),account.ssn(),new BigDecimal("1111"));
        AccountsFileHelper.updateAccount(account,updatedAccount);

        Account fetchedUpdatedAccount = AccountsFileHelper.getAccountWithSsn(testSsn);

        assert fetchedUpdatedAccount != null;
        Assertions.assertEquals(fetchedUpdatedAccount.balance(),new BigDecimal(1111));
    }


}