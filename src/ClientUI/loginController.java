package ClientUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {

    @FXML
    private PasswordField accpass ;

    @FXML
    private TextField accname;

    @FXML
    private Button loginButton;

    @FXML
    void changeScene(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ClinetUI.fxml"));
        Parent sampleParent = loader.load();
        Scene scene = new Scene(sampleParent);
        stage.setScene(scene);
    }
    @FXML
    void checkLogin(ActionEvent event) throws IOException {
        String trueacc = "123";
        String truepass = "123";
        String acc = accname.getText();
        String pass = accpass.getText();
        if ( trueacc.equals(acc) && truepass.equals(pass)){
            changeScene(event);
        }
        else {
            Alert.AlertType alertAlertType;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Wrong acc/pass");
        }
    }
}


