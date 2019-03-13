package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.mvp.contract.ChartContract;

public class ChartModel implements ChartContract.Model {
    private Context mContext;
    private DatabaseHelper helper;

    public ChartModel() {
    }

    public ChartModel(Context mContext) {
        this.mContext = mContext;
        helper = new DatabaseHelper(mContext);
    }

    public void setDefault(String content, String submenu) {
        helper.setDefault(content,submenu);
    }
}
