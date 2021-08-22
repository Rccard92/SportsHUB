package soccerHUB;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import soccerHUB.utility.ConnectDB;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerEditBooking implements Initializable {
  public ChoiceBox<String> myChoiceBoxOrario;
  public Label myLabelOrario;
  public ChoiceBox<String> myChoiceBoxCampo;
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
    myChoiceBoxOrario
        .getItems()
        .addAll("9:00", "10:00", "11:00", "12:00", "15:00", "16:00", "17:00", "18:00", "19:00");

    myChoiceBoxCampo
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

  ///////////////////////////////////////////////////////////////////////////////////////

  public void switchToAddBooking(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/ViewBooking.fxml")));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void save(ActionEvent event) throws IOException {

    conn = ConnectDB.getConnect();
    String nome = txt_nome.getText();
    String classe = txt_classe.getText();
    String campo = myChoiceBoxCampo.getValue();
    String data = String.valueOf(addData.getValue());
    String orario = myChoiceBoxOrario.getValue();
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
      clean();
      switchToAddBooking(event);
    }
  }

  @FXML
  public void clean() {
    txt_nome.setText(null);
    txt_classe.setText(null);
    myChoiceBoxCampo.setValue(null);
    addData.setValue(null);
    myChoiceBoxOrario.setValue(null);
    txt_number.setText(null);
    txt_mail.setText(null);
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
      pst.setString(3, myChoiceBoxCampo.getValue());
      pst.setString(4, String.valueOf(addData.getValue()));
      pst.setString(5, myChoiceBoxOrario.getValue());
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
    myChoiceBoxCampo.setValue(campo);
    addData.setValue(data);
    myChoiceBoxOrario.setValue(ora);
    txt_number.setText(String.valueOf(cellulare));
    txt_mail.setText(mail);
  }

  public void setUpdate(boolean b) {
    this.update = b;
  }
}
