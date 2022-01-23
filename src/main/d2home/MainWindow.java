package main.d2home;

import main.d2home.queries.*;

import javax.swing.*;
import java.awt.EventQueue;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainWindow extends JFrame {

    private JPanel mainPanel;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTF;
    private JPasswordField passwordTF;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton selectButton;

    // TODO: sostituisci "prova" con "d2home"
    private static final String DB_URL = "jdbc:mysql://localhost:3306/prova?serverTimezone=UTC";

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
                    try {
                        MainWindow frame = new MainWindow();
                        frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    public MainWindow() {
        setContentPane(mainPanel);
        setTitle("D2Home");
        setBounds(600, 300, 320, 256);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        insertButton.addActionListener(e -> lancia("insert"));
        updateButton.addActionListener(e -> lancia("update"));
        deleteButton.addActionListener(e -> lancia("delete"));
        selectButton.addActionListener(e -> lancia("select"));
    }

    private void lancia(String query) {
        /* TODO: rimuovi commento
        String user = userTF.getText();
        String password = new String(passwordTF.getPassword());
        if(user.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "Inserisci le credenziali!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }
        */
        String user = "root";
        String password = "studentiTSW";

        Connection con;
        try {
            con = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (query) {
            case "select":
                Select select = new Select(con);
                break;
            case "insert":
                Insert insert = new Insert(con);
                break;
            case "update":
                Update update = new Update(con);
                break;
            case "delete":
                Delete delete = new Delete(con);
                break;
        }
    }

    public static String getHashFromPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            byte[] hashedPwd = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte bit : hashedPwd) {
                builder.append(String.format("%02x", bit));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
