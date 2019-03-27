package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.data.entity.SearchRecord;
import project.ys.glasssystem_r1.http.HttpContract;
import project.ys.glasssystem_r1.http.HttpFeedBackUtil;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.http.RetrofitUtils;
import project.ys.glasssystem_r1.mvp.contract.SearchContract;

import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;

public class SearchModel implements SearchContract.Model {
    private Context mContext;
    private DatabaseHelper helper;

    public SearchModel() {
    }

    public SearchModel(Context mContext) {
        this.mContext = mContext;
        helper = new DatabaseHelper(mContext);
    }

    @Override
    public List<Push> getPushList(String receiver, String search) {
        return helper.searchPush(receiver, mContext.getString(R.string.sort_by_date), search);
    }

    @Override
    public List<Push> getPushList(String receiver, String order, String search) {
        return helper.searchPush(receiver, order, search);
    }

    @Override
    public void setRead(Push push) {
        helper.setPushRead(push);
    }

    @Override
    public void getUserList(String searchText, OnHttpCallBack<RetResult> callBack) {
        RetrofitUtils.newInstance(HTTP + getURL() + PORT + "/")
                .create(HttpContract.class)
                .searchUserList(searchText)
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
    public List<SearchRecord> getRecords() {
        return helper.findRecentFive();
    }

    @Override
    public void insertRecord(String searchText) {
        helper.insertSearch(searchText);
    }
}
