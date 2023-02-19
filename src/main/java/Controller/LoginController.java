package Controller;

import bll.AccountBll;
import model.Account;
import validators.SQLInjectionSafe;

public class LoginController {
    AccountBll accountBll;
    public LoginController()  {
        accountBll = new AccountBll();
    }


    public Account checkLogin(String request) {
        String username = "";
        String password = "";
        String[] parts = request.split(",");
            if(parts[1] != null)
            username = parts[1];
            if(parts[2] != null)
            password = parts[2]; //de aici trimitem username si parola in bll
            return accountBll.verifyAccount(username, password);
    }

    public String validateAccount(String username, String password) {
        try {
            SQLInjectionSafe.isSQLValid(username);
            SQLInjectionSafe.isSQLValid(password);
            return "valid";
        }
        catch (IllegalArgumentException e){
            return e.getMessage();
        }
    }
}
