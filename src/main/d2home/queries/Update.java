package main.d2home.queries;

import javax.swing.*;
import java.sql.Connection;

public class Update extends JFrame {

    private JPanel mainPanel;
    private Connection con;

    public Update(Connection con) {
        setContentPane(mainPanel);
        setTitle("Update");
        setVisible(true);
        setBounds(300, 30, 900, 750);

        this.con = con;
    }

}
