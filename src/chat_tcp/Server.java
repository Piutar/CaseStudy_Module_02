package chat_tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    private int port;
    public static ArrayList<Socket> listSk = new ArrayList<>();

    public Server(int port) {
        this.port = port;
    }

    private void execute() throws IOException {
        ServerSocket server = new ServerSocket(port);
        WriteServer write = new WriteServer();
        write.start();
        System.out.println("Server đợi kết nối...");
        while (true){
            Socket socket = server.accept();
            System.out.println("Server đã kết nối với "+ socket);
            Server.listSk.add(socket);
            ReadServer read = new ReadServer(socket);
            read.start();
        }

    }

    public static void main(String[] args) throws IOException {
        InetAddress host = InetAddress.getLocalHost();
        System.out.println(host);
        //Server.listSk = new ArrayList<>();
        Server server = new Server(15797);
        server.execute();
    }
}

class ReadServer extends Thread{
    private Socket socket;

    public ReadServer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(socket.getInputStream());
            while (true) {
                String sms = dis.readUTF();
                if (sms.contains("exit")){
                    Server.listSk.remove(socket);
                    System.out.println("Đã ngắt kết nối với" +socket);
                    dis.close();
                    socket.close();
                    continue; // Nhắt kết nối và không gửi từ exit
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

    @Override
    public void run() {
        DataOutputStream dos = null;
        Scanner sc = new Scanner(System.in);
        while (true){
            String sms = sc.nextLine();
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
}

