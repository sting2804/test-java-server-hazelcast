package hazelcast;

import com.hazelcast.config.Config;
import com.hazelcast.core.*;
import model.Level;
import model.Result;
import model.User;

public class ServerNode {

    public static void main(String[] args) {
        Config config = new Config();
        config.getListConfig("user").setMaxSize(20);
        config.getListConfig("level").setMaxSize(20);
        config.getListConfig("result").setMaxSize(20);

        HazelcastInstance server = Hazelcast.newHazelcastInstance(config);
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
            resultList.add(new Result(idGenerator.newId(), levelList.get(i % 2 == 0 ? 1 : 2), userList.get(10 - i / 2)));
        }
    }
}