package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        URL formUrl = Main.class.getClassLoader().getResource("Scene1.fxml");

        Parent root = null;
        try {
            root = FXMLLoader.load(formUrl);
        } catch (IOException e) {
            //PROBLEMI DI CARIMENTO FXML
            e.printStackTrace();
        }


        primaryStage.setTitle("OMORI ITA Installer");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/omorita_installer_icon.png")));
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMaxHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMaxWidth(primaryStage.getWidth());

    }


    public static void main(String[] args) {
        launch(args);
    }
}
