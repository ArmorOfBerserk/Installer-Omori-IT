import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import model.Main;
import utilities.FolderCheck;
import utilities.UnzipFile;
import utilities.WinRegistry;
import javafx.scene.control.TextArea;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class Controller2{

    @FXML
    private Button buttonProsegui;

    @FXML
    private Button buttonInstall;

    @FXML
    private Button buttonBack;

    @FXML
    private Button buttonFolder;

    @FXML
    private TextField textview;

    @FXML
    private TextArea actionLog;

    String value = "";
    FolderCheck folderCheck = FolderCheck.getInstance();
    String path = "";

    @FXML
    public void initialize(){
        /*VA NEL REGISTRO DI SISTEMA E SI PRENDE IL PATH DI STEAM*/

        try {

            value = utilities.WinRegistry.readString(
                    WinRegistry.HKEY_CURRENT_USER, "SOFTWARE\\Valve\\Steam", "SteamPath");
        } catch (IllegalAccessException | InvocationTargetException e) {
            /*SE NON RIESCE AD ACCEDERE AL PATH DI STEAM*/
            e.printStackTrace();
        }


        if( (path = folderCheck.checkDefaultFolder(value) ).equals("false") ){
            if( ( path = folderCheck.checkOtherFolder(value) ).equals("false")){
                /* ERRORONE QUINDI MI SONO ROTTO IL CAZZO, VEDI SE HAI INSTALLATO OMORI
                           * OPPURE METTI TU IL PERCORSO CORRETTO EH.*/
                buttonInstall.setDisable(true);
                textview.setText("Omori.exe non trovato, seleziona il percorso a mano.");
            } else { textview.setText(path);}
        } else { textview.setText(path);}

    }

    @FXML
    void openFolder(ActionEvent event) {
        //la classe che ci servirà per selezionare le cartelle
        DirectoryChooser fileChooser = new DirectoryChooser();

        File file = new File(path);

        if(!path.equals("false")){
             fileChooser.setInitialDirectory(file);
        }

        File fileSelected = fileChooser.showDialog(null);
        /*fileChooser.getExtensionFilters().add("All Direc")*/

        if(fileSelected != null){
            if(folderCheck.selectedFolder(fileSelected.getPath())){
                textview.setText(fileSelected.getPath());
                path = fileSelected.getPath();
                buttonInstall.setDisable(false);
            } else {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setTitle("ERRORE");
                a1.setContentText("Omori.exe non trovato nella cartella corrente.\n" + "Seleziona un'altra cartella.");
                a1.setHeaderText(null);
                a1.showAndWait();
                a1.setResizable(false);
            }
        }
    }

    @FXML
    void install(ActionEvent event) {

        UnzipFile unzipFile = new UnzipFile(actionLog);

        if(folderCheck.selectedFolder(path)){
            buttonBack.setDisable(true);
            buttonFolder.setDisable(true);


            InputStream zipFile = getClass().getResourceAsStream("/OMORI.zip");

            unzipFile.unzip(zipFile, path);

            /*QUANDO HAI FINITO TUTTO, CAMBIA SCHERMATA*/

            folderCheck.checkfile(path);

            buttonInstall.setDisable(true);
            buttonProsegui.setDisable(false);
        }

        actionLog.appendText("INSTALLAZIONE FINITA!");

    }


    @FXML
    void forward(ActionEvent event) {

        URL formUrl = Main.class.getClassLoader().getResource("Scene3.fxml");
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
    void back(ActionEvent event) {
        URL formUrl = Main.class.getClassLoader().getResource("Scene1.fxml");
        Parent root = null;
        try {
            root = FXMLLoader.load(formUrl);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        Stage newStage = (Stage) ( (Node) event.getSource() ).getScene().getWindow();
        newStage.setScene(new Scene(root));
    }

}