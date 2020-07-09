package ClientUI;

import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress host;
    private int port;
    private String name;
    private Socket client;

    public Client(String name, InetAddress host, int port) {
        this.name = name;
        this.host = host;
        this.port = port;
        try {
            client = new Socket(host,port);
        } catch (IOException e) {
            System.out.println("Lỗi kết nối.");
        }
    }

    public void read(TextArea in){
        ReadClient read = new ReadClient(client, in);
        read.start();
    }

    public void write(String sms){
        WriteClient write = new WriteClient(client, name, sms);
        write.start();
    }

}

class ReadClient extends Thread{
    private Socket client;
    private TextArea in;

    public ReadClient(Socket client, TextArea in){
        this.client = client;
        this.in = in;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(client.getInputStream());
            while (true) {
                String sms = dis.readUTF();
                System.out.println(sms);
                in.setText(in.getText() + "\n" +sms);
            }
        } catch (Exception e) {

            try {
                dis.close();
                client.close();
            } catch (Exception ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }
}

class WriteClient extends Thread {
    private Socket client;
    private String name;
    private String sms;

    public WriteClient(Socket client, String name, String sms) {
        this.client = client;
        this.name = name;
        this.sms = sms;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(name + ": " + sms);
        } catch (Exception e) {
            try {
                dos.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }
}

class Run{
    public static void main(String[] args) throws IOException {
//        InetAddress host = InetAddress.getLocalHost();
//        String host = hosts.getHostAddress();
        InetAddress host = InetAddress.getByName("192.168.56.1");
        System.out.println(host);
        Client client = new Client("abc", host, 8888);
        client.write(" 00 ");
        TextArea in = null;
        client.read(in);
    }
}