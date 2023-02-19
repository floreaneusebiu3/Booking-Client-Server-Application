package utils;

import model.Account;

import java.io.Serializable;
import java.util.ArrayList;

public class AccountsList implements Serializable {
    private ArrayList<Account> accounts;
    public AccountsList(){}

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
