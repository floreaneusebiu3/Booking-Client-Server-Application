package bll;

import dao.MessageDao;
import model.Message;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MessageBll {
    MessageDao messageDao;

    public MessageBll() {
        messageDao = new MessageDao();
    }

    public MessageBll(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public void insertMessage(Message message) {
        messageDao.insert(message);
    }

    public ArrayList<Message> findAllByAccountId(String id) {
        ArrayList<Message> messages = (ArrayList<Message>) messageDao.readAll();
        ArrayList<Message> filteredMessages = (ArrayList<Message>) messages.stream()
                .filter(message -> message.getAccount().getAccountId().equals(id)).collect(Collectors.toList());
        return filteredMessages;
    }

    public ArrayList<Message> findAllByChatId(String id) {
        ArrayList<Message> messages = (ArrayList<Message>) messageDao.readAll();
        ArrayList<Message> filteredMessages = (ArrayList<Message>) messages.stream()
                .filter(message -> message.getChat().getChatId().equals(id)).collect(Collectors.toList());
        return filteredMessages;
    }

    public ArrayList<Message> findAllMessagesForOneChat(String accountId, String chatId) {
        ArrayList<Message> messages = (ArrayList<Message>) messageDao.readAll();
        ArrayList<Message> filteredMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getChat().getChatId().equals(chatId) && message.getAccount().getAccountId().equals(accountId)) {
                filteredMessages.add(message);
            }
        }
        return filteredMessages;
    }

    public ArrayList<Message> findAll() {
        return (ArrayList<Message>) (new MessageDao()).readAll();
    }
}
