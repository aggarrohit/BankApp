package __tests__;

import fileHelper.UsersFileHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Disabled
    @Test
    void checkLogin() {
        Assertions.assertNotNull(UsersFileHelper.getUserBySsnPassword("1990-roh","pA$sw0rd"));
    }

    @Test
    void checkInvalidLogin() {
        Assertions.assertNull(UsersFileHelper.getUserBySsnPassword("1990-roi","pA$sw0rd"));
        Assertions.assertNull(UsersFileHelper.getUserBySsnPassword("1990-roh","PA$sw0rd"));
    }

}
