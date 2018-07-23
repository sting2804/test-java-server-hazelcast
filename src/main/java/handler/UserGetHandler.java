package handler;

import com.google.gson.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.UserService;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class UserGetHandler extends UserHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        Headers requestHeaders = he.getRequestHeaders();
        Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
        Map<String, Object> result = null;

        if (he.getRequestMethod().equalsIgnoreCase("GET")) {
            String mainAddress = "/useringo/";
            String path = he.getRequestURI().getPath();
            String userId;
            if (path.length() > mainAddress.length()) {
                userId = path.substring(mainAddress.length());
                result = userService.getUserTop(userId);
                log.info("user get result: " + result.toString());
            } else
                log.severe("UserId is required!");
        }
        if (result != null) {
            he.getResponseHeaders().add("content-type","application/json");
            he.sendResponseHeaders(200, 0);
        }
        else
            he.sendResponseHeaders(400, 0);
        OutputStream os = he.getResponseBody();
        String response = gson.toJson(result);
        if (response != null) {
            os.write(response.getBytes());
        }
        os.close();
    }
}