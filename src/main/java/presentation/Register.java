package presentation;

import bll.AccountBll;
import lombok.extern.java.Log;
import model.Account;
import model.User;
import utils.Hash;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

public class Register {
    private final String[] userTypes = {"client", "owner", "admin"};
    private JFrame registerFrame;
    private JLabel imageLabel;
    private JButton createAdminButton;
    private JLabel newAccountTitle;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JTextField emailField;
    private JComboBox typeOfUsersCombo;
    private JTextField usernameField;
    private JPasswordField paswordField;
    private JPasswordField repeatPaswordField;
    private JButton registerButton;
    private JPasswordField adminPassword;
    private final Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public Register(Socket socket) throws IOException {
        this.socket = socket;
        registerFrame = new JFrame("BookingRegister");
        imageLabel = new JLabel();
        createAdminButton = new JButton("New Admin Account");
        newAccountTitle = new JLabel("SIGN UP TO BOOKING");
        firstNameField = new JTextField("First Name");
        lastNameField = new JTextField("Last Name");
        ageField = new JTextField("Your Age");
        emailField = new JTextField("Your E-mail");
        usernameField = new JTextField("Client Username");
        paswordField = new JPasswordField("Client Password");
        adminPassword = new JPasswordField("Admin Password");
        repeatPaswordField = new JPasswordField("Client Repeat Password");
        typeOfUsersCombo = new JComboBox<String>(userTypes);
        registerButton = new JButton("REGISTER");
        paintRegister();
        addActionListener();
        addPlaceHolderToFields();

    }

    public void paintRegister() throws IOException {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        registerFrame.setSize(650, 280);
        int x = (int) ((dimension.getWidth() - registerFrame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - registerFrame.getHeight()) / 2);
        registerFrame.setLocation(x, y);
        registerFrame.setLayout(null);
        registerFrame.getContentPane().setBackground(Color.WHITE);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedImage image = ImageIO.read(new File("img/hotel.png"));
        imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setBounds(0, 0, 250, 150);

        typeOfUsersCombo.setBounds(365, 10, 150, 30);
        typeOfUsersCombo.setFont(new Font("Verdana", Font.BOLD, 15));
        typeOfUsersCombo.setBackground(new Color(106, 190, 237));

        firstNameField.setBounds(280, 55, 150, 30);
        firstNameField.setForeground(Color.gray);
        firstNameField.setHorizontalAlignment(JTextField.CENTER);
        firstNameField.setFont(new Font("Verdana", Font.BOLD, 15));

        lastNameField.setBounds(450, 55, 150, 30);
        lastNameField.setForeground(Color.gray);
        lastNameField.setHorizontalAlignment(JTextField.CENTER);
        lastNameField.setFont(new Font("Verdana", Font.BOLD, 15));

        ageField.setBounds(280, 100, 150, 30);
        ageField.setForeground(Color.gray);
        ageField.setHorizontalAlignment(JTextField.CENTER);
        ageField.setFont(new Font("Verdana", Font.BOLD, 15));

        emailField.setBounds(450, 100, 150, 30);
        emailField.setForeground(Color.gray);
        emailField.setHorizontalAlignment(JTextField.CENTER);
        emailField.setFont(new Font("Verdana", Font.BOLD, 15));


        usernameField.setBounds(280, 150, 150, 30);
        usernameField.setForeground(Color.GRAY);
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.setFont(new Font("Verdana", Font.BOLD, 15));

        paswordField.setBounds(450, 150, 150, 30);
        paswordField.setText("Client Password");
        paswordField.setEchoChar('\u0000');
        paswordField.setForeground(Color.GRAY);
        paswordField.setHorizontalAlignment(JTextField.CENTER);
        paswordField.setFont(new Font("Verdana", Font.BOLD, 15));

        repeatPaswordField.setBounds(280, 190, 150, 30);
        repeatPaswordField.setText("Repeat Password");
        repeatPaswordField.setEchoChar('\u0000');
        repeatPaswordField.setForeground(Color.GRAY);
        repeatPaswordField.setHorizontalAlignment(JTextField.CENTER);
        repeatPaswordField.setFont(new Font("Verdana", Font.BOLD, 15));

        registerButton.setBounds(450, 190, 150, 30);
        registerButton.setForeground(Color.BLACK);
        registerButton.setHorizontalAlignment(JTextField.CENTER);
        registerButton.setFont(new Font("Verdana", Font.BOLD, 15));
        registerButton.setBackground(new Color(106, 190, 237));

        createAdminButton.setBounds(30, 195, 200, 30);
        createAdminButton.setForeground(Color.BLACK);
        createAdminButton.setHorizontalAlignment(JTextField.CENTER);
        createAdminButton.setFont(new Font("Verdana", Font.BOLD, 15));
        createAdminButton.setBackground(new Color(106, 190, 237));
        createAdminButton.setEnabled(false);

        adminPassword.setBounds(30, 155, 200, 30);
        adminPassword.setText("Admin Password");
        adminPassword.setEchoChar('\u0000');
        adminPassword.setForeground(Color.GRAY);
        adminPassword.setHorizontalAlignment(JTextField.CENTER);
        adminPassword.setFont(new Font("Verdana", Font.BOLD, 15));
        adminPassword.setEnabled(false);

        registerFrame.add(adminPassword);
        registerFrame.add(createAdminButton);
        registerFrame.add(registerButton);
        registerFrame.add(repeatPaswordField);
        registerFrame.add(usernameField);
        registerFrame.add(paswordField);
        registerFrame.add(typeOfUsersCombo);
        registerFrame.add(emailField);
        registerFrame.add(ageField);
        registerFrame.add(lastNameField);
        registerFrame.add(firstNameField);
        registerFrame.add(imageLabel);
        registerFrame.setVisible(true);
    }

