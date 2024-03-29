import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.Main;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class Controller1 {

    @FXML
    public ImageView gitHubLink;

    @FXML
    private Label textWall;

    public void initialize(){

    }

    @FXML
    void forward(ActionEvent event) {
        URL formUrl = Main.class.getClassLoader().getResource("Scene2.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(formUrl);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Stage newStage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        newStage.setScene(new Scene(root));
    }



    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    public void linkToGitHub(MouseEvent mouseEvent) {
        Hyperlink hyperlink = new Hyperlink();

        if(Desktop.isDesktopSupported()){
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/ArmorOfBerserk"));
            } catch (IOException | URISyntaxException exception) {
                exception.printStackTrace();
            }
        }
    }
}
