package guesscolor.explead.ru.guesscolor;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import guesscolor.explead.ru.guesscolor.app.App;
import guesscolor.explead.ru.guesscolor.fragment.GameFragment;
import guesscolor.explead.ru.guesscolor.utils.Utils;

/**
 * Created by develop on 05.05.2017.
 */

public class DialogWin extends Dialog {

    private Context context;

    public DialogWin(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_win);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        setCancelable(false);

        int width = (int)App.getWidthScreen();


        ImageView ivQueen = (ImageView) findViewById(R.id.ivQueen);
        TextView tvCenter = (TextView) findViewById(R.id.tvCenter);

        TextView tvMenu = (TextView) findViewById(R.id.tvMenu);
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(width/4, width/4);
        param1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.center);
        tvMenu.setLayoutParams(param1);


        TextView tvNext = (TextView) findViewById(R.id.tvNext);
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(width/4, width/4);
        param2.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.center);
        param2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tvNext.setLayoutParams(param2);

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams((int)(width/2.4f), (int)(width/2.4));
        tvCenter.setLayoutParams(p);


        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.queen);
        ivQueen.startAnimation(anim);
        tvNext.startAnimation(anim);
        tvMenu.startAnimation(anim);

        tvMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ((MainActivity)context).onBackPressed();
            }
        });
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.getFragment() instanceof GameFragment) {
                    ((GameFragment)MainActivity.getFragment()).nextLevel();
                    dismiss();
                }
            }
        });

        tvMenu.setTypeface(Utils.getTypeFaceLevel());
        tvNext.setTypeface(Utils.getTypeFaceLevel());
        tvCenter.setTypeface(Utils.getTypeFaceLevel());

        if(App.getLevel().getLevel() >= 200) {
            tvNext.setVisibility(View.INVISIBLE);
        }
    }

}
