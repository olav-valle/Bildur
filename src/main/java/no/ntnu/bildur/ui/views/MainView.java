package no.ntnu.bildur.ui.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import no.ntnu.bildur.ui.controllers.MainController;


/**
 * The startingpoint for the app
 */
public class MainView extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception{

    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
    Parent root = loader.load();
    MainController mainController = loader.getController();

    primaryStage.setTitle("Bildur");
    primaryStage.setScene(new Scene(root, 930, 630));
    primaryStage.setMinWidth(930);
    primaryStage.setMinHeight(640);

    mainController.setTitleText("All images");
    mainController.viewAllImages();
    primaryStage.show();
  }

  @Override
  /**
   * todo: javadoc
   */
  public void stop() {
    // TODO: 07/04/2020 Where do we place calls to close database EM at app exit?

  }


  public static void main(String[] args) {
    launch(args);
  }
}