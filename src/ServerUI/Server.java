package ServerUI;

import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server extends Thread {
    private int port;
    public static ArrayList<Socket> listSk = new ArrayList<>();
    private ServerSocket server;
    private TextArea in;

    public Server(int port, TextArea in) {
        this.port = port;
        this.in = in;
        try {
            server = new ServerSocket(port);
            in.setText("Server đợi kết nối...");
        } catch (IOException e) {
            System.out.println("Lỗi khởi tạo sever.");
        }
    }

    public void run() {
        while (true){
            Socket socket = null;
            try {
                socket = server.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            in.setText(in.getText()+"\n" + "Server đã kết nối với "+ socket);
            Server.listSk.add(socket);
            ReadServer read = new ReadServer(socket, in);
            read.start();
        }
    }

    public void write(String sms){
        WriteServer write = new WriteServer(sms);
        write.start();
    }


//    public static void main(String[] args) throws IOException {
//        InetAddress host = InetAddress.getLocalHost();
//        System.out.println(host);
//        int port = (int) Math.floor(Math.random()*5500);
//        port = 8888;
//        System.out.println(port);
//        TextArea in = null;
//        Server server = new Server(port, in);
//        //server.execute();
//    }
}

class ReadServer extends Thread{
    private Socket socket;
    private TextArea in;

    public ReadServer(Socket socket, TextArea in){
        this.socket = socket;
        this.in = in;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            while (true) {
                String sms = dis.readUTF();
                in.setText(in.getText() + "\n" + sms);
                if (sms.contains("exit")){
                    Server.listSk.remove(socket);
                    in.setText(in.getText() +"\n"+"Đã ngắt kết nối với" +socket);
                    System.out.println("Đã ngắt kết nối với" +socket);
                    dis.close();
                    socket.close();
                    continue; // Ngắt kết nối và không gửi từ exit
                }
                for (Socket item : Server.listSk){
                    if (item.getPort() != socket.getPort()){
                        DataOutputStream dos = new DataOutputStream(item.getOutputStream());
                        dos.writeUTF(sms);
                    }
                }
                System.out.println(sms);
            }
        } catch (Exception e) {

            try {
                dis.close();
                socket.close();
            } catch (Exception ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }
}

class WriteServer extends Thread {
    private String sms;

    public WriteServer(String sms){
        this.sms = sms;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        try {
            for (Socket item : Server.listSk){
                dos = new DataOutputStream(item.getOutputStream());
                dos.writeUTF("Server: "+ sms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}