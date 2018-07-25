package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import model.Level;
import model.Result;
import model.User;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResultService {
    private static final long TOP_SIZE = 20;
    private IList<User> userList;
    private IList<Level> levelList;
    private IList<Result> resultList;

    public ResultService() {
        this(HazelcastClient.newHazelcastClient());
    }

    public ResultService(HazelcastInstance client) {
        userList = client.getList("user");
        levelList = client.getList("level");
        resultList = client.getList("result");
    }

    /**
     * установка значения result по параметрам user_id и level_id
     *
     * @param params JSON c параметрами result, user_id и level_id
     * @return обновлённый объект Result
     * @throws InvalidParameterException если любого из параметров нет в JSON-объекте
     */
    public Result setUserResult(JsonElement params) throws InvalidParameterException {
        if (!params.isJsonObject())
            throw new InvalidParameterException("Invalid json object");
        else {
            JsonObject jsonObject = params.getAsJsonObject();
            if (!jsonObject.has("user_id")
                    || !jsonObject.has("level_id")
                    || !jsonObject.has("result"))
                throw new InvalidParameterException("user_id, level_id and result are required");
            long userId, levelId;
            String resultParam;
            try {
                userId = jsonObject.get("user_id").getAsLong();
                levelId = jsonObject.get("level_id").getAsLong();
                resultParam = jsonObject.get("result").getAsString();
            } catch (ClassCastException | IllegalStateException e) {
                throw new InvalidParameterException("user_id, level_id and result are required");
            }
            List<Result> foundResults = resultList.stream()
                    .filter(entry ->
                            entry.getUser().getId() == userId
                                    && entry.getLevel().getId() == levelId)
                    .collect(Collectors.toList());
            if (!foundResults.isEmpty()) {
                Result r = foundResults.get(0);
                resultList.remove(r);
                r.setValue(resultParam);
                resultList.add(r);
                return r;
            } else
                throw new InvalidParameterException("result is not found.");
        }
    }

    /**
     * поиск топ20 значений результатов по userId, отсортированных по убыванию value
     *
     * @param userId id пользователя
     * @return список значений Result.value
     */
    public List<String> getUserTop(long userId) {
        return resultList.stream()
                .filter(entry ->
                        entry.getUser().getId() == userId)
                .sorted(Comparator.comparing(Result::getValue, Comparator.reverseOrder()))
                .limit(TOP_SIZE)
                .map(Result::getValue)
                .collect(Collectors.toList());
    }

    /**
     * полечение топ20 результатов по levelId, отсортированных по убыванию id (новые записи вначале)
     *
     * @param levelId id уровня
     * @return список результатов
     */
    public List<Result> getTopOfResultsByLevel(long levelId) {
        List<Result> foundResults = resultList.stream()
                .filter(entry ->
                        entry.getLevel().getId() == levelId)
                .sorted(Comparator.comparing(Result::getValue, Comparator.reverseOrder()))
                .limit(TOP_SIZE)
                .collect(Collectors.toList());
        return foundResults;
    }
}
