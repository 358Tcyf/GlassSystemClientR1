package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBeanOrderByName;
import project.ys.glasssystem_r1.data.bean.UserBeanOrderByRole;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.contract.SearchContract;
import project.ys.glasssystem_r1.mvp.model.SearchModel;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;

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
    public void searchPush(String account, String searchText) {
        List<Push> pushList = searchModel.getPushList("", searchText);
        if (pushList.size() == 0) {
            searchView.showNoData();
        } else {
            searchView.setList((ArrayList) pushList);
        }
    }

    @Override
    public void sortList(String account, String order, String searchText) {
        List<Push> pushList = searchModel.getPushList("", order, searchText);
        if (pushList.size() == 0) {
            searchView.showNoData();
        } else {
            searchView.setList((ArrayList) pushList);
        }
    }

    @Override
    public void setRead(Push push) {
        searchModel.setRead(push);
    }

    @Override
    public void searchUser(String order,String searchText) {
        searchModel.getUserList(searchText, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                Map<String, Object> userMap = (Map<String, Object>) retResult.getData();
                List<Map<String, Object>> userMapList = (List<Map<String, Object>>) userMap.get("staffs");
                if (order.equals(mContext.getString(R.string.order_by_name)))
                    searchView.setList((ArrayList) parseArray(toJSONString(userMapList), UserBeanOrderByName.class));
                if (order.equals(mContext.getString(R.string.order_by_role)))
                    searchView.setList((ArrayList) parseArray(toJSONString(userMapList), UserBeanOrderByRole.class));
            }

            @Override
            public void onFailed(String errorMsg) {
                searchView.showErrorMsg(errorMsg);
            }
        });
    }

}
