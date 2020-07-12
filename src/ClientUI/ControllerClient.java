package ClientUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;

public class ControllerClient {
    private boolean checked = true;
    private Client client = null;

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
    void connect(ActionEvent event) throws IOException {
        if (checked && (name.getText() != null) && (ip.getText() !=null) && (port.getText() != null)){
            name.setEditable(false);
            client = new Client(name.getText(), InetAddress.getByName(ip.getText()), Integer.parseInt(port.getText()));
            client.read(textout);
            textout.setText("Kết nối đến Server:" + InetAddress.getByName(ip.getText()) + " - "+ Integer.parseInt(port.getText()));
            checked = false;
        }
    }


    @FXML
    void send(ActionEvent event) {
        if (textin.getText() != null){
            client.write(textin.getText());
            textout.appendText("\n" + name.getText() + ": " + textin.getText());
            //textout.setText(textout.getText() + "\n" + name.getText() + ": " + textin.getText());
        }
        textin.setText(null);
    }

    @FXML
    void textin(ActionEvent event) {
        send(event);
        //textout.appendText("000");


    }

    void display(){
        String text = textout.getText();
        textout.appendText("\n" + name.getText() + ": " + textin.getText());
        //textout.appendText("\n" + name.getText() + ": " + textin.getText());
        textin.setText(null);
    }

}
