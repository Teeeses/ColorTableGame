package guesscolor.explead.ru.guesscolor.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;

import java.util.ArrayList;

import guesscolor.explead.ru.guesscolor.R;
import guesscolor.explead.ru.guesscolor.adapters.GridAdapter;
import guesscolor.explead.ru.guesscolor.app.App;
import guesscolor.explead.ru.guesscolor.beans.ButtonLevel;

/**
 * Created by develop on 05.05.2017.
 */

public class LevelsFragment extends Fragment {

    protected ArrayList<ButtonLevel> array = new ArrayList<>();
    protected GridView gvMain;
    protected GridAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_levels, container, false);

        gvMain = (GridView) view.findViewById(R.id.gvMain);
        createButtons(App.getLogic().getMany_levels());

        return view;
    }

    public void createButtons(int size) {
        this.array.clear();
        for (int i = 0; i < size; i++) {
            array.add(new ButtonLevel(i + 1));
        }
        adapter = new GridAdapter(array);
        gvMain.setAdapter(adapter);
        gvMain.setNumColumns(3);
    }

    public void refreshStatus() {
        for (int i = 0; i < array.size(); i++) {
            array.get(i).installStatus();
        }
    }


    @Override
    public void onResume() {
        refreshStatus();
        if (adapter != null) {
            //int current = MainActivity.getPref().getInt(Utils.CURRENT_LEVEL, 1);
            gvMain.smoothScrollToPosition((App.getLevel() == null ? 0 : App.getLevel().getLevel()));
            adapter.notifyDataSetChanged();
        }
        super.onResume();
    }


    public void startWrongAnimation(View view) {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.wrong);
        view.startAnimation(anim);
    }
}