package main.d2home.queries;

import main.d2home.MainWindow;

import javax.swing.*;
import java.sql.*;

public class Select extends JFrame {

    private JPanel mainPanel;

    public Select(String user, String password) {
        setContentPane(mainPanel);
        setTitle("Select");
        setVisible(true);
        setBounds(600, 300, 320, 256);
    }

}
