package __tests__;

import utilities.PasswordHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PasswordTest {

    @Test
    void checkIfDetectSpaces(){
        Assertions.assertEquals(1, PasswordHelper.getPasswordShortcomings("As! joisjdc77").size());
    }

    @Test
    void checkIfDetectNoLowerCaseLetter(){
        Assertions.assertEquals(1, PasswordHelper.getPasswordShortcomings("A!VKNFVDJ77").size());
    }

    @Test
    void checkIfDetectNoUpperCaseLetter(){
        Assertions.assertEquals(1, PasswordHelper.getPasswordShortcomings("s!csdjoisjdc77").size());
    }

    @Test
    void checkIfDetectNoSpecialCharacter(){
        Assertions.assertEquals(1, PasswordHelper.getPasswordShortcomings("As55joisjdc77").size());
    }

    @Test
    void checkIfDetectNoNumeric(){
        Assertions.assertEquals(1, PasswordHelper.getPasswordShortcomings("As%joisjdc").size());
    }

    @Test
    void checkIfDetectSizeLesserThan6(){
        Assertions.assertEquals(1, PasswordHelper.getPasswordShortcomings("As!77").size());
    }

    @Test
    void checkIfDetectAllShortComingsAtOnce(){
        Assertions.assertEquals(6, PasswordHelper.getPasswordShortcomings(" ").size());
    }

    @Test
    void checkPassword(){
        Assertions.assertEquals(0,PasswordHelper.getPasswordShortcomings("pA$sw0rd").size());
    }

}