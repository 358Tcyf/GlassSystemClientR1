package project.ys.glasssystem_r1.ui.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.WindowManager;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.fragment.LoginFragment;
import project.ys.glasssystem_r1.ui.fragment.LoginFragmentNew;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class LoginActivity extends SupportActivity {

    @AfterInject
    void afterInject() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    @AfterViews
    void afterViews() {
//        QMUIStatusBarHelper.translucent(getWindow());
        SupportFragment fragment = findFragment(LoginFragmentNew.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, LoginFragmentNew.newInstance());
        }
    }
}
