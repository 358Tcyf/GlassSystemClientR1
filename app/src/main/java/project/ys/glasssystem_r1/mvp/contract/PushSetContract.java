package project.ys.glasssystem_r1.mvp.contract;

import java.util.List;

import project.ys.glasssystem_r1.data.bean.AlarmTag;
import project.ys.glasssystem_r1.data.bean.PushSet;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;

public interface PushSetContract {
    interface Model {
        void getTags(String no, OnHttpCallBack<RetResult> callBack);

        void updateTags(String no, List<String> tags, OnHttpCallBack<RetResult> callBack);

        void getSets(String no, OnHttpCallBack<RetResult> callBack);

        void updateSets(String no, PushSet set, OnHttpCallBack<RetResult> callBack);

        void getAlarmTags(String no, OnHttpCallBack<RetResult> callBack);

        void uploadAlarmTags(String no, List<AlarmTag> alarmTags, OnHttpCallBack<RetResult> callBack);
    }

    interface View {
        void showTagsChoices(List<Integer> checks);

        void setAlarmTags(List<AlarmTag> alarmTags);

        void showErrorMsg(String errorMsg);

        void showSuccess();
    }

    interface Presenter {
        void getTags(String no);

        void updateTags(String no, List<String> tags);

        void getSets(String no);

        void updateSets(String no, PushSet set);

        void getAlarmTags(String no);

        void uploadAlarmTags(String no, List<AlarmTag> alarmTags);
    }
}
