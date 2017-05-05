package guesscolor.explead.ru.guesscolor.utils;

import android.graphics.Typeface;

import guesscolor.explead.ru.guesscolor.MainActivity;

/**
 * Created by develop on 05.05.2017.
 */

public class Utils {


    public final static String CURRENT_LEVEL = "current_level";
    public static final String APP_PREFERENCES = "mysettings";

    public static Typeface getTypeFaceLevel() {
        return Typeface.createFromAsset(MainActivity.getActivity().getAssets(),"fonts/level_personal.ttf");
    }
}
