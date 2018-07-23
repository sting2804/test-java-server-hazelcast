package hazelcast;

import java.util.Map;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IdGenerator;
import model.Level;
import model.User;

public class ServerNode {

    public static void main(String[] args) {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();
        Map<Long, User> userMap = hazelcastInstance.getMap("user");
        Map<Long, Level> levelMap = hazelcastInstance.getMap("level");
        IdGenerator idGenerator = hazelcastInstance.getIdGenerator("newid");
        for (int i = 0; i < 5; i++) {
            long id = idGenerator.newId();
            levelMap.put(id, new Level(""+id,"level_" + i));
        }
        for (int i = 0; i < 10; i++) {
            long id = idGenerator.newId();
            userMap.put(id, new User(""+id,levelMap.get(1)));
        }
    }
}