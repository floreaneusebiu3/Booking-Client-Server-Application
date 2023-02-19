package test;

import bll.AccountBll;
import bll.ChatBll;
import dao.AccountDao;
import dao.ChatDao;
import model.Account;
import model.Chat;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ChatBllTest {

    @Mock
    ChatDao chatDao;

    ChatBll chatBll;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        chatBll = new ChatBll(chatDao);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }


    @Test
    void findChatByIdTest() {
        Chat chat = new Chat();
        Account account = new Account();
        account.setAccountId("djfgsdjfh332dsf");
        account.setUsername( "user1");
        account.setType("client");
        account.setPassword("password");
        chat.setAccount(account);
        chat.setChatId("fdgbfjsjkfjhfr");
        ArrayList<Chat> chats = new ArrayList<>();
        chats.add(chat);
        when(chatDao.readAll()).thenReturn(chats);
        assertEquals(chatBll.findChatByAccountId("djfgsdjfh332dsf"), chat);
    }

}