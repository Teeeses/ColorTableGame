package guesscolor.explead.ru.guesscolor.logic;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by develop on 04.05.2017.
 */

public class Logic {

    public final static int ACTION_PLUS = 0, ACTION_MINUS = 1;
    private int statusAction = ACTION_PLUS;

    private ArrayList<int[][]> allLevels = new ArrayList<>();
    private ArrayList<Coordinate> moves = new ArrayList<>();

    private int table[][];

    private int bigValue = 13;
    private int clicks;
    private int number;

    private int level;

    private AllColors allColors = new AllColors();
    private Colors colors;

    private int many_levels = 200;

    public Logic() {
        createAllLevels();
    }


    private void createAllLevels() {
        for(int i = 0; i < many_levels; i++) {
            findLevel(i);
            allLevels.add(createLevel());
        }
    }

    public void start(int level) {
        this.level = level-1;
        clearMoves();
        findLevel(level-1);
        table = new int[number][number];
        colors = allColors.getColors().get(getRandom(0, 0));
        int field[][] = allLevels.get(level-1);
        for(int i = 0; i < field.length; i++) {
            for(int j = 0; j < field.length; j++) {
                table[i][j] = field[i][j];
            }
        }
    }

    public int getRandom(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt((max - min) + 1) + min;
    }


    public int[][] createLevel() {
        int[][] table = new int[number][number];
        int[][] clicksTable = new int[number][number];
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                clicksTable[i][j] = 0;
                table[i][j] = bigValue;
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
                for(int k = 0; k < clicksTable[i][j]; k++) {
                    update(table, i, j, (statusAction == ACTION_PLUS ? ACTION_MINUS : ACTION_PLUS));
                }
            }
        }
        return table;
    }

    public void nextLevel() {
        setStatusAction(ACTION_PLUS);
        level++;
        start(level);
    }

    public void restart() {
        clearMoves();
        int field[][] = allLevels.get(level);
        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                table[i][j] = field[i][j];
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

    private void findLevel(int current) {
        if(current >= 0 && current <= 0) {
            number = 3;
            clicks = 1;
        }
        else if(current >= 1 && current <= 3) {
            number = 3;
            clicks = 2;
        }
        else if(current >= 4 && current <= 7) {
            number = 3;
            clicks = 3;
        }
        else if(current >= 8 && current <= 10) {
            number = 4;
            clicks = 4;
        }
        else if(current >= 11 && current <= 13) {
            number = 4;
            clicks = 5;
        }
        else if(current >= 14 && current <= 17) {
            number = 4;
            clicks = 6;
        }
        else if(current >= 18 && current <= 21) {
            number = 4;
            clicks = 7;
        }
        else if(current >= 22 && current <= 25) {
            number = 4;
            clicks = 7;
        }
        else if(current >= 26 && current <= 30) {
            number = 4;
            clicks = 8;
        }
        else if(current >= 31 && current <= 36) {
            number = 4;
            clicks = 9;
        }

        else if(current >= 37 && current <= 42) {
            number = 4;
            clicks = 10;
        }
        else if(current >= 43 && current <= 50) {
            number = 5;
            clicks = 11;
        }
        else if(current >= 51 && current <= 60) {
            number = 5;
            clicks = 12;
        }
        else if(current >= 61 && current <= 70) {
            number = 5;
            clicks = 13;
        }
        else if(current >= 71 && current <= 80) {
            number = 5;
            clicks = 14;
        }
        else if(current >= 81 && current <= 90) {
            number = 6;
            clicks = 15;
        }
        else if(current >= 91 && current <= 100) {
            number = 6;
            clicks = 16;
        }
        else if(current >= 101 && current <= 106) {
            number = 6;
            clicks = 17;
        }
        else if(current >= 107 && current <= 112) {
            number = 6;
            clicks = 18;
        }
        else if(current >= 113 && current <= 118) {
            number = 6;
            clicks = 19;
        }
        else if(current >= 119 && current <= 125) {
            number = 6;
            clicks = 20;
        }
        else if(current >= 126 && current <= 132) {
            number = 6;
            clicks = 21;
        }
        else if(current >= 133 && current <= 138) {
            number = 6;
            clicks = 22;
        }
        else if(current >= 139 && current <= 145) {
            number = 6;
            clicks = 23;
        }
        else if(current >= 146 && current <= 152) {
            number = 6;
            clicks = 24;
        }
        else if(current >= 153 && current <= 158) {
            number = 6;
            clicks = 25;
        }
        else if(current >= 159 && current <= 164) {
            number = 6;
            clicks = 26;
        }
        else if(current >= 165 && current <= 170) {
            number = 6;
            clicks = 27;
        }
        else if(current >= 171 && current <= 176) {
            number = 6;
            clicks = 28;
        }
        else if(current >= 177 && current <= 184) {
            number = 6;
            clicks = 29;
        }
        else if(current >= 185 && current <= 188) {
            number = 6;
            clicks = 30;
        }
        else if(current >= 189 && current <= 192) {
            number = 6;
            clicks = 31;
        }
        else if(current >= 193 && current <= 196) {
            number = 6;
            clicks = 32;
        }
        else if(current >= 197 && current <= 200) {
            number = 6;
            clicks = 33;
        }

    }

    public void update(int[][] table, int x, int y, int statusAction) {
        if(statusAction == ACTION_MINUS) {
            if (x != 0) {
                if(table[x - 1][y] != 0)
                    table[x - 1][y]--;
            }
            if (x != number - 1) {
                if(table[x + 1][y] != 0)
                    table[x + 1][y]--;
            }
            if (y != 0) {
                if(table[x][y - 1] != 0)
                    table[x][y - 1]--;
            }
            if (y != number - 1) {
                if(table[x][y + 1] != 0)
                    table[x][y + 1]--;
            }
        }
        if(statusAction == ACTION_PLUS) {
            if (x != 0) {
                if(table[x - 1][y] != 20)
                    table[x - 1][y]++;
            }
            if (x != number - 1) {
                if(table[x + 1][y] != 20)
                    table[x + 1][y]++;
            }
            if (y != 0) {
                if(table[x][y - 1] != 20)
                    table[x][y - 1]++;
            }
            if (y != number - 1) {
                if(table[x][y + 1] != 20)
                    table[x][y + 1]++;
            }
        }
    }

    public int getNumber() {
        return number;
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

    public int[][] getTable() {
        return table;
    }

    public Colors getColors() {
        return colors;
    }

    public int getBigValue() {
        return bigValue;
    }

    public int getMany_levels() {
        return many_levels;
    }

    public void addMove(Coordinate coordinate) {
        moves.add(coordinate);
    }

    public void deleteMove() {
        if(moves.size() != 0) {
            update(table, moves.get(moves.size()-1).getX(), moves.get(moves.size()-1).getY(), ACTION_MINUS);
            moves.remove(moves.size()-1);
        }
    }

    public void clearMoves() {
        moves.clear();
    }
}
