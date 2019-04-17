package project.ys.glasssystem_r1;

import android.app.Activity;
import android.app.Application;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.igexin.sdk.PushManager;
import com.mob.MobSDK;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

import es.dmoral.toasty.Toasty;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.bean.PushSet;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.data.dao.PushDao;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.service.getui.MyIntentService;
import project.ys.glasssystem_r1.service.getui.MyPushService;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.data.DatabaseHelper.showDebugDBAddressLogToast;
import static project.ys.glasssystem_r1.util.utils.NotifyUtilsKt.notifyDefault;

@EApplication
public class CustomerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initFragmentation();
        initGlobeActivity();
        initMMKV();
        initLogger();
        initDatabases();
        initGetuiPush();
        initMob();
    }


    @SystemService
    NotificationManager notificationManager;

    private static CustomerApp _instance;
    private Activity app_activity = null;
    private DatabaseHelper helper;
    private UserBeanPlus mUser;//当前用户
    private PushSet mPushSet;//当前用户

    private void initGlobeActivity() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                app_activity = activity;
                Log.e("onActivityCreated===", app_activity + "");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                app_activity = activity;
                Log.e("onActivityDestroyed===", app_activity + "");
            }

            /** Unused implementation **/
            @Override
            public void onActivityStarted(Activity activity) {
                app_activity = activity;
                Log.e("onActivityStarted===", app_activity + "");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                app_activity = activity;
                Log.e("onActivityResumed===", app_activity + "");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                app_activity = activity;
                Log.e("onActivityPaused===", app_activity + "");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                app_activity = activity;
                Log.e("onActivityStopped===", app_activity + "");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }
        });
    }

    private void initFragmentation() {
        Fragmentation.builder()
                // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
                /**
                 * 可以获取到{@link me.yokeyword.fragmentation.exception.AfterSaveStateTransactionWarning}
                 * 在遇到After onSaveInstanceState时，不会抛出异常，会回调到下面的ExceptionHandler
                 */
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();
    }

    private void initMMKV() {
        MMKV.initialize(this);
    }

    private void initLogger() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void initGetuiPush() {
        PushManager.getInstance().initialize(this.getApplicationContext(), MyPushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), MyIntentService.class);
        String cid = PushManager.getInstance().getClientid(this);
    }

    public void setGetuiAlias() {
        if (mUser.getNo() != null && !isEmpty(mUser.getNo())) {
            PushManager.getInstance().bindAlias(this, mUser.getNo());
        }
    }

    private void initMob() {
        MobSDK.init(this);
    }

    private void initDatabases() {
        helper = new DatabaseHelper(this);
        showDebugDBAddressLogToast(this);
    }

    public PushDao getPushDao() {
        return helper.getPushDao();
    }

    public void showToast(@NonNull String message) {
        Toasty.normal(this, message).show();
    }

    public static CustomerApp getInstance() {
        return _instance;
    }

    /**
     * 公开方法，外部可通过 MyApplication.getInstance().getCurrentActivity() 获取到当前最上层的activity
     */
    public Activity getCurrentActivity() {
        return app_activity;
    }

    /**
     * 设置当前用户
     *
     * @param mUser 用户
     */
    public void setCurrentUser(@NonNull UserBeanPlus mUser) {
        this.mUser = mUser;
        MMKV user = MMKV.defaultMMKV();
        user.encode("userAccount", mUser.getNo());
        user.encode("userPassword", mUser.getPassword());
        setGetuiAlias();
    }


    /**
     * 获取当前用户
     *
     * @return 用户
     */
    public UserBeanPlus getCurrentUser() {
        if (mUser == null) {
            MMKV user = MMKV.defaultMMKV();
            String account = user.decodeString("userAccount");
            String password = user.decodeString("userPassword");
            mUser = new UserBeanPlus(account, password);
            setGetuiAlias();
        }
        if (isEmpty(mUser.getNo()))
            return null;
        return mUser;
    }


    /**
     * 用户设置
     */
    public void setPushSet(@NonNull PushSet mPushSet) {
        this.mPushSet = mPushSet;
        MMKV set = MMKV.defaultMMKV();
        set.encode("commonSwitch", mPushSet.isPushSwitch());
        set.encode("sound", mPushSet.isSound());
        set.encode("vibrate", mPushSet.isVibrate());
        set.encode("flags", mPushSet.isFlags());
        set.encode("pushSwitch", mPushSet.isPushSwitch());
        set.encode("time", mPushSet.getTime());
        set.encode("alarmSwitch", mPushSet.isAlarmSwitch());
        set.encode("start", mPushSet.getStart());
        set.encode("end", mPushSet.getEnd());
    }


    /**
     * 获取当前设置
     */
    public PushSet getPushSet() {
        if (mPushSet == null) {
            MMKV set = MMKV.defaultMMKV();
            Boolean pushSwitch = set.decodeBool("pushSwitch", true);
            int time = set.decodeInt("time", 1);
            Boolean alarmSwitch = set.decodeBool("alarmSwitch", false);
            long start = set.decodeLong("start", 0);
            long end = set.decodeLong("end", 0);
            Boolean commonSwitch = set.decodeBool("commonSwitch", true);
            Boolean sound = set.decodeBool("sound", true);
            Boolean vibrate = set.decodeBool("vibrate", true);
            Boolean flags = set.decodeBool("flags", true);
            mPushSet = new PushSet(commonSwitch, sound, vibrate, flags, pushSwitch, time, alarmSwitch, start, end);
        }
        return mPushSet;
    }


    /**
     * 退出当前用户
     */
    public void exitCurrentUser() {
        MMKV user = MMKV.defaultMMKV();
        user.remove("userAccount");
        user.remove("userPassword");
        mUser = null;
    }


    public void sendMessage(String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        if (jsonObject.containsKey("pushUuid")) {
            Push push = JSON.parseObject(data, Push.class);
            helper.insertPush(push);
            EventBusActivityScope.getDefault(getCurrentActivity()).post(new RefreshListEvent());
            notifyDefault(this, "数据推送", push.getTitle());
        } else if (jsonObject.containsKey("alarmUuid")) {
            Alarm alarm = JSON.parseObject(data, Alarm.class);
            helper.insertAlarm(alarm);
            EventBusActivityScope.getDefault(getCurrentActivity()).post(new RefreshListEvent());
            notifyDefault(this, "数据推送", alarm.getTitle());
        } else {
            Logger.d("不存在Uuid");
        }
    }


}
