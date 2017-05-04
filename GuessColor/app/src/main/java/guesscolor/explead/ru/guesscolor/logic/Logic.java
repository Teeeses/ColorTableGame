package guesscolor.explead.ru.guesscolor.logic;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by develop on 03.05.2017.
 */

public class Logic {

    private int level = 0;
    private int number;
    private int color;
    private int differenceColor;
    private int difference = 10;
    private int x;
    private int y;

    private int r;
    private int g;
    private int b;


    public Logic() {
        nextLevel();
    }

    public void nextLevel() {
        level++;
        number = findNumber();
        color = generateRandomColor();
        difference = (difference > 3 ? difference - 1 : 3);
        differenceColor = generateDifferenceColor(difference);
        x = getRandom(0, number-1);
        y = getRandom(0, number-1);
    }

    private int findNumber() {
        int size = 3 + level/10;
        return (size > 7 ? 7 : size);
    }

    public int getRandom(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt((max - min) + 1) + min;
    }

    public int generateRandomColor() {

        int r = (int)(255*(getRandom(0, 100-1)/100f));
        int g = (int)(255*(getRandom(0, 80)/100f));
        int b = (int)(255*(getRandom(0, 70)/100f));
        int color = Color.argb(255, r, g, b);
        return color;
    }

    public int generateBackgroundColor() {
        //int color = Color.argb(255, (int)(255*(24 + ((float)(74 - r))/100f)), (int)(255*(25 + ((float)(74- r))/100f)), (int)(255*(33 + ((float)(51 - r))/100f)));

        int color = Color.argb(255, 230, 210, 128);
        return color;
    }

    public int generateDifferenceColor(int value) {

        int _r = (r + getRandom(-value, value));
        if(_r > 255)
            _r = 255;
        if(_r < 0)
            _r = 0;

        int _g = (g + getRandom(-value, value));
        if(_g > 255)
            _g = 255;
        if(_g < 0)
            _g = 0;

        int _b = (b + getRandom(-value, value));
        if(_b > 255)
            _b = 255;
        if(_b < 0)
            _b = 0;

        int _color = Color.argb(255, _r, _g, _b);
        return _color;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getColor() {
        return color;
    }

    public int getDifferenceColor() {
        return differenceColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
