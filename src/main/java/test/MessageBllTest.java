package test;

import bll.MessageBll;
import dao.MessageDao;
import model.Account;
import model.Chat;
import model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MessageBllTest {

    @Mock
    MessageDao messageDao;

    MessageBll messageBll;

    @BeforeEach
    void setUp() {
        messageDao = mock(MessageDao.class);
        messageBll = new MessageBll(messageDao);
    }

    @Test
    void findAllMessagesForOneChatTest() {
        Chat chat = new Chat();
        chat.setChatId("123456");
        Account account = new Account();
        account.setAccountId("asd23243");
        chat.setAccount(account);
        Message message = new Message();
        message.setMessageId("hgdshdsjsd");
        message.setText("Salut");
        message.setChat(chat);
        message.setAccount(account);
        Message message1 = new Message();
        message1.setMessageId("hfghjhdsjsd");
        message1.setText("Bafta");
        message1.setChat(chat);
        message1.setAccount(account);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        messages.add(message1);
        when(messageDao.readAll()).thenReturn(messages);
        assertEquals(messageBll.findAllMessagesForOneChat("asd23243", "123456"), messages);
    }
}