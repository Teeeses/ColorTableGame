package guesscolor.explead.ru.guesscolor.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import guesscolor.explead.ru.guesscolor.app.App;
import guesscolor.explead.ru.guesscolor.MainActivity;
import guesscolor.explead.ru.guesscolor.R;
import guesscolor.explead.ru.guesscolor.beans.ButtonLevel;
import guesscolor.explead.ru.guesscolor.logic.Level;
import guesscolor.explead.ru.guesscolor.utils.Utils;


/**
 * Created by develop on 30.01.2017.
 */

public class GridAdapter extends BaseAdapter {

    ArrayList<ButtonLevel> array =  new ArrayList<>();
    private LayoutInflater lInflater;
    private ViewHolder viewHolder;

    private int widthCell;

    public GridAdapter(ArrayList<ButtonLevel> array){
        this.array.clear();
        this.array.addAll(array);
        lInflater = (LayoutInflater) MainActivity.getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        widthCell = (int)(App.getWidthScreen() - MainActivity.getActivity().getResources().getDimension(R.dimen.standard_margin)*8)/3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.item_level, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.levelLayout = (RelativeLayout) convertView.findViewById(R.id.levelLayout);
            viewHolder.levelLayout.setLayoutParams(new RelativeLayout.LayoutParams(widthCell, widthCell));
            viewHolder.ivLevel = convertView.findViewById(R.id.ivLevel);
            viewHolder.ivLock = (ImageView) convertView.findViewById(R.id.ivLock);
            viewHolder.tvLevel = (TextView) convertView.findViewById(R.id.tvLevel);

            viewHolder.tvLevel.setTypeface(Utils.getTypeFaceLevel());
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final ButtonLevel buttonLevel = (ButtonLevel) getItem(position);
        viewHolder.tvLevel.setText(Integer.toString(buttonLevel.getNumber()));
        if(buttonLevel.getStatus() == ButtonLevel.STATUS_CURRENT) {
            viewHolder.ivLevel.setBackgroundColor(Color.parseColor("#DF38B1"));
            viewHolder.ivLock.setVisibility(View.GONE);
            viewHolder.ivLevel.setVisibility(View.VISIBLE);
            viewHolder.tvLevel.setVisibility(View.VISIBLE);
        }
        if(buttonLevel.getStatus() == ButtonLevel.STATUS_CLOSE) {
            viewHolder.ivLevel.setBackgroundColor(Color.parseColor("#3E0470"));
            viewHolder.ivLock.setVisibility(View.VISIBLE);
            viewHolder.ivLevel.setVisibility(View.GONE);
            viewHolder.tvLevel.setVisibility(View.GONE);
        }
        if(buttonLevel.getStatus() == ButtonLevel.STATUS_OPEN) {
            viewHolder.ivLevel.setBackgroundColor(Color.parseColor("#AD66D5"));
            viewHolder.ivLock.setVisibility(View.GONE);
            viewHolder.ivLevel.setVisibility(View.VISIBLE);
            viewHolder.tvLevel.setVisibility(View.VISIBLE);
        }


        viewHolder.levelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(buttonLevel.getStatus() == ButtonLevel.STATUS_OPEN || buttonLevel.getStatus() == ButtonLevel.STATUS_CURRENT) {
                    App.setLevel(new Level(buttonLevel.getNumber()));
                    ((MainActivity) MainActivity.getActivity()).openGameFragment();
                    ((MainActivity) MainActivity.getActivity()).sendAction(Integer.toString(buttonLevel.getNumber()));
                }
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView tvLevel;
        View ivLevel;
        ImageView ivLock;
        RelativeLayout levelLayout;
    }
}