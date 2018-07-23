package handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserPutHandler extends UserHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        Headers requestHeaders = he.getRequestHeaders();
        Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
        Map<String, Object> result = null;

        if (he.getRequestMethod().equalsIgnoreCase("PUT")) {
            int contentLength = Integer.parseInt(requestHeaders.getFirst("Content-length"));
            InputStream is = he.getRequestBody();
            Reader isr = new InputStreamReader(is);
            JsonElement paramsElement = null;
            try {
                paramsElement = jsonParser.parse(isr);
                try {
                    result = userService.setUserResult(paramsElement);
                    log.info("user update result: " + result.toString());
                }catch (InvalidParameterException e){
                    log.severe(e.getLocalizedMessage());
                }
            } catch (JsonIOException | JsonSyntaxException e) {
                log.severe(e.getLocalizedMessage());
            }
        }
        if (result != null) {
            he.getResponseHeaders().add("content-type", "application/json");
            he.sendResponseHeaders(200, 0);
        } else
            he.sendResponseHeaders(400, 0);
        OutputStream os = he.getResponseBody();
        String response = gson.toJson(result);
        if (response != null) {
            os.write(response.getBytes());
        }
        os.close();
    }
}