import java.io.DataOutputStream;

@FunctionalInterface
public interface Handler {

    void handle(Request request, DataOutputStream out);
}
