package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
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
    public void getList(String account, int limit) {
        List<Alarm> alarmList = alarmModel.getAllAlarm(account,limit);
        if (alarmList.size() == 0) {
            alarmView.refreshFail();
        } else {
            alarmView.setList((ArrayList) alarmList);
        }
    }

    @Override
    public void sortList(String account, int limit, String tag) {
        List<Alarm> pushList = alarmModel.sortAlarmList(account,limit, tag);
        if (pushList.size() == 0) {
            alarmView.refreshFail();
        } else {
            alarmView.setList((ArrayList) pushList);
        }
    }

    @Override
    public void setRead(Alarm alarm) {
        alarmModel.setRead(alarm);
    }

    @Override
    public void deleteOne(int id) {
        alarmModel.deleteOne(id);
    }

    @Override
    public int getTotal(String account) {
        List<Alarm> alarmList = alarmModel.getAllAlarm(account);
        return alarmList.size();
    }

    @Override
    public void upload(String no) {
        alarmModel.upload(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {

            }

            @Override
            public void onFailed(String errorMsg) {
                alarmView.showErrorMsg(errorMsg);
            }
        });
    }
    @Override
    public void download(String no) {
        alarmModel.download(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {

            }

            @Override
            public void onFailed(String errorMsg) {
                alarmView.showErrorMsg(errorMsg);
            }
        });
    }
}
