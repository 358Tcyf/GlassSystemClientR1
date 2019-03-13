package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

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

    public void setDefault(String content, String submenu) {
        chartModel.setDefault(content, submenu);
    }
}
