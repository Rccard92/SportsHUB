package soccerHUB;

import com.gluonhq.charm.glisten.control.BottomNavigationButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
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

public class ControllerViewBooking implements Initializable {

  public BottomNavigationButton viewPrenotazione;
  public BottomNavigationButton addPrenotazione;
  public BottomNavigationButton home;
  public BottomNavigationButton viewStorico;
  @FXML private TableView<Storico> tableStoricoBooking;
  @FXML private TableColumn<Storico, Integer> colID;
  @FXML private TableColumn<Storico, String> colNome;
  @FXML private TableColumn<Storico, String> colClasse;
  @FXML private TableColumn<Storico, String> colCampo;
  @FXML private TableColumn<Storico, Integer> colOra;
  @FXML private TableColumn<Storico, CalendarData> colData;
  @FXML private TableColumn<Storico, String> colEdit;
  @FXML private TableColumn<Storico, Integer> colCell;
  @FXML private TableColumn<Storico, String> colMail;
  String query = null;
  Connection conn;
  ResultSet rs = null;
  PreparedStatement pst = null;
  Storico storico = null;

  ObservableList<Storico> listViewBooking = FXCollections.observableArrayList();

  public void switchToHome(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/Home.fxml")));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToAddBooking(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/AddBooking.fxml")));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  public void switchToStorico(ActionEvent event) throws IOException {
      Parent root =
              FXMLLoader.load(
                      Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/ViewStorico.fxml")));
      Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
      Scene scene = new Scene(root);
      stage.setScene(scene);
      stage.show();
  }

  // METODO AGGIORNAMENTO TABELLA
  @FXML
  public void refreshTable() {
    try {
      listViewBooking.clear();
      // QUERY DI RICERCA DALLA DATA CORRENTE IN POI
      query = "SELECT * FROM storico WHERE data >= CURRENT_DATE ORDER BY data ASC";
      pst = conn.prepareStatement(query);
      rs = pst.executeQuery();

      while (rs.next()) {
        listViewBooking.add(
            new Storico(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("classe"),
                rs.getString("campo"),
                rs.getDate("data").toLocalDate(),
                rs.getString("ora"),
                rs.getBigDecimal("cellulare"),
                rs.getString("mail")));
        tableStoricoBooking.setItems(listViewBooking);
      }
      pst.close();
      rs.close();
    } catch (SQLException ex) {
      Logger.getLogger(ControllerHome.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    conn = ConnectDB.getConnect();
    refreshTable();

    colID.setCellValueFactory(new PropertyValueFactory<>("id"));
    colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
    colClasse.setCellValueFactory(new PropertyValueFactory<>("classe"));
    colCampo.setCellValueFactory(new PropertyValueFactory<>("campo"));
    colData.setCellValueFactory(new PropertyValueFactory<>("data"));
    colOra.setCellValueFactory(new PropertyValueFactory<>("ora"));
    colCell.setCellValueFactory(new PropertyValueFactory<>("cellulare"));
    colMail.setCellValueFactory(new PropertyValueFactory<>("mail"));

    Callback<TableColumn<Storico, String>, TableCell<Storico, String>> cellFactory =
        (TableColumn<Storico, String> param) -> {
          final TableCell<Storico, String> cell =
              new TableCell<Storico, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                  super.updateItem(item, empty);
                  if (empty) {
                    setGraphic(null);
                    setText(null);
                  } else {
                    FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                    FontAwesomeIconView editIcon =
                        new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                    deleteIcon.setStyle(
                        " -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill:#ff1744;");
                    editIcon.setStyle(
                        " -fx-cursor: hand ;" + "-glyph-size:28px;" + "-fx-fill: #45b28a;");

                    // CANCELLAZIONE DELLA PRENOTAZIONE
                    deleteIcon.setOnMouseClicked(
                        (MouseEvent event) -> {
                          try {
                            storico = tableStoricoBooking.getSelectionModel().getSelectedItem();
                            query = "DELETE FROM storico WHERE ID = " + storico.getId();
                            conn = ConnectDB.getConnect();
                            pst = conn.prepareStatement(query);
                            pst.execute();
                            refreshTable();

                          } catch (SQLException ex) {
                            Logger.getLogger(ControllerHome.class.getName())
                                .log(Level.SEVERE, null, ex);
                          }
                        });

                    // MODIFICA DELLA PRENOTAZIONE
                    editIcon.setOnMouseClicked(
                        (MouseEvent event) -> {
                          storico = tableStoricoBooking.getSelectionModel().getSelectedItem();
                          FXMLLoader loader = new FXMLLoader();
                          loader.setLocation(
                              getClass().getResource("/soccerHUB/fxml/EditBooking.fxml"));
                          try {
                            loader.load();
                          } catch (IOException ex) {
                            Logger.getLogger(ControllerHome.class.getName())
                                .log(Level.SEVERE, null, ex);
                          }

                          ControllerEditBooking controllerEditBooking = loader.getController();
                          controllerEditBooking.setUpdate(true);
                          controllerEditBooking.setTextField(
                              storico.getId(),
                              storico.getNome(),
                              storico.getClasse(),
                              storico.getCampo(),
                              storico.getData(),
                              storico.getOra(),
                              storico.getCellulare(),
                              storico.getMail());
                          Parent parent = loader.getRoot();
                          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                          stage.setScene(new Scene(parent));
                          stage.initStyle(StageStyle.UNDECORATED);
                          stage.show();
                        });

                    HBox managebtn = new HBox(editIcon, deleteIcon);
                    managebtn.setStyle("-fx-alignament:center");
                    HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                    HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                    setGraphic(managebtn);
                    setText(null);
                  }
                }
              };

          return cell;
        };
    // listViewBooking = ConnectDB.getDataStorico(listViewBooking);
    colEdit.setCellFactory(cellFactory);
    tableStoricoBooking.setItems(listViewBooking);
  }
}
