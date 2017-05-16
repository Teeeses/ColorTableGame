package guesscolor.explead.ru.guesscolor.fragment;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import guesscolor.explead.ru.guesscolor.DialogWin;
import guesscolor.explead.ru.guesscolor.MainActivity;
import guesscolor.explead.ru.guesscolor.app.App;
import guesscolor.explead.ru.guesscolor.beans.Cell;
import guesscolor.explead.ru.guesscolor.R;
import guesscolor.explead.ru.guesscolor.logic.Coordinate;
import guesscolor.explead.ru.guesscolor.logic.Logic;
import guesscolor.explead.ru.guesscolor.utils.Utils;

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
    private ImageView btnBack;

    private TextView tvLevel;
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

        tvLevel = (TextView) view.findViewById(R.id.tvLevel);
        tvLevel.setTypeface(Utils.getTypeFaceNumber());

        btnBack = (ImageView) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.deleteMove();
                start();
            }
        });

        ImageView btnRestart = (ImageView) view.findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.restart();
                start();
                startAroundAnimation();
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

        startAroundAnimation();

        rootLayout.setBackgroundColor(logic.getColors().getBackground());

        return view;
    }

    private void setTextLevel() {
        tvLevel.setText(String.format(getActivity().getResources().getString(R.string.level), App.getLevel().getLevel()));
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
        views = new Cell[number][number];
        int size = (widthField)/number;
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Resources res = getActivity().getResources();
        float sizeText = res.getDimensionPixelSize(R.dimen.standard_text);
        if(number == 3) {
            sizeText = res.getDimensionPixelSize(R.dimen.more_standard_text);
        }
        if(number == 4) {
            sizeText = res.getDimensionPixelSize(R.dimen.standard_text);
        }
        if(number == 5) {
            sizeText = res.getDimensionPixelSize(R.dimen.more_mini_text);
        }
        if(number == 6) {
            sizeText = res.getDimensionPixelSize(R.dimen.more_mini_text);
        }

        Typeface face = Utils.getTypeFaceLevel();


        for(int i = 0; i < number; i++) {
            LinearLayout lay = new LinearLayout(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, size);
            lay.setOrientation(LinearLayout.HORIZONTAL);
            lay.setLayoutParams(params);
            for(int j = 0; j < number; j++) {
                RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.cell, null, false);
                layout.setLayoutParams(new RelativeLayout.LayoutParams(size, size));

                TextView view = (TextView) layout.findViewById(R.id.cell);

                view.setTypeface(face);
                view.setTextSize(getContext().getResources().getDimensionPixelSize(R.dimen.standard_text));
                view.setBackgroundColor(logic.getColors().getMainColor());
                String str = Integer.toString(logic.getTableValue(i, j));
                view.setText(str);
                view.setTextSize(sizeText);
                lay.addView(layout);
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
                        logic.addMove(new Coordinate(x, y));
                        update(x, y);
                        checkWin();
                    }
                });
            }
        }
    }

    public void startAroundAnimation() {
        for(int i = 0; i < logic.getNumber(); i++) {
            for(int j = 0; j < logic.getNumber(); j++) {
                Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.around);
                views[i][j].getView().startAnimation(anim);
            }
        }
    }

    private void checkWin() {
        if(logic.checkWin()) {
            ((MainActivity)getActivity()).setCurrentEasyLevel(App.getLevel().getLevel());
            DialogWin dialog = new DialogWin(getContext());
            dialog.show();
            App.addLevel();
        }
    }

    private void update(int x, int y) {
        logic.update(logic.getTable(), x, y, logic.getStatusAction());
        start();
    }

    public void start() {
        createTable();
        updateColors();
        setTextLevel();
        setVisibilityButtonBack();
    }

    public void nextLevel() {
        logic.start(App.getLevel().getLevel());
        start();
        startAroundAnimation();
    }

    private void updateColors() {
        for(int i = 0; i < logic.getNumber(); i++) {
            for(int j = 0; j < logic.getNumber(); j++) {
                if(logic.getTableValue(i, j) == logic.getBigValue()) {
                    views[i][j].getView().setBackgroundColor(logic.getColors().getMainColor());
                } else if(logic.getTableValue(i, j) > logic.getBigValue()) {
                    views[i][j].getView().setBackgroundColor(MainActivity.getRes().getColor(R.color.dark_red));
                } else {
                    views[i][j].getView().setBackgroundColor(logic.getColors().getDifferentColor());
                }
            }
        }
    }

    public void setVisibilityButtonBack() {
        if(logic.isEmptyMoves())
            btnBack.setVisibility(View.INVISIBLE);
        else
            btnBack.setVisibility(View.VISIBLE);

    }

}
