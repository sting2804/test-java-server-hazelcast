package model;

import java.io.Serializable;

public class Result implements Serializable {
    private long id;
    private Level level;
    private User user;
    private String value;

    public Result(long id, Level level, User user, String value) {
        this.id = id;
        this.level = level;
        this.user = user;
        this.value = value;
    }

    public Result(long id, Level level, User user) {
        this(id, level, user, null);
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
