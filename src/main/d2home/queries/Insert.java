package main.d2home.queries;

import javax.swing.*;
import java.sql.Connection;

public class Insert extends JFrame {

    private JPanel mainPanel;
    private Connection con;

    public Insert(Connection con) {
        setContentPane(mainPanel);
        setTitle("Insert");
        setVisible(true);
        setBounds(300, 30, 900, 750);

        this.con = con;
    }

}
