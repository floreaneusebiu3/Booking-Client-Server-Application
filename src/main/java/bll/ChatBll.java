package bll;

import dao.ChatDao;
import model.Chat;

import java.util.ArrayList;

public class ChatBll {
    ChatDao chatDao;
    public ChatBll() {
        chatDao = new ChatDao();
    }
    public ChatBll(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public void insertChat(Chat chat) {
        chatDao.insert(chat);
    }

    public Chat findChatByAccountId(String accountId) {
        ArrayList<Chat> chats = (ArrayList<Chat>) chatDao.readAll();
        for (Chat chat : chats) {
            if (chat.getAccount().getAccountId().equals(accountId)) {
                return chat;
            }
        }
        return null;
    }
}
