package sample;

import chat_tcp.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Controller {
    @FXML
    private TextField name;

    @FXML
    private TextField port;

    @FXML
    private TextArea textout;

    @FXML
    private TextField textin;

    @FXML
    private Button send;

    @FXML
    private TextField ip;

    @FXML
    private Button connect;

    @FXML
    void connect(ActionEvent event) throws UnknownHostException {
        Client client = new Client(name.getText(), InetAddress.getByName(ip.getText()), Integer.parseInt(port.getText()));
    }

    @FXML
    void ip(ActionEvent event) {

    }

    @FXML
    void port(ActionEvent event) {

    }

    @FXML
    void send(ActionEvent event) {
        Textout(event);

    }

    @FXML
    void textin(ActionEvent event) {
        Textout(event);
        textin.setText(null);

    }

    @FXML
    void Textout(ActionEvent event){
        textout.setText(textin.getText());
    }

}
