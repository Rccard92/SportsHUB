package soccerHUB.utility;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

  // CREAZIONE METODO PER LA CONNESSIONE AL DATABASE

  public static Connection getConnect() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn =
          DriverManager.getConnection(
              "jdbc:mysql://database-prenotazioni.cq9a1bxko8yo.us-east-2.rds.amazonaws.com/prenotazioni",
              "admin",
              "#Corsojava2021");
      // JOptionPane.showMessageDialog(null, "Connessione Stabilita");
      return conn;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
      return null;
    }
  }

  /*public static Connection getConnect() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection conn =
              DriverManager.getConnection("jdbc:mysql://localhost/prenotazioni", "root", "");
      // JOptionPane.showMessageDialog(null, "Connessione Stabilita");
      return conn;
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
      return null;
    }
  }*/
}
