package model;


import java.io.Serializable;
import java.util.Objects;

public class Level implements Serializable {
    private long id;
    private String name;

    public Level(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Level{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return id == level.id &&
                Objects.equals(name, level.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
