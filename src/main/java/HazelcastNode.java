import com.hazelcast.core.*;
import model.Level;
import model.Result;
import model.User;

public class HazelcastNode {

    public static void main(String[] args) {
        HazelcastInstance server = Hazelcast.newHazelcastInstance();
        server.getClientService().addClientListener(new ClientListener() {
            @Override
            public void clientConnected(Client client) {
                System.out.println(client.getClientType() + ", " + client.getUuid() + " is connected.");
            }

            @Override
            public void clientDisconnected(Client client) {
                System.out.println(client.getClientType() + ", " + client.getUuid() + " is disconnected.");
            }
        });
        IList<User> userList = server.getList("user");
        IList<Level> levelList = server.getList("level");
        IList<Result> resultList = server.getList("result");
        System.out.println("resMap size: " + resultList.size());
        IdGenerator idGenerator = server.getIdGenerator("newid");
        for (int i = 0; i < 5; i++) {
            levelList.add(new Level(idGenerator.newId(), "level_" + i));
        }
        for (int i = 0; i < 10; i++) {
            userList.add(new User(idGenerator.newId(), "name_" + i));
        }
        for (int i = 0; i < 21; i++) {
            int userIdParam = 10 - i / 2;
            if (userIdParam >= 10)
                userIdParam = 5;
            resultList.add(new Result(idGenerator.newId(), levelList.get(i % 2 == 0 ? 1 : 2), userList.get(userIdParam), "result_" + i));
        }
        userList.forEach(System.out::println);
        levelList.forEach(System.out::println);
        resultList.forEach(System.out::println);
    }
}