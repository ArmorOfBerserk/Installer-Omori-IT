import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Controller3 {

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

}
