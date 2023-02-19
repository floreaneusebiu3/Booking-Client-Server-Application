package Controller;

import bll.AccountBll;
import bll.ChatBll;
import model.Account;
import model.Chat;
import model.User;
import presentation.Register;
import utils.ConfirmEmail;
import utils.Hash;
import validators.AgeValid;
import validators.EmailValid;
import validators.NameValid;
import validators.SQLInjectionSafe;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

public class RegisterController {
    Random random;
    ConfirmEmail confirmEmail;

    public RegisterController() {
        random = new Random();
        confirmEmail = new ConfirmEmail();
    }

    public Account createAdminAccount(String request) {
        String[] parts = request.split(",");
        String firstName = null;
        String lastName = null;
        String email = null;
        String age = null;
        if (parts[1] != null) {
            firstName = parts[1];
        } else {
            return null;
        }
        if (parts[2] != null) {
            lastName = parts[2];
        } else {
            return null;
        }
        if (parts[3] != null) {
            email = parts[3];
        } else {
            return null;
        }
        if (parts[4] != null) {
            age = parts[4];
        } else {
            return null;
        }
        StringBuilder emailMessage = new StringBuilder();
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.createUser(firstName, lastName, email, age);
        Account account = new Account();
        account.setAccountId(UUID.randomUUID().toString());
        StringBuilder sb = new StringBuilder();
        sb.append("admin");
        sb.append(random.nextInt(100000));
        account.setUsername(String.valueOf(sb));
        emailMessage.append("username: ");
        emailMessage.append(sb);
        sb.delete(0, sb.length());
        sb.append("booking");
        sb.append(random.nextInt(100000));
        emailMessage.append("\npassword: ");
        emailMessage.append(String.valueOf(sb));
        confirmEmail.sendMail(email, email, String.valueOf(emailMessage));
        String hashPassword = Hash.hashPassword(String.valueOf(sb));
        account.setPassword(hashPassword);
        account.setType("admin");
        account.setUser(user);
        return account;
    }

    public Account createOwnerAccount(String request, User user) {
        String[] parts = request.split(",");
        Account account = new Account();
        account.setAccountId(UUID.randomUUID().toString());
        StringBuilder sb = new StringBuilder();
        StringBuilder emailMessage = new StringBuilder();
        emailMessage.append("username: ");
        sb.append(parts[2]);
        sb.append(random.nextInt(100000));
        emailMessage.append(sb);
        emailMessage.append("\n");
        account.setUsername(String.valueOf(sb));
        sb.delete(0, sb.length());
        sb.append("booking");
        sb.append(random.nextInt(100000));
        emailMessage.append("password:");
        emailMessage.append(sb);
        String hashPassword = Hash.hashPassword(String.valueOf(sb));
        account.setPassword(hashPassword);
        account.setType("owner");
        account.setUser(user);
        AccountBll accountBll = new AccountBll();
        accountBll.insertAccount(account);
        confirmEmail.sendMail(parts[4], parts[4], String.valueOf(emailMessage));
        return account;
    }

    public Account createClientAccount(String request, User user) {
        String[] parts = request.split(",");
        Account account = new Account();
        account.setAccountId(UUID.randomUUID().toString());
        account.setUsername(parts[6]);
        if (parts[7].compareTo(parts[8]) == 0) {
            String hashPassword = Hash.hashPassword(parts[7]);
            account.setPassword(hashPassword);
        } else {
            return null;
        }
        account.setType("client");
        account.setUser(user);
        return account;
    }

    public String validateUser(String firstName, String lastName, String email, String age) {
        try {
            NameValid.isValidName(firstName);
            NameValid.isValidName(lastName);
            EmailValid.isValidMail(email);
            try {
                Integer.parseInt(age);
            } catch (Exception e) {
                return "age must be a number";
            }
            AgeValid.isValidAge(Integer.parseInt(age));
            return "valid";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }
}
