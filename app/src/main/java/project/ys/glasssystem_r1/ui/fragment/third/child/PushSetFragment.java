package project.ys.glasssystem_r1.ui.fragment.third.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.mvp.contract.PushSetContract;
import project.ys.glasssystem_r1.mvp.presenter.PushSetPresenter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.common.constant.UserConstant.push_tags;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showFailDialog;

@EFragment
public class PushSetFragment extends BaseBackFragment implements PushSetContract.View {


    public static PushSetFragment newInstance() {

        return new PushSetFragment_();
    }


    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    @StringRes(R.string.cancel)
    String cancel;
    @StringRes(R.string.submit)
    String submit;

    @StringRes(R.string.push_switch)
    String pushSwitch;
    @StringRes(R.string.push_time)
    String pushTime;
    @StringRes(R.string.push_tags)
    String pushTags;
    @StringRes(R.string.alarm_switch)
    String alarmSwitch;
    @ColorRes(R.color.colorText_Icon)
    int topbarText;

    private UserBeanPlus currentUser;
    private PushSetPresenter pushSetPresenter;

    @AfterInject
    void afterInject() {
        currentUser = CustomerApp.getInstance().getCurrentUser();
        pushSetPresenter = new PushSetPresenter(this);

    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initGroupList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_push_set, container, false);
        return attachToSwipeBack(view);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
        mTopBar.setTitle(R.string.push_set).setTextColor(topbarText);
        Button saveBtn = mTopBar.addRightTextButton(R.string.save, R.id.save);
        saveBtn.setTextColor(topbarText);
        saveBtn.setOnClickListener(v -> {
        });
    }

    private void initGroupList() {

        QMUICommonListItemView pushSwitchItem = mGroupListView.createItemView(
                null,
                pushSwitch,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        QMUICommonListItemView pushTimeItem = mGroupListView.createItemView(
                null,
                pushTime,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView pushTagsItem = mGroupListView.createItemView(
                null,
                pushTags,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        addNewSection("数据推送设置", "", pushSwitchItem, pushTimeItem, pushTagsItem);

        QMUICommonListItemView alarmSwitchItem = mGroupListView.createItemView(
                null,
                alarmSwitch,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);

        addNewSection("数据预警设置", "", alarmSwitchItem);


    }

    private void addNewSection(String title, String description, QMUICommonListItemView... itemView) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext());
        if (!isEmpty(title))
            section.setTitle(title);
        if (!isEmpty(description))
            section.setDescription(description);
        for (int i = 0; i < itemView.length; i++) {
            section.addItemView(itemView[i], onClickListener);
        }
        section.addTo(mGroupListView);
    }

    private View.OnClickListener onClickListener = this::onClick;

    private void onClick(View v) {
        if (v instanceof QMUICommonListItemView) {
            CharSequence tag = ((QMUICommonListItemView) v).getText();
            action(tag.toString());

        }
    }



    private void action(String tag) {
        if (tag.equals(pushSwitch)) {
            //TODO 接受数据推送
        }

        if (tag.equals(pushTime)) {
            //TODO 推送时间
        }

        if (tag.equals(pushTags)) {
            //TODO 推送标签
            pushSetPresenter.getTags(currentUser.getNo());
        }

        if (tag.equals(alarmSwitch)) {
            //TODO 接受数据预警
        }


    }

    @Override
    public void showTagsChoices(List<Integer> checks) {
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(getActivity())
                .addItems(push_tags, (dialog, which) -> {
                });
        if (checks != null) {
            {
                int[] checkedIndexes = new int[checks.size()];
                for (int i = 0; i < checks.size(); i++) {
                    checkedIndexes[i] = checks.get(i);
                }
                builder.setCheckedItems(checkedIndexes);
            }
        }
        builder.addAction(cancel, (dialog, index) -> dialog.dismiss());
        builder.addAction(submit, (dialog, index) -> {
            List<String> tags = new ArrayList<>();
            for (int i = 0; i < builder.getCheckedItemIndexes().length; i++) {
                tags.add(push_tags[builder.getCheckedItemIndexes()[i]]);
            }
            pushSetPresenter.updateTags(currentUser.getNo(), tags);
            dialog.dismiss();
        });
        builder.create().show();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            showFailDialog(getContext(), errorMsg);
        }, 2000);
    }
}
