package project.ys.glasssystem_r1.mvp.presenter;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.http.OnHttpCallBack;
import project.ys.glasssystem_r1.http.RetResult;
import project.ys.glasssystem_r1.mvp.contract.PushSetContract;
import project.ys.glasssystem_r1.mvp.model.PushSetModel;

import static android.text.TextUtils.isEmpty;
import static com.alibaba.fastjson.JSON.parseArray;

public class PushSetPresenter implements PushSetContract.Presenter {
    private PushSetContract.View pushSetView;
    private PushSetModel pushSetModel;

    public PushSetPresenter(PushSetContract.View pushSetView) {
        this.pushSetView = pushSetView;
        pushSetModel = new PushSetModel();
    }

    final String[] items = new String[]{"生产量", "生产型号统计", "生产质量", "生产能耗"};

    public void getTags(String no) {
        Logger.d(no);
        pushSetModel.getTags(no, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {
                if (isEmpty(retResult.getMsg())) {
                    pushSetView.showTagsChoices(null);

                } else {
                    List<String> tags = parseArray(retResult.getMsg(), String.class);
                    List<Integer> checks = new ArrayList<>();
                    for (int i = 0; i < items.length; i++) {
                        if (tags.contains(items[i])) {
                            checks.add(i);
                        }
                    }
                    pushSetView.showTagsChoices(checks);
                }
            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }

    public void updateTags(String no, List<String> tags) {
        pushSetModel.updateTags(no, tags, new OnHttpCallBack<RetResult>() {
            @Override
            public void onSuccess(RetResult retResult) {

            }

            @Override
            public void onFailed(String errorMsg) {
                pushSetView.showErrorMsg(errorMsg);
            }
        });
    }
}
