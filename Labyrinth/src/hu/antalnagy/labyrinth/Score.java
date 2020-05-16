package hu.antalnagy.labyrinth;

class Score {
    private long time;
    private int level;
    private String name;

    Score(int level, long time, String name) {
        this.level = level;
        this.time = time;
        this.name = name;
    }

    public long getTime() {
        return time;
    }

    public int getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }
}
