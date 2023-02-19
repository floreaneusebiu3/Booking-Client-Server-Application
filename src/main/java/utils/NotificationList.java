package utils;

import model.Notification;
import model.Review;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationList implements Serializable {
    private ArrayList<Notification> notifications;

    public NotificationList() {
        notifications = new ArrayList<>();;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }
}
