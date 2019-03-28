package project.ys.glasssystem_r1.ui.widget.qmui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;

import project.ys.glasssystem_r1.R;

public class QMUITopBarHelper {
    public static QMUIAlphaImageButton addBtnItem(Context context, int resId) {
        LayoutInflater inflater = LayoutInflater.from(context);
        QMUIAlphaImageButton btn = (QMUIAlphaImageButton) inflater.inflate(R.layout.item_topbar_btn, null);
        btn.setImageResource(resId);
        return btn;
    }



    public static LinearLayout addBtns(Context context, QMUIAlphaImageButton... btn) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout btns = (LinearLayout) inflater.inflate(R.layout.layout_topbar_btns, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        for (int i = 0; i < btn.length; i++) {
            btns.addView(btn[i], layoutParams);
        }
        return btns;
    }

    public static LinearLayout addViews(Context context, View... view) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout views = (LinearLayout) inflater.inflate(R.layout.layout_topbar_btns, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        for (int i = 0; i < view.length; i++) {
            views.addView(view[i], layoutParams);
        }
        return views;
    }


}
