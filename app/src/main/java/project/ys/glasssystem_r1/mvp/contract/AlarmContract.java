package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Alarm;

public interface AlarmContract {
    interface Model {
        List<Alarm> getAllAlarm(String receiver);
        List<Alarm> getAllAlarm(String receiver, int limit);
        List<Alarm> sortAlarmList(String receiver, int limit, String tag);
        void setRead(Alarm alarm);
        void deleteOne(int id);
    }

    interface View {
        void refreshView();

        void refreshFail();

        void showErrorMsg(String errorMsg);

        void setList(ArrayList list);
    }

    interface Presenter {
        void getList(String account,int limit);
        void sortList(String account,int limit, String tag);
        void setRead(Alarm alarm);
        void deleteOne(int id);

        int getTotal(String account);
    }
}