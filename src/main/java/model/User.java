package model;

public class User {
    private String id;
    private Level level;
    private String result;

    public User(String id, Level level, String result) {
        this.id = id;
        this.level = level;
        this.result = result;
    }

    public User(String id, Level level) {
        this(id, level, null);
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }
}
