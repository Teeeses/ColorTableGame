package guesscolor.explead.ru.guesscolor.logic;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

/**
 * Created by develop on 04.05.2017.
 */

public class LogicNumber {

    public final static int ACTION_PLUS = 0, ACTION_MINUS = 1;
    private int statusAction = ACTION_PLUS;
    private int bigValue;
    private int cliks;
    private int table[][];
    private int startTable[][];
    private int level = 0;
    private int number;
    private int color;

    public LogicNumber() {
        number = 3;
        cliks = 1;
        bigValue = 5;
        createLevel(cliks);
        color = generateRandomColor();
    }

    public int getRandom(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt((max - min) + 1) + min;
    }

    private void createTable() {
        table = new int[number][number];
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                int value = getRandom(0, 10);
                table[i][j] = value;
            }
        }
        saveStartLevel();
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

    public int getLevel() {
        return level;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    public int getTableValue(int i, int j) {
        return table[i][j];
    }

    public int getStatusAction() {
        return statusAction;
    }

    public void setStatusAction(int statusAction) {
        this.statusAction = statusAction;
    }

    public void createLevel(int clicks) {
        table = new int[number][number];
        startTable = new int[number][number];
        int[][] clicksTable = new int[number][number];
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                clicksTable[i][j] = 0;
                table[i][j] = bigValue;
                startTable[i][j] = bigValue;
            }
        }
        for(int i = 0; i < clicks; i++) {
            int x = getRandom(0, number-1);
            int y = getRandom(0, number-1);
            clicksTable[x][y]++;
            Log.d("TAG", "x: " + Integer.toString(x) + " y: " + Integer.toString(y));
        }
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                if(clicksTable[i][j] != 0) {
                    update(i, j, (statusAction == ACTION_PLUS ? ACTION_MINUS : ACTION_PLUS));
                }
            }
        }
        saveStartLevel();
    }

    public void nextLevel() {
        setStatusAction(ACTION_PLUS);
        cliks++;
        number = 3 + cliks/3;
        createLevel(cliks);
    }

    public void restart() {
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                table[i][j] = startTable[i][j];
            }
        }
    }

    private void saveStartLevel() {
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                startTable[i][j] = table[i][j];
            }
        }
    }


    public boolean checkWin() {
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                if(table[i][j] != bigValue) {
                    return false;
                }
            }
        }
        return true;
    }

    public void update(int x, int y, int statusAction) {
        if(statusAction == ACTION_MINUS) {
            if (x != 0) {
                table[x - 1][y]--;
            }
            if (x != number - 1) {
                table[x + 1][y]--;
            }
            if (y != 0) {
                table[x][y - 1]--;
            }
            if (y != number - 1) {
                table[x][y + 1]--;
            }
        }
        if(statusAction == ACTION_PLUS) {
            if (x != 0) {
                table[x - 1][y]++;
            }
            if (x != number - 1) {
                table[x + 1][y]++;
            }
            if (y != 0) {
                table[x][y - 1]++;
            }
            if (y != number - 1) {
                table[x][y + 1]++;
            }
        }
    }
}
