package project.ys.glasssystem_r1.service.getui;

import android.content.Context;
import android.os.Message;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import static com.igexin.sdk.PushManager.getInstance;

import project.ys.glasssystem_r1.CustomerApp;

public class MyIntentService extends GTIntentService {
    public MyIntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
//        Logger.d("onReceiveServicePid -> " + pid, TAG);

    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage message) {
        // 透传消息的处理，详看SDK demo
        byte[] payload = message.getPayload();
        String appid = message.getAppid();
        String taskid = message.getTaskId();
        String messageid = message.getMessageId();
        String pkg = message.getPkgName();
        Log.e(TAG, "receiver payload = null");
        String cid = message.getClientId();

        // 第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
        boolean result = getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
        if (payload == null) {
        } else {
        }
        String data = new String(payload);
        Log.d(TAG, "receiver payload = " + data);
        try {

            sendMessage(data, 0);//这里调用方法 将透传消息发送给App类进行处理
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
//        Logger.d("onReceiveClientId -> " + "clientid = " + clientid, TAG);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
//        Logger.d("onReceiveOnlineState -> " + (online ? "online" : "offline"), TAG);

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
//        Logger.d("onReceiveCommandResult -> " + cmdMessage, TAG);


    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage message) {
//        Logger.d("onNotificationMessageArrived -> " + "appid = " + message.getAppid() + "\ntaskid = " + message.getTaskId() + "\nmessageid = "
//                + message.getMessageId() + "\npkg = " + message.getPkgName() + "\ncid = " + message.getClientId() + "\ntitle = "
//                + message.getTitle() + "\ncontent = " + message.getContent(), TAG);
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage message) {
//        Logger.d("onNotificationMessageClicked -> " + "appid = " + message.getAppid() + "\ntaskid = " + message.getTaskId() + "\nmessageid = "
//                + message.getMessageId() + "\npkg = " + message.getPkgName() + "\ncid = " + message.getClientId() + "\ntitle = "
//                + message.getTitle() + "\ncontent = " + message.getContent(), TAG);
    }

    private void sendMessage(String data, int what) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.obj = data;
        CustomerApp.getInstance().sendMessage(data);
    }
}
