package __tests__;

import utilities.StringHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatTest {

    @Test
    void checkIfDetectIncorrectSsnFormat(){
        Assertions.assertFalse(StringHelper.isSsnFormatCorrect("As! joisjdc77"));
        Assertions.assertFalse(StringHelper.isSsnFormatCorrect("csad-123"));
        Assertions.assertFalse(StringHelper.isSsnFormatCorrect(" 123-add"));
        Assertions.assertFalse(StringHelper.isSsnFormatCorrect("1234 sas"));
    }

    @Test
    void checkIfDetectCorrectSsnFormat(){
        Assertions.assertTrue(StringHelper.isSsnFormatCorrect("1234-abc"));
        Assertions.assertTrue(StringHelper.isSsnFormatCorrect("6554-ABC"));
        Assertions.assertTrue(StringHelper.isSsnFormatCorrect("6544-aBc"));
    }

    @Test
    void checkIfDetectIncorrectName(){
        Assertions.assertEquals(2,StringHelper.getFullNameShortcomings("1234-abc").size());
        Assertions.assertEquals(1,StringHelper.getFullNameShortcomings("ABC vdfho i davfih idufvh iud fvuih diufvh iudah fviuh difuvh sdf").size());
        Assertions.assertEquals(0,StringHelper.getFullNameShortcomings("Rohit Agarwal").size());
    }
}
