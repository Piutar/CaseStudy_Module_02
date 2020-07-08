package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.event.ActionEvent;

public class Controller {

    @FXML
    private TextField textin;
    @FXML
    private TextField port;
    @FXML
    private TextField ip;

    public void textin(javafx.event.ActionEvent event){


    }

    public void textout(javafx.event.ActionEvent event){


    }

    public void ip(javafx.event.ActionEvent event){
        String ipServer = ip.getText();
    }

    public void port(javafx.event.ActionEvent event){
        int portServer =Integer.parseInt(port.getText());
    }

    public void connect(javafx.event.ActionEvent event){
    }

    public void send(javafx.event.ActionEvent event){
        String textSend = textin.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("0123" + textSend);
        alert.show();
    }

}
