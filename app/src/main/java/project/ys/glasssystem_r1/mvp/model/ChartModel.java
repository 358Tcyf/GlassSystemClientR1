package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.bean.PushSet;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.http.HttpContract;
import project.ys.glasssystem_r1.http.HttpFeedBackUtil;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.http.RetrofitUtils;
import project.ys.glasssystem_r1.mvp.contract.ChartContract;

import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;

public class ChartModel implements ChartContract.Model {
    private Context mContext;
    private DatabaseHelper helper;

    public ChartModel() {
    }

    public ChartModel(Context mContext) {
        this.mContext = mContext;
        helper = new DatabaseHelper(mContext);
    }

    public void setDefault(Push push, String submenu) {
        helper.setDefault(push, submenu);
    }

    @Override
    public void cutFirst(String no, List<String> tags) {
        for (String tag : tags) {
            helper.cutOne(no, tag);
        }
    }

    @Override
    public void addOne(String no, String tag) {
        helper.addOne(no, tag);
    }

    @Override
    public void setRead(Push push) {
        helper.setPushRead(push);
    }

    @Override
    public int getCount(String no, String tag) {
        return helper.getCount(no, tag);
    }

    @Override
    public void cancelSmartSub(String no, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(HTTP + getURL() + PORT + "/")
                .create(HttpContract.class)
                .cancelSmartSub(no)
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
                            PushSet set = CustomerApp.getInstance().getPushSet();
                            set.setSmartSub(false);
                            CustomerApp.getInstance().setPushSet(set);

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
    public void cancelTags(String no, List<String> tags, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(HTTP + getURL() + PORT + "/")
                .create(HttpContract.class)
                .cancelTags(no, tags)
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
}
