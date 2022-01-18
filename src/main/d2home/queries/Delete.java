package main.d2home.queries;

import javax.swing.*;
import java.sql.Connection;

public class Delete extends JFrame {

    private JPanel mainPanel;
    private Connection con;

    public Delete(Connection con) {
        setContentPane(mainPanel);
        setTitle("Delete");
        setVisible(true);
        setBounds(300, 30, 900, 750);

        this.con = con;
    }

}
