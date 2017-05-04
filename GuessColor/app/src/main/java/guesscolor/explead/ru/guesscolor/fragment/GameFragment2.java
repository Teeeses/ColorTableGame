package guesscolor.explead.ru.guesscolor.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import guesscolor.explead.ru.guesscolor.App;
import guesscolor.explead.ru.guesscolor.Cell;
import guesscolor.explead.ru.guesscolor.R;
import guesscolor.explead.ru.guesscolor.logic.LogicNumber;

/**
 * Created by develop on 04.05.2017.
 */

public class GameFragment2 extends Fragment {


    private Cell[][] views;
    private LinearLayout field;
    private int widthField;
    private RelativeLayout rootLayout;

    private TextView btnMinus;
    private TextView btnPlus;

    private LogicNumber logic = new LogicNumber();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        rootLayout = (RelativeLayout) view.findViewById(R.id.rootView);

        ImageView btnRestart = (ImageView) view.findViewById(R.id.btnRestart);
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.restart();
                createTable();
            }
        });

        btnMinus = (TextView) view.findViewById(R.id.btnMinus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.setStatusAction(LogicNumber.ACTION_MINUS);
                btnMinus.setBackgroundColor(getContext().getResources().getColor(R.color.green));
                btnPlus.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
            }
        });
        btnPlus = (TextView) view.findViewById(R.id.btnPlus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logic.setStatusAction(LogicNumber.ACTION_PLUS);
                btnMinus.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimaryDark));
                btnPlus.setBackgroundColor(getContext().getResources().getColor(R.color.green));
            }
        });


        createField();
        createTable();

        rootLayout.setBackgroundColor(logic.generateBackgroundColor());

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
                view.setBackgroundColor(logic.getColor());
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
            createTable();
        }
    }

    private void update(int x, int y) {
        logic.update(x, y, logic.getStatusAction());
        createTable();
    }


}
