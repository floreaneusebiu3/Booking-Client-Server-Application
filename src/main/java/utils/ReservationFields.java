package utils;

import model.Account;
import model.Room;

import java.io.Serializable;

public class ReservationFields implements Serializable {
    private Account account;
    private Room room;

    public ReservationFields(){}

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
