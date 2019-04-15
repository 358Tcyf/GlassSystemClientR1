package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.http.HttpContract;
import project.ys.glasssystem_r1.http.HttpFeedBackUtil;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.http.RetrofitUtils;
import project.ys.glasssystem_r1.mvp.contract.AlarmContract;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;

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
    public List<Alarm> getAllAlarm(String receiver, int limit) {
        return helper.getAllAlarm(receiver, limit);
    }

    @Override
    public List<Alarm> sortAlarmList(String receiver, int limit, String tag) {
        return helper.sortAllAlarm(receiver, limit, tag);
    }

    @Override
    public void setRead(Alarm alarm) {
        helper.setAlarmRead(alarm);
    }

    @Override
    public void deleteOne(int id) {
        helper.deleteAlarm(id);
    }

    @Override
    public void upload(String no, OnHttpCallBack<RetResult> callBack) {
        List<Alarm> alarms = helper.getAllAlarm(no);
        RetrofitUtils.newInstance(HTTP + getURL() + PORT + "/")
                .create(HttpContract.class)
                .uploadAlarm(no, alarms)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RetResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RetResult retResult) {
                        HttpFeedBackUtil.handleRetResult(retResult, callBack);
                        if (retResult.getCode() == RetResult.RetCode.SUCCESS.code) {
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        HttpFeedBackUtil.handleException(e, callBack);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void download(String no, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(HTTP + getURL() + PORT + "/")
                .create(HttpContract.class)
                .downloadAlarm(no)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RetResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RetResult retResult) {
                        HttpFeedBackUtil.handleRetResult(retResult, callBack);
                        if (retResult.getCode() == RetResult.RetCode.SUCCESS.code) {
                            List<Alarm> alarms = parseArray(toJSONString(retResult.getData()), Alarm.class);
                            List<Alarm> oldAlarms = helper.getAllAlarm(no);
                            for (Alarm alarm : oldAlarms) {
                                helper.getAlarmDao().delete(alarm);
                            }
                            for (Alarm alarm : alarms) {
                                helper.getAlarmDao().insert(alarm);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        HttpFeedBackUtil.handleException(e, callBack);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
