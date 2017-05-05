package guesscolor.explead.ru.guesscolor.fragment;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;

import guesscolor.explead.ru.guesscolor.MainActivity;
import guesscolor.explead.ru.guesscolor.app.App;
import guesscolor.explead.ru.guesscolor.beans.Cell;
import guesscolor.explead.ru.guesscolor.R;
import guesscolor.explead.ru.guesscolor.logic.Logic;

/**
 * Created by develop on 04.05.2017.
 */

public class GameFragment extends Fragment {


    private Cell[][] views;
    private LinearLayout field;
    private int widthField;
    private RelativeLayout rootLayout;

    private TextView btnMinus;
    private TextView btnPlus;

    private Logic logic;

    private SoundPool soundPool;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        logic = App.getLogic();
        logic.start(App.getLevel().getLevel());


        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPool.load(getActivity(), R.raw.one, 1);

        rootLayout = (RelativeLayout) view.findViewById(R.id.rootView);

        ImageView btnRestart = (ImageView) view.findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.restart();
                start();
            }
        });

        ImageView btnMenu = (ImageView) view.findViewById(R.id.btnMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btnMinus = (TextView) view.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.setStatusAction(Logic.ACTION_MINUS);
                btnMinus.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                btnPlus.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        btnPlus = (TextView) view.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.setStatusAction(Logic.ACTION_PLUS);
                btnMinus.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                btnPlus.setBackgroundColor(getContext().getResources().getColor(R.color.green));
            }
        });


        createField();
        start();

        rootLayout.setBackgroundColor(logic.getColors().getBackground());

        return view;
    }

    private void createField() {
        field = new LinearLayout(getActivity());
        widthField = (int) App.getWidthScreen() - 30;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(widthField, widthField);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        field.setLayoutParams(params);
        field.setOrientation(LinearLayout.VERTICAL);
        field.setBackgroundColor(Color.TRANSPARENT);

        rootLayout.addView(field);

    }

    public void createTable() {
        field.removeAllViews();
        int number = logic.getNumber();
        int margin = 4;
        views = new Cell[number][number];

        for(int i = 0; i < number; i++) {
            LinearLayout lay = new LinearLayout(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, widthField/number);
            lay.setOrientation(LinearLayout.HORIZONTAL);
            lay.setLayoutParams(params);
            for(int j = 0; j < number; j++) {
                TextView view = new TextView(getActivity());
                LinearLayout.LayoutParams viewParam = new LinearLayout.LayoutParams((widthField - margin*2*number)/number, (widthField - margin*2*number)/number);
                viewParam.setMargins(margin, margin, margin, margin);
                view.setLayoutParams(viewParam);
                view.setBackgroundColor(logic.getColors().getMainColor());
                view.setTextColor(Color.WHITE);
                view.setTextSize(28);
                view.setGravity(Gravity.CENTER);
                view.setText(Integer.toString(logic.getTableValue(i, j)));
                lay.addView(view);
                views[i][j] = new Cell(view);
            }
            field.addView(lay);
        }

        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                final int x = i;
                final int y = j;
                views[i][j].getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        soundPool.play(1, 0.5f, 0.5f, 1, 0, 1f);
                        update(x, y);
                        checkWin();
                    }
                });
            }
        }
    }

    private void checkWin() {
        if(logic.checkWin()) {
            logic.nextLevel();
            start();
            ((MainActivity)getActivity()).setCurrentEasyLevel(App.getLevel().getLevel());
            (getActivity()).onBackPressed();
        }
    }

    private void update(int x, int y) {
        logic.update(logic.getTable(), x, y, logic.getStatusAction());
        start();
    }

    public void start() {
        createTable();
        updateColors();
    }

    private void updateColors() {
        for(int i = 0; i < logic.getNumber(); i++) {
            for(int j = 0; j < logic.getNumber(); j++) {
                if(logic.getTableValue(i, j) == logic.getBigValue()) {
                    views[i][j].getView().setBackgroundColor(logic.getColors().getMainColor());
                } else if(logic.getTableValue(i, j) > logic.getBigValue()) {
                    views[i][j].getView().setBackgroundColor(MainActivity.getRes().getColor(R.color.dark_red));
                } else{
                    views[i][j].getView().setBackgroundColor(logic.getColors().getDifferentColor());
                }
            }
        }
    }

}
