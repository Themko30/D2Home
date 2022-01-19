package main.d2home.queries;

import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Update extends JFrame {

    private JPanel mainPanel;
    private JTable table1;
    private JTextField nomeTF;
    private JTextField cognomeTF;
    private JTextField telefonoTF;
    private JTextField indirizzoTF;
    private JTextField adminTF;
    private JTextField whereTF;
    private JTextField updateTF;
    private JButton eseguiButton;
    private JLabel updateLabel;
    private JLabel nomeLabel;
    private JLabel cognomeLabel;
    private JLabel passwordLabel;
    private JLabel telefonoLabel;
    private JLabel indirizzoLabel;
    private JLabel adminLabel;
    private JLabel whereLabel;
    private JPasswordField passwordTF;
    private Connection con;

    public Update(Connection con) {
        setContentPane(mainPanel);
        setTitle("Update");
        setVisible(true);
        setBounds(350, 100, 850, 600);

        this.con = con;
        eseguiButton.addActionListener(eseguiListener);
    }

    ActionListener eseguiListener = e -> {
        String nome = nomeTF.getText().trim();
        String cognome = cognomeTF.getText().trim();
        String password = new String(passwordTF.getPassword());
        String telefono = telefonoTF.getText().trim();
        String indirizzo = indirizzoTF.getText().trim();
        String admin = adminTF.getText().trim();
        String email = whereTF.getText().trim();
        String where = " WHERE email='";
        where += email + "';";

        int a;
        try {
            a = Integer.parseInt(admin);
            if (a != 0 && a != 1)
                throw new IllegalArgumentException();
        } catch (Exception exception) {
            JOptionPane.showMessageDialog(this, "Campo Admin non valido", "Errore", JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
            return;
        }

        String sql = "UPDATE utente SET admin=?,";

        if (!nome.equals(""))
            sql += "nome='"+ nome + "',";
        if (!cognome.equals(""))
            sql += "cognome='"+ cognome + "',";
        if (!password.equals(""))
            sql += "password='"+ password + "',";
        if (!telefono.equals(""))
            sql += "telefono='"+ telefono + "',";
        if (!indirizzo.equals(""))
            sql += "indirizzo='"+ indirizzo + "',";

        sql = sql.substring(0, sql.length()-1); // rimuovo ultima virgola
        sql += where;

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, a);
            if (ps.executeUpdate() != 1)
                throw new Exception("Aggiornamento fallito. Controlla l'email e riprova");

            JOptionPane.showMessageDialog(this, "Aggiornamento riuscito!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            ps = con.prepareStatement("SELECT * FROM utente WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    };
}
