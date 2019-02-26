package project.ys.glasssystem_r1.model;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.ys.glasssystem_r1.bean.UserBean;
import project.ys.glasssystem_r1.contract.LoginContract;
import project.ys.glasssystem_r1.http.HttpContract;
import project.ys.glasssystem_r1.http.HttpFeedBackUtil;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.http.RetrofitUtils;

import static project.ys.glasssystem_r1.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.constant.HttpConstant.URL;

public class LoginModel implements LoginContract.Model {

    @Override
    public void login(String account, String password, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(URL + PORT + "/")
                .create(HttpContract.class)
                .login(account, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RetResult<UserBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(RetResult<UserBean> retResult) {
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
