package project.ys.glasssystem_r1.presenter;

import project.ys.glasssystem_r1.contract.LoginContract;
import project.ys.glasssystem_r1.contract.PushContract;
import project.ys.glasssystem_r1.model.LoginModel;
import project.ys.glasssystem_r1.model.PushModel;

public class PushPresenter implements PushContract.Presenter {
    private PushContract.View view;
    private PushModel pushModel;

    public PushPresenter(PushContract.View view) {
        this.view = view;
        pushModel = new PushModel();
    }
}
