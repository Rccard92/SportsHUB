package soccerHUB;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/soccerHUB/fxml/Home.fxml")));
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setTitle("Sports HUB");
    primaryStage.setResizable(false);
    Image icon = new Image("/soccerHUB/resources/favicon.png");
    primaryStage.getIcons().add(icon);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
