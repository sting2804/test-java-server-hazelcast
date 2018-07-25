package model;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", level=" + level +
                ", user=" + user +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return id == result.id &&
                Objects.equals(level, result.level) &&
                Objects.equals(user, result.user) &&
                Objects.equals(value, result.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, level, user, value);
    }
}
