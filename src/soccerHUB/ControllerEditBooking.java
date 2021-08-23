package soccerHUB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import soccerHUB.utility.ConnectDB;
import soccerHUB.utility.Switch;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerEditBooking implements Initializable {

  public ComboBox<String> myComboBoxOrario;
  public Label myLabelOrario;
  public ComboBox<String> myComboBoxCampo;
  public TextField txt_nome;
  public TextField txt_classe;
  public DatePicker addData;
  public boolean update;
  public TextField txt_number;
  public TextField txt_mail;
  int storicoId;
  String query = null;
  Connection conn;
  PreparedStatement pst;

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
  }

  @FXML
  private void save(ActionEvent event) throws IOException {

    conn = ConnectDB.getConnect();
    String nome = txt_nome.getText();
    String classe = txt_classe.getText();
    String campo = myComboBoxCampo.getValue();
    String data = String.valueOf(addData.getValue());
    String orario = myComboBoxOrario.getValue();
    String cellulare = txt_number.getText();
    String email = txt_mail.getText();

    if (nome.isEmpty()
        || classe.isEmpty()
        || campo.isEmpty()
        || data.isEmpty()
        || orario.isEmpty()
        || cellulare.isEmpty()
        || email.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setHeaderText(null);
      alert.setContentText("Compilare tutti i campi");
      alert.showAndWait();

    } else {
      getQuery();
      insert();
      Switch switcha = new Switch();
      switcha.switchToViewBooking(event);
    }
  }

  public void getQuery() {
    query =
        "UPDATE `storico` SET "
            + "`nome`=?,"
            + "`classe`=?,"
            + "`campo`=?,"
            + "`data`=?,"
            + "`ora`=?,"
            + "`cellulare`=?,"
            + "`mail`= ? WHERE id = '"
            + storicoId
            + "'";
  }

  public void insert() {
    try {
      pst = conn.prepareStatement(query);
      pst.setString(
          1, txt_nome.getText().substring(0, 1).toUpperCase() + txt_nome.getText().substring(1));
      pst.setString(2, txt_classe.getText().toUpperCase());
      pst.setString(3, myComboBoxCampo.getValue());
      pst.setString(4, String.valueOf(addData.getValue()));
      pst.setString(5, myComboBoxOrario.getValue());
      pst.setString(6, txt_number.getText());
      pst.setString(7, txt_mail.getText());
      pst.execute();
      JOptionPane.showMessageDialog(null, "Prenotazione aggiornata con successo.");

    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e);
    }
  }

  public void setTextField(
      int id,
      String nome,
      String classe,
      String campo,
      LocalDate data,
      String ora,
      BigDecimal cellulare,
      String mail) {
    storicoId = id;
    txt_nome.setText(nome);
    txt_classe.setText(classe);
    myComboBoxCampo.setValue(campo);
    addData.setValue(data);
    myComboBoxOrario.setValue(ora);
    txt_number.setText(String.valueOf(cellulare));
    txt_mail.setText(mail);
  }

  public void setUpdate(boolean b) {
    this.update = b;
  }
}
