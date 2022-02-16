package main.d2home.queries;

import main.d2home.MainWindow;
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
        String passwordhash = MainWindow.getHashFromPassword(password);
        String telefono = telefonoTF.getText().trim();
        String[] indirizzo = indirizzoTF.getText().trim().split(",");
        String admin = adminTF.getText().trim();
        String email = whereTF.getText().trim();

        int adminFlag;
        try {
            adminFlag = Integer.parseInt(admin);
            if (adminFlag != 0 && adminFlag != 1)
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
            sql += "password='"+ passwordhash + "',";
        if (indirizzo.length == 3) {
            sql += "via='" + indirizzo[0] + "',";
            sql += "n_civico='" + indirizzo[1] + "',";
            sql += "cap='" + indirizzo[2] + "',";
        }

        sql = sql.substring(0, sql.length()-1); // rimuovo ultima virgola

        try {
            PreparedStatement ps = con.prepareStatement(sql + " WHERE email=?;");
            ps.setInt(1, adminFlag);
            ps.setString(2, email);
            if (ps.executeUpdate() != 1)
                throw new Exception("Aggiornamento fallito. Controlla l'email e riprova");

            // Se vengono inseriti nuovi numeri, prima vengono cancellati i vecchi
            if (!telefono.equals("")) {
                ps = con.prepareStatement("DELETE FROM telefono WHERE utente=?");
                ps.setString(1, email);
                ps.executeUpdate();

                String[] numeri = telefono.split(",");
                for (String numero : numeri) {
                    ps = con.prepareStatement("INSERT INTO telefono (numero, utente) VALUES (?,?)");
                    ps.setString(1, numero);
                    ps.setString(2, email);
                    ps.executeUpdate();
                }
            }

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
