package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.model.PushModel;

public class PushPresenter implements PushContract.Presenter {
    private PushContract.View pushView;
    private PushModel pushModel;
    private Context mContext;

    public PushPresenter(PushContract.View pushView) {
        this.pushView = pushView;
        pushModel = new PushModel();
    }

    public PushPresenter(PushContract.View pushView, Context mContext) {
        this.pushView = pushView;
        this.mContext = mContext;
        pushModel = new PushModel(mContext);
    }

    @Override
    public void getList(String account,int limit) {
        List<Push> pushList = pushModel.getPushList(account,limit);
        if (pushList.size() == 0) {
            pushView.refreshFail();
        } else {
            pushView.setList((ArrayList) pushList);
        }
    }

    @Override
    public void sortList(String account,int limit, String tag) {
        List<Push> pushList = pushModel.sortPushList(account,limit, tag);
        if (pushList.size() == 0) {
            pushView.refreshFail();
        } else {
            pushView.setList((ArrayList) pushList);
        }
    }

    @Override
    public void setRead(Push push) {
        pushModel.setRead(push);
    }


    @Override
    public void deleteOne(int id) {
        pushModel.deleteOne(id);
    }

    @Override
    public int getTotal(String account) {
        List<Push> pushList = pushModel.getPushList(account);
        return pushList.size();
    }


}
