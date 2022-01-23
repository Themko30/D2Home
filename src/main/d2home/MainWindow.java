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
    private JLabel label1;
    private JTextField userTF;
    private JPasswordField passwordTF;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton selectButton;
    private JButton q1Button;
    private JButton q2Button;
    private JButton q3Button;
    private JButton q4Button;
    private JButton q5Button;
    private JButton q6Button;
    private JButton q7Button;
    private JButton q8Button;

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
        setBounds(425, 150, 620, 530);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        insertButton.addActionListener(e -> lancia("insert"));
        updateButton.addActionListener(e -> lancia("update"));
        deleteButton.addActionListener(e -> lancia("delete"));
        selectButton.addActionListener(e -> lancia("select"));
        q1Button.addActionListener(e -> lancia("query1"));
        q2Button.addActionListener(e -> lancia("query2"));
        q3Button.addActionListener(e -> lancia("query3"));
        q4Button.addActionListener(e -> lancia("query4"));
        q5Button.addActionListener(e -> lancia("query5"));
        q6Button.addActionListener(e -> lancia("query6"));
        q7Button.addActionListener(e -> lancia("query7"));
        q8Button.addActionListener(e -> lancia("query8"));
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
            case "query1":
                //Query1 query1 = new Query1(con);
                break;
            case "query2":
                //Query2 query2 = new Query2(con);
                break;
            case "query3":
                //Query3 query3 = new Query3(con);
                break;
            case "query4":
                //Query4 query4 = new Query4(con);
                break;
            case "query5":
                //Query5 query5 = new Query5(con);
                break;
            case "query6":
                //Query6 query6 = new Query6(con);
                break;
            case "query7":
                //Query7 query7 = new Query7(con);
                break;
            case "query8":
                //Query8 query8 = new Query8(con);
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
