package handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import model.Result;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LevelHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
        Headers requestHeaders = he.getRequestHeaders();
        Set<Map.Entry<String, List<String>>> entries = requestHeaders.entrySet();
        List<Result> results = null;

        if (he.getRequestMethod().equalsIgnoreCase("GET")) {
            String mainAddress = "/levelinfo/";
            String path = he.getRequestURI().getPath();
            long levelId = 0;
            if (path.length() > mainAddress.length()) {
                try {
                    levelId = Long.parseLong(path.substring(mainAddress.length()));
                } catch (NumberFormatException e) {
                    log.severe(e.getLocalizedMessage());
                    writeError(he, e.getLocalizedMessage());
                }
                results = resultService.getTopOfResultsByLevel(levelId);
                log.info("level get result: " + results);
                writeResult(he, results);
            } else {
                log.severe("levelId is required!");
                writeError(he, "levelId is required!");
            }
        }
        writeError(he, "Unsupported request type");
    }
}