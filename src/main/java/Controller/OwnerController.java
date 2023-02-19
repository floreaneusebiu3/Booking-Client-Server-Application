package Controller;

import bll.*;
import dao.RentUnitDao;
import model.*;
import utils.Hash;
import utils.NotificationList;
import utils.RentUnitList;
import validators.AgeValid;
import validators.EmailValid;
import validators.NameValid;
import validators.TelephoneValid;

import java.util.*;

public class OwnerController {
    UserBll userBll;
    public OwnerController(){userBll=new UserBll();}

    public Notification createNotification(String request, Account account) {
        String[] parts = request.split(",");
        Notification notification = new Notification();
        notification.setNotificationnId(UUID.randomUUID().toString());
        notification.setAccount(account);
        String info=parts[2]+','+parts[3]+','+parts[4]+','+parts[5]+','+parts[6]+','+parts[7]+',';
        notification.setInformation(info);

        return notification;
    }

    public Room createRoom(String request, RentUnit rentUnit){
        Room room=new Room();
        String[] parts = request.split(",");
        room.setRoomId(UUID.randomUUID().toString());
        room.setCapacity(Integer.parseInt(parts[2]));
        room.setPrice(Float.parseFloat(parts[3]));
        room.setFacilitati(parts[4]);

        room.setRentUnit(rentUnit);

        return room;
    }
    public Room editRoom(String request, Room room){
        //Room room=new Room();
        String[] parts = request.split(",");

        room.setCapacity(Integer.parseInt(parts[2]));
        room.setPrice(Float.parseFloat(parts[3]));
        room.setFacilitati(parts[4]);

        return room;
    }
    public RentUnit editUnit(String request, RentUnit unit){
        //Room room=new Room();
        String[] parts = request.split(",");
         unit.setUnitType(parts[2]);
         unit.setName(parts[3]);
         unit.setCountry(parts[4]);
         unit.setTown(parts[5]);
         unit.setTelephoneNumber(parts[6]);
         unit.setDescription(parts[7]);
         return unit;
    }
    public Image editImage(String name, Image image){
        //Room room=new Room();
        image.setImageName(name);
        return image;
    }

    public RentUnitList getRentUnitReviewsAndRooms(String unitId) {

        RentUnitDao rentUnitDao = new RentUnitDao();
        ReviewBll reviewBll=new ReviewBll();
        RoomBll roomBll=new RoomBll();

        Set<Review> reviewsSet=reviewBll.getReviewForThisRentUnitbyId(unitId);
        Set<Room> roomsSet=roomBll.getRoomsForThisRentUnitbyId(unitId);

        ArrayList<Review> reviews=new ArrayList<>();
        ArrayList<Room> rooms=new ArrayList<>();

        reviews.addAll(reviewsSet);
        rooms.addAll(roomsSet);

        RentUnitList rentUnitList=new RentUnitList();
        rentUnitList.setRentUnits(null);
        rentUnitList.setRooms(rooms);
        rentUnitList.setReviews(reviews);
        return rentUnitList;

    }
    public NotificationList getNotificationsFromAccount(String id){
        NotificationBll notificationBll=new NotificationBll();

        Set<Notification> notificationsSet=notificationBll.getNotificationForThisAccount(id);
        ArrayList<Notification> notifications=new ArrayList<>();

        notifications.addAll(notificationsSet);

        NotificationList notificationList=new NotificationList();
        notificationList.setNotifications(notifications);
        return notificationList;

    }
    public RentUnit createRentUnit(String request){

        String[] parts = request.split(",");
        RentUnit rentUnit=new RentUnit();
        AccountBll accountBll=new AccountBll();
        RentUnitBll rentUnitBll=new RentUnitBll();
        Account account=accountBll.findAccountById(parts[2]);
        RentUnit rentUnit1=rentUnitBll.findByOwner(account.getUsername());
        if (rentUnit1!=null)
            return null;
        rentUnit.setUnitId(UUID.randomUUID().toString());
        rentUnit.setUnitType(parts[3]);
        rentUnit.setName(parts[4]);
        rentUnit.setCountry(parts[5]);
        rentUnit.setTown(parts[6]);
        rentUnit.setTelephoneNumber(parts[7]);
        rentUnit.setDescription(parts[8]);
        rentUnit.setAccount(account);

        Image image=new Image();
        image.setImageId(UUID.randomUUID().toString());
        image.setImageName(parts[9]);

        ImageBll imageBll=new ImageBll();
        imageBll.insertImage(image);
        rentUnit.setImage(image);
        rentUnit.setAccount(account);
        rentUnit.setRating(0F);
        return rentUnit;
    }
    public String validateTelephoneNumber(String telephone) {
        try {
            TelephoneValid.isValidNumber(telephone);
            return "valid";
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
    }



}
