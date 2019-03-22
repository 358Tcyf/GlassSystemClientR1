package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.SearchContract;
import project.ys.glasssystem_r1.mvp.model.SearchModel;

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

}
