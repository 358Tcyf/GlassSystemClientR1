package project.ys.glasssystem_r1.ui.fragment.third;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;
import com.tencent.mmkv.MMKV;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.data.bean.AlarmTag;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.http.MyRestClient;
import project.ys.glasssystem_r1.http.MyRestClient_;
import project.ys.glasssystem_r1.mvp.contract.PushSetContract;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.mvp.presenter.PushSetPresenter;
import project.ys.glasssystem_r1.mvp.presenter.UserDetailPresenter;
import project.ys.glasssystem_r1.ui.activity.LoginActivity_;
import project.ys.glasssystem_r1.ui.fragment.third.child.PushSetFragment;
import project.ys.glasssystem_r1.ui.fragment.third.child.UserEditFragment;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.URL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.changeURL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showTipDialog;
import static project.ys.glasssystem_r1.util.utils.DateUtils.getNowTime;
import static project.ys.glasssystem_r1.util.utils.ToastUtils.showNormalToast;

@EFragment(R.layout.fragment_about)
public class ThirdTabFragment extends SupportFragment implements UserDetailContract.View, PushSetContract.View {

    public static ThirdTabFragment newInstance() {
        return new ThirdTabFragment_();
    }

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
    @ViewById(R.id.date_dot)
    QMUIRadiusImageView userPic;
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
    @StringRes(R.string.editUrl)
    String editUrl;
    @StringRes(R.string.instantPush)
    String instantPush;

    @StringRes(R.string.instantTish)
    String instantTish;
    @StringRes(R.string.refreshFail)
    String refreshFail;

    @StringRes(R.string.clickRetry)
    String clickRetry;

    @StringRes(R.string.cancel)
    String cancel;

    @StringRes(R.string.logout)
    String logout;


    MyRestClient restClient;

    private UserDetailPresenter userDetailPresenter;
    private PushSetPresenter pushSetPresenter;
    private UserBeanPlus currentUser;

    @AfterInject
    void afterInject() {
        userDetailPresenter = new UserDetailPresenter(this);
        pushSetPresenter = new PushSetPresenter(this, _mActivity);
        currentUser = CustomerApp.getInstance().getCurrentUser();
        restClient = new MyRestClient_(_mActivity);
    }

    @AfterViews
    void afterViews() {
        initTopBar();
        if (currentUser.getNo().startsWith("A")) {
            userCard.setVisibility(View.GONE);
            mEmptyView.hide();
        } else {
            initCard();
        }
        initPushSet();
        initGroupListView();
    }

    private void initTopBar() {
        mTopBar.addRightImageButton(R.drawable.ic_details, R.id.detail)
                .setOnClickListener(v -> debugBottomSheet());
    }

    private void debugBottomSheet() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
                new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                        .addItem(editUrl)
                        .addItem(instantPush)
                        .addItem(instantTish)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            action(tag);
                            dialog.dismiss();
                        });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }


    private void initCard() {
        mEmptyView.show(true);
        new Handler().postDelayed(() -> userDetailPresenter.getDetail(currentUser.getNo()), 1000);

    }

    private void initPushSet() {
        pushSetPresenter.getSets(currentUser.getNo());
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
        if (currentUser.getNo().startsWith("A")) {
            addNewSection(pushSettingItem);
            addNewSection(appSettingItem);
            addNewSection(aboutSystemItem);
            addNewSection(logoutItem);

        } else {
            addNewSection(userInfoItem, pushSettingItem, aboutSystemItem, logoutItem);
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
            QMUITipDialog loading = showLoadingDialog(_mActivity, "检查更新");
            loading.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading.dismiss();
                    showTipDialog(_mActivity, "当前已是最新版本");
                }
            }, 1000);
        }
        if (tag.equals(strCallback)) {
            //TODO 反馈
            showNormalToast(_mActivity, "欢迎联系本应用开发者simple_yu");
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
        if (tag.equals(editUrl)) {
            //TODO 修改网路通信地址
            showEditUrlDialog();
        }
        if (tag.equals(instantPush)) {
            //TODO 即时推送
            getInstantPush();
        }
        if (tag.equals(instantTish)) {
            //TODO 即时推送：本机
            getInstantPush(currentUser.getNo());
        }
    }

    @Background
    void getInstantPush() {
        restClient.getInstantPush();
    }

    @Background
    void getInstantPush(String alias) {
        restClient.getInstantPushWithAlias(alias);
    }

    private void userInfo() {
        EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(UserEditFragment.newInstance(currentUser)));
    }

    private void pushSet() {
        EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(PushSetFragment.newInstance()));
    }

    private void appSet() {
    }

    @Override
    public void setDetail(UserBeanPlus user) {
        resetCard(user);
    }

    @UiThread
    void resetCard(UserBeanPlus user) {
        if (mEmptyView != null)
            mEmptyView.hide();
        if (userName != null) {
            userName.setText(user.getName());
            currentUser.setName(user.getName());
        }
        if (userRole != null) {
            userRole.setText(user.getRoleName());
            currentUser.setRoleName(user.getRoleName());
        }
        if (userNo != null)
            userNo.setText(user.getNo());
        currentUser.setPicPath(user.getPicPath());
        setUserPic();
        if (userEmail != null) {
            userEmail.setText(user.getEmail());
            currentUser.setEmail(user.getEmail());
        }
        if (userPhone != null) {
            userPhone.setText(user.getPhone());
            currentUser.setPhone(user.getPhone());
        }

    }

    @UiThread
    void setUserPic() {
        if (!isEmpty(currentUser.getPicPath())) {
            Glide.with(this)
                    .load(Uri.parse(HTTP + getURL() + PORT + currentUser.getPicPath() + "/" + getNowTime()))
                    .apply(new RequestOptions().error(R.mipmap.ic_account_circle))
                    .into(userPic);
        }
    }

    @Override
    public void showErrorView(String errorMsg) {
        mEmptyView.show(false, refreshFail, errorMsg, clickRetry, v -> initCard());
    }

    private void logoutDialog() {
        QMUIDialog.CheckBoxMessageDialogBuilder logoutDailog = new QMUIDialog.CheckBoxMessageDialogBuilder(_mActivity);
        logoutDailog.setTitle("退出后是否删除账号信息?")
                .setMessage("删除账号信息")
                .setChecked(false)
                .addAction(cancel, (dialog, index) -> dialog.dismiss())
                .addAction(logout, (dialog, index) -> {
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


    private void showEditUrlDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("修改网络通信地址")
                .setDefaultText(getURL())
//                .setPlaceholder(URL)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("默认", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        builder.getEditText().setText(URL);
                        changeURL(URL);
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        changeURL(text.toString());
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    @Override
    public void showTagsChoices(List<Integer> checks) {

    }

    @Override
    public void setAlarmTags(List<AlarmTag> alarmTags) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void showSuccess() {

    }
}
