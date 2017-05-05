package guesscolor.explead.ru.guesscolor.logic;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by develop on 05.05.2017.
 */

public class AllColors {

    private ArrayList<Colors> colors = new ArrayList<>();

    public AllColors() {
        colors.add(new Colors(Color.parseColor("#FFDD73"), Color.parseColor("#592680"), Color.parseColor("#06799F")));
    }

    public int getSize() {
        return colors.size();
    }

    public ArrayList<Colors> getColors() {
        return colors;
    }
}
