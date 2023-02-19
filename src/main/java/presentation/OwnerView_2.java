package presentation;

import dao.UserDao;
import model.*;
import utils.AccountsList;
import utils.MessagesContainer;
import utils.NotificationList;
import utils.RentUnitList;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class OwnerView_2 {
    JFrame frame;
    JLabel image;
    JLabel username;
    JLabel points_value;
    JLabel starImage;
    JButton ADDUnits;
    JButton MANAGEUnits;
    JButton NOTIFICATIONS;
    JButton chat;
    JButton selectButton;
    JButton sendButton;
    JPanel menuPanel;
    JPanel notificationPanel;
    JPanel chatPanel;
    JPanel roomsPanel;
    JPanel messagesPanel;
    String[] head = {"USER", "GRADE", "TEXT"};
    String[] head2 = {"Room nr.", "Capacity", "Price", "Facilities"};
    String[] head3 = {"NOTIFICATIONS"};
    String[] head4 = {"CHATS"};
    String[] head5 = {"PERSON", "ME"};
    Object[][] data = new Object[100][3];
    Object[][] data2 = new Object[100][4];
    Object[][] data3 = new Object[100][1];
    Object[][] data4 = new Object[100][1];
    Object[][] data5 = new Object[100][2];
    JTable reviewTable;
    JTable roomsTable;
    JTable notificationTable;
    JTable chatTable;
    JTable chatTable2;
    JLabel rentUnitImage;

    JButton addButton;
    JButton editButton;
    JButton changePhotoButton;
    JButton editRoomButton;
    JButton addRoomButton;
    JButton deleteRoomButton;
    //for ADD PANEL
    JLabel unitName;
    JLabel unitContry;
    JLabel unitTown;
    JLabel unitType;
    JLabel unitTelephone;
    JLabel unitDescription;
    JLabel unitPhoto;

    JTextField textName;
    JTextField textCountry;
    JTextField textTown;
    JComboBox typeOfUnitCombo;
    JTextField textTelephone;
    JTextField textPhoto;
    JTextArea textDescription;

    JComboBox photoManageCombo;
    JComboBox photoAddCombo;
    //for manage panel
    JTextField textManageName;
    JTextField textManageCountry;
    JTextField textManageTown;
    JTextField textManageType;
    JTextField textManageRating;
    JTextField textManageTelephone;
    JTextField textManagePhoto;
    JTextArea textManageDescription;

    JTextField messageChat;

    JLabel roomCapacity;
    JLabel roomFacilities;
    JLabel roomPrice;

    JTextField textCapacity;
    JTextField textFacilities;
    JTextArea textPrice;
    JButton deleteNotificationButton;
    ////////////////////////////////////

    JPanel managePanel;
    JPanel addUnitPanel;
    Socket socket;
    PrintWriter out;
    OutputStream outputStream;
    BufferedReader in = null;
    ObjectOutputStream objectOutputStream;
    Account account;
    RentUnit rentUnit;
    RentUnitList rentUnitList;
    Image photo;
    ArrayList<Room> rooms;
    ArrayList<Review> reviews;
    ArrayList<Notification> notifications;
    AccountsList accountsList = new AccountsList();
    Account selectedAccount = new Account();

    public OwnerView_2(Socket socket, Account account) {
        this.socket = socket;
        this.account=account;
        frame = new JFrame("OWNER");
        frame.setSize(1200, 840);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
///MENU
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 0, 1200, 50);
        menuPanel.setBackground(new Color(187, 227, 239, 255));


        try {
            BufferedImage imageBuffer = ImageIO.read(new File("img/BookingTtitle.png"));
            image = new JLabel(new ImageIcon(imageBuffer));
            image.setBounds(0, 0, 200, 50);

            BufferedImage starBuffer = ImageIO.read(new File("img/room1.jpg"));
            starImage = new JLabel(new ImageIcon(starBuffer));
            starImage.setBounds(450, 100, 300, 300);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedImage rentImageBuffer = null;
        try {
            rentImageBuffer = ImageIO.read(new File("img/room1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        rentUnitImage = new JLabel(new ImageIcon(rentImageBuffer));
        rentUnitImage.setBounds(450, 100, 300, 300);
        //managePanel.add(rentUnitImage);

        ADDUnits = new JButton("Add unit");
        ADDUnits.setBounds(200, 10, 130, 30);
        menuPanel.add(ADDUnits);

        MANAGEUnits = new JButton("Manage unit");
        MANAGEUnits.setBounds(360, 10, 130, 30);
        menuPanel.add(MANAGEUnits);

        NOTIFICATIONS = new JButton("Notifications");
        NOTIFICATIONS.setBounds(520, 10, 130, 30);
        menuPanel.add(NOTIFICATIONS);

        chat = new JButton("Chat");
        chat.setBounds(680, 10, 130, 30);
        menuPanel.add(chat);

        menuPanel.add(image);

        //add panel
        addUnitPanel = new JPanel();
        addUnitPanel.setLayout(null);
        addUnitPanel.setBounds(0, 50, 1200, 700);
        addUnitPanel.setBackground(Color.white);

        unitName = new JLabel("UNIT NAME");
        unitName.setBounds(100, 50, 200, 30);
        unitName.setFont(new Font("Verdana", Font.BOLD, 18));
        addUnitPanel.add(unitName);

        textName = new JTextField("");
        textName.setBackground(new Color(187, 227, 239, 255));
        textName.setBounds(100, 80, 300, 30);
        textName.setForeground(Color.gray);
        textName.setHorizontalAlignment(JTextField.CENTER);
        textName.setFont(new Font("Verdana", Font.BOLD, 15));
        addUnitPanel.add(textName);

        unitType= new JLabel("TYPE");
        unitType.setBounds(700, 50, 200, 30);
        unitType.setFont(new Font("Verdana", Font.BOLD, 18));
        addUnitPanel.add(unitType);

        typeOfUnitCombo=new JComboBox();
        typeOfUnitCombo.setBounds(700, 80, 300, 30);
        typeOfUnitCombo.setFont(new Font("Verdana", Font.BOLD, 15));
        typeOfUnitCombo.setBackground(new Color(187, 227, 239, 255));
        typeOfUnitCombo.setForeground(Color.gray);
        typeOfUnitCombo.addItem("Hotel");
        typeOfUnitCombo.addItem("Pensiune");
        typeOfUnitCombo.addItem("Cabana");
        typeOfUnitCombo.addItem("Apartament");
        addUnitPanel.add(typeOfUnitCombo);

        unitContry= new JLabel("COUNTRY");
        unitContry.setBounds(100, 150, 200, 30);
        unitContry.setFont(new Font("Verdana", Font.BOLD, 18));
        addUnitPanel.add(unitContry);

        textCountry = new JTextField("");
        textCountry.setBackground(new Color(187, 227, 239, 255));
        textCountry.setBounds(100, 180, 300, 30);
        textCountry.setForeground(Color.gray);
        textCountry.setHorizontalAlignment(JTextField.CENTER);
        textCountry.setFont(new Font("Verdana", Font.BOLD, 15));
        addUnitPanel.add(textCountry);

        unitTown= new JLabel("TOWN");
        unitTown.setBounds(700, 150, 200, 30);
        unitTown.setFont(new Font("Verdana", Font.BOLD, 18));
        addUnitPanel.add(unitTown);

        textTown = new JTextField("");
        textTown.setBackground(new Color(187, 227, 239, 255));
        textTown.setBounds(700, 180, 300, 30);
        textTown.setForeground(Color.gray);
        textTown.setHorizontalAlignment(JTextField.CENTER);
        textTown.setFont(new Font("Verdana", Font.BOLD, 15));
        addUnitPanel.add(textTown);

        unitTelephone= new JLabel("TELEPHONE");
        unitTelephone.setBounds(100, 250, 200, 30);
        unitTelephone.setFont(new Font("Verdana", Font.BOLD, 18));
        addUnitPanel.add(unitTelephone);

        textTelephone = new JTextField("");
        textTelephone.setBackground(new Color(187, 227, 239, 255));
        textTelephone.setBounds(100, 280, 300, 30);
        textTelephone.setForeground(Color.gray);
        textTelephone.setHorizontalAlignment(JTextField.CENTER);
        textTelephone.setFont(new Font("Verdana", Font.BOLD, 15));
        addUnitPanel.add(textTelephone);

        unitPhoto= new JLabel("PHOTO");
        unitPhoto.setBounds(700, 250, 200, 30);
        unitPhoto.setFont(new Font("Verdana", Font.BOLD, 18));
        addUnitPanel.add(unitPhoto);

        photoAddCombo=new JComboBox();
        photoAddCombo.setBounds(700, 280, 300, 30);
        photoAddCombo.setFont(new Font("Verdana", Font.BOLD, 15));
        photoAddCombo.setBackground(new Color(187, 227, 239, 255));
        photoAddCombo.setForeground(Color.gray);
        photoAddCombo.addItem("room1");
        photoAddCombo.addItem("room2");
        photoAddCombo.addItem("room3");
        photoAddCombo.addItem("room4");
        photoAddCombo.addItem("room5");
        photoAddCombo.addItem("room6");
        photoAddCombo.addItem("image2");
        addUnitPanel.add(photoAddCombo);



        unitDescription= new JLabel("DESCRIPTION");
        unitDescription.setBounds(480, 380, 200, 30);
        unitDescription.setFont(new Font("Verdana", Font.BOLD, 18));
        addUnitPanel.add(unitDescription);

        textDescription = new JTextArea();
        textDescription.setBackground(new Color(187, 227, 239, 255));
        textDescription.setText("");
        textDescription.setFont(new Font("Verdana", Font.BOLD, 13));
        textDescription.setBounds(370, 420, 400, 200);
        addUnitPanel.add(textDescription);

        addButton = new JButton("ADD");
        addButton.setBounds(500, 650, 130, 30);
        addUnitPanel.add(addButton);

        ///manage panel
        managePanel = new JPanel();
        managePanel.setLayout(null);
        managePanel.setBounds(0, 50, 1200, 700);
        managePanel.setBackground(Color.white);

        textManageName = new JTextField("NUME");
        textManageName.setBackground(new Color(187, 227, 239, 255));
        textManageName.setBounds(100, 35, 1000, 30);
        textManageName.setForeground(Color.black);
        textManageName.setHorizontalAlignment(JTextField.CENTER);
        textManageName.setFont(new Font("Verdana", Font.BOLD, 15));
        managePanel.add(textManageName);

        textManageType = new JTextField("TYPE");
        textManageType.setBackground(new Color(187, 227, 239, 255));
        textManageType.setBounds(450, 70, 300, 30);
        textManageType.setForeground(Color.black);
        textManageType.setEditable(false);
        textManageType.setHorizontalAlignment(JTextField.CENTER);
        textManageType.setFont(new Font("Verdana", Font.BOLD, 15));
        managePanel.add(textManageType);

        textManageCountry = new JTextField("COUNTRY");
        textManageCountry.setBackground(new Color(187, 227, 239, 255));
        textManageCountry.setBounds(100, 110, 300, 30);
        textManageCountry.setForeground(Color.black);
        textManageCountry.setHorizontalAlignment(JTextField.CENTER);
        textManageCountry.setFont(new Font("Verdana", Font.BOLD, 15));
        managePanel.add(textManageCountry);

        textManageTown = new JTextField("TOWN");
        textManageTown.setBackground(new Color(187, 227, 239, 255));
        textManageTown.setBounds(100, 150, 300, 30);
        textManageTown.setForeground(Color.black);
        textManageTown.setHorizontalAlignment(JTextField.CENTER);
        textManageTown.setFont(new Font("Verdana", Font.BOLD, 15));
        managePanel.add(textManageTown);

        textManageRating = new JTextField("RATING");
        textManageRating.setBackground(new Color(187, 227, 239, 255));
        textManageRating.setBounds(800, 110, 300, 30);
        textManageRating.setForeground(Color.black);
        textManageRating.setEditable(false);
        textManageRating.setHorizontalAlignment(JTextField.CENTER);
        textManageRating.setFont(new Font("Verdana", Font.BOLD, 15));
        managePanel.add(textManageRating);

        textManageTelephone = new JTextField("TELEPHONE");
        textManageTelephone.setBackground(new Color(187, 227, 239, 255));
        textManageTelephone.setBounds(800, 150, 300, 30);
        textManageTelephone.setForeground(Color.black);
        textManageTelephone.setHorizontalAlignment(JTextField.CENTER);
        textManageTelephone.setFont(new Font("Verdana", Font.BOLD, 15));
        managePanel.add(textManageTelephone);
        //photo
     //   managePanel.add(starImage);

        managePanel.add(rentUnitImage);


        reviewTable = new JTable(data, head);
        JScrollPane scrollPane = new JScrollPane(reviewTable);
        scrollPane.setBounds(25, 450,500, 200 );
        managePanel.add(scrollPane);

        roomsTable = new JTable(data2, head2);
        JScrollPane scrollPane2 = new JScrollPane(roomsTable);
        scrollPane2.setBounds(625, 450,500, 200 );
        managePanel.add(scrollPane2);

        editButton=new JButton("Edit");
        editButton.setBounds(100, 360, 130, 30);
        managePanel.add(editButton);

        changePhotoButton=new JButton("Change photo");
        changePhotoButton.setBounds(800, 300, 130, 30);
        managePanel.add( changePhotoButton);

        photoManageCombo=new JComboBox();
        photoManageCombo.setBounds(800, 340, 300, 30);
        photoManageCombo.setFont(new Font("Verdana", Font.BOLD, 15));
        photoManageCombo.setBackground(new Color(187, 227, 239, 255));
        photoManageCombo.setForeground(Color.gray);
        photoManageCombo.addItem("room1");
        photoManageCombo.addItem("room2");
        photoManageCombo.addItem("room3");
        photoManageCombo.addItem("room4");
        photoManageCombo.addItem("room5");
        photoManageCombo.addItem("room6");
        photoManageCombo.addItem("image2");
        managePanel.add(photoManageCombo);


        textManageDescription = new JTextArea();
        textManageDescription.setBackground(new Color(187, 227, 239, 255));
        textManageDescription.setText("DESCRIPTION");
        textManageDescription.setFont(new Font("Verdana", Font.BOLD, 13));
        textManageDescription.setBounds(100, 190, 300, 160);
        managePanel.add(textManageDescription);

        addRoomButton=new JButton("Add room");
        addRoomButton.setBounds(625, 655, 130, 30);
        managePanel.add( addRoomButton);

        editRoomButton=new JButton("Edit room");
        editRoomButton.setBounds(760, 655, 130, 30);
        managePanel.add( editRoomButton);

        deleteRoomButton=new JButton("Delete room");
        deleteRoomButton.setBounds(895, 655, 130, 30);
        managePanel.add(deleteRoomButton);

        /// notification panel
        notificationPanel=new JPanel();
        notificationPanel.setLayout(null);
        notificationPanel.setBounds(0, 50, 1200, 700);
        notificationPanel.setBackground(Color.white);

        notificationTable = new JTable(data3, head3);
        JScrollPane scrollPane3 = new JScrollPane(notificationTable);
        scrollPane3.setBounds(50, 50,1000, 605 );
        notificationPanel.add(scrollPane3);

        deleteNotificationButton=new JButton("DELETE");
        deleteNotificationButton.setBounds(485, 660, 130, 30);
        notificationPanel.add(deleteNotificationButton);

        //chat panel
        chatPanel=new JPanel();
        chatPanel.setLayout(null);
        chatPanel.setBounds(0, 50, 1200, 700);
        chatPanel.setBackground(Color.white);

        chatTable = new JTable(data4, head4);
        JScrollPane scrollPane4 = new JScrollPane(chatTable);
        scrollPane4.setBounds(300, 100,500, 300 );
        chatPanel.add(scrollPane4);

        selectButton=new JButton("SELECT");
        selectButton.setBounds(300, 420, 130, 30);
        chatPanel.add(selectButton);



        ///messages panel
        messagesPanel=new JPanel();
        messagesPanel.setLayout(null);
        messagesPanel.setBounds(0, 50, 1200, 700);
        messagesPanel.setBackground(Color.white);

        sendButton=new JButton("SEND");
        sendButton.setBounds(670, 410, 130, 30);
        messagesPanel.add(sendButton);

        messageChat = new JTextField("");
        messageChat.setBackground(new Color(187, 227, 239, 255));
        messageChat.setBounds(300, 410, 360, 30);
        messageChat.setForeground(Color.black);
        messageChat.setHorizontalAlignment(JTextField.CENTER);
        messageChat.setFont(new Font("Verdana", Font.BOLD, 15));
        messagesPanel.add(messageChat);

        //
        frame.add(menuPanel);
        frame.setVisible(true);
        addPlaceHolderToFields();

    }

    public void addPlaceHolderToFields() {

        ADDUnits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                frame.add(menuPanel);
                frame.add(addUnitPanel);
                frame.getContentPane().repaint();

            }
        });

        MANAGEUnits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               StringBuilder request = new StringBuilder();
                request.append("owner,manage,");
                request.append(account.getUsername()+",");
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(request);
                    out.flush();
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    rentUnit= (RentUnit) objectInputStream.readObject();

                    if(rentUnit==null) {
                        JOptionPane.showMessageDialog(addUnitPanel, "You dont't have a unit loaded in app!");
                    }
                    else{
                        System.out.println( rentUnit.getImage().getImageName());
                        if( rentUnit.getImage() == null) {
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
                        StringBuilder request2 = new StringBuilder();
                        request2.append("viewReviewRooms,"); //ask for the photo
                        request2.append(rentUnit.getUnitId()+","); //for this rent unit

                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out.println(request2);
                        out.flush();

                        ObjectInputStream objectInputStream2 = new ObjectInputStream(socket.getInputStream());
                        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(socket.getOutputStream());
                        rentUnitList= (RentUnitList) objectInputStream2.readObject();

                        rooms = rentUnitList.getRooms();

                        reviews = rentUnitList.getReviews();

                        frame.getContentPane().removeAll();
                        frame.add(menuPanel);
                        float rating=0;
                        for(Review review: reviews)
                            rating=rating+review.getGrade();
                        rating=rating/(reviews.size());
                        textManageRating.setText(Float.toString(rating));
                        textManageName.setText(rentUnit.getName());
                        textManageType.setText(rentUnit.getUnitType());
                        textManageCountry.setText(rentUnit.getCountry());
                        textManageTown.setText(rentUnit.getTown());
                        textManageTelephone.setText(rentUnit.getTelephoneNumber());
                        textManageDescription.setText(rentUnit.getDescription());

                        int cntRoom = 0;

                        for (int i = 0; i < 100; i++) {
                            data2[i][0] = "";
                            data2[i][1] = "";
                            data2[i][2] = "";
                            data2[i][3] = "";
                        }
                        for(Room room : rooms) {
                            if(room.getRentUnit() != null )
                                if(room.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                                    data2[cntRoom][0] = cntRoom;
                                    data2[cntRoom][1] = room.getCapacity();
                                    data2[cntRoom][2] = room.getPrice();
                                    if(room.getFacilitati() != null) {
                                        data2[cntRoom++][3] = room.getFacilitati();
                                    } else {
                                        data2[cntRoom++][3] = room.getFacilitati();
                                    }
                                }
                        }
                        int cntReview = 0;
                        for(Review review : reviews) {
                            if(review.getRentUnit() != null)
                                if(review.getRentUnit().getUnitId().equals(rentUnit.getUnitId())) {
                                    data[cntReview][0] = review.getAccount().getUsername();
                                    data[cntReview][1] = review.getGrade();
                                    data[cntReview++][2] = review.getText();
                                }
                        }

                        frame.add(managePanel);
                        frame.repaint();
                    }
                } catch (IOException y) {
                    y.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }
        });
        NOTIFICATIONS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                StringBuilder request = new StringBuilder();
                request.append("owner,notifications,");
                request.append(account.getAccountId()+",");
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(request);
                    out.flush();
                    ObjectInputStream objectInputStream2 = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(socket.getOutputStream());
                    NotificationList notificationList=(NotificationList) objectInputStream2.readObject();
                    notifications=notificationList.getNotifications();

                    frame.getContentPane().removeAll();
                    frame.add(menuPanel);

                    int cntNotification = 0;
                    for (int i = 0; i < 100; i++) {
                        data3[i][0] = "";
                        
                    }
                    for (Notification not: notifications){
                        data3[cntNotification][0]=not.getInformation();
                        cntNotification++;
                    }



                } catch (IOException y) {
                    y.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                frame.add(notificationPanel);
                frame.repaint();

            }
        });


        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rentUnit==null) {
                    StringBuilder request = new StringBuilder();
                    request.append("owner,addUnit,");
                    request.append(account.getAccountId() + ",");
                    request.append((String) typeOfUnitCombo.getSelectedItem() + ",");
                    request.append(textName.getText() + ",");
                    request.append(textCountry.getText() + ",");
                    request.append(textTown.getText() + ",");
                    request.append(textTelephone.getText() + ",");
                    request.append(textDescription.getText() + ",");
                    request.append("img/" + (String) photoAddCombo.getSelectedItem() + ".jpg" + ",");


                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out.println(request);
                        out.flush();

                        String reply = in.readLine();
                        System.out.println(reply);
                        if (reply.equals("ok")) {
                            JOptionPane.showMessageDialog(addUnitPanel, "Your unit has been successfully loaded!");
                        }
                        else if(reply.equals("nok"))
                            JOptionPane.showMessageDialog(addUnitPanel, "You already have a unit loaded!");
                        else JOptionPane.showMessageDialog(addUnitPanel, reply);
                    } catch (IOException y) {
                        y.printStackTrace();
                    }
                }else{
                    JOptionPane.showMessageDialog(addUnitPanel, "You already have a unit loaded !");
                }

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder request = new StringBuilder();
                request.append("owner,editUnit,");
                request.append(textManageType.getText() + ",");
                request.append(textManageName.getText() + ",");
                request.append(textManageCountry.getText() + ",");
                request.append(textManageTown.getText() + ",");
                request.append(textManageTelephone.getText() + ",");
                request.append(textManageDescription.getText() + ",");
                request.append(account.getUsername()+",");
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(request);
                    out.flush();
                    String reply = in.readLine();
                    System.out.println(reply);
                    if(reply.equals("ok")) {
                        JOptionPane.showMessageDialog(managePanel, "EDIT DONE!");
                    }
                    else   JOptionPane.showMessageDialog(managePanel, reply);
                } catch (IOException y) {
                    y.printStackTrace();
                }

            }
        });
        changePhotoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder request = new StringBuilder();
                request.append("owner,changePhoto,");
                request.append(rentUnit.getUnitId()+ ",");
                request.append("img/"+(String) photoManageCombo.getSelectedItem()+".jpg"+",");

                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(request);
                    out.flush();
                    String reply = in.readLine();
                    System.out.println(reply);
                    if(reply.equals("ok")) {
                        JOptionPane.showMessageDialog(managePanel, "The unit photo was changed!");
                    }
                } catch (IOException y) {
                    y.printStackTrace();
                }

            }
        });
        editRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder request = new StringBuilder();
                request.append("owner,editRoom,");
                int row = roomsTable.getSelectedRow();
                String[] col= new String[4];
                col[0]=roomsTable.getValueAt(row, 0).toString();
                col[1]=roomsTable.getValueAt(row, 1).toString();
                col[2]=roomsTable.getValueAt(row, 2).toString();
                col[3]=roomsTable.getValueAt(row, 3).toString();
                request.append(col[1]+",");
                request.append(col[2]+",");
                request.append(col[3]+",");
                request.append((rooms.get(Integer.parseInt(col[0]))).getRoomId()+","); //room id

                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(request);
                    out.flush();
                    String reply = in.readLine();
                    System.out.println(reply);
                    if(reply.equals("ok")) {
                        JOptionPane.showMessageDialog(managePanel, "The room was edited!");
                    }
                } catch (IOException y) {
                    y.printStackTrace();
                }

            }
        });
        addRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder request = new StringBuilder();
                request.append("owner,addRoom,");
                int row = roomsTable.getSelectedRow();
                String[] col= new String[4];
                col[1]=roomsTable.getValueAt(row, 1).toString();
                col[2]=roomsTable.getValueAt(row, 2).toString();
                col[3]=roomsTable.getValueAt(row, 3).toString();

                // request.append(col[0]+",");
                request.append(col[1]+",");
                request.append(col[2]+",");
                request.append(col[3]+",");
                request.append(rentUnit.getUnitId()+',');
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(request);
                    out.flush();
                    String reply = in.readLine();
                    System.out.println(reply);
                    if(reply.equals("ok")) {
                        JOptionPane.showMessageDialog(managePanel, "The room was added!");
                    }
                } catch (IOException y) {
                    y.printStackTrace();
                }

            }
        });
        deleteRoomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder request = new StringBuilder();
                request.append("owner,deleteRoom,");
                int row = roomsTable.getSelectedRow();
                String[] col= new String[4];
                col[0]=roomsTable.getValueAt(row, 0).toString();
                request.append((rooms.get(Integer.parseInt(col[0]))).getRoomId()+","); //room id

                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    out.println(request);
                    out.flush();
                    String reply = in.readLine();
                    System.out.println(reply);
                    if(reply.equals("ok")) {
                        JOptionPane.showMessageDialog(managePanel, "The room was deleted!");
                    }
                } catch (IOException y) {
                    y.printStackTrace();
                }

            }
        });
        deleteNotificationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = notificationTable.getSelectedRow();

                if (index < notifications.size()) {
                    String id = notifications.get(index).getNotificationnId();
                    StringBuilder request = new StringBuilder();
                    request.append("deleteNotification,");
                    request.append(id+",");
                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out.println(request);
                        out.flush();

                        String reply = in.readLine();
                        System.out.println(reply);
                        if(reply.equals("ok")) {
                            JOptionPane.showMessageDialog(notificationPanel, "DELETE DONE!");
                        }
                    } catch (IOException y) {
                        y.printStackTrace();
                    }
                }
                else  JOptionPane.showMessageDialog(notificationPanel, "Select a valid row!");


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
                        data4[i][0] = "";
                    }
                    for (Account account1 : accountsList.getAccounts()) {
                        if (!account.getAccountId().equals(account1.getAccountId()))
                            data4[cnt++][0] = account1.getUsername();
                        else
                            data4[cnt++][0] = "me";
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


    }
    public void printMessages(MessagesContainer messagesContainer) {
        for (int i = 0; i < 100; i++) {
            data5[i][0] = "";
            data5[i][1] = "";
        }

        head5[0] = selectedAccount.getUsername();
        chatTable2 = new JTable(data5, head5);
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
                data5[cnt++][1] = myMessages.get(0).getText();
                myMessages.remove(0);
            } else {
                data5[cnt++][0] = otherMessages.get(0).getText();
                otherMessages.remove(0);
            }
        }
        while (!myMessages.isEmpty()) {
            data5[cnt++][1] = myMessages.get(0).getText();
            myMessages.remove(0);
        }
        while (!otherMessages.isEmpty()) {
            data5[cnt++][0] = otherMessages.get(0).getText();
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
}
