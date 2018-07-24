package model;

import java.io.Serializable;

public class Result implements Serializable{
    private long id;
    private Level level;
    private User user;

    public Result(long id, Level level, User user) {
        this.id = id;
        this.level = level;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public long getId() {
        return id;
    }
}
