package project.ys.glasssystem_r1.mvp.contract;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Alarm;

public interface AlarmContract {
    interface Model {
        List<Alarm> getAllAlarm(String receiver);

        void setRead(Alarm alarm);
    }

    interface View {
        void refreshView();

        void refreshFail();

        void showErrorMsg(String errorMsg);

        void setList(ArrayList list);
    }

    interface Presenter {
        void getList(String account);

        void setRead(Alarm alarm);
    }
}
