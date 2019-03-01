package project.ys.glasssystem_r1.mvp.presenter;

import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.model.PushModel;

public class PushPresenter implements PushContract.Presenter {
    private PushContract.View view;
    private PushModel pushModel;

    public PushPresenter(PushContract.View view) {
        this.view = view;
        pushModel = new PushModel();
    }
}
