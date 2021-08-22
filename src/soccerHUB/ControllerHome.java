package soccerHUB;

import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import soccerHUB.utility.ConnectDB;
import soccerHUB.utility.Storico;
import sun.util.resources.CalendarData;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerHome implements Initializable {

  public BottomNavigationButton addPrenotazione;
  // DATI TABELLA CAMPI DA CALCIO
  @FXML private TableColumn<Storico, Integer> colID;
  @FXML private TableColumn<Storico, String> colNome;
  @FXML private TableColumn<Storico, String> colClasse;
  @FXML private TableColumn<Storico, String> colCampo;
  @FXML private TableColumn<Storico, Integer> colOra;
  @FXML private TableColumn<Storico, CalendarData> colData;
  @FXML private TableColumn<Storico, Integer> colCell;
  @FXML private TableColumn<Storico, String> colMail;
  // DATI TABELLA CAMPI DA PALLAVOLO
  @FXML private TableColumn<Storico, Integer> colID1;
  @FXML private TableColumn<Storico, String> colNome1;
  @FXML private TableColumn<Storico, String> colClasse1;
  @FXML private TableColumn<Storico, String> colCampo1;
  @FXML private TableColumn<Storico, Integer> colOra1;
  @FXML private TableColumn<Storico, CalendarData> colData1;
  @FXML private TableColumn<Storico, Integer> colCell1;
  @FXML private TableColumn<Storico, String> colMail1;
  private Stage stage;
  String query = null;
  Connection conn = null;
  ResultSet rs = null;
  PreparedStatement pst = null;

  ObservableList<Storico> listHomePallavolo = FXCollections.observableArrayList();
  ObservableList<Storico> listHomeCalcio = FXCollections.observableArrayList();
  @FXML TableView<Storico> tableStoricoHomeCalcio;
  @FXML TableView<Storico> tableStoricoHomePallavolo;

  public void switchToAddBooking(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/AddBooking.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToViewBooking(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/ViewBooking.fxml")));
    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  // METODO AGGIORNAMENTO TABELLA CALCIO
  @FXML
  public void refreshTableCalcio() {

    try {
      listHomeCalcio.clear();

      query =
          "SELECT * FROM `storico` WHERE `data`= CURRENT_DATE AND `campo` BETWEEN 1 AND 5";
      pst = conn.prepareStatement(query);
      rs = pst.executeQuery();

      while (rs.next()) {
        listHomeCalcio.add(
            new Storico(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("classe"),
                rs.getString("campo"),
                rs.getDate("data").toLocalDate(),
                rs.getString("ora"),
                rs.getBigDecimal("cellulare"),
                rs.getString("mail")));
        tableStoricoHomeCalcio.setItems(listHomeCalcio);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ControllerHome.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  // METODO AGGIORNAMENTO TABELLA PALLAVOLO
  @FXML
  public void refreshTablePallavolo() {

    try {
      listHomePallavolo.clear();

      query =
          "SELECT * FROM `storico` WHERE `data`= CURRENT_DATE AND `campo` BETWEEN 6 AND 9";
      pst = conn.prepareStatement(query);
      rs = pst.executeQuery();

      while (rs.next()) {
        listHomePallavolo.add(
            new Storico(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("classe"),
                rs.getString("campo"),
                rs.getDate("data").toLocalDate(),
                rs.getString("ora"),
                rs.getBigDecimal("cellulare"),
                rs.getString("mail")));
        tableStoricoHomePallavolo.setItems(listHomePallavolo);
      }
    } catch (SQLException ex) {
      Logger.getLogger(ControllerHome.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    conn = ConnectDB.getConnect();
    refreshTableCalcio();
    colID.setCellValueFactory(new PropertyValueFactory<>("id"));
    colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    colClasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
    colCampo.setCellValueFactory(new PropertyValueFactory<>("campo"));
    colData.setCellValueFactory(new PropertyValueFactory<>("data"));
    colOra.setCellValueFactory(new PropertyValueFactory<>("ora"));
    colCell.setCellValueFactory(new PropertyValueFactory<>("cellulare"));
    colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));

    refreshTablePallavolo();
    colID1.setCellValueFactory(new PropertyValueFactory<>("id"));
    colNome1.setCellValueFactory(new PropertyValueFactory<>("nome"));
    colClasse1.setCellValueFactory(new PropertyValueFactory<>("classe"));
    colCampo1.setCellValueFactory(new PropertyValueFactory<>("campo"));
    colData1.setCellValueFactory(new PropertyValueFactory<>("data"));
    colOra1.setCellValueFactory(new PropertyValueFactory<>("ora"));
    colCell1.setCellValueFactory(new PropertyValueFactory<>("cellulare"));
    colMail1.setCellValueFactory(new PropertyValueFactory<>("mail"));

    // listHomeCalcio = ConnectDB.getDataStorico(listHomeCalcio);
    tableStoricoHomeCalcio.setItems(listHomeCalcio);
    // listHomePallavolo = ConnectDB.getDataStorico(listHomePallavolo);
    tableStoricoHomePallavolo.setItems(listHomePallavolo);
  }
}
