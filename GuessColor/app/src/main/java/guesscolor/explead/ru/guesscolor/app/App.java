package guesscolor.explead.ru.guesscolor.app;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import guesscolor.explead.ru.guesscolor.R;
import guesscolor.explead.ru.guesscolor.logic.Level;
import guesscolor.explead.ru.guesscolor.logic.Logic;

/**
 * Created by develop on 03.05.2017.
 */

public class App extends Application {

    private static float widthScreen;
    private static float heightScreen;
    private static Level level;
    private static Logic logic;

    private Tracker mTracker;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Получает счетчик {@link Tracker}, используемый по умолчанию для этого приложения {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // Чтобы включить ведение журнала отладки, используйте adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.analytics);
        }
        return mTracker;
    }

    public static void addLevel() {
        level.addLevel();
    }

    public static float getWidthScreen() {
        return widthScreen;
    }

    public static void setWidthScreen(float widthScreen) {
        App.widthScreen = widthScreen;
    }

    public static float getHeightScreen() {
        return heightScreen;
    }

    public static void setHeightScreen(float heightScreen) {
        App.heightScreen = heightScreen;
    }

    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        App.level = level;
    }

    public static Logic getLogic() {
        return logic;
    }

    public static void setLogic(Logic logic) {
        App.logic = logic;
    }
}
