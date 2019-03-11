package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.model.PushModel;

public class PushPresenter implements PushContract.Presenter {
    private PushContract.View pushView;
    private PushModel pushModel;
    private Context mContext;

    public PushPresenter(PushContract.View view) {
        this.pushView = view;
        pushModel = new PushModel();
    }

    public PushPresenter(PushContract.View view, Context mContext) {
        this.pushView = view;
        this.mContext = mContext;
        pushModel = new PushModel(mContext);
    }

    public void getList(String account) {
        List<Push> pushList = pushModel.getPushList("");
        if (pushList.size() == 0) {
            pushView.refreshFail();
        } else {
            pushView.setList((ArrayList) pushList);
        }
    }

    public void setRead(Push push) {
        pushModel.setRead(push);
    }
}
