package project.ys.glasssystem_r1.ui.fragment.third;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;
import com.tencent.mmkv.MMKV;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBeanOrderByName;
import project.ys.glasssystem_r1.mvp.contract.PushSetContract;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.mvp.presenter.PushSetPresenter;
import project.ys.glasssystem_r1.mvp.presenter.UserDetailPresenter;
import project.ys.glasssystem_r1.ui.activity.LoginActivity_;

import static project.ys.glasssystem_r1.common.constant.UserConstant.USER_ACCOUNT;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showFailDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showTipDialog;

@EFragment(R.layout.fragment_about)
public class AboutFragment extends SupportFragment implements UserDetailContract.View, PushSetContract.View {
    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.user_card)
    CardView userCard;

    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    @DrawableRes(R.drawable.ic_item_user_info)
    Drawable icUserInfo;

    @DrawableRes(R.drawable.ic_item_index_line)
    Drawable icIndexLine;
    @DrawableRes(R.drawable.ic_item_setting)
    Drawable icSetting;
    @DrawableRes(R.drawable.ic_item_details)
    Drawable icDetails;
    @DrawableRes(R.drawable.ic_bottom_update)
    Drawable icUpdate;
    @DrawableRes(R.drawable.ic_bottom_callback)
    Drawable icCallback;
    @DrawableRes(R.drawable.ic_item_logout)
    Drawable icLogout;

    @DrawableRes(R.drawable.ic_bottom_logout)
    Drawable icLogoutAccount;
    @DrawableRes(R.drawable.ic_bottom_exit)
    Drawable icExit;
    @ViewById(R.id.user_name)
    TextView userName;

    @ViewById(R.id.user_role)
    TextView userRole;
    @ViewById(R.id.user_no)
    TextView userNo;
    @ViewById(R.id.user_email)
    QMUILinkTextView userEmail;
    @ViewById(R.id.user_phone)
    QMUILinkTextView userPhone;

    @StringRes(R.string.userInfo)
    String strUserInfo;
    @StringRes(R.string.pushSetting)
    String strPushSetting;
    @StringRes(R.string.appSetting)
    String strSetting;
    @StringRes(R.string.aboutApp)
    String strDetails;
    @StringRes(R.string.checkUpdate)
    String strUpdate;
    @StringRes(R.string.callback)
    String strCallback;
    @StringRes(R.string.logout)
    String strLogout;
    @StringRes(R.string.logoutAccount)
    String strLogoutAccount;
    @StringRes(R.string.exitApp)
    String strExitApp;

    @StringRes(R.string.refreshFail)
    String refreshFail;
    @StringRes(R.string.clickRetry)
    String clickRetry;

    public static AboutFragment newInstance() {
        return new AboutFragment_();
    }

    public static AboutFragment newInstance(String no) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        AboutFragment fragment = new AboutFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    private UserDetailPresenter userDetailPresenter;
    private PushSetPresenter pushSetPresenter;
    private String no;

    @AfterInject
    void afterInject() {
        userDetailPresenter = new UserDetailPresenter(this);
        pushSetPresenter = new PushSetPresenter(this);
        no = getArguments().getString(USER_ACCOUNT);
    }

    @AfterViews
    void afterViews() {
        if (no.startsWith("A")) {
            userCard.setVisibility(View.GONE);
            mEmptyView.hide();
        } else {
            initCard();
        }
        initGroupListView();
    }


    private void initCard() {
        mEmptyView.show(true);
        new Handler().postDelayed(() -> userDetailPresenter.getDetail(no), 1000);

    }


    private void initGroupListView() {
        QMUICommonListItemView userInfoItem = mGroupListView.createItemView(
                icUserInfo,
                strUserInfo,
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView pushSettingItem = mGroupListView.createItemView(
                icIndexLine,
                strPushSetting,
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView appSettingItem = mGroupListView.createItemView(
                icSetting,
                strSetting,
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView aboutSystemItem = mGroupListView.createItemView(
                icDetails,
                strDetails,
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUICommonListItemView logoutItem = mGroupListView.createItemView(
                icLogout,
                strLogout,
                null,
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        if (no.startsWith("A")) {
            addNewSection(pushSettingItem);
            addNewSection(appSettingItem);
            addNewSection(aboutSystemItem);
            addNewSection(logoutItem);

        } else {
            addNewSection(userInfoItem, pushSettingItem, appSettingItem, aboutSystemItem, logoutItem);
        }

    }

    private void addNewSection(QMUICommonListItemView... itemView) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext());
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

    private void showAboutApp() {
        new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                .addItem(icUpdate, strUpdate)
                .addItem(icCallback, strCallback)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    action(tag);
                })
                .build()
                .show();
    }

    private void showLogoutSheet() {
        new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                .addItem(icLogoutAccount, strLogoutAccount)
                .addItem(icExit, strExitApp)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    action(tag);
                })
                .build()
                .show();
    }


    private void action(String tag) {
        if (tag.equals(strUserInfo)) {
            //TODO 用户信息
            userInfo();
        }

        if (tag.equals(strPushSetting)) {
            //TODO 推送设置
            pushSet();
        }

        if (tag.equals(strSetting
        )) {
            //TODO 应用设置
            appSet();
        }

        if (tag.equals(strDetails)) {
            //TODO 关于应用
            showAboutApp();
        }

        if (tag.equals(strUpdate)) {
            //TODO 检查更新
            showTipDialog(getContext(), tag);
        }
        if (tag.equals(strCallback)) {
            //TODO 反馈
            showTipDialog(getContext(), tag);
        }
        if (tag.equals(strLogout)) {
            //TODO 退出
            showLogoutSheet();
        }
        if (tag.equals(strLogoutAccount)) {
            //TODO 退出账号
            logoutDialog();
        }
        if (tag.equals(strExitApp)) {
            //TODO 关闭应用
            _mActivity.moveTaskToBack(false);
        }
    }

    private void userInfo() {
    }

    private void pushSet() {
        pushSetPresenter.getTags(no);
    }

    private void appSet() {
    }

    @Override
    public void setDetail(UserBeanOrderByName user) {
        resetCard(user);
    }

    @UiThread
    void resetCard(UserBeanOrderByName user) {
        if (mEmptyView != null)
            mEmptyView.hide();
        if (userName != null)
            userName.setText(user.getName());
        if (userRole != null)
            userRole.setText(user.getRoleName());
        if (userNo != null)
            userNo.setText(user.getNo());
        if (userEmail != null)
            userEmail.setText(user.getEmail());
        if (userPhone != null)
            userPhone.setText(user.getPhone());
    }

    @Override
    public void showErrorView(String errorMsg) {
        mEmptyView.show(false, refreshFail, errorMsg, clickRetry, v -> initCard());
    }

    @Override
    public void showTagsChoices(List<Integer> checks) {
        final String[] items = new String[]{"生产量", "生产型号统计", "生产质量", "生产能耗"};
        final QMUIDialog.MultiCheckableDialogBuilder builder = new QMUIDialog.MultiCheckableDialogBuilder(getActivity())
                .addItems(items, (dialog, which) -> {
                });
        if (checks != null) {{
            int[] checkedIndexes = new int[checks.size()];
            for(int i =0;i<checks.size();i++){
                checkedIndexes[i] = checks.get(i);
            }
            builder.setCheckedItems(checkedIndexes);
        }
        }
        builder.addAction("取消", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                dialog.dismiss();
            }
        });
        builder.addAction("提交", new QMUIDialogAction.ActionListener() {
            @Override
            public void onClick(QMUIDialog dialog, int index) {
                List<String> tags = new ArrayList<>();
                for (int i = 0; i < builder.getCheckedItemIndexes().length; i++) {
                    tags.add(items[builder.getCheckedItemIndexes()[i]]);
                }
                pushSetPresenter.updateTags(no,tags);
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            showFailDialog(getContext(), errorMsg);
        }, 2000);
    }

    private void logoutDialog() {
        QMUIDialog.CheckBoxMessageDialogBuilder logoutDailog = new QMUIDialog.CheckBoxMessageDialogBuilder(_mActivity);
        logoutDailog.setTitle("退出后是否删除账号信息?")
                .setMessage("删除账号信息")
                .setChecked(false)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("退出", (dialog, index) -> {
                    if (logoutDailog.isChecked()) {
                        MMKV user = MMKV.defaultMMKV();
                        user.encode("userAccount", "");
                        user.encode("userPassword", "");
                    }
                    startActivity(new Intent(_mActivity, LoginActivity_.class));
                    _mActivity.finish();
                    dialog.dismiss();
                }).show();
    }


}
