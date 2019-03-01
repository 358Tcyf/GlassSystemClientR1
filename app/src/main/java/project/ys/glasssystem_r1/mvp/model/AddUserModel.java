package project.ys.glasssystem_r1.mvp.model;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.mvp.contract.AddUserContract;
import project.ys.glasssystem_r1.http.HttpContract;
import project.ys.glasssystem_r1.http.HttpFeedBackUtil;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.http.RetrofitUtils;

import static project.ys.glasssystem_r1.common.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.HttpConstant.URL;

public class AddUserModel implements AddUserContract.Model {
    @Override
    public void getLatestNo(int roleId, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(URL + PORT + "/")
                .create(HttpContract.class)
                .getLatestNo(roleId)
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
    public void addUser(UserBean user, int roleId, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(URL + PORT + "/")
                .create(HttpContract.class)
                .addUser(user.getName(), user.getNo(), user.getEmail(), user.getPhone(), roleId)
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
