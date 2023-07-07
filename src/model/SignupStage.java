package model;

public enum SignupStage {
    SSN_INPUT("input_ssn"),
    NEW_PASSWORD_INPUT("input_new_password"),
    NEW_PASSWORD_RE_INPUT("re_input_new_password"),
    MATCH_NEW_PASSWORDS("match_new_passwords"),
    FULL_NAME_INPUT("input_full_name"),
    CREATE_ACCOUNT("create_account"),
    UPDATE_ACCOUNT("update_account"),
    COMPLETED("completed");

    private final String signupStage;

    SignupStage(String signupStage) {
        this.signupStage=signupStage;
    }

    public String getSignupStage() {
        return signupStage;
    }

    public static SignupStage getByValue(String value) {
        for (SignupStage myEnum : SignupStage.values()) {
            if (myEnum.signupStage.equals(value)) {
                return myEnum;
            }
        }
        return null; // Value not found
    }
}
