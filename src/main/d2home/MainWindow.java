package main.d2home;

import main.d2home.queries.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/d2home?serverTimezone=UTC";
    private JPanel mainPanel;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTF;
    private JPasswordField passwordTF;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton selectButton;

    public MainWindow() {
        setContentPane(mainPanel);
        setTitle("D2Home");
        setBounds(600, 300, 320, 256);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancia("insert");
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancia("update");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancia("delete");
            }
        });

        selectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lancia("select");
            }
        });
    }

    private void lancia(String query) {
        String user = userTF.getText();
        String password = new String(passwordTF.getPassword());
        if(user.equals("") || password.equals("")) {
            JOptionPane.showMessageDialog(this, "Inserisci le credenziali!", "Errore", JOptionPane.ERROR_MESSAGE);
            return;
        }

        switch (query) {
            case "select":
                Select select = new Select(user, password);
                break;
            case "insert":
                Insert insert = new Insert(user, password);
                break;
            case "update":
                Update update = new Update(user, password);
                break;
            case "delete":
                Delete delete = new Delete(user, password);
                break;
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
