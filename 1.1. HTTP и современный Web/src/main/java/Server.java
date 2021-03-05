import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread{

    private static final int port = 9999;
    private static HashMap<String, HashMap<String, Handler>> handlerMap = new HashMap<>();
    private static HashMap<String, Handler> bodyHandler = new HashMap<>();
    private static ExecutorService threadPool = Executors.newFixedThreadPool(64);

    @Override
    public void run() {
        try(ServerSocket serverSocket = new ServerSocket(port)){
            while (!serverSocket.isClosed()){
                Socket client = serverSocket.accept();
                threadPool.submit(new ThreadClientService(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addHandler(String method, String messages, Handler handler){
        bodyHandler.put(messages, handler);
        handlerMap.put(method, bodyHandler);
    }

    public synchronized static HashMap<String, HashMap<String, Handler>> getHandlerMap() {
        return handlerMap;
    }
}
