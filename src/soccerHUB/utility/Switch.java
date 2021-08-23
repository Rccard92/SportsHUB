package soccerHUB.utility;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Switch {

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

  public void switchToViewBooking(ActionEvent event) throws IOException {
    Parent root =
        FXMLLoader.load(
            Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/ViewBooking.fxml")));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }
}
