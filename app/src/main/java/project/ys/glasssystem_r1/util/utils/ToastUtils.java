package project.ys.glasssystem_r1.util.utils;

import android.app.Activity;

import es.dmoral.toasty.Toasty;
import project.ys.glasssystem_r1.R;

public class ToastUtils {

    public static void showExitToast(Activity mActivity) {
        Toasty.normal(mActivity, mActivity.getString(R.string.againExit)).show();
    }

    public static void showNormalToast(Activity mActivity,String msg) {
        Toasty.normal(mActivity, msg).show();
    }
}
