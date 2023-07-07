package __tests__;

import fileHelper.UsersFileHelper;
import model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class UserTest {

    @Disabled
    @Test
    void checkIfUserUpdates() throws IOException {
        User user = UsersFileHelper.getUserBySsnPassword("1990-roh","Pas$w0rd");
        assert user != null;
        User newUser = new User(user.getSsn(),user.getFullName(),"Pas$w0r");
        UsersFileHelper.updateUser(user,newUser);
        Assertions.assertEquals(newUser.getHashedPassword(), UsersFileHelper.getUserBySsnPassword("1990-roh","Pas$w0r").getHashedPassword());
    }


}