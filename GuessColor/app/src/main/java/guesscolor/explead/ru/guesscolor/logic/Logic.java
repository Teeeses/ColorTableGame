package guesscolor.explead.ru.guesscolor.logic;

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
        int last_x = -1;
        int last_y = -1;
        for(int i = 0; i < clicks; i++) {
            int x, y;
            do {
                x = getRandom(0, number - 1);
                y = getRandom(0, number - 1);
            } while (last_x == x && last_y == y);
            last_x = x;
            last_y = y;
            clicksTable[x][y]++;
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
            return;
        }
        else if(current >= 1 && current <= 2) {
            number = 3;
            clicks = 2;
            return;
        }
        else if(current >= 3 && current <= 7) {
            number = 3;
        }
        else if(current >= 8 && current <= 30) {
            number = 4;
        }
        else if(current >= 31 && current <= 80) {
            number = 5;
        }
        else if(current >= 81 && current <= 200) {
            number = 6;
        }

        clicks = current/3 + 2;

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

    public boolean isEmptyMoves() {
        if(moves.size() == 0) {
            return true;
        }
        return false;
    }
}
