package guesscolor.explead.ru.guesscolor.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import guesscolor.explead.ru.guesscolor.App;
import guesscolor.explead.ru.guesscolor.Cell;
import guesscolor.explead.ru.guesscolor.R;
import guesscolor.explead.ru.guesscolor.logic.Logic;

/**
 * Created by develop on 03.05.2017.
 */

public class GameFragment extends Fragment {


    private Cell[][] views;
    private LinearLayout field;
    private int widthField;
    private RelativeLayout rootLayout;

    private Logic logic = new Logic();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        rootLayout = (RelativeLayout) view.findViewById(R.id.rootView);
        createField();
        createTable();

        rootLayout.setBackgroundColor(logic.generateBackgroundColor());

        return view;
    }

    private void createField() {
        field = new LinearLayout(getActivity());
        widthField = (int)App.getWidthScreen() - 30;
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
                View v = new View(getActivity());
                LinearLayout.LayoutParams viewParam = new LinearLayout.LayoutParams((widthField - margin*2*number)/number, (widthField - margin*2*number)/number);
                viewParam.setMargins(margin, margin, margin, margin);
                v.setLayoutParams(viewParam);
                v.setBackgroundColor(logic.getColor());
                lay.addView(v);
                views[i][j] = new Cell(v);
            }
            field.addView(lay);
        }

        views[logic.getX()][logic.getY()].getView().setBackgroundColor(logic.getDifferenceColor());

        for(int i = 0; i < number; i++) {
            for(int j = 0; j < number; j++) {
                final int x = i;
                final int y = j;
                views[i][j].getView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(x == logic.getX() && y == logic.getY()) {
                            nextLevel();
                        }
                    }
                });
            }
        }
    }

    private void nextLevel() {
        logic.nextLevel();
        rootLayout.setBackgroundColor(logic.generateBackgroundColor());
        createTable();

        Log.d("TAG", "Color: " + Integer.toString(logic.getColor()) + " Difference: " + Integer.toString(logic.getDifferenceColor()));
    }


}
