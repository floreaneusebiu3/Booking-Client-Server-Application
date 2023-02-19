package utils;

import model.Reservation;

import java.io.Serializable;
import java.util.ArrayList;

public class ReservationList implements Serializable {
    private ArrayList<Reservation> reservations = new ArrayList<>();
    public ReservationList() {}

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}
