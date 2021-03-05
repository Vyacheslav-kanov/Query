import java.io.*;
import java.net.Socket;

public class ThreadClientService implements Runnable{

    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;

    public ThreadClientService(Socket client) {
        this.client = client;
        try {
            out = new DataOutputStream(client.getOutputStream());
            in = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("Service created!");
            String request = in.readUTF();
            System.out.println(request);
            handlerToRequest(request)
                    .handle(Request.convert(request), out);
            System.out.println("complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Handler handlerToRequest(Request request){
        String requestStr = request.toString().replace("\r\n", " ");
        String[] elements = requestStr.split(" ");

        for( String e: Server.getHandlerMap().keySet()){
            if(e.equals(elements[0])){
                for(String r:Server.getHandlerMap().get(e).keySet()){
                    if(r.equals(elements[1])){
                        return Server.getHandlerMap().get(e).get(r);
                    }
                }
            }
        }
        return null;
    }
    private Handler handlerToRequest(String request){
        request = request.replace("\r\n", " ");
        String[] elements = request.split(" ");

        for( String e: Server.getHandlerMap().keySet()){
            if(e.equals(elements[0])){
                for(String r:Server.getHandlerMap().get(e).keySet()){
                    if(r.equals(elements[1])){
                        System.out.println(Server.getHandlerMap().get(e).get(r));
                        return Server.getHandlerMap().get(e).get(r);
                    }
                }
            }
        }
        return null;
    }
}
