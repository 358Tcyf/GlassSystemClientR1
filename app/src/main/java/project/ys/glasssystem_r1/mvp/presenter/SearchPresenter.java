package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.data.entity.SearchRecord;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.contract.SearchContract;
import project.ys.glasssystem_r1.mvp.model.SearchModel;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_ALARM;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_PUSH;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View searchView;
    private SearchModel searchModel;
    private Context mContext;

    public SearchPresenter(SearchContract.View searchView) {
        this.searchView = searchView;
        searchModel = new SearchModel();
    }

    public SearchPresenter(SearchContract.View searchView, Context mContext) {
        this.searchView = searchView;
        this.mContext = mContext;
        searchModel = new SearchModel(mContext);
    }

    @Override
    public void searchPush(String account, String searchText,int limit) {
        List<Push> pushList = searchModel.getPushList(account, searchText);
        if (pushList.size() == 0) {
            searchView.showNoData();
        } else {
            searchView.setList((ArrayList) pushList);
        }
    }

    @Override
    public void sortPushList(String account, String order, String searchText,int limit) {
        List<Push> pushList = searchModel.getPushList(account, order, searchText);
        if (pushList.size() == 0) {
            searchView.showNoData();
        } else {
            searchView.setList((ArrayList) pushList);
        }
    }

    @Override
    public void searchAlarm(String account, String searchText,int limit) {
        List<Alarm> alarmList = searchModel.getAlarmList(account, searchText);
        if (alarmList.size() == 0) {
            searchView.showNoData();
        } else {
            searchView.setList((ArrayList) alarmList);
        }
    }

    @Override
    public void sortAlarmList(String account, String order, String searchText,int limit) {
        List<Alarm> alarmList = searchModel.getAlarmList(account, order, searchText);
        if (alarmList.size() == 0) {
            searchView.showNoData();
        } else {
            searchView.setList((ArrayList) alarmList);
        }
    }


    @Override
    public void setRead(Push push) {
        searchModel.setRead(push);
    }

    @Override
    public void searchUser(String searchText) {
        searchModel.getUserList(searchText, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                Map<String, Object> userMap = (Map<String, Object>) retResult.getData();
                List<Map<String, Object>> userMapList = (List<Map<String, Object>>) userMap.get("staffs");
                if (userMapList.size() == 0) {
                    searchView.showNoData();
                } else {
                    searchView.setList((ArrayList) parseArray(toJSONString(userMapList), UserBeanPlus.class));
                }
            }

            @Override
            public void onFailed(String errorMsg) {
                searchView.showErrorMsg(errorMsg);
            }
        });
    }

    @Override
    public void getRecord() {
        List<SearchRecord> list = searchModel.getRecords();
            searchView.showSearchRecord((ArrayList) list);
    }

    @Override
    public void insertRecord(String searchText) {
        searchModel.insertRecord(searchText);
    }

    @Override
    public int getTotal(String account, String searchText,int searchClass) {
        switch (searchClass) {
            case SEARCH_PUSH:
                return searchModel.getPushList(account, searchText).size();
            case SEARCH_ALARM:
                return searchModel.getAlarmList(account, searchText).size();
        }
        return 0;
    }

    @Override
    public void deleteRecord(SearchRecord searchRecord) {
        searchModel.deleteRecord(searchRecord);
        getRecord();
    }

}
