package handler;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import service.ResultService;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

abstract class BaseHandler implements HttpHandler {
    ResultService resultService;
    JsonParser jsonParser;
    Gson gson;
    Logger log = Logger.getLogger(this.getClass().getName());

    BaseHandler() {
        resultService = new ResultService();
        jsonParser = new JsonParser();
        gson = new Gson();
    }

    void writeError(HttpExchange he, String localizedMessage) throws IOException {
        he.sendResponseHeaders(400, 0);
        OutputStream os = he.getResponseBody();
        if (localizedMessage != null) {
            os.write(localizedMessage.getBytes());
        }
        os.close();
    }

    void writeResult(HttpExchange he, Object result) throws IOException {
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