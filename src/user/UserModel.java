package user;

import base.BaseModel;

public class UserModel extends BaseModel {

    public String[] userMenuOptions = new String[]
            {
                    "View Transactions",
                    "Deposit Money",
                    "Withdraw Money",
                    "Send Money",
                    "Change Password",
                    "Logout"
            };
}
