package presentation;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CodeConfirmation {
    private JFrame frame;
    private JTextField code;
    private JLabel info;
    private JButton verify;
    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public CodeConfirmation(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException y) {
            y.printStackTrace();
        }
        frame = new JFrame("Code Confirmation");
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize( 400, 200);
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.WHITE);

        info = new JLabel("Write the code from your email here:");
        info.setBounds(10, 10, 350, 50);
        info.setFont(new Font("Verdana", Font.BOLD, 15));

        code = new JTextField();
        code.setBounds(10, 70, 100, 50);
        code.setFont(new Font("Verdana", Font.BOLD, 20));

        verify = new JButton("CONFIRM");
        verify.setBounds(120, 70, 200, 50);
        verify.setFont(new Font("Verdana", Font.BOLD, 15));

        frame.add(info);
        frame.add(code);
        frame.add(verify);
        frame.setVisible(true);
        addActionListener();
        System.out.println("itsz ok");
    }
    public void addActionListener() {
        verify.addActionListener(new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {

                StringBuilder request = new StringBuilder();
                request.append("codeConfirmation");
                request.append(",");
                request.append(code.getText());
                out.println(request);
                frame.setVisible(false);
                LoginView loginView = new LoginView(socket);
                loginView.init();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}
