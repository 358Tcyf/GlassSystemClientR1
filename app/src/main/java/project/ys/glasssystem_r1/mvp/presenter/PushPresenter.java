package project.ys.glasssystem_r1.mvp.presenter;

import android.content.Context;

import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.model.PushModel;

public class PushPresenter implements PushContract.Presenter {
    private PushContract.View view;
    private PushModel pushModel;
    private Context mContext;

    public PushPresenter(PushContract.View view) {
        this.view = view;
        pushModel = new PushModel();
    }

    public PushPresenter(PushContract.View view,Context mContext) {
        this.view = view;
        this.mContext=mContext;
        pushModel = new PushModel(mContext);
    }
}
