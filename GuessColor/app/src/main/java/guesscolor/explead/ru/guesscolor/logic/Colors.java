package guesscolor.explead.ru.guesscolor.logic;

/**
 * Created by develop on 05.05.2017.
 */

public class Colors {

    private int background;
    private int mainColor;
    private int differentColor;

    public Colors(int background, int mainColor, int differentColor) {
        this.background = background;
        this.mainColor = mainColor;
        this.differentColor = differentColor;
    }

    public int getBackground() {
        return background;
    }

    public int getMainColor() {
        return mainColor;
    }

    public int getDifferentColor() {
        return differentColor;
    }
}
