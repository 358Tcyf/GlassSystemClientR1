package project.ys.glasssystem_r1;

import android.app.Application;
import android.app.NotificationManager;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.igexin.sdk.PushManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.mmkv.MMKV;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;

import es.dmoral.toasty.Toasty;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.data.dao.PushDao;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.service.getui.MyIntentService;
import project.ys.glasssystem_r1.service.getui.MyPushService;

import static project.ys.glasssystem_r1.data.DatabaseHelper.showDebugDBAddressLogToast;
import static project.ys.glasssystem_r1.util.utils.NotifyUtilsKt.notifyDefault;

@EApplication
public class CustomerApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        initFragmentation();
        initMMKV();
        initLogger();
        initDatabases();
        initGetuiPush();
    }


    @SystemService
    NotificationManager notificationManager;

    private static CustomerApp _instance;
    private DatabaseHelper helper;
    private UserBeanPlus mUser;//当前用户

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
//        Logger.d(cid);
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
     * 设置当前用户
     *
     * @param mUser 用户
     */
    public void setCurrentUser(@NonNull UserBeanPlus mUser) {
        this.mUser = mUser;
        MMKV user = MMKV.defaultMMKV();
        user.encode("userAccount", mUser.getNo());
        user.encode("userPassword", mUser.getPassword());
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
        }
        return mUser;
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
        Push push = JSON.parseObject(data, Push.class);
        Logger.d(push.toString());
        helper.insertPush(push);
        notifyDefault(this, "数据推送", push.getTitle());
    }


}
