package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.http.HttpContract;
import project.ys.glasssystem_r1.http.HttpFeedBackUtil;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.http.RetrofitUtils;
import project.ys.glasssystem_r1.mvp.contract.PushContract;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;

public class PushModel implements PushContract.Model {

    private Context mContext;
    private DatabaseHelper helper;

    public PushModel() {
    }

    public PushModel(Context mContext) {
        this.mContext = mContext;
        helper = new DatabaseHelper(mContext);
    }

    @Override
    public void addPushItem(String content) {
        Push push = new Push();
        push.setContent(content);
        helper.insertPush(push);
    }

    @Override
    public List<Push> getPushList(String receiver) {
        return helper.getAllPush(receiver);
    }

    @Override
    public List<Push> getPushList(String receiver, int limit) {
        return helper.getAllPush(receiver, limit);
    }

    @Override
    public List<Push> sortPushList(String receiver, int limit, String tag) {
        return helper.sortAllPush(receiver, limit, tag);
    }

    @Override
    public void setRead(Push push) {
        helper.setPushRead(push);
    }

    @Override
    public void deleteOne(int id) {
        helper.deletePush(id);
    }

    @Override
    public void upload(String no, OnHttpCallBack<RetResult> callBack) {
        List<Push> pushes = helper.getAllPush(no);
        RetrofitUtils.newInstance(HTTP + getURL() + PORT + "/")
                .create(HttpContract.class)
                .uploadPush(no, pushes)
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
                .downloadPush(no)
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
                            if (retResult.getCode() == RetResult.RetCode.SUCCESS.code) {
                                List<Push> pushes = parseArray(toJSONString(retResult.getData()), Push.class);
                                List<Push> oldPushes = helper.getAllPush(no);
                                for (Push alarm : oldPushes) {
                                    helper.getPushDao().delete(alarm);
                                }
                                for (Push alarm : pushes) {
                                    helper.getPushDao().insert(alarm);
                                }
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
