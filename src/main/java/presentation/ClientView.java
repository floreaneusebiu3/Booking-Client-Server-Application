package presentation;

import bll.AccountBll;
import model.*;

import utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class ClientView {
    JFrame frame;
    JLabel image;
    JLabel username;
    JLabel points_value;
    JLabel starImage;
    JButton viewRentUnits;
    JButton cancelRental;
    JButton addReview;
    JButton chat;
    JPanel menuPanel;
    String[] rentUnitsHead = {"Rent Unit Name", "Town"};
    String[] reviewsHead = {"USER", "GRADE", "TEXT"};
    String[] reviewsHeadInReviews = {"RENT UNIT NAME", "GRADE", "TEXT"};
    String[] roomsHead = {"Room nr.", "Capacity", "Price", "Facilities"};
    String[] chatHead = {"Chats"};
    String[] chatHead2 = {"PERSON", "ME"};
    Object[][] rentUnitsData = new Object[100][2];
    Object[][] roomsData = new Object[100][4];
    Object[][] chatData = new Object[100][4];
    Object[][] chatData2 = new Object[100][4];
    Object[][] reviewsData = new Object[100][4];
    Object[][] rentUnitsDataInReviews = new Object[100][2];
    JTable rentUnitsTable;
    JTable roomsTable;
    JTable chatTable;

    JLabel checkIn;
    JLabel checkOut;

    JTextField day1;
    JTextField day2;
    JTextField month1;
    JTextField month2;
    JTextField year1;
    JTextField year2;
    JTextField country;
    JTextField town;

    JButton viewButton;
    JButton rentButton;
    JLabel rentUnitName;
    JLabel rentUnitContry;
    JLabel rentUnitTown;
    JLabel rentUnitRating;
    JLabel rentUnitTelephone;
    JLabel rentUnitReviews;
    JLabel rentUnitImage;
    JTextArea rentUnitDescription;
    JTable reviewsTable;
    JPanel viewAllPanel;
    JPanel chatPanel;
    JPanel rentUnitPanel;
    JPanel messagesPanel;
    JButton selectButton;
    JButton sendButton;
    JTextField messageChat;
    ArrayList<RentUnit> rentUnits;
    ArrayList<Room> rooms;
    ArrayList<Review> reviews;

    JTable chatTable2;

    Socket socket;
    PrintWriter out;
    BufferedReader in;
    int i = 0;
    RentUnitList rentUnitListClient;

    Account account;

    JTable rentUnitsTableInReviews;
    JTable reviewsTableInRevies;
    JTextField reviewText;
    JTextField gradeField;
    JLabel rentUnitsTitle;
    JLabel reviewsTitle;
    JPanel reviewsPanel;
    JButton addReviewButton;
    Object[][] reviewsDataInReviews = new Object[100][4];
    AccountsList accountsList = new AccountsList();
    Account selectedAccount = new Account();

    String[] reservationsHead = {"Rent Unit Name", "CheckIn Day", "CheckInMonth", "CheckInYear", "CheckOut Day", "CheckOut Month", "CheckOut Year"};
    Object[][] reservationsData = new Object[100][7];
    JTable reservationsTable;
    JButton deleteReservation;
    JPanel reservationsPanel;
    ReservationList reservationList;
    JPanel userPanel;

    public ClientView(Socket socket, Account account) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
        this.account = account;
        frame = new JFrame("Client");
        frame.setSize(1200, 840);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 1200, 50);
        menuPanel.setBackground(new Color(187, 227, 239, 255));

        userPanel = new JPanel();
        userPanel.setBackground(new Color(187, 227, 239, 255));
        userPanel.setBounds(1000, 10, 200, 30);

        try {
            BufferedImage imageBuffer = ImageIO.read(new File("img/BookingTtitle.png"));
            image = new JLabel(new ImageIcon(imageBuffer));
            image.setBounds(0, 0, 200, 50);

            BufferedImage starBuffer = ImageIO.read(new File("img/star.png"));
            starImage = new JLabel(new ImageIcon(starBuffer));
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewRentUnits = new JButton("View Rent Units");
        viewRentUnits.setBounds(200, 10, 130, 30);
        menuPanel.add(viewRentUnits);

        cancelRental = new JButton("RESERVATIONS");
        cancelRental.setBounds(360, 10, 130, 30);
        menuPanel.add(cancelRental);

        addReview = new JButton("Add review");
        addReview.setBounds(520, 10, 100, 30);
        menuPanel.add(addReview);

        chat = new JButton("Open chat");
        chat.setBounds(650, 10, 100, 30);
        menuPanel.add(chat);

        username = new JLabel(account.getUsername());
        username.setSize(100, 20);
        userPanel.add(username);

        points_value = new JLabel(" ");
        userPanel.add(points_value);
        userPanel.add(starImage);

        menuPanel.add(userPanel);
        menuPanel.add(image);

        viewAllPanel = new JPanel();
        viewAllPanel.setLayout(null);
        viewAllPanel.setBounds(0, 50, 1200, 300);
        viewAllPanel.setBackground(Color.white);


        checkIn = new JLabel("check in");
        checkIn.setFont(new Font("Verdana", Font.BOLD, 15));
        checkIn.setBounds(300, 10, 100, 30);
        viewAllPanel.add(checkIn);

        checkOut = new JLabel("check out");
        checkOut.setFont(new Font("Verdana", Font.BOLD, 15));
        checkOut.setBounds(600, 10, 100, 30);
        viewAllPanel.add(checkOut);

        day1 = new JTextField("day");
        day1.setBackground(new Color(187, 227, 239, 255));
        day1.setBounds(220, 40, 70, 30);
        day1.setForeground(Color.gray);
        day1.setHorizontalAlignment(JTextField.CENTER);
        day1.setFont(new Font("Verdana", Font.BOLD, 15));
        viewAllPanel.add(day1);

        month1 = new JTextField("month");
        month1.setBackground(new Color(187, 227, 239, 255));
        month1.setBounds(300, 40, 70, 30);
        month1.setForeground(Color.gray);
        month1.setHorizontalAlignment(JTextField.CENTER);
        month1.setFont(new Font("Verdana", Font.BOLD, 15));
        viewAllPanel.add(month1);

        year1 = new JTextField("year");
        year1.setBackground(new Color(187, 227, 239, 255));
        year1.setBounds(380, 40, 70, 30);
        year1.setForeground(Color.gray);
        year1.setHorizontalAlignment(JTextField.CENTER);
        year1.setFont(new Font("Verdana", Font.BOLD, 15));
        viewAllPanel.add(year1);

        day2 = new JTextField("day");
        day2.setBackground(new Color(187, 227, 239, 255));
        day2.setBounds(520, 40, 70, 30);
        day2.setForeground(Color.gray);
        day2.setHorizontalAlignment(JTextField.CENTER);
        day2.setFont(new Font("Verdana", Font.BOLD, 15));
        viewAllPanel.add(day2);

        month2 = new JTextField("month");
        month2.setBackground(new Color(187, 227, 239, 255));
        month2.setBounds(600, 40, 70, 30);
        month2.setForeground(Color.gray);
        month2.setHorizontalAlignment(JTextField.CENTER);
        month2.setFont(new Font("Verdana", Font.BOLD, 15));
        viewAllPanel.add(month2);

        year2 = new JTextField("year");
        year2.setBackground(new Color(187, 227, 239, 255));
        year2.setBounds(680, 40, 70, 30);
        year2.setForeground(Color.gray);
        year2.setHorizontalAlignment(JTextField.CENTER);
        year2.setFont(new Font("Verdana", Font.BOLD, 15));
        viewAllPanel.add(year2);

        country = new JTextField("country");
        country.setBackground(new Color(187, 227, 239, 255));
        country.setForeground(Color.gray);
        country.setHorizontalAlignment(JTextField.CENTER);
        country.setFont(new Font("Verdana", Font.BOLD, 15));
        country.setBounds(800, 40, 100, 30);
        viewAllPanel.add(country);

        town = new JTextField("town");
        town.setBackground(new Color(187, 227, 239, 255));
        town.setForeground(Color.gray);
        town.setBounds(905, 40, 100, 30);
        town.setHorizontalAlignment(JTextField.CENTER);
        town.setFont(new Font("Verdana", Font.BOLD, 15));
        viewAllPanel.add(town);

        viewButton = new JButton("view");
        viewButton.setBackground(new Color(187, 227, 239, 255));
        viewButton.setBounds(1050, 40, 80, 30);
        viewButton.setHorizontalAlignment(JTextField.CENTER);
        viewButton.setFont(new Font("Verdana", Font.BOLD, 14));
        viewAllPanel.add(viewButton);

        rentUnitsTable = new JTable(rentUnitsData, rentUnitsHead);
        JScrollPane scrollPane = new JScrollPane(rentUnitsTable);
        scrollPane.setBounds(100, 100, 480, 200);
        viewAllPanel.add(scrollPane);

        roomsTable = new JTable(roomsData, roomsHead);
        JScrollPane scrollPane1 = new JScrollPane(roomsTable);
        scrollPane1.setBounds(600, 100, 500, 200);
        viewAllPanel.add(scrollPane1);

        rentUnitPanel = new JPanel();
        rentUnitPanel.setBounds(0, 350, 1200, 400);
        rentUnitPanel.setLayout(null);
        rentUnitPanel.setBackground(Color.white);


        rentUnitName = new JLabel("name");
        rentUnitName.setOpaque(true);
        rentUnitName.setBackground(new Color(187, 227, 239, 255));
        rentUnitName.setBounds(30, 20, 670, 30);
        rentUnitName.setHorizontalAlignment(JTextField.CENTER);
        rentUnitName.setFont(new Font("Verdana", Font.BOLD, 18));
        rentUnitPanel.add(rentUnitName);

        rentButton = new JButton("Rent");
        rentButton.setBackground(new Color(187, 227, 239, 255));
        rentButton.setBounds(702, 20, 398, 30);
        rentButton.setHorizontalAlignment(JTextField.CENTER);
        rentButton.setFont(new Font("Verdana", Font.BOLD, 14));
        rentUnitPanel.add(rentButton);

        rentUnitContry = new JLabel("<html>Country:<br/>" + "Romania</html>", SwingConstants.CENTER);
        rentUnitContry.setBounds(30, 70, 120, 80);
        rentUnitContry.setFont(new Font("Verdana", Font.BOLD, 18));
        rentUnitPanel.add(rentUnitContry);

        rentUnitTown = new JLabel("<html>Town:<br/>" + "Cluj-Napoca</html>", SwingConstants.CENTER);
        rentUnitTown.setBounds(230, 70, 130, 80);
        rentUnitTown.setFont(new Font("Verdana", Font.BOLD, 18));
        rentUnitPanel.add(rentUnitTown);

        rentUnitRating = new JLabel("<html>Rating:<br/>" + "8</html>", SwingConstants.CENTER);
        rentUnitRating.setBounds(400, 70, 120, 80);
        rentUnitRating.setFont(new Font("Verdana", Font.BOLD, 18));
        rentUnitPanel.add(rentUnitRating);

        rentUnitTelephone = new JLabel("<html>Telephone:<br/>" + "0762570580</html>", SwingConstants.CENTER);
        rentUnitTelephone.setBounds(560, 70, 190, 80);
        rentUnitTelephone.setFont(new Font("Verdana", Font.BOLD, 18));
        rentUnitPanel.add(rentUnitTelephone);

        BufferedImage rentImageBuffer = null;
        try {
            rentImageBuffer = ImageIO.read(new File("img/room1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rentUnitImage = new JLabel(new ImageIcon(rentImageBuffer));
        rentUnitImage.setBounds(30, 160, 400, 267);
        rentUnitPanel.add(rentUnitImage);

        rentUnitDescription = new JTextArea();
        rentUnitDescription.setBackground(new Color(187, 227, 239, 255));
        rentUnitDescription.setText("");
        rentUnitDescription.append("CEA MAI BUNA OFERTA.\n ORICAT AI CAUTA NU \n GASESTI \n NIMIC MAI BUN");
        rentUnitDescription.setFont(new Font("Verdana", Font.BOLD, 13));
        rentUnitDescription.setBounds(450, 160, 650, 70);
        rentUnitPanel.add(rentUnitDescription);

        rentUnitReviews = new JLabel("REVIEWS:");
        rentUnitReviews.setBounds(750, 240, 100, 30);
        rentUnitReviews.setFont(new Font("Verdana", Font.BOLD, 17));
        rentUnitPanel.add(rentUnitReviews);

        reviewsTable = new JTable(reviewsData, reviewsHead);
        JScrollPane jScrollPane3 = new JScrollPane(reviewsTable);
        jScrollPane3.setBounds(450, 280, 650, 120);
        rentUnitPanel.add(jScrollPane3);

        //chat panel
        chatPanel = new JPanel();
        chatPanel.setLayout(null);
        chatPanel.setBounds(0, 50, 1200, 700);
        chatPanel.setBackground(Color.white);

        chatTable = new JTable(chatData, chatHead);
        JScrollPane scrollPane4 = new JScrollPane(chatTable);
        scrollPane4.setBounds(300, 100, 500, 300);
        chatPanel.add(scrollPane4);

        selectButton = new JButton("SELECT");
        selectButton.setBounds(500, 420, 130, 30);
        chatPanel.add(selectButton);

        ///messages panel
        messagesPanel = new JPanel();
        messagesPanel.setLayout(null);
        messagesPanel.setBounds(0, 50, 1200, 700);
        messagesPanel.setBackground(Color.white);

        sendButton = new JButton("SEND");
        sendButton.setBounds(670, 410, 130, 30);
        messagesPanel.add(sendButton);

        messageChat = new JTextField("");
        messageChat.setBackground(new Color(187, 227, 239, 255));
        messageChat.setBounds(300, 410, 360, 30);
        messageChat.setForeground(Color.black);
        messageChat.setHorizontalAlignment(JTextField.CENTER);
        messageChat.setFont(new Font("Verdana", Font.BOLD, 15));
        messagesPanel.add(messageChat);

        reviewsPanel = new JPanel();
        reviewsPanel.setBackground(Color.white);
        reviewsPanel.setBounds(0, 50, 1200, 700);
        reviewsPanel.setLayout(null);

        reviewsTitle = new JLabel("My reviews");
        reviewsTitle.setOpaque(true);
        reviewsTitle.setBackground(new Color(187, 227, 239, 255));
        reviewsTitle.setBounds(500, 10, 200, 30);
        reviewsTitle.setForeground(Color.black);
        reviewsTitle.setHorizontalAlignment(JTextField.CENTER);
        reviewsTitle.setFont(new Font("Verdana", Font.BOLD, 15));
        reviewsPanel.add(reviewsTitle);

        reviewsTableInRevies = new JTable(reviewsDataInReviews, reviewsHeadInReviews);
        JScrollPane jScrollPane6 = new JScrollPane(reviewsTableInRevies);
        jScrollPane6.setBounds(300, 50, 600, 200);
        reviewsPanel.add(jScrollPane6);

        rentUnitsTitle = new JLabel("My Rents");
        rentUnitsTitle.setOpaque(true);
        rentUnitsTitle.setBackground(new Color(187, 227, 239, 255));
        rentUnitsTitle.setBounds(500, 260, 200, 30);
        rentUnitsTitle.setForeground(Color.black);
        rentUnitsTitle.setHorizontalAlignment(JTextField.CENTER);
        rentUnitsTitle.setFont(new Font("Verdana", Font.BOLD, 15));
        reviewsPanel.add(rentUnitsTitle);

        rentUnitsTableInReviews = new JTable(rentUnitsDataInReviews, rentUnitsHead);
        JScrollPane jScrollPane5 = new JScrollPane(rentUnitsTableInReviews);
        jScrollPane5.setBounds(300, 300, 600, 200);
        reviewsPanel.add(jScrollPane5);

        reviewText = new JTextField("TYPE YOUR REVIEW HERE");
        reviewText.setBackground(new Color(187, 227, 239, 255));
        reviewText.setBounds(100, 520, 1000, 30);
        reviewText.setForeground(Color.GRAY);
        reviewText.setHorizontalAlignment(JTextField.CENTER);
        reviewText.setFont(new Font("Verdana", Font.BOLD, 15));
        reviewsPanel.add(reviewText);

        gradeField = new JTextField("GRADE");
        gradeField.setBackground(new Color(187, 227, 239, 255));
        gradeField.setBounds(500, 570, 200, 30);
        gradeField.setForeground(Color.GRAY);
        gradeField.setHorizontalAlignment(JTextField.CENTER);
        gradeField.setFont(new Font("Verdana", Font.BOLD, 15));
        reviewsPanel.add(gradeField);

        addReviewButton = new JButton("ADD REVIEW");
        addReviewButton.setBounds(500, 620, 200, 30);
        addReviewButton.setBackground(new Color(187, 227, 239, 255));
        addReviewButton.setHorizontalAlignment(JTextField.CENTER);
        addReviewButton.setFont(new Font("Verdana", Font.BOLD, 14));
        reviewsPanel.add(addReviewButton);

        reservationsPanel = new JPanel();
        reservationsPanel.setBackground(Color.white);
        reservationsPanel.setBounds(0, 50, 1200, 700);
        reservationsPanel.setLayout(null);

        reservationsTable = new JTable(reservationsData, reservationsHead);
        JScrollPane jScrollPane7 = new JScrollPane(reservationsTable);
        jScrollPane7.setBounds(100, 50, 1000, 400);
        reservationsPanel.add(jScrollPane7);

        deleteReservation = new JButton("DELETE RESERVATION");
        deleteReservation.setBounds(450, 500, 250, 30);
        deleteReservation.setBackground(new Color(187, 227, 239, 255));
        deleteReservation.setHorizontalAlignment(JTextField.CENTER);
        deleteReservation.setFont(new Font("Verdana", Font.BOLD, 14));
        reservationsPanel.add(deleteReservation);

        frame.add(menuPanel);
        frame.setVisible(true);
        addPlaceHolderToFields();
    }

    public void addPlaceHolderToFields() {

        viewRentUnits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllRentUnits();
                frame.getContentPane().removeAll();
                frame.add(menuPanel);
                frame.add(viewAllPanel);
                frame.getContentPane().repaint();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllRentUnits();
            }
        });

        rentUnitsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < 100; i++) {
                    roomsData[i][0] = "";
                    roomsData[i][1] = "";
                    roomsData[i][2] = "";
                    roomsData[i][3] = "";
                    reviewsData[i][0] = "";
                    reviewsData[i][1] = "";
                    reviewsData[i][2] = "";
                }

                frame.getContentPane().removeAll();
                frame.getContentPane().add(menuPanel);
                frame.getContentPane().add(viewAllPanel);
                frame.getContentPane().add(rentUnitPanel);
                frame.getContentPane().repaint();
                int ind = rentUnitsTable.getSelectedRow();
                if (ind < rentUnits.size()) {
                    RentUnit rentUnit = rentUnits.get(ind);
                    if (rentUnit.getName() == null) {
                        rentUnitName.setText("-");
                    } else {
                        rentUnitName.setText("Name: " + rentUnit.getName());
                    }
                    if (rentUnit.getCountry() == null) {
                        rentUnitContry.setText("-");
                    } else {
                        rentUnitContry.setText("<html>Country:<br/>" + rentUnit.getCountry() + "</html>");
                    }
                    if (rentUnit.getTown() == null) {
                        rentUnitTown.setText("-");
                    } else {
                        rentUnitTown.setText("<html>Town:<br/>" + rentUnit.getTown() + "</html>");
                    }
                    float nota = 0;
                    int nr = 0;
                    for (Review review : rentUnitListClient.getReviews()) {
                        if (review.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                            nota += review.getGrade();
                            nr++;
                        }
                    }

                    if (nr == 0) {
                        rentUnitRating.setText("-");
                    } else {
                        rentUnitRating.setText("<html>Rating:<br/>" + nota / nr + "</html>");
                    }
                    if (rentUnit.getTelephoneNumber() == null) {
                        rentUnitTelephone.setText("-");
                    } else {
                        rentUnitTelephone.setText("<html>Telephone::<br/>" + rentUnit.getTelephoneNumber() + "</html>");
                    }
                    if (rentUnit.getDescription() == null) {
                        rentUnitDescription.setText("");
                    } else {
                        rentUnitDescription.setText(rentUnit.getDescription());
                    }
                    if (rentUnit.getImage() == null) {
                        try {
                            BufferedImage imageBuffer = ImageIO.read(new File("img/noo.jpg"));
                            rentUnitImage.setIcon(new ImageIcon(imageBuffer));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        try {
                            BufferedImage rentImageBuffer = ImageIO.read(new File(rentUnit.getImage().getImageName()));
                            rentUnitImage.setIcon(new ImageIcon(rentImageBuffer));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    int cntRoom = 0;
                    for (Room room : rooms) {
                        if (room.getRentUnit() != null)
                            if (room.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                                roomsData[cntRoom][0] = cntRoom;
                                roomsData[cntRoom][1] = room.getCapacity();
                                roomsData[cntRoom][2] = room.getPrice();
                                if (room.getFacilitati() != null) {
                                    roomsData[cntRoom++][3] = room.getFacilitati();
                                } else {
                                    roomsData[cntRoom++][3] = room.getFacilitati();
                                }
                            }
                    }
                    int cntReview = 0;
                    for (Review review : reviews) {
                        if (review.getRentUnit() != null)
                            if (review.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                                reviewsData[cntReview][0] = review.getAccount().getUsername();
                                reviewsData[cntReview][1] = review.getGrade();
                                reviewsData[cntReview++][2] = review.getText();
                            }
                    }
                } else {
                    rentUnitName.setText("--");
                    rentUnitContry.setText("--");
                    rentUnitTown.setText("--");
                    rentUnitRating.setText("--");
                    rentUnitTelephone.setText("--");
                    rentUnitDescription.setText("--");
                    try {
                        BufferedImage rentImageBuffer = ImageIO.read(new File("img/noo.jpg"));
                        rentUnitImage.setIcon(new ImageIcon(rentImageBuffer));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                frame.getContentPane().repaint();
            }
        });

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StringBuilder request = new StringBuilder("createReservation,");
                    request.append(day1.getText() + ",");
                    request.append(month1.getText() + ",");
                    request.append(year1.getText() + ",");
                    request.append(day2.getText() + ",");
                    request.append(month2.getText() + ",");
                    request.append(year2.getText());
                    int roomInd = roomsTable.getSelectedRow();
                    int rentUnitId = rentUnitsTable.getSelectedRow();
                    RentUnit rentUnit = rentUnitListClient.getRentUnits().get(rentUnitId);
                    ArrayList<Room> rooms = rentUnitListClient.getRooms();
                    ArrayList<Room> availableRooms = new ArrayList<>();
                    for(Room room : rooms) {
                        if( room.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                            availableRooms.add(room);
                        }
                    }
                    if (roomInd < availableRooms.size()) {
                        Room room = availableRooms.get(roomInd);
                        ReservationFields reservationFields = new ReservationFields();
                        reservationFields.setAccount(account);
                        reservationFields.setRoom(room);
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(request);
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(reservationFields);
                        viewAllRentUnitsWithModifiedPrice();
                        out.println("giveMeUpdatedAccount," + account.getAccountId());
                        out.flush();
                        ObjectInputStream objectInputStream1 = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket.getOutputStream());
                        account = (Account) objectInputStream1.readObject();
                        points_value.setText(String.valueOf(account.getLoyaltyPoints().getValue()));
                        userPanel.repaint();

                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        cancelRental.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewReservations();
            }
        });

        deleteReservation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = reservationsTable.getSelectedRow();
                    StringBuilder request = new StringBuilder("deleteReservation,");
                    if (index < reservationList.getReservations().size()) {
                        Reservation reservation = reservationList.getReservations().get(index);
                        request.append(reservation.getReservationId() + ",");
                        request.append(account.getAccountId());
                        out = new PrintWriter(socket.getOutputStream(), true);
                        out.println(request);
                        out.flush();
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String requestReply = in.readLine();
                        if (requestReply.equals("ok")) {
                            JOptionPane.showMessageDialog(frame, "You have deleted your reservation");
                        }
                        viewReservations();
                        out.println("giveMeUpdatedAccount," + account.getAccountId());
                        out.flush();
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        account = (Account) objectInputStream.readObject();
                        //account = (new AccountBll()).findAccountById(account.getAccountId());
                        points_value.setText(String.valueOf(account.getLoyaltyPoints().getValue()));
                        userPanel.repaint();
                    }
                    else {
                        JOptionPane.showMessageDialog(frame, "You must select a reservation!");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        addReview.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showReviews();
            }
        });

        addReviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedRentUnitId = "0";
                    int index = rentUnitsTableInReviews.getSelectedRow();
                    if (index < rentUnitListClient.getRentUnits().size()) {
                        selectedRentUnitId = rentUnitListClient.getRentUnits().get(index).getUnitId();
                        StringBuilder request = new StringBuilder("createReview,");
                        request.append(account.getAccountId() + ",");
                        request.append(selectedRentUnitId + ",");
                        request.append(reviewText.getText() + ",");
                        request.append(gradeField.getText() + ",");
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out.println(request);
                        out.flush();
                        String requestReply = in.readLine();
                        if (requestReply.equals("ok")) {
                            JOptionPane.showMessageDialog(frame, "Thank you for the added review");
                            showReviews();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "You must select a Rent Unit!");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

        chat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String request = new String("getAllAccounts");
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(request);
                    out.flush();
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    accountsList = (AccountsList) objectInputStream.readObject();

                    int cnt = 0;
                    for (int i = 0; i < 100; i++) {
                        chatData[i][0] = "";
                    }
                    for (Account account1 : accountsList.getAccounts()) {
                        if (!account.getAccountId().equals(account1.getAccountId()))
                            chatData[cnt++][0] = account1.getUsername();
                        else
                            chatData[cnt++][0] = "me";
                    }

                    frame.getContentPane().removeAll();
                    frame.add(menuPanel);
                    frame.add(chatPanel);
                    frame.getContentPane().repaint();
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintChat();
            }
        });

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    StringBuilder request = new StringBuilder("sendMessage,");
                    request.append(selectedAccount.getAccountId() + ",");
                    request.append(account.getAccountId() + ",");
                    request.append(messageChat.getText() + ",");
                    out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(request);
                    out.flush();
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String requestReply = in.readLine();
                    if (requestReply.equals("ok")) {
                        paintChat();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                messageChat.setText("");
            }
        });


        day1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (day1.getText().equals("day")) {
                    day1.setForeground(Color.black);
                    day1.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (day1.getText().isEmpty()) {
                    day1.setForeground(Color.gray);
                    day1.setText("day");
                }
            }
        });

        day2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (day2.getText().equals("day")) {
                    day2.setForeground(Color.black);
                    day2.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (day2.getText().isEmpty()) {
                    day2.setForeground(Color.gray);
                    day2.setText("day");
                }
            }
        });
        month1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (month1.getText().equals("month")) {
                    month1.setForeground(Color.black);
                    month1.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (month1.getText().isEmpty()) {
                    month1.setForeground(Color.gray);
                    month1.setText("month");
                }
            }
        });

        month2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (month2.getText().equals("month")) {
                    month2.setForeground(Color.black);
                    month2.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (month2.getText().isEmpty()) {
                    month2.setForeground(Color.gray);
                    month2.setText("month");
                }
            }
        });

        year1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (year1.getText().equals("year")) {
                    year1.setForeground(Color.black);
                    year1.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (year1.getText().isEmpty()) {
                    year1.setForeground(Color.gray);
                    year1.setText("year");
                }
            }
        });

        year2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (year2.getText().equals("year")) {
                    year2.setForeground(Color.black);
                    year2.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (year2.getText().isEmpty()) {
                    year2.setForeground(Color.gray);
                    year2.setText("year");
                }
            }
        });

        country.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (country.getText().equals("country")) {
                    country.setForeground(Color.black);
                    country.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (country.getText().isEmpty()) {
                    country.setForeground(Color.gray);
                    country.setText("country");
                }
            }
        });

        town.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (town.getText().equals("town")) {
                    town.setForeground(Color.black);
                    town.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (town.getText().isEmpty()) {
                    town.setForeground(Color.gray);
                    town.setText("town");
                }
            }
        });

        reviewText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (reviewText.getText().equals("TYPE YOUR REVIEW HERE")) {
                    reviewText.setForeground(Color.black);
                    reviewText.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (reviewText.getText().isEmpty()) {
                    reviewText.setForeground(Color.gray);
                    reviewText.setText("TYPE YOUR REVIEW HERE");
                }
            }
        });

        gradeField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (gradeField.getText().equals("GRADE")) {
                    gradeField.setForeground(Color.black);
                    gradeField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (gradeField.getText().isEmpty()) {
                    gradeField.setForeground(Color.gray);
                    gradeField.setText("GRADE");
                }
            }
        });

    }

    public void showReviews() {
        try {
            gradeField.setText("GRADE");
            gradeField.setForeground(Color.gray);
            reviewText.setText("TYPE YOUR REVIEW HERE");
            reviewText.setForeground(Color.gray);
            int cnt = 0;
            StringBuilder request = new StringBuilder("seeAccountRentUnitsAndReviews");
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);
            out.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            rentUnitListClient = (RentUnitList) objectInputStream.readObject();
            for (int i = 0; i < 100; i++) {
                rentUnitsDataInReviews[i][0] = "";
                rentUnitsDataInReviews[i][1] = "";
                reviewsDataInReviews[i][0] = "";
                reviewsDataInReviews[i][1] = "";
                reviewsDataInReviews[i][2] = "";
            }
            if (rentUnitListClient != null) {
                if (rentUnitListClient.getRentUnits() != null) {
                    cnt = 0;
                    for (RentUnit rentUnit : rentUnitListClient.getRentUnits()) {
                        rentUnitsDataInReviews[cnt][0] = rentUnit.getName();
                        rentUnitsDataInReviews[cnt++][1] = rentUnit.getTown();
                    }
                }
                if (rentUnitListClient.getReviews() != null) {
                    cnt = 0;
                    for (Review review : rentUnitListClient.getReviews()) {
                        String accountId = review.getAccount().getAccountId();
                        if (accountId.equals(account.getAccountId())) {
                            reviewsDataInReviews[cnt][0] = review.getRentUnit().getName();
                            reviewsDataInReviews[cnt][1] = review.getGrade();
                            reviewsDataInReviews[cnt++][2] = review.getText();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        frame.getContentPane().removeAll();
        frame.getContentPane().add(menuPanel);
        frame.add(reviewsPanel);
        frame.getContentPane().repaint();
    }

    public void printMessages(MessagesContainer messagesContainer) {
        for (int i = 0; i < 100; i++) {
            chatData2[i][0] = "";
            chatData2[i][1] = "";
        }

        chatHead2[0] = selectedAccount.getUsername();
        chatTable2 = new JTable(chatData2, chatHead2);
        chatTable2.setGridColor(Color.WHITE);
        JScrollPane scrollPane5 = new JScrollPane(chatTable2);
        scrollPane5.setBounds(300, 100, 500, 300);
        messagesPanel.add(scrollPane5);

        ArrayList<Message> myMessages = messagesContainer.getMyMessages();
        for (Message message : myMessages) {
            System.out.println(message.getText() + " " + message.getMessageId());
        }
        //Collections.sort(myMessages, Comparator.comparing(Message::getMessageId));
        ArrayList<Message> otherMessages = messagesContainer.getOtherMessages();
        //Collections.sort(otherMessages, Comparator.comparing(Message::getMessageId));
        System.out.println("*****************************");
        for (Message message : otherMessages) {
            System.out.println(message.getText() + " " + message.getMessageId());
        }
        int cnt = 0;
        while (!myMessages.isEmpty() && !otherMessages.isEmpty()) {
            int myFirstMessageIndex = Integer.parseInt(myMessages.get(0).getMessageId());
            int otherFirstMessageIndex = Integer.parseInt(otherMessages.get(0).getMessageId());
            if (myFirstMessageIndex < otherFirstMessageIndex) {
                chatData2[cnt++][1] = myMessages.get(0).getText();
                myMessages.remove(0);
            } else {
                chatData2[cnt++][0] = otherMessages.get(0).getText();
                otherMessages.remove(0);
            }
        }
        while (!myMessages.isEmpty()) {
            chatData2[cnt++][1] = myMessages.get(0).getText();
            myMessages.remove(0);
        }
        while (!otherMessages.isEmpty()) {
            chatData2[cnt++][0] = otherMessages.get(0).getText();
            otherMessages.remove(0);
        }
        frame.getContentPane().repaint();
    }

    public void paintChat() {
        try {
            int index = chatTable.getSelectedRow();
            if (index < accountsList.getAccounts().size()) {
                selectedAccount = accountsList.getAccounts().get(index);
            }
            System.out.println(selectedAccount.getUsername() + "----------------------------------------------------------");
            String request = new String("selectChatAccount," + selectedAccount.getAccountId()
                    + "," + account.getAccountId());
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);
            out.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            MessagesContainer messagesContainer = (MessagesContainer) objectInputStream.readObject();
            printMessages(messagesContainer);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        frame.getContentPane().removeAll();
        frame.add(menuPanel);
        frame.add(messagesPanel);
        frame.getContentPane().repaint();
    }

    public void viewAllRentUnits() {
        try {
            points_value.setText(String.valueOf(account.getLoyaltyPoints().getValue()));
            String day1Text = "", day2Text = "", month1Text = "", month2Text = "",
                    year1Text = "", year2Text = "", locationText = "", countryText = "", townText = "";
            StringBuilder request = new StringBuilder("viewRentUnits,");
            if (day1 != null) {
                day1Text = day1.getText();
            }
            if (day2.getText() != null) {
                day2Text = day2.getText();
            }
            if (month1.getText() != null) {
                month1Text = month1.getText();
            }
            if (month2.getText() != null) {
                month2Text = month2.getText();
            }
            if (year1.getText() != null) {
                year1Text = year1.getText();
            }
            if (year2.getText() != null) {
                year2Text = year2.getText();
            }
            if (country.getText() != null) {
                countryText = country.getText();
            }
            if (town.getText() != null) {
                townText = town.getText();
            }
            request.append(day1Text + ",");
            request.append(month1Text + ",");
            request.append(year1Text + ",");
            request.append(day2Text + ",");
            request.append(month2Text + ",");
            request.append(year2Text + ",");
            request.append(countryText + ",");
            request.append(townText + ",");
            request.append(account.getAccountId() + ",");
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            rentUnitListClient = (RentUnitList) objectInputStream.readObject();
            rentUnits = rentUnitListClient.getRentUnits();
            rooms = rentUnitListClient.getRooms();
            reviews = rentUnitListClient.getReviews();

            int cnt = 0;
            for (int i = 0; i < 100; i++) {
                rentUnitsData[i][0] = "";
                rentUnitsData[i][1] = "";
                roomsData[i][0] = "";
                roomsData[i][1] = "";
                roomsData[i][2] = "";
                roomsData[i][3] = "";
                reviewsData[i][0] = "";
                reviewsData[i][1] = "";
                reviewsData[i][2] = "";

            }

            for (RentUnit rentUnit : rentUnits) {
                rentUnitsData[cnt][0] = rentUnit.getName();
                rentUnitsData[cnt++][1] = rentUnit.getTown();
            }
            frame.getContentPane().remove(userPanel);
            points_value.setText(String.valueOf(account.getLoyaltyPoints().getValue()));
            userPanel.repaint();
            viewAllPanel.repaint();
            frame.getContentPane().repaint();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void viewAllRentUnitsWithModifiedPrice() {
        try {
            points_value.setText(String.valueOf(account.getLoyaltyPoints().getValue()));
            String day1Text = "", day2Text = "", month1Text = "", month2Text = "",
                    year1Text = "", year2Text = "", locationText = "", countryText = "", townText = "";
            StringBuilder request = new StringBuilder("viewAllRentUnitsWithModifiedPrice,");
            if (day1 != null) {
                day1Text = day1.getText();
            }
            if (day2.getText() != null) {
                day2Text = day2.getText();
            }
            if (month1.getText() != null) {
                month1Text = month1.getText();
            }
            if (month2.getText() != null) {
                month2Text = month2.getText();
            }
            if (year1.getText() != null) {
                year1Text = year1.getText();
            }
            if (year2.getText() != null) {
                year2Text = year2.getText();
            }
            if (country.getText() != null) {
                countryText = country.getText();
            }
            if (town.getText() != null) {
                townText = town.getText();
            }
            request.append(day1Text + ",");
            request.append(month1Text + ",");
            request.append(year1Text + ",");
            request.append(day2Text + ",");
            request.append(month2Text + ",");
            request.append(year2Text + ",");
            request.append(countryText + ",");
            request.append(townText + ",");
            request.append(account.getAccountId() + ",");
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);
            out.flush();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String replay = in.readLine();
            if(replay.equals("sale")) {
                JOptionPane.showMessageDialog(frame, "Congratulations! You can rent a room with a discount of 10% if you do it right now.\n Don't miss this unique opportunity!");
            }
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            rentUnitListClient = (RentUnitList) objectInputStream.readObject();
            rentUnits = rentUnitListClient.getRentUnits();
            rooms = rentUnitListClient.getRooms();
            reviews = rentUnitListClient.getReviews();

            int cnt = 0;
            for (int i = 0; i < 100; i++) {
                rentUnitsData[i][0] = "";
                rentUnitsData[i][1] = "";
                roomsData[i][0] = "";
                roomsData[i][1] = "";
                roomsData[i][2] = "";
                roomsData[i][3] = "";
                reviewsData[i][0] = "";
                reviewsData[i][1] = "";
                reviewsData[i][2] = "";

            }

            for (RentUnit rentUnit : rentUnits) {
                rentUnitsData[cnt][0] = rentUnit.getName();
                rentUnitsData[cnt++][1] = rentUnit.getTown();
            }
            frame.getContentPane().remove(userPanel);
            points_value.setText(String.valueOf(account.getLoyaltyPoints().getValue()));
            userPanel.repaint();
            viewAllPanel.repaint();
            frame.getContentPane().repaint();

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void viewReservations() {
        try {
            for(int i=0; i<100; i++) {
                reservationsData[i][0] = "";
                reservationsData[i][1] = "";
                reservationsData[i][2] = "";
                reservationsData[i][3] = "";
                reservationsData[i][4] = "";
                reservationsData[i][5] = "";
                reservationsData[i][6] = "";
            }
            StringBuilder request = new StringBuilder("showAllReservations,");
            request.append(account.getAccountId());
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(request);
            out.flush();
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            reservationList = (ReservationList) objectInputStream.readObject();
            if (reservationList != null) {
                int cnt = 0;
                for (Reservation reservation : reservationList.getReservations()) {
                    reservationsData[cnt][0] = reservation.getRoom().getRentUnit().getName();
                    reservationsData[cnt][1] = reservation.getCheckInDay();
                    reservationsData[cnt][2] = reservation.getCheckInMonth();
                    reservationsData[cnt][3] = reservation.getCheckInYear();
                    reservationsData[cnt][4] = reservation.getCheckOutDay();
                    reservationsData[cnt][5] = reservation.getCheckOutMonth();
                    reservationsData[cnt++][6] = reservation.getCheckOutYear();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        frame.getContentPane().removeAll();
        frame.getContentPane().add(menuPanel);
        frame.add(reservationsPanel);
        frame.getContentPane().repaint();
    }
}


