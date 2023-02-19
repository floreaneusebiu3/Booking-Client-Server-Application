package test;

import bll.AccountBll;
import dao.AccountDao;
import model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AccountBllTest {

    @Mock
    AccountDao accountDao;

    AccountBll accountBll;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
         accountBll = new AccountBll(accountDao);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void findAccountByIdTest() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setAccountId("djfgsdjfh332dsf");
        account.setUsername( "user1");
        account.setType("client");
        account.setPassword("password");
        accounts.add(account);
        when(accountDao.readAll()).thenReturn(accounts);
        assertEquals(accountBll.findAccountById("djfgsdjfh332dsf"), account);
    }
    @org.junit.jupiter.api.Test
    void verifyAccountTest() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setAccountId("djfgsdjfh332dsf");
        account.setUsername( "user1");
        account.setType("client");
        account.setPassword("password");
        accounts.add(account);
        when(accountDao.readAll()).thenReturn(accounts);
        assertEquals(accountBll.verifyAccount("user1", "password"), account);
    }

    @org.junit.jupiter.api.Test
    void verifyWrongAccountTest() {
        List<Account> accounts = new ArrayList<>();
        Account account = new Account();
        account.setAccountId("djfgsdjfh332dsf");
        account.setUsername( "user1");
        account.setType("client");
        account.setPassword("password");
        accounts.add(account);
        when(accountDao.readAll()).thenReturn(accounts);
        assertEquals(accountBll.verifyAccount("user2", "password"), null);
    }


}