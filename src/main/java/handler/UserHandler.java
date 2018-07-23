package handler;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpHandler;
import service.UserService;

import java.util.logging.Logger;

abstract class UserHandler implements HttpHandler {
    UserService userService;
    JsonParser jsonParser;
    Gson gson;
    Logger log = Logger.getLogger(this.getClass().getName());

    UserHandler() {
        userService = new UserService();
        jsonParser = new JsonParser();
        gson = new Gson();
    }
}