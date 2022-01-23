package main.d2home.queries;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import main.d2home.MainWindow;
import net.proteanit.sql.DbUtils;

public class Insert extends JFrame {

  private JPanel mainPanel;
  private JTable table1;
  private JTextField utenteTextField;
  private JTextField emailTextField1;
  private JTextField nomeTextField2;
  private JTextField cognomeTextField3;
  private JTextField telefonoTextField5;
  private JTextField indirizzoTextField6;
  private JTextField adminTextField7;
  private JPasswordField passwordField1;
  private JButton eseguiButton;
  private Connection con;

  public Insert(Connection con) {
    setContentPane(mainPanel);
    setTitle("Insert");
    setVisible(true);
    setBounds(300, 30, 900, 750);

    this.con = con;
    eseguiButton.addActionListener(eseguiListener);
  }

  ActionListener eseguiListener =
      e -> {
        String email = emailTextField1.getText().trim();
        String nome = nomeTextField2.getText().trim();
        String cognome = cognomeTextField3.getText().trim();
        String password = new String(passwordField1.getPassword());
        String passwordhash = MainWindow.getHashFromPassword(password);
        String telefono = telefonoTextField5.getText().trim();
        String indirizzo = indirizzoTextField6.getText().trim();
        String admin = adminTextField7.getText().trim();

        int a = 0;
        try {
          if (email.equals(""))
            throw new Exception("Inserimento fallito, controlla email e riprova.");
          if (nome.equals(""))
            throw new Exception("Inserimento fallito, controlla nome e riprova.");
          if (cognome.equals(""))
            throw new Exception("Inserimento fallito, controlla cognome e riprova.");
          if (password.equals(""))
            throw new Exception("Inserimento fallito, controlla password e riprova.");
          if (telefono.equals(""))
            throw new Exception("Inserimento fallito, controlla telefono e riprova.");
          if (indirizzo.equals(""))
            throw new Exception("Inserimento fallito, controlla indirizzo e riprova.");
          a = Integer.parseInt(admin);
          if (a != 0 && a != 1) {
            throw new Exception("Inserimento fallito, controlla admin e riprova.");
          }
        } catch (NumberFormatException ne) {
          JOptionPane.showMessageDialog(
              this, "Errore in admin, solo numeri!", "Errore", JOptionPane.ERROR_MESSAGE);
          ne.printStackTrace();
          return;
        } catch (Exception throwables) {
          JOptionPane.showMessageDialog(
              this, throwables.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
          throwables.printStackTrace();
          return;
        }

        try {
          PreparedStatement ps =
              con.prepareStatement(
                  "INSERT INTO utente (email, nome, cognome, indirizzo, telefono, password, admin) VALUES (?,?,?,?,?,?,?)");
          ps.setString(1, email);
          ps.setString(2, nome);
          ps.setString(3, cognome);
          ps.setString(4, indirizzo);
          ps.setString(5, telefono);
          ps.setString(6, passwordhash);
          ps.setInt(7, a);

          if (ps.executeUpdate() != 1) {
            throw new Exception("Inserimento fallito, controlla i campi e riprova.");
          }
          JOptionPane.showMessageDialog(
              this, "Inserimento riuscito!", "Successo", JOptionPane.INFORMATION_MESSAGE);
          ps = con.prepareStatement("SELECT * FROM utente");
          ResultSet rs = ps.executeQuery();
          table1.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception throwables) {
          JOptionPane.showMessageDialog(
              this, throwables.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
          throwables.printStackTrace();
        }
      };
}
