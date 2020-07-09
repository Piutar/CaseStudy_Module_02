package ServerUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ControllerServer {
    private boolean checked = true;
    private Server server = null;

    @FXML
    private TextArea textout;

    @FXML
    private TextField portout;

    @FXML
    private TextField textin;

    @FXML
    private Button send;

    @FXML
    private TextField ipout;

    @FXML
    private Button startserver;

    @FXML
    void startserver(ActionEvent event) throws IOException {
        if (checked){
            InetAddress host= InetAddress.getLocalHost();
            String texthost = host.getHostAddress();
            ipout.setText(texthost);

            int portRandom = (int) Math.floor(Math.random()*5500);
            String textport = Integer.toString(portRandom);
            portout.setText(textport);

            server = new Server(portRandom, textout);
            server.start();
            checked = false;
        }
    }

    @FXML
    void send(ActionEvent event) throws UnknownHostException {
        if (textin.getText() != null){
            server.write(textin.getText());
            textout.setText(textout.getText() + "\n" + "Server: " + textin.getText());
        }
        textin.setText(null);
    }

    @FXML
    void textin(ActionEvent event) throws IOException {
        send(event);
    }

}