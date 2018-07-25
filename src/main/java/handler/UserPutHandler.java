package handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import model.Result;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserPutHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange he) throws IOException {
        if (he.getRequestMethod().equalsIgnoreCase("PUT")) {
            InputStream is = he.getRequestBody();
            Reader isr = new InputStreamReader(is);
            JsonElement paramsElement = null;
            try {
                paramsElement = jsonParser.parse(isr);
                try {
                    Result resultUpdated = resultService.setUserResult(paramsElement);
                    log.info("user update result: " + resultUpdated);
                    writeResult(he, resultUpdated);
                } catch (InvalidParameterException e) {
                    log.severe(e.getLocalizedMessage());
                    writeError(he, e.getLocalizedMessage());
                }
            } catch (JsonIOException | JsonSyntaxException e) {
                log.severe(e.getLocalizedMessage());
                writeError(he, e.getLocalizedMessage());
            }
        }
        writeError(he, "Unsupported request type");
    }


}