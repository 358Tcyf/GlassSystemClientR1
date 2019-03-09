package project.ys.glasssystem_r1.ui.activity;

import android.annotation.SuppressLint;

import com.igexin.sdk.PushManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.service.getui.MyIntentService;
import project.ys.glasssystem_r1.service.getui.MyPushService;
import project.ys.glasssystem_r1.ui.fragment.HomeFragment;

import static project.ys.glasssystem_r1.data.DatabaseHelper.showDebugDBAddressLogToast;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class HomeActivity extends SupportActivity {

    @AfterInject
    void afterInject() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @AfterViews
    void afterViews() {

        initPushManager();
        showDebugDBAddressLogToast(this);
        SupportFragment fragment = findFragment(HomeFragment.class);
        if (fragment == null) {
            loadRootFragment(R.id.fl_container, HomeFragment.newInstance("S0001"));
        }
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }


    private void initPushManager() {
        PushManager.getInstance().initialize(this.getApplicationContext(), MyPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyIntentService.class);
        String cid = PushManager.getInstance().getClientid(this);
//        Logger.i(cid);
    }
}
