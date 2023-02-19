package utils;

import model.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class MessagesContainer implements Serializable {

    private ArrayList<Message> myMessages;
    private ArrayList<Message> otherMessages;

    public MessagesContainer() {
        myMessages = new ArrayList<>();
        otherMessages = new ArrayList<>();
    }

    public ArrayList<Message> getMyMessages() {
        return myMessages;
    }

    public void setMyMessages(ArrayList<Message> myMessages) {
        this.myMessages = myMessages;
    }

    public ArrayList<Message> getOtherMessages() {
        return otherMessages;
    }

    public void setOtherMessages(ArrayList<Message> otherMessages) {
        this.otherMessages = otherMessages;
    }
}
