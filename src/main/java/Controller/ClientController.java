package Controller;

import bll.*;
import dao.RentUnitDao;
import dao.ReservationDao;
import dao.ReviewDao;
import model.*;
import utils.DateComparator;
import utils.RentUnitList;
import utils.ReservationList;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class ClientController {

    public ClientController() {
    }

    public RentUnitList getRentUnitList(String request) {
        RentUnitList rentUnitList = new RentUnitList();
        final String day1Text, day2Text, month1Text, month2Text,
                year1Text, year2Text, locationText, countryText,
                townText, accountId;
        String[] parts = request.split(",");
        if (parts[1] != null) {
            day1Text = parts[1];
        } else {
            day1Text = "";
        }
        if (parts[2] != null) {
            month1Text = parts[2];
        } else {
            month1Text = "";
        }
        if (parts[3] != null) {
            year1Text = parts[3];
        } else {
            year1Text = "";
        }
        if (parts[4] != null) {
            day2Text = parts[4];
        } else {
            day2Text = "";
        }
        if (parts[5] != null) {
            month2Text = parts[5];
        } else {
            month2Text = "";
        }
        if (parts[6] != null) {
            year2Text = parts[6];
        } else {
            year2Text = "";
        }
        if (parts[7] != null) {
            countryText = parts[7];
        } else {
            countryText = "";
        }
        if (parts[8] != null) {
            townText = parts[8];
        } else {
            townText = "";
        }
        if (parts[9] != null) {
            accountId = parts[9];
        } else {
            accountId = "";
        }

        RentUnitDao rentUnitDao = new RentUnitDao();
        ArrayList<RentUnit> rentUnits = (ArrayList<RentUnit>) rentUnitDao.readAll();
        ArrayList<RentUnit> rentUnitsFiltered = new ArrayList<>();
        for (RentUnit rentUnit : rentUnits) {
            if (!countryText.equals("") && !townText.equals("")) {
                if (((rentUnit.getCountry()).toUpperCase(Locale.ROOT)).equals(countryText.toUpperCase(Locale.ROOT)) &&
                        (rentUnit.getTown().toUpperCase(Locale.ROOT)).equals(townText.toUpperCase(Locale.ROOT))) {
                    rentUnitsFiltered.add(rentUnit);
                    System.out.println(countryText);
                    System.out.println(townText);
                }
            }
        }
        ReservationBll reservationBll = new ReservationBll();
        RoomBll roomBll = new RoomBll();
        ArrayList<Room> availableRooms = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<RentUnit> rentUnitsToBeRemoved = new ArrayList<>();
        for (RentUnit rentUnit : rentUnitsFiltered) {
            Set<Room> rooms1 = roomBll.getRoomsForThisRentUnit(rentUnit);
            if (rooms1.isEmpty()) {
                rentUnitsToBeRemoved.add(rentUnit);
            }
            rooms1.stream().forEach(room -> rooms.add(room));
        }
        for (RentUnit rentUnit : rentUnitsToBeRemoved) {
            rentUnitsFiltered.remove(rentUnit);
        }
        int ok = 1;
        for (Room room : rooms) {
            ok = 1;
            Set<Reservation> reservations = reservationBll.getReservationsForThisRoom(room);
            if (reservations.isEmpty()) {
                availableRooms.add(room);
            } else {
                for (Reservation reservation : reservations) {
                    if ((
                            DateComparator.compareDates(Integer.parseInt(day1Text), Integer.parseInt(month1Text), Integer.parseInt(year1Text), reservation.getCheckInDay(), reservation.getCheckInMonth(), reservation.getCheckInYear()) >= 0
                                    &&
                                    DateComparator.compareDates(Integer.parseInt(day1Text), Integer.parseInt(month1Text), Integer.parseInt(year1Text), reservation.getCheckOutDay(), reservation.getCheckOutMonth(), reservation.getCheckOutYear()) <= 0
                    )
                            ||
                            (DateComparator.compareDates(Integer.parseInt(day2Text), Integer.parseInt(month2Text), Integer.parseInt(year2Text), reservation.getCheckOutDay(), reservation.getCheckOutMonth(), reservation.getCheckOutYear()) >= 0
                                    &&
                                    DateComparator.compareDates(Integer.parseInt(day1Text), Integer.parseInt(month1Text), Integer.parseInt(year1Text), reservation.getCheckOutDay(), reservation.getCheckOutMonth(), reservation.getCheckOutYear()) <= 0
                            )
                    ) {
                        ok = 0;
                    }
//                    if ((checkInDate.compareTo(reservation.getCheckInDate()) >= 0 &&
//                            checkInDate.compareTo(reservation.getCheckOutDate()) <= 0) ||
//                            (checkOutData.compareTo(reservation.getCheckOutDate()) >= 0 &&
//                                    checkInDate.compareTo(reservation.getCheckOutDate()) <= 0)) {
//                        ok = false;
//                    }
                }
                if (ok == 1 && !availableRooms.contains(room)) {
                    availableRooms.add(room);
                }
            }
        }
        Account account = (new AccountBll()).findAccountById(accountId);
        ReviewBll reviewBll = new ReviewBll();
        ArrayList<Review> availableReviews = new ArrayList<>();
        for (RentUnit rentUnit : rentUnitsFiltered) {
            Set<Review> reviews = reviewBll.getReviewForThisRentUnit(rentUnit);
            reviews.stream().forEach(review -> availableReviews.add(review));
        }

        rentUnitList.setRentUnits(rentUnitsFiltered);
        rentUnitList.setRooms(availableRooms);
        rentUnitList.setReviews(availableReviews);
        return rentUnitList;
    }

    public RentUnitList getRentUnitListWithModifiedPrice(String request, Socket socket) {
        RentUnitList rentUnitList = new RentUnitList();
        final String day1Text, day2Text, month1Text, month2Text,
                year1Text, year2Text, locationText, countryText,
                townText, accountId;
        String[] parts = request.split(",");
        if (parts[1] != null) {
            day1Text = parts[1];
        } else {
            day1Text = "";
        }
        if (parts[2] != null) {
            month1Text = parts[2];
        } else {
            month1Text = "";
        }
        if (parts[3] != null) {
            year1Text = parts[3];
        } else {
            year1Text = "";
        }
        if (parts[4] != null) {
            day2Text = parts[4];
        } else {
            day2Text = "";
        }
        if (parts[5] != null) {
            month2Text = parts[5];
        } else {
            month2Text = "";
        }
        if (parts[6] != null) {
            year2Text = parts[6];
        } else {
            year2Text = "";
        }
        if (parts[7] != null) {
            countryText = parts[7];
        } else {
            countryText = "";
        }
        if (parts[8] != null) {
            townText = parts[8];
        } else {
            townText = "";
        }
        if (parts[9] != null) {
            accountId = parts[9];
        } else {
            accountId = "";
        }

        RentUnitDao rentUnitDao = new RentUnitDao();
        ArrayList<RentUnit> rentUnits = (ArrayList<RentUnit>) rentUnitDao.readAll();
        ArrayList<RentUnit> rentUnitsFiltered = new ArrayList<>();
        for (RentUnit rentUnit : rentUnits) {
            if (!countryText.equals("") && !townText.equals("")) {
                if (((rentUnit.getCountry()).toUpperCase(Locale.ROOT)).equals(countryText.toUpperCase(Locale.ROOT)) &&
                        (rentUnit.getTown().toUpperCase(Locale.ROOT)).equals(townText.toUpperCase(Locale.ROOT))) {
                    rentUnitsFiltered.add(rentUnit);
                    System.out.println(countryText);
                    System.out.println(townText);
                }
            }
        }
        ReservationBll reservationBll = new ReservationBll();
        RoomBll roomBll = new RoomBll();
        ArrayList<Room> availableRooms = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<RentUnit> rentUnitsToBeRemoved = new ArrayList<>();
        for (RentUnit rentUnit : rentUnitsFiltered) {
            Set<Room> rooms1 = roomBll.getRoomsForThisRentUnit(rentUnit);
            if (rooms1.isEmpty()) {
                rentUnitsToBeRemoved.add(rentUnit);
            }
            rooms1.stream().forEach(room -> rooms.add(room));
        }
        for (RentUnit rentUnit : rentUnitsToBeRemoved) {
            rentUnitsFiltered.remove(rentUnit);
        }
        int ok = 1;
        for (Room room : rooms) {
            ok = 1;
            Set<Reservation> reservations = reservationBll.getReservationsForThisRoom(room);
            if (reservations.isEmpty()) {
                availableRooms.add(room);
            } else {
                for (Reservation reservation : reservations) {
                    if ((
                            DateComparator.compareDates(Integer.parseInt(day1Text), Integer.parseInt(month1Text), Integer.parseInt(year1Text), reservation.getCheckInDay(), reservation.getCheckInMonth(), reservation.getCheckInYear()) >= 0
                                    &&
                                    DateComparator.compareDates(Integer.parseInt(day1Text), Integer.parseInt(month1Text), Integer.parseInt(year1Text), reservation.getCheckOutDay(), reservation.getCheckOutMonth(), reservation.getCheckOutYear()) <= 0
                    )
                            ||
                            (DateComparator.compareDates(Integer.parseInt(day2Text), Integer.parseInt(month2Text), Integer.parseInt(year2Text), reservation.getCheckOutDay(), reservation.getCheckOutMonth(), reservation.getCheckOutYear()) >= 0
                                    &&
                                    DateComparator.compareDates(Integer.parseInt(day1Text), Integer.parseInt(month1Text), Integer.parseInt(year1Text), reservation.getCheckOutDay(), reservation.getCheckOutMonth(), reservation.getCheckOutYear()) <= 0
                            )
                    ) {
                        ok = 0;
                    }
//                    if ((checkInDate.compareTo(reservation.getCheckInDate()) >= 0 &&
//                            checkInDate.compareTo(reservation.getCheckOutDate()) <= 0) ||
//                            (checkOutData.compareTo(reservation.getCheckOutDate()) >= 0 &&
//                                    checkInDate.compareTo(reservation.getCheckOutDate()) <= 0)) {
//                        ok = false;
//                    }
                }
                if (ok == 1 && !availableRooms.contains(room)) {
                    availableRooms.add(room);
                }
            }
        }
        Account account = (new AccountBll()).findAccountById(accountId);
        int pointsValue = account.getLoyaltyPoints().getValue();
        ReviewBll reviewBll = new ReviewBll();
        ArrayList<Review> availableReviews = new ArrayList<>();
            try {
                PrintWriter out =  new PrintWriter(socket.getOutputStream(), true);
                if (pointsValue == 20) {
                    for (Room room : availableRooms) {
                        room.setPrice(((float) 90 * room.getPrice()) / ((float) 100));
                    }
                    account.getLoyaltyPoints().setValue(0);
                    (new LoyaltyPointsBll()).edit(account.getLoyaltyPoints());
                    out.println("sale");
                    out.flush();
                } else {
                    out.println("notSale");
                    out.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        for (RentUnit rentUnit : rentUnitsFiltered) {
            Set<Review> reviews = reviewBll.getReviewForThisRentUnit(rentUnit);
            reviews.stream().forEach(review -> availableReviews.add(review));
        }

        System.out.println("RoomDebugging");
        for (Room room : availableRooms) {
            System.out.println(room.getPrice() +"   "+ pointsValue);
        }

        rentUnitList.setRentUnits(rentUnitsFiltered);
        rentUnitList.setRooms(availableRooms);
        rentUnitList.setReviews(availableReviews);
        return rentUnitList;

    }

    public void createReservation(String request, Room room, Account account) {
        Notification notification = new Notification();
        notification.setNotificationnId(UUID.randomUUID().toString());
        notification.setAccount(room.getRentUnit().getAccount());
        StringBuilder sb = new StringBuilder("Client ");
        sb.append(account.getUsername());
        sb.append(" with name ");
        sb.append(account.getUser().getFirstName());
        sb.append(" and with mail ");
        sb.append(account.getUser().getMail());
        sb.append(" has rent a room at ");
        sb.append(room.getRentUnit().getName());
        notification.setInformation(sb.toString());
        (new NotificationBll()).insertNotification(notification);
        System.out.println("Not   " + notification.getInformation());
        String dayIn, monthIn, yearIn, dayOut, monthOut, yearOut;
        String[] parts = request.split(",");
        if (parts[1] != null) {
            dayIn = parts[1];
        } else {
            dayIn = "0";
        }
        if (parts[2] != null) {
            monthIn = parts[2];
        } else {
            monthIn = "0";
        }
        if (parts[3] != null) {
            yearIn = parts[3];
        } else {
            yearIn = "0";
        }
        if (parts[4] != null) {
            dayOut = parts[4];
        } else {
            dayOut = "0";
        }
        if (parts[5] != null) {
            monthOut = parts[5];
        } else {
            monthOut = "0";
        }
        if (parts[6] != null) {
            yearOut = parts[6];
        } else {
            yearOut = "0";
        }
        account.getLoyaltyPoints().setValue(account.getLoyaltyPoints().getValue() + 10);
        (new LoyaltyPointsBll()).edit(account.getLoyaltyPoints());
        Reservation reservation = new Reservation();
        reservation.setReservationId(UUID.randomUUID().toString());
        Date checkInDate = new Date(Integer.parseInt(yearIn), Integer.parseInt(monthIn), Integer.parseInt(dayIn));
        Date checkOutDate = new Date(Integer.parseInt(yearOut), Integer.parseInt(monthOut), Integer.parseInt(dayOut));
        reservation.setCheckInDay(Integer.parseInt(dayIn));
        reservation.setCheckInMonth(Integer.parseInt(monthIn));
        reservation.setCheckInYear(Integer.parseInt(yearIn));
        reservation.setCheckOutDay(Integer.parseInt(dayOut));
        reservation.setCheckOutMonth(Integer.parseInt(monthOut));
        reservation.setCheckOutYear(Integer.parseInt(yearOut));
        reservation.setRoom(room);
        reservation.setAccount(account);
        (new ReservationBll()).insertReservation(reservation);
    }

    public RentUnitList getAllRentUnitsAndAllReviewsForThisAccount(Account account) {
        RentUnitList rentUnitList = new RentUnitList();
        ArrayList<Reservation> reservations = (ArrayList<Reservation>) (new ReservationDao()).readAll();

        for (Reservation reservation : reservations) {
            if (reservation.getAccount().getAccountId().equals(account.getAccountId())) {
                RentUnit rentUnit = reservation.getRoom().getRentUnit();
                if (!rentUnitList.getRentUnits().contains(rentUnit))
                    rentUnitList.getRentUnits().add(reservation.getRoom().getRentUnit());
            }
        }
        ArrayList<Review> reviews = (ArrayList<Review>) (new ReviewDao()).readAll();
        for (Review review : reviews) {
            if (review.getAccount().getAccountId().equals(account.getAccountId())) {
                rentUnitList.getReviews().add(review);
            }
        }

        return rentUnitList;
    }

    public void createAndInsertReview(String request) {
        String[] parts = request.split(",");
        String accountId = "", rentUnitId = "", reviewText = "", grade = "0";
        if (parts[1] != null) {
            accountId = parts[1];
        }
        if (parts[2] != null) {
            rentUnitId = parts[2];
        }
        if (parts[3] != null) {
            reviewText = parts[3];
        }
        if (parts[4] != null) {
            grade = parts[4];
        }
        Account account = (new AccountBll()).findAccountById(accountId);
        RentUnit rentUnit = (new RentUnitBll()).findById(rentUnitId);
        Review review = new Review();
        review.setReviewId(UUID.randomUUID().toString());
        review.setText(reviewText);
        review.setGrade(Float.parseFloat(grade));
        review.setRentUnit(rentUnit);
        review.setAccount(account);
        (new ReviewBll()).insertReview(review);
    }

    public ReservationList showAllReservations(String requst) {
        String[] parts = requst.split(",");
        String accountId = "";
        if (parts[1] != null) {
            accountId = parts[1];
        }
        Account account = (new AccountBll()).findAccountById(accountId);
        ReservationList reservationList = new ReservationList();
        if (account != null) {
            Set<Reservation> reservationSet = (new AccountBll()).getReservationsForThisAccount(account);
            for (Reservation reservation : reservationSet) {
                reservationList.getReservations().add(reservation);
            }
        }
        return reservationList;
    }

    public void findAndDeleteReservation(String request) {
        String[] parts = request.split(",");
        ReservationBll reservationBll = new ReservationBll();
        String reservationId = "";
        String accountId = "";
        if (parts[1] != null) {
            reservationId = parts[1];
        }
        if (parts[2] != null) {
            accountId = parts[2];
            Account account = (new AccountBll()).findAccountById(accountId);
            if (account.getLoyaltyPoints().getValue() >= 10) {
                account.getLoyaltyPoints().setValue(account.getLoyaltyPoints().getValue() - 10);
                (new LoyaltyPointsBll()).edit(account.getLoyaltyPoints());
            }
        }

        Reservation reservation = reservationBll.findReservationById(reservationId);
        reservationBll.deleteReservation(reservation);

    }


}
