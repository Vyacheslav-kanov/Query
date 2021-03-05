import java.util.HashMap;
import java.util.Map;

public class Request {

    public enum Method{
        GET,POST,PUT,DELETE
    }

    private String method;
    private String messages;
    private String protocolVersion;

    public Request(Method method, String messages) {
        this.method = method.toString();
        this.messages = messages;
        this.protocolVersion = "HTTP/1.1";
    }

    public static Request convert(String request){
        String[] elements = request.split(" ");
        return new Request(Method.valueOf(elements[0]), elements[1]);
    }

    public String getQueryParam(String name){
        return getQueryParams().get(name);
    }

    public Map<String, String> getQueryParams(){
        String[] params;
        Map<String, String> result = new HashMap<>();

        int start = messages.indexOf("?");
        if(messages.contains("#")){
            int end = messages.indexOf("#");
            params = messages.substring(start,end).split("&");
        }
        else params = messages.substring(start).split("&");

        for(int i = 0; i < params.length; i ++){
            result.put(params[i].split("=")[0], params[i].split("=")[1]);
        }
        return result;
    }

    @Override
    public String toString() {
        return method + "\r\n" + messages + "\r\n" + protocolVersion + "\r\n" + "\r\n" + "\r\n";
    }
}
