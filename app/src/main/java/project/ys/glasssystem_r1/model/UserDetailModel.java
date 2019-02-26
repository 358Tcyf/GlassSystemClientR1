package project.ys.glasssystem_r1.model;

import android.util.Log;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.ys.glasssystem_r1.contract.UserDetailContract;
import project.ys.glasssystem_r1.http.HttpContract;
import project.ys.glasssystem_r1.http.HttpFeedBackUtil;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.http.RetrofitUtils;

import static project.ys.glasssystem_r1.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.constant.HttpConstant.URL;

public class UserDetailModel implements UserDetailContract.Model {
    @Override
    public void getDetail(String no, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(URL + PORT + "/")
                .create(HttpContract.class)
                .userInfo(no)
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
