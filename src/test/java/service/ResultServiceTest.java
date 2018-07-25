package service;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.hazelcast.client.test.TestHazelcastFactory;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import model.Level;
import model.Result;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ResultServiceTest {

    private HazelcastInstance hazelcastInstance;
    private ResultService resultService;

    private IList<User> userList;
    private IList<Level> levelList;
    private IList<Result> resultList;

    @Before
    public void setUp() {
        TestHazelcastFactory factory = new TestHazelcastFactory();
        hazelcastInstance = factory.newHazelcastInstance();
        userList = hazelcastInstance.getList("user");
        levelList = hazelcastInstance.getList("level");
        resultList = hazelcastInstance.getList("result");
        resultService = new ResultService(factory.newHazelcastClient());
    }

    @Test
    public void setUserResult() {
        Assert.assertEquals(0, userList.size() + levelList.size() + resultList.size());
        userList.add(new User(1, "user1"));
        levelList.add(new Level(1, "level_1"));
        resultList.add(new Result(1, levelList.get(0), userList.get(0), "before_test"));

        JsonObject obj = new JsonObject();
        obj.add("user_id", new JsonPrimitive(1));
        obj.add("level_id", new JsonPrimitive(1));
        obj.add("result", new JsonPrimitive("new_value"));
        Assert.assertEquals("before_test", resultList.get(0).getValue());
        Result result = resultService.setUserResult(obj);
        Assert.assertEquals("new_value", result.getValue());
    }

    @Test
    public void getUserTop() {
        Assert.assertEquals(0, userList.size() + levelList.size() + resultList.size());
        userList.add(new User(1, "user1"));
        for (int i = 0; i < 25; i++) {
            levelList.add(new Level(i, "level_" + i));
        }
        List<String> results = resultService.getUserTop(1);
        Assert.assertEquals(0, results.size());
        for (int i = 0; i < 50; i++) {
            resultList.add(new Result(i, levelList.get(i % 2 == 0 ? 1 : 2), userList.get(0), "value_" + i));
        }
        Assert.assertEquals(1, userList.size());
        Assert.assertEquals(25, levelList.size());
        Assert.assertEquals(50, resultList.size());
        results = resultService.getUserTop(1);
        Assert.assertEquals(20, results.size());
        List<String> expectedValues = resultList.stream()
                .sorted(Comparator.comparing(Result::getValue,Comparator.reverseOrder()))
                .map(Result::getValue)
                .limit(20)
                .collect(Collectors.toList());
        Assert.assertEquals(20, results.size());
        Assert.assertArrayEquals(expectedValues.toArray(), results.toArray());
    }

    @Test
    public void getTopOfResultsByLevel() {
        Assert.assertEquals(0, userList.size() + levelList.size() + resultList.size());
        userList.add(new User(1, "user1"));
        for (int i = 0; i < 25; i++) {
            levelList.add(new Level(i, "level_" + i));
        }
        List results = resultService.getTopOfResultsByLevel(1);
        Assert.assertEquals(0, results.size());
        for (int i = 0; i < 50; i++) {
            resultList.add(new Result(i, levelList.get(i % 2 == 0 ? 1 : 2), userList.get(0), "value_" + i));
        }
        Assert.assertEquals(1, userList.size());
        Assert.assertEquals(25, levelList.size());
        Assert.assertEquals(50, resultList.size());
        results = resultService.getUserTop(1);
        Assert.assertEquals(20, results.size());
    }
}