package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.contract.ChartContract;
import project.ys.glasssystem_r1.mvp.model.ChartModel;

public class ChartPresenter implements ChartContract.Presenter {
    private ChartContract.View chartView;
    private ChartModel chartModel;
    private Context mContext;

    public ChartPresenter(ChartContract.View chartView) {
        this.chartView = chartView;
        chartModel = new ChartModel();

    }

    public ChartPresenter(ChartContract.View chartView, Context mContext) {
        this.chartView = chartView;
        this.mContext = mContext;
        chartModel = new ChartModel(mContext);
    }

    public void setDefault(Push push, String submenu) {
        chartModel.setDefault(push, submenu);
    }

    @Override
    public void getTags(String no) {

    }

    @Override
    public void cutFirst(String no, List<String> tags) {
        chartModel.cutFirst(no, tags);
    }

    @Override
    public void addOne(String no, String tag) {
        chartModel.addOne(no, tag);

    }

    @Override
    public void setRead(Push push) {
        chartModel.setRead(push);

    }

    @Override
    public void checkCount(String no, List<String> tags) {
        List<String> tips = new ArrayList();
        for (String tag : tags) {
            if (chartModel.getCount(no, tag) <= 0) {
                tips.add(tag);
            }
        }
        if (tips.size() > 0) {
            chartView.showTips(tips);
        }
    }

    @Override
    public void cancelSmartSub(String no) {
        chartModel.cancelSmartSub(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {

            }

            @Override
            public void onFailed(String errorMsg) {
            }
        });
    }

    @Override
    public void cancelTags(String no, List<String> tags) {
        chartModel.cancelTags(no,tags, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {

            }

            @Override
            public void onFailed(String errorMsg) {
            }
        });
    }
}
