package main.d2home.queries;

import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;

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
        String telefono = telefonoTextField5.getText().trim();
        String indirizzo = indirizzoTextField6.getText().trim();
        String admin = adminTextField7.getText().trim();

        int a;
        try {
          a = Integer.parseInt(admin);
          if (a != 0 && a != 1) {
            throw new IllegalArgumentException();
          }
        } catch (IllegalArgumentException exception) {
          exception.printStackTrace();
        }
      };
}
