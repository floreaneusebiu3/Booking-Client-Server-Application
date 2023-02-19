package Controller;

import bll.AccountBll;
import bll.MessageBll;
import model.Chat;
import model.Message;

import java.util.ArrayList;

public class MessagesController {

    public void createAndInsertMessage(Chat chat, String request) {
        String[] parts = request.split(",");
        Message message = new Message();
        ArrayList<Message> messages = (new MessageBll()).findAll();
        System.out.println(messages.size() + "************************************************");
        message.setMessageId(String.valueOf(messages.size()));
        message.setAccount((new AccountBll()).findAccountById(parts[2]));
        message.setChat(chat);
        message.setText(parts[3]);
        (new MessageBll()).insertMessage(message);
    }
}
