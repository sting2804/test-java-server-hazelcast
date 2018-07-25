package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserGetHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        Headers requestHeaders = he.getRequestHeaders();
        Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
        List<String> results = null;

        if (he.getRequestMethod().equalsIgnoreCase("GET")) {
            String mainAddress = "/useringo/";
            String path = he.getRequestURI().getPath();
            long userId = 0;
            if (path.length() > mainAddress.length()) {
                try {
                    userId = Long.parseLong(path.substring(mainAddress.length()));
                } catch (NumberFormatException e) {
                    log.severe(e.getLocalizedMessage());
                    writeError(he, e.getLocalizedMessage());
                }
                results = resultService.getUserTop(userId);
                log.info("user get result: " + results);
                writeResult(he, results);
            } else {
                log.severe("UserId is required!");
                writeError(he, "UserId is required!");
            }
        }
        writeError(he, "Unsupported request type");
    }
}