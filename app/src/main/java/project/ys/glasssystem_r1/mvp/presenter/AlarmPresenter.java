package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.AlarmContract;
import project.ys.glasssystem_r1.mvp.model.AlarmModel;

public class AlarmPresenter implements AlarmContract.Presenter {
    private AlarmContract.View alarmView;
    private AlarmModel alarmModel;
    private Context mContext;

    public AlarmPresenter(AlarmContract.View alarmView) {
        this.alarmView = alarmView;
        alarmModel = new AlarmModel();
    }

    public AlarmPresenter(AlarmContract.View alarmView, Context mContext) {
        this.alarmView = alarmView;
        this.mContext = mContext;
        alarmModel = new AlarmModel(mContext);
    }

    @Override
    public void getList(String account) {
        List<Alarm> alarmList = alarmModel.getAllAlarm(account);
        if (alarmList.size() == 0) {
            alarmView.refreshFail();
        } else {
            alarmView.setList((ArrayList) alarmList);
        }
    }

    @Override
    public void setRead(Alarm alarm) {
        alarmModel.setRead(alarm);
    }

}
