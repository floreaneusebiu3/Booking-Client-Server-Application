package presentation;


import bll.AccountBll;
import dao.AccountDao;
import model.Account;
import utils.Hash;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LoginView {
    private JFrame frame;
    private JLabel label;

    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private final Socket socket;


    private JButton loginButton;
    private JButton registerButton;


    public LoginView(Socket socket) {
        this.socket = socket;
    }

    public void init() throws IOException {

        frame = new JFrame("BookingLogin");
        frame.setSize(600, 300);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);

        BufferedImage image = ImageIO.read(new File("img/hotel.png"));
        label = new JLabel(new ImageIcon(image));
        label.setBounds(20, 10, 250, 200);


        usernameLabel = new JLabel("username:");
        usernameLabel.setBounds(280, 50, 120, 30);
        usernameLabel.setFont(new Font("Verdana", Font.BOLD, 20));


        passwordLabel = new JLabel("password:");
        passwordLabel.setBounds(280, 90, 120, 30);
        passwordLabel.setFont(new Font("Verdana", Font.BOLD, 20));

        usernameTextField = new JTextField();
        usernameTextField.setBounds(405, 50, 170, 30);
        usernameTextField.setFont(new Font("Verdana", Font.BOLD, 20));

        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(405, 90, 170, 30);
        passwordTextField.setFont(new Font("Verdana", Font.BOLD, 20));

        loginButton = new JButton("LOGIN");
        loginButton.setBounds(280, 160, 120, 30);
        loginButton.setFont(new Font("Verdana", Font.BOLD, 20));
        loginButton.setBackground(new Color(106, 190, 237));

        registerButton = new JButton("REGISTER");
        registerButton.setBounds(410, 160, 160, 30);
        registerButton.setFont(new Font("Verdana", Font.BOLD, 20));
        registerButton.setBackground(new Color(106, 190, 237));

        frame.setVisible(true);
        frame.add(label);
        frame.add(usernameLabel);
        frame.add(passwordLabel);
        frame.add(usernameTextField);
        frame.add(passwordTextField);
        frame.add(loginButton);
        frame.add(registerButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addActionListener();
    }

    public void addActionListener() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String password = Hash.hashPassword(String.valueOf(passwordTextField.getPassword()));
                String username = usernameTextField.getText();
                try {
                    PrintWriter out = null;
                    out = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    StringBuilder request = new StringBuilder();
                    request.append("login,");
                    request.append(username);
                    request.append(",");
                    request.append(password);
                    out.println(request);
                    out.flush();
                    Account account = null;
                    String requestReply = in.readLine();
                    System.out.println(requestReply);
                    String[] parts = requestReply.split(",");
                    requestReply = parts[0];
                    if (requestReply.equals("client") || requestReply.equals("owner") || requestReply.equals("admin")) {
//                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
//                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
//                        account = (Account) objectInputStream.readObject();
                        account = (new AccountBll()).findAccountById(parts[1]);
                    }
                    if (requestReply.equals("client")) {
                        frame.setVisible(false);
                        System.out.println("Succes");
                        ClientView clientView = new ClientView(socket, account);
                    } else if (requestReply.equals("owner")) {
                        frame.setVisible(false);
                        OwnerView_2 ownerView_2 = new OwnerView_2(socket, account);
                    } else if (requestReply.equals("admin")) {
                        frame.setVisible(false);
                        AdminView adminView = new AdminView();
                    } else if (requestReply.equals("notOk"))
                        JOptionPane.showMessageDialog(frame, "Account doesn't exist");
                    else {
                        JOptionPane.showMessageDialog(frame, requestReply);
                    }


                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Register register = new Register(socket);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                frame.setVisible(false);
            }
        });
    }
}