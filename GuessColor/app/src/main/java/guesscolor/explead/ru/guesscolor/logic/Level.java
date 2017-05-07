package guesscolor.explead.ru.guesscolor.logic;

/**
 * Created by develop on 20.01.2017.
 */

public class Level {

    public static int EASY = 0, MEDIUM = 1, HARD = 2, VERY_HARD = 3;
    private int complexity;

    private int level;

    public Level(int level) {
        this.level = level;
    }

    public int getComplexity() {
        return complexity;
    }

    public int getLevel() {
        return level;
    }

    public void addLevel() {
        level++;
    }
}
