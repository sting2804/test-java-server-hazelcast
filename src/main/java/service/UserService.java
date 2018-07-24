package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.sun.istack.internal.NotNull;
import model.Level;
import model.Result;
import model.User;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private IList<User> userList;
    private IList<Level> levelList;
    private IList<Result> resultList;

    public UserService() {
        HazelcastInstance client = HazelcastClient.newHazelcastClient();
        userList = client.getList("user");
        levelList = client.getList("level");
        resultList = client.getList("result");
        System.out.println("user map size: " + userList.size());
        System.out.println("level map size: " + levelList.size());
        System.out.println("result map size: " + resultList.size());
    }

    public List<Result> setUserResult(JsonElement params) throws InvalidParameterException {
        if (!params.isJsonObject())
            throw new InvalidParameterException("Invalid json object");
        else {
            JsonObject jsonObject = params.getAsJsonObject();
            if (!jsonObject.has("user_id") || !jsonObject.has("level_id") || !jsonObject.has("result"))
                throw new InvalidParameterException("user_id, level_id and result are required");
            List results = resultList.stream()
                    .filter(result -> result.getUser().getId() == jsonObject.get("user_id").getAsLong())
                    .collect(Collectors.toList());

        }
        return null;
    }

    public Map<String, Object> getUserTop(@NotNull String userId) {
        Map<String, Object> result = new HashMap<>();
        return result;
    }
}
