package main.d2home;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import main.d2home.queries.Delete;
import main.d2home.queries.Insert;
import main.d2home.queries.Select;
import main.d2home.queries.Update;

public class MainWindow extends JFrame {

  private JPanel mainPanel;
  private JLabel userLabel;
  private JLabel passwordLabel;
  private JTextField userTF;
  private JPasswordField passwordTF;
  private JButton insertButton;
  private JButton updateButton;
  private JButton deleteButton;
  private JButton selectButton;
  private static final String DB_URL =
      "jdbc:mysql://localhost:3306/g8?serverTimezone=UTC"; // TODO: cambia g8 in d2home

  public static void main(String[] args) {
    EventQueue.invokeLater(
        () -> {
          try {
            MainWindow frame = new MainWindow();
            frame.setVisible(true);
          } catch (Exception e) {
            e.printStackTrace();
          }
        });
  }

  public MainWindow() {
    setContentPane(mainPanel);
    setTitle("D2Home");
    setBounds(600, 300, 320, 256);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    insertButton.addActionListener(e -> lancia("insert"));
    updateButton.addActionListener(e -> lancia("update"));
    deleteButton.addActionListener(e -> lancia("delete"));
    selectButton.addActionListener(e -> lancia("select"));
  }

  private void lancia(String query) {
    /* TODO: rimuovi commento
    String user = userTF.getText();
    String password = new String(passwordTF.getPassword());
    if(user.equals("") || password.equals("")) {
        JOptionPane.showMessageDialog(this, "Inserisci le credenziali!", "Errore", JOptionPane.ERROR_MESSAGE);
        return;
    }
    */
    String user = "root";
    String password = "BasiDati";

    Connection con;
    try {
      con = DriverManager.getConnection(DB_URL, user, password);
    } catch (SQLException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(
          this, "Credenziali errate!", "Errore", JOptionPane.ERROR_MESSAGE);
      return;
    }

    switch (query) {
      case "select":
        Select select = new Select(con);
        break;
      case "insert":
        Insert insert = new Insert(con);
        break;
      case "update":
        Update update = new Update(con);
        break;
      case "delete":
        Delete delete = new Delete(con);
        break;
    }
  }
}
