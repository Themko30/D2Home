package main.d2home.queries;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.proteanit.sql.DbUtils;

public class Delete extends JFrame {

  private JPanel mainPanel;
  private JTable table1;
  private JTextField utenteTextField;
  private JTextField emailTextField1;
  private JButton eseguiButton;
  private Connection con;

  public Delete(Connection con) {
    setContentPane(mainPanel);
    setTitle("Delete");
    setVisible(true);
    setBounds(300, 30, 900, 750);

    this.con = con;
    eseguiButton.addActionListener(eseguiListener);
    try {
      PreparedStatement ps = con.prepareStatement("SELECT * FROM utente");
      ResultSet rs = ps.executeQuery();
      table1.setModel(DbUtils.resultSetToTableModel(rs));

    } catch (Exception throwables) {
      JOptionPane.showMessageDialog(
          this, throwables.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
      throwables.printStackTrace();
    }
  }

  ActionListener eseguiListener =
      e -> {
        String email = emailTextField1.getText().trim();

        try {
          PreparedStatement ps = con.prepareStatement("DELETE FROM utente WHERE email=?");
          ps.setString(1, email);

          if (ps.executeUpdate() != 1) {
            throw new Exception("Cancellazione fallita, controlla email e riprova.");
          }
          JOptionPane.showMessageDialog(
              this, "Cancellazione riuscita!", "Successo", JOptionPane.INFORMATION_MESSAGE);
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
