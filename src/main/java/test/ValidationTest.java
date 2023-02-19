package test;

import Controller.LoginController;
import Controller.RegisterController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import validators.ExceptionBooking;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ValidationTest {

    LoginController loginController;
    RegisterController registerController;
    @BeforeEach
    void setUp() {
       loginController = new LoginController();
       registerController = new RegisterController();
    }

    @Test
    void validateAccountTest() {
        String username = "user1";
        String password = "password1";
        assertEquals( loginController.validateAccount(username, password), "valid");
    }

    @Test
    void validateWrongAccountTest() {
        String username = "user1";
        String password = "password1";
        assertEquals( loginController.validateAccount("select", password), ExceptionBooking.sqlInjection);
    }

    @Test
    void validateRegisterUserTest() {
        String firstName = "name";
        String lastName = "surname";
        String email = "user@gmail.com";
        String age= String.valueOf(19);
        assertEquals( registerController.validateUser(firstName, lastName, email, age), "valid");
    }

    @Test
    void validateRegisterUserTestWrongName() {
        String firstName = "name12";
        String lastName = "surname";
        String email = "user@gmail.com";
        String age= String.valueOf(19);
        assertEquals( registerController.validateUser(firstName, lastName, email, age), ExceptionBooking.notName);
    }

    @Test
    void validateRegisterUserTestTooYoung() {
        String firstName = "name";
        String lastName = "surname";
        String email = "user@gmail.com";
        String age= String.valueOf(12);
        assertEquals( registerController.validateUser(firstName, lastName, email, age), ExceptionBooking.tooYoung);
    }

    @Test
    void validateRegisterUserTestTooOld() {
        String firstName = "name";
        String lastName = "surname";
        String email = "user@gmail.com";
        String age= String.valueOf(120);
        assertEquals( registerController.validateUser(firstName, lastName, email, age), ExceptionBooking.tooOld);
    }

    @Test
    void validateRegisterUserTestWrongMail() {
        String firstName = "name";
        String lastName = "surname";
        String email = "usergmail.com";
        String age= String.valueOf(19);
        assertEquals( registerController.validateUser(firstName, lastName, email, age), ExceptionBooking.mailInvalid);
    }


}
