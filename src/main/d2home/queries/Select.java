package main.d2home.queries;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Select extends JFrame {

    private JPanel mainPanel;
    private JLabel selectLabel;
    private JLabel fromLabel;
    private JLabel whereLabel;
    private JTextField selectTF;
    private JTextField whereTF;
    private JButton eseguiButton;
    private JComboBox<String> fromCB;
    private JTable table1;
    private Connection con;

    public Select(Connection con) {
        setContentPane(mainPanel);
        setTitle("Select");
        setVisible(true);
        setBounds(300, 30, 900, 750);

        this.con = con;
        eseguiButton.addActionListener(eseguiListener);
    }

    ActionListener eseguiListener = e -> {
        String select = selectTF.getText().trim();
        String from = (String) fromCB.getSelectedItem();
        String where = whereTF.getText().trim();

        if (select.equals("")) {
            select = "*";
        }

        String sql = "SELECT " + select + " FROM " + from;
        if (!where.equals("")) {
            sql += " WHERE " + where;
        }
        sql += ";";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    };
}
