package utils;

import model.RentUnit;
import model.Review;
import model.Room;

import java.io.Serializable;
import java.util.ArrayList;

public class RentUnitList implements Serializable {
    private ArrayList<RentUnit> rentUnits;
    private ArrayList<Room> rooms;
    private  ArrayList<Review> reviews;
    public RentUnitList() {
        rentUnits = new ArrayList<>();
        rooms = new ArrayList<>();
        reviews = new ArrayList<>();
    }

    public ArrayList<RentUnit> getRentUnits() {
        return rentUnits;
    }

    public void setRentUnits(ArrayList<RentUnit> rentUnits) {
        this.rentUnits = rentUnits;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

}
