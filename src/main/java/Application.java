import com.sun.net.httpserver.HttpServer;
import handler.LevelHandler;
import handler.UserGetHandler;
import handler.UserPutHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {
    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create();
            server.createContext("/useringo", new UserGetHandler());
            server.createContext("/levelinfo", new LevelHandler());
            server.createContext("/setinfo", new UserPutHandler());
            server.bind(new InetSocketAddress(8080), 0);
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
