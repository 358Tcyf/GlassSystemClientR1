package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.mvp.contract.AlarmContract;

public class AlarmModel implements AlarmContract.Model {


    private Context mContext;
    private DatabaseHelper helper;


    public AlarmModel() {
    }

    public AlarmModel(Context mContext) {
        this.mContext = mContext;
        helper = new DatabaseHelper(mContext);

    }

    @Override
    public List<Alarm> getAllAlarm(String receiver) {
        return helper.getAllAlarm(receiver);
    }

    @Override
    public void setRead(Alarm alarm) {
        helper.setAlarmRead(alarm);
    }
}