    public void addPlaceHolderToFields() {
        firstNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (firstNameField.getText().equals("First Name")) {
                    firstNameField.setForeground(Color.BLACK);
                    firstNameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (firstNameField.getText().isEmpty()) {
                    firstNameField.setForeground(Color.GRAY);
                    firstNameField.setText("First Name");
                }
            }
        });

        lastNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (lastNameField.getText().equals("Last Name")) {
                    lastNameField.setForeground(Color.BLACK);
                    lastNameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (lastNameField.getText().isEmpty()) {
                    lastNameField.setForeground(Color.GRAY);
                    lastNameField.setText("Last Name");
                }
            }
        });

        ageField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (ageField.getText().equals("Your Age")) {
                    ageField.setForeground(Color.BLACK);
                    ageField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (ageField.getText().isEmpty()) {
                    ageField.setForeground(Color.GRAY);
                    ageField.setText("Your Age");
                }
            }
        });

        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Your E-mail")) {
                    emailField.setForeground(Color.BLACK);
                    emailField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setForeground(Color.GRAY);
                    emailField.setText("Your E-mail");
                }
            }
        });

        usernameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Client Username")) {
                    usernameField.setForeground(Color.BLACK);
                    usernameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setForeground(Color.GRAY);
                    usernameField.setText("Client Username");
                }
            }
        });

        paswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(paswordField.getPassword()).equals("Client Password")) {
                    paswordField.setForeground(Color.BLACK);
                    paswordField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                    paswordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (paswordField.getPassword().length == 0) {
                    paswordField.setForeground(Color.GRAY);
                    paswordField.setEchoChar('\u0000');
                    paswordField.setText("Client Password");
                }
            }
        });

        repeatPaswordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(repeatPaswordField.getPassword()).equals("Repeat Password")) {
                    repeatPaswordField.setForeground(Color.BLACK);
                    repeatPaswordField.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                    repeatPaswordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (repeatPaswordField.getPassword().length == 0) {
                    repeatPaswordField.setForeground(Color.GRAY);
                    repeatPaswordField.setEchoChar('\u0000');
                    repeatPaswordField.setText("Repeat Password");
                }
            }
        });

        adminPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(adminPassword.getPassword()).equals("Admin Password")) {
                    adminPassword.setForeground(Color.BLACK);
                    adminPassword.setEchoChar((Character) UIManager.get("PasswordField.echoChar"));
                    adminPassword.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (adminPassword.getPassword().length == 0) {
                    adminPassword.setForeground(Color.GRAY);
                    adminPassword.setEchoChar('\u0000');
                    adminPassword.setText("Admin Password");
                }
            }
        });
    }

    public void addActionListener() {
        createAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (String.valueOf(adminPassword.getPassword()).equals("parolatopsecret")) {
                    AdminRegister adminRegister = new AdminRegister(socket);
                    registerFrame.dispose();
                }
            }
        });

        typeOfUsersCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (typeOfUsersCombo.getSelectedItem() == userTypes[0]) {
                    firstNameField.setEnabled(true);
                    lastNameField.setEnabled(true);
                    ageField.setEnabled(true);
                    emailField.setEnabled(true);
                    usernameField.setEnabled(true);
                    paswordField.setEnabled(true);
                    repeatPaswordField.setEnabled(true);
                    adminPassword.setEnabled(false);
                    createAdminButton.setEnabled(false);
                    registerButton.setEnabled(true);
                } else if (typeOfUsersCombo.getSelectedItem() == userTypes[1]) {
                    usernameField.setEnabled(false);
                    paswordField.setEnabled(false);
                    repeatPaswordField.setEnabled(false);
                    adminPassword.setEnabled(false);
                    createAdminButton.setEnabled(false);
                    registerButton.setEnabled(true);
                } else {
                    firstNameField.setEnabled(false);
                    lastNameField.setEnabled(false);
                    ageField.setEnabled(false);
                    emailField.setEnabled(false);
                    usernameField.setEnabled(false);
                    paswordField.setEnabled(false);
                    repeatPaswordField.setEnabled(false);
                    adminPassword.setEnabled(true);
                    createAdminButton.setEnabled(true);
                    registerButton.setEnabled(false);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) typeOfUsersCombo.getSelectedItem();
                if (selected == userTypes[0]) { //if client
                    StringBuilder request = new StringBuilder();
                    request.append("register,client,");
                    request.append(firstNameField.getText() + ",");
                    request.append(lastNameField.getText() + ",");
                    request.append(emailField.getText() + ",");
                    request.append(ageField.getText() + ",");
                    request.append(usernameField.getText() + ",");
                    request.append(String.valueOf(paswordField.getPassword()) + ",");
                    request.append(String.valueOf(repeatPaswordField.getPassword()) + ",");


                    try {
                        out = new PrintWriter(socket.getOutputStream(), true);
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        out.println(request);
                        out.flush();
                        String reply = in.readLine();
                        if(reply.equals("ok")) {
                            registerFrame.setVisible(false);
                            CodeConfirmation codeConfirmation = new CodeConfirmation(socket);
                        } else {
                            JOptionPane.showMessageDialog(registerFrame, reply);
                        }
                    } catch (IOException y) {
                        y.printStackTrace();
                    }

                } else { // if owner
                    try {
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        StringBuilder request = new StringBuilder();
                        request.append("register,owner,");
                        request.append(firstNameField.getText() + ",");
                        request.append(lastNameField.getText() + ",");
                        request.append(emailField.getText() + ",");
                        request.append(ageField.getText() + ",");
                        out.println(request);
                        out.flush();
                        String requestReply = in.readLine();
                        System.out.println(requestReply);
                        if (requestReply.equals("ok")) {
                            registerFrame.setVisible(false);
                            System.out.println("Succes");
                            LoginView loginView = new LoginView(socket);
                            loginView.init();
                        } else {
                            JOptionPane.showMessageDialog(registerFrame, requestReply);
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
