package soccerHUB;

import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import soccerHUB.utility.ConnectDB;
import soccerHUB.utility.Switch;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerAddBooking implements Initializable {

  public BottomNavigationButton addPrenotazione;
  public Label myLabelOrario;
  public BottomNavigationButton viewPrenotazione;
  public TextField txt_nome;
  public TextField txt_classe;
  public DatePicker addData;
  public TextField txt_number;
  public TextField txt_mail;
  public BottomNavigationButton add_users;
  public ComboBox<String> myComboBoxOrario;
  public ComboBox<String> myComboBoxCampo;
  public ComboBox<String> myComboBoxCampoMemory;

  Connection conn;
  PreparedStatement pst = null;

  public void switchToViewBooking(ActionEvent event) throws IOException {
    Switch switcha = new Switch();
    switcha.switchToViewBooking(event);
  }

  public void switchToHome(ActionEvent event) throws IOException {
    Switch switcha = new Switch();
    switcha.switchToHome(event);
  }

  public void Add_users(ActionEvent event) {

    conn = ConnectDB.getConnect();
    String sql =
        "insert into storico (nome,classe,campo,data,ora,cellulare,mail)values(?,?,?,?,?,?,? )";
    try {

      assert conn != null;
      pst = conn.prepareStatement(sql);
      pst.setString(
          1, txt_nome.getText().substring(0, 1).toUpperCase() + txt_nome.getText().substring(1));
      pst.setString(2, txt_classe.getText().toUpperCase());
      pst.setString(3, myComboBoxCampo.getValue());
      pst.setDate(4, Date.valueOf(addData.getValue()));
      pst.setString(5, myComboBoxOrario.getValue());
      pst.setString(6, txt_number.getText());
      pst.setString(7, txt_mail.getText());
      pst.execute();
      JOptionPane.showMessageDialog(null, "Prenotazione aggiunta con successo.");
      switchToViewBooking(event);

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    myComboBoxOrario
        .getItems()
        .addAll("9:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00", "18:00", "19:00");

    myComboBoxCampo
        .getItems()
        .addAll(
            "1 CALCIO",
            "2 CALCIO",
            "3 CALCIO",
            "4 CALCIO",
            "5 CALCIO",
            "6 PALLAVOLO",
            "7 PALLAVOLO",
            "8 PALLAVOLO",
            "9 PALLAVOLO");

    this.myComboBoxCampoMemory = new ComboBox<>();
    this.myComboBoxCampoMemory.getItems().addAll(this.myComboBoxCampo.getItems());
  }

  // METODO DI CONTROLLO PER IL COMBOBOX ORARIO
  public void data_event(ActionEvent event) {
    LocalDate mydate = addData.getValue();
    myComboBoxOrario.setDisable(mydate.toString().isEmpty());
  }

  // METODO DI CONTROLLO PER IL COMBOBOX CAMPO CON VERIFICA DI CAMPI GIA' OCCUPATI IN BASE ALLA
  // QUERY
  public void comboOraEvent(ActionEvent event) {
    this.myComboBoxCampo.setItems(this.myComboBoxCampoMemory.getItems());
    String myOra = myComboBoxOrario.getValue();
    myComboBoxCampo.setDisable(myOra.isEmpty());

    ObservableList<String> campo = FXCollections.observableArrayList();
    ObservableList<String> newListMemory =
        FXCollections.observableArrayList(); // NUOVA LISTA CAMPI NON OCCUPATI

    Connection conn = ConnectDB.getConnect();
    try {
      String SQL =
          "SELECT DISTINCT `campo` FROM storico WHERE Data = '"
              + addData.getValue()
              + "' AND `ora` = '"
              + myComboBoxOrario.getValue()
              + "'";
      assert conn != null;
      ResultSet rs = conn.createStatement().executeQuery(SQL);
      while (rs.next()) {
        campo.add(rs.getString("campo"));
      }

      for (String listComboBox : myComboBoxCampo.getItems()) {
        for (String listCampo : campo) {
          if (!listComboBox.equals(listCampo)) {
            newListMemory.add(listComboBox);
            myComboBoxCampo.setItems(newListMemory);
          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e);
    }
  }
}
