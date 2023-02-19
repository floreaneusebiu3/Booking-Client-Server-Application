package presentation;

import bll.AccountBll;
import model.Account;
import model.User;
import utils.ConfirmEmail;
import utils.Hash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

public class AdminRegister {
    private JFrame frame;
    private JTextField emailField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField ageField;
    private JButton registerButton;
    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;


    public AdminRegister(Socket socket) {
        this.socket = socket;
        frame = new JFrame("Admin Register");
        firstNameField = new JTextField("First Name");
        lastNameField = new JTextField("Last Name");
        ageField = new JTextField("Your Age");
        emailField = new JTextField("Your E-mail");
        registerButton = new JButton("REGISTER ADMIN");

        frame.setSize( 240, 250);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        firstNameField.setBounds(10,  10, 200, 30);
        firstNameField.setForeground(Color.gray);
        firstNameField.setHorizontalAlignment(JTextField.CENTER);
        firstNameField.setFont(new Font("Verdana", Font.BOLD, 15));

        lastNameField.setBounds(10, 50, 200, 30);
        lastNameField.setForeground(Color.gray);
        lastNameField.setHorizontalAlignment(JTextField.CENTER);
        lastNameField.setFont(new Font("Verdana", Font.BOLD, 15));

        ageField.setBounds(10, 90, 200, 30);
        ageField.setForeground(Color.gray);
        ageField.setHorizontalAlignment(JTextField.CENTER);
        ageField.setFont(new Font("Verdana", Font.BOLD, 15));

        emailField.setBounds(10, 130, 200, 30);
        emailField.setForeground(Color.gray);
        emailField.setHorizontalAlignment(JTextField.CENTER);
        emailField.setFont(new Font("Verdana", Font.BOLD, 15));


        registerButton.setBounds(10, 170, 200, 30);
        registerButton.setForeground(Color.BLACK);
        registerButton.setHorizontalAlignment(JTextField.CENTER);
        registerButton.setFont(new Font("Verdana", Font.BOLD, 12));
        registerButton.setBackground(new Color(106, 190, 237));

        addActionListener();
        addPlaceHolderToFields();
        frame.add(firstNameField);
        frame.add(lastNameField);
        frame.add(ageField);
        frame.add(emailField);
        frame.add(registerButton);
        frame.setVisible(true);
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
    }

    public void addActionListener() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException y) {
                    y.printStackTrace();
                }
                System.out.println(firstNameField.getText() + " " + firstNameField.getText().length());
                StringBuilder request = new StringBuilder();
                request.append("adminRegister,");
                request.append(firstNameField.getText() + ",");
                request.append(lastNameField.getText() + ",");
                request.append(emailField.getText() + ",");
                request.append(ageField.getText() );
                out.println(request);
                out.flush();
                try {
                    String requestReply = in.readLine();
                    if(requestReply.equals("ok")) {
                        frame.setVisible(false);
                        LoginView loginView = new LoginView(socket);
                        loginView.init();
                    } else {
                        JOptionPane.showMessageDialog(frame, requestReply);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}
