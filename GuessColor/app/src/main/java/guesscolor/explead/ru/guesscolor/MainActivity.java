package guesscolor.explead.ru.guesscolor;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import guesscolor.explead.ru.guesscolor.app.App;
import guesscolor.explead.ru.guesscolor.fragment.GameFragment;
import guesscolor.explead.ru.guesscolor.fragment.LevelsFragment;
import guesscolor.explead.ru.guesscolor.logic.Logic;
import guesscolor.explead.ru.guesscolor.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private static Activity activity;
    private static Fragment fragment;
    private static Resources res;

    private static SharedPreferences sPref;

    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        res = getResources();
        sPref = getSharedPreferences(Utils.APP_PREFERENCES, MODE_PRIVATE);

        Logic logic = new Logic();
        App.setLogic(logic);


        App application = (App) getApplication();
        mTracker = application.getDefaultTracker();

        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        App.setWidthScreen(displaymetrics.widthPixels);
        App.setHeightScreen(displaymetrics.heightPixels);

        openLevelsFragment();
    }

    public void openGameFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new GameFragment();
        transaction.add(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void openLevelsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment = new LevelsFragment();
        transaction.add(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    public void setCurrentEasyLevel(int currentLevel) {
        System.out.println("Сохранение нового текущего уровня");
        SharedPreferences.Editor editor = sPref.edit();
        if(currentLevel == sPref.getInt(Utils.CURRENT_LEVEL, 1)) {
            editor.putInt(Utils.CURRENT_LEVEL, currentLevel + 1);
        }
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        fragment.onResume();
    }

    public static Activity getActivity() {
        return activity;
    }

    public static Fragment getFragment() {
        return fragment;
    }

    public static Resources getRes() {
        return res;
    }


    public static SharedPreferences getPref() {
        return sPref;
    }

    @Override
    public void onResume() {
        mTracker.setScreenName("Image~" + "MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
    }

    public void sendAction(String str) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction(str)
                .build());
    }

}
