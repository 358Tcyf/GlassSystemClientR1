package project.ys.glasssystem_r1.mvp.model;

import android.content.Context;

import java.util.List;

import project.ys.glasssystem_r1.data.DatabaseHelper;
import project.ys.glasssystem_r1.data.entity.Push;
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

    public void setDefault(Push push, String submenu) {
        helper.setDefault(push, submenu);
    }

    @Override
    public void cutFirst(String no, List<String> tags) {
        for (String tag : tags) {
            helper.cutOne(no, tag);
        }
    }

    @Override
    public void addOne(String no, String tag) {
        helper.addOne(no, tag);
    }

    @Override
    public void setRead(Push push) {
        helper.setPushRead(push);
    }

    @Override
    public int getCount(String no, String tag) {
        return helper.getCount(no, tag);
    }
}
