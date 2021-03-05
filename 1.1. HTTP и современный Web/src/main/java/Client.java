import java.io.*;
import java.net.Socket;

public class Client extends Thread{

    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

    public Client() {
        try {
            client = new Socket("localhost", 9999);
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            out.writeUTF("GET" + "\r\n" + "/messages" + "\r\n" + "HTTP/1.1" + "\r\n" + "\r\n");
            out.flush();
            while (true) {
                String request = in.readUTF();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
