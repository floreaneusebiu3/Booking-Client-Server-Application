
import Controller.*;
import bll.*;

import dao.LoyaltyPointsDao;
import dao.MessageDao;
import dao.ReservationDao;
import model.*;

import presentation.Register;
import utils.*;

import java.io.*;
import java.net.*;
import java.util.*;

class Server {


    public static void main(String[] args) {
        int code = 0;
        ServerSocket server = null;
        try {
            server = new ServerSocket(1234);
            server.setReuseAddress(true);
            while (true) {
                Socket client = server.accept();
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());
                ClientHandler clientSock = new ClientHandler(client);
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        int k = 0;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;

        }

        public void run() {

            RentUnitList rentUnitListClient;
            PrintWriter out = null;
            BufferedReader in = null;
            int code = 0;
            Random random = new Random();
            ConfirmEmail confirmEmail = new ConfirmEmail();
            Account accountCopy = new Account();

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String request;
                while ((request = in.readLine()) != null) {
                    System.out.println(request);
                    String[] parts = request.split(",");
                    System.out.println(parts[0]);
                    if (parts[0].equals("login")) {
                        LoginController loginController = new LoginController();
                        String validStatus = loginController.validateAccount(parts[1], parts[2]);
                        if (validStatus.equals("NOT VALID")) {
                            out.println(validStatus);
                        } else {
                            Account account = loginController.checkLogin(request);
                            if (account != null) {
                                accountCopy = account;
                                System.out.println(account.getAccountId());
                                String role = account.getType();
                                out.println(role + "," + account.getAccountId());
                                out.flush();
//                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//                                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
//                                objectOutputStream.writeObject(account);
                            } else
                                out.println("notOk");
                        }
                    } else if (parts[0].equals("adminRegister")) {
                        RegisterController registerController = new RegisterController();
                        String validStatus = registerController.validateUser(parts[1], parts[2], parts[3], parts[4]);
                        if (validStatus.equals("valid")) {
                            Account account = new Account();
                            account = registerController.createAdminAccount(request);
                            AccountBll accountBll = new AccountBll();
                            accountBll.insertAccount(account);
                            out.println("ok");
                        } else {
                            out.println(validStatus);
                        }

                    } else if (parts[0].equals("register")) {
                        RegisterController registerController = new RegisterController();
                        String validStatus = registerController.validateUser(parts[2], parts[3], parts[4], parts[5]);
                        User user = new User();
                        user.setUserId(UUID.randomUUID().toString());
                        user.createUser(parts[2], parts[3], parts[4], parts[5]);
                        if (parts[1].equals("client")) {
                            code = random.nextInt(100000);
                            confirmEmail.sendMail(parts[4], parts[4], String.valueOf(code));
                            if (validStatus.equals("valid")) {
                                accountCopy = registerController.createClientAccount(request, user);
                                if (accountCopy == null) {
                                    out.println("passwords not equal");
                                    out.flush();
                                } else {
                                    out.println("ok");
                                }
                            } else {
                                out.println(validStatus);
                                out.flush();
                            }
                        } else if (parts[1].equals("owner")) {
                            if (validStatus.equals("valid")) {
                                RegisterController registerController1 = new RegisterController();
                                Account account = registerController1.createOwnerAccount(request, user);
                                if (account != null) {
                                    Chat chat = new Chat();
                                    chat.setChatId(UUID.randomUUID().toString());
                                    chat.setAccount(account);
                                    (new ChatBll()).insertChat(chat);
                                    out.println("ok");
                                }
                            }
                            else
                                out.println(validStatus);
                        }
                    } else if (parts[0].equals("codeConfirmation")) {
                        if (Integer.parseInt(parts[1]) == code) {
                            LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
                            loyaltyPoints.setLoyaltyPointsId(UUID.randomUUID().toString());
                            loyaltyPoints.setValue(0);
                            (new LoyaltyPointsDao()).insert(loyaltyPoints);
                            accountCopy.setLoyaltyPoints(loyaltyPoints);
                            AccountBll accountBll = new AccountBll();
                            accountBll.insertAccount(accountCopy);
                            Chat chat = new Chat();
                            chat.setChatId(UUID.randomUUID().toString());
                            chat.setAccount(accountCopy);
                            (new ChatBll()).insertChat(chat);
                        }
                    } else if (parts[0].equals("owner")) {
                        if (parts[1].equals("manage")) {
                            RentUnit rentUnit = new RentUnit();
                            RentUnitBll rentUnitBll = new RentUnitBll();
                            rentUnit = rentUnitBll.findByOwner(parts[2]);
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                            objectOutputStream.writeObject(rentUnit);
                            objectOutputStream.flush();

                        } else if (parts[1].equals("addUnit")) {
                            OwnerController ownerController = new OwnerController();
                            if (!ownerController.validateTelephoneNumber(parts[7]).equals("valid")) {
                                out.println(ownerController.validateTelephoneNumber(parts[7]));
                                out.flush();
                            } else {
                                RentUnit rentUnit = ownerController.createRentUnit(request);
                                RentUnitBll rentUnitBll = new RentUnitBll();
                                if (rentUnit == null) {
                                    out.println("nok");
                                    out.flush();
                                } else {
                                    rentUnitBll.insertRentUnit(rentUnit);
                                    out.println("ok");
                                    out.flush();
                                }
                            }
                        } else if (parts[1].equals("editUnit")) {
                            OwnerController ownerController = new OwnerController();
                            if (!ownerController.validateTelephoneNumber(parts[6]).equals("valid")) {
                                out.println(ownerController.validateTelephoneNumber(parts[6]));
                                out.flush();
                            } else {
                                RentUnitBll rentUnitBll = new RentUnitBll();
                                RentUnit rentUnit = rentUnitBll.findByOwner(parts[8]);
                                RentUnit rentUnitUpdated = ownerController.editUnit(request, rentUnit);
                                if (rentUnitUpdated != null) {
                                    rentUnitBll.updateRentUnit(rentUnitUpdated);
                                    out.println("ok");
                                    out.flush();
                                }
                            }
                        } else if (parts[1].equals("changePhoto")) {
                            OwnerController ownerController = new OwnerController();
                            ImageBll imageBll = new ImageBll();
                            RentUnitBll rentUnitBll = new RentUnitBll();
                            RentUnit unit = rentUnitBll.findById(parts[2]);
                            Image oldImage = unit.getImage();
                            Image newImage = ownerController.editImage(parts[3], oldImage);
                            if (newImage != null) {
                                imageBll.updateImage(newImage);
                                out.println("ok");
                                out.flush();
                            }

                        } else if (parts[1].equals("editRoom")) {
                            OwnerController ownerController = new OwnerController();
                            RoomBll roomBll = new RoomBll();
                            Room room = roomBll.findById(parts[5]);
                            Room roomUpdated = ownerController.editRoom(request, room);
                            if (roomUpdated != null) {
                                roomBll.updateRoom(roomUpdated);
                                out.println("ok");
                                out.flush();
                            }

                        } else if (parts[1].equals("addRoom")) {
                            OwnerController ownerController = new OwnerController();
                            RentUnitBll rentUnitBll = new RentUnitBll();
                            RoomBll roomBll = new RoomBll();
                            RentUnit rentUnit = rentUnitBll.findById(parts[5]);
                            Room room = ownerController.createRoom(request, rentUnit);
                            if (room != null) {
                                roomBll.insertRoom(room);
                                out.println("ok");
                                out.flush();
                            }

                        } else if (parts[1].equals("deleteRoom")) {

                            RoomBll roomBll = new RoomBll();
                            Room room = roomBll.findById(parts[2]);
                            if (room != null) {
                                roomBll.deleteRoom(room);
                                out.println("ok");
                                out.flush();
                            }

                        } else if (parts[1].equals("notifications")) {

                            // OwnerController ownerController=new OwnerController();
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                            NotificationList notificationList = (new OwnerController()).getNotificationsFromAccount(parts[2]);
                            objectOutputStream.writeObject(notificationList);

                        }
                    } else if (parts[0].equals("mesaj")) {
                        try {
                            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                            User user1 = (User) objectInputStream.readObject();
                            System.out.println(user1.getFirstName());
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if (parts[0].equals("viewRentUnits")) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        rentUnitListClient = (new ClientController()).getRentUnitList(request);
                        objectOutputStream.writeObject(rentUnitListClient);
                    } else if (parts[0].equals("viewAllRentUnitsWithModifiedPrice")) {
                        rentUnitListClient = (new ClientController()).getRentUnitListWithModifiedPrice(request, clientSocket);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        objectOutputStream.writeObject(rentUnitListClient);
                    } else if (parts[0].equals("createReservation")) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        ReservationFields reservationFields = (ReservationFields) objectInputStream.readObject();
                        System.out.println(reservationFields.getRoom().getRoomId());
                        (new ClientController()).createReservation(request, reservationFields.getRoom(), reservationFields.getAccount());
                    } else if (parts[0].equals("showAllReservations")) {
                        ReservationList reservationList = new ReservationList();
                        reservationList = (new ClientController()).showAllReservations(request);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        objectOutputStream.writeObject(reservationList);
                    } else if (parts[0].equals("deleteReservation")) {
                        (new ClientController()).findAndDeleteReservation(request);
                        out.println("ok");
                        out.flush();
                    } else if (parts[0].equals("seeAccountRentUnitsAndReviews")) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        RentUnitList rentUnitList = (new ClientController()).getAllRentUnitsAndAllReviewsForThisAccount(accountCopy);
                        objectOutputStream.writeObject(rentUnitList);
                    } else if (parts[0].equals("createReview")) {
                        (new ClientController()).createAndInsertReview(request);
                        out.println("ok");
                    } else if (parts[0].equals("viewReviewRooms")) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        RentUnitList rentUnitList = (new OwnerController()).getRentUnitReviewsAndRooms(parts[1]);
                        objectOutputStream.writeObject(rentUnitList);
                    } else if (parts[0].equals("getAllAccounts")) {
                        AccountsList accountList = new AccountsList();
                        accountList.setAccounts((ArrayList<Account>) (new AccountBll()).realAllAccounts());
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        objectOutputStream.writeObject(accountList);
                    } else if (parts[0].equals("selectChatAccount")) {
                        System.out.println("i m in");
                        Chat myChat = (new ChatBll()).findChatByAccountId(parts[2]);
                        Chat otherChat = (new ChatBll()).findChatByAccountId(parts[1]);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        MessagesContainer messagesContainer = new MessagesContainer();
                        MessageBll messagesBll = new MessageBll();
                        ArrayList<Message> myMessages = messagesBll.findAllMessagesForOneChat(parts[2], otherChat.getChatId());
                        ArrayList<Message> otherMessages = messagesBll.findAllMessagesForOneChat(parts[1], myChat.getChatId());
                        System.out.println("defrgfrtgtr");
                        for (Message message : myMessages) {
                            System.out.println(message.getText() + message.getMessageId());
                        }
                        messagesContainer.setMyMessages(myMessages);
                        messagesContainer.setOtherMessages(otherMessages);
                        objectOutputStream.writeObject(messagesContainer);
                    } else if (parts[0].equals("sendMessage")) {
                        Chat otherChat = (new ChatBll()).findChatByAccountId(parts[1]);
                        (new MessagesController()).createAndInsertMessage(otherChat, request);
                        out.println("ok");
                        out.flush();
                    } else if (parts[0].equals("deleteNotification")) {
                        NotificationBll notificationBll = new NotificationBll();
                        Notification notification = notificationBll.findNotificationById(parts[1]);
                        if (notification != null) {
                            notificationBll.deleteNotification(notification);
                            out.println("ok");
                            out.flush();
                        }
                    } else if (parts[0].equals("giveMeUpdatedAccount")) {
                        Account account = (new AccountBll()).findAccountById(parts[1]);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                        objectOutputStream.writeObject(account);
                        objectOutputStream.flush();

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}