package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.istack.internal.NotNull;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    public Map<String, Object> setUserResult(JsonElement params) throws InvalidParameterException {
        if (!params.isJsonObject())
            throw new InvalidParameterException("Invalid json object");
        else {
            JsonObject jsonObject = params.getAsJsonObject();
            if (!jsonObject.has("user_id") || !jsonObject.has("level_id") || !jsonObject.has("result"))
                throw new InvalidParameterException("user_id, level_id and result are required");
            //
        }
        return null;
    }

    public Map<String, Object> getUserTop(@NotNull String userId) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }
}
