package project.ys.glasssystem_r1.ui.fragment.third;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.mvp.presenter.UserDetailPresenter;

import static project.ys.glasssystem_r1.common.UserConstant.USER_ACCOUNT;

@EFragment(R.layout.fragment_about)
public class AboutFragment extends SupportFragment implements UserDetailContract.View {
    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;

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
    private String no;

    @AfterInject
    void afterInject() {
        userDetailPresenter = new UserDetailPresenter(this);
        no = getArguments().getString(USER_ACCOUNT);
    }

    @AfterViews
    void afterViews() {
        initCard();
        initTopBar();
        initGroupListView();
    }


    private void initCard() {
        mEmptyView.show(true);
        new Handler().postDelayed(() -> userDetailPresenter.getDetail(no), 1000);

    }

    private void initTopBar() {
        mTopBar.addRightImageButton(R.drawable.ic_more_vert, R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showBottomSheetList();
            }
        });

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
        addNewSection(userInfoItem, pushSettingItem, appSettingItem, aboutSystemItem, logoutItem);
//        addNewSection(logoutItem);

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
            CharSequence text = ((QMUICommonListItemView) v).getText();
            if (text.equals(strDetails))
                showAboutApp();
            if (text.equals(strLogout))
                showLogoutSheet();
        }
    }

    private void showAboutApp() {
        new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                .addItem(icUpdate, strUpdate)
                .addItem(icCallback, strCallback)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    switch (tag) {

                        default:
                    }
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
                    switch (tag) {

                        default:
                    }
                })
                .build()
                .show();
    }

    @Override
    public void setDetail(UserBean user, String roleName) {
        resetCard(user, roleName);
    }

    @UiThread
    void resetCard(UserBean user, String roleName) {
        if (mEmptyView != null)
            mEmptyView.hide();
        if (userName != null)
            userName.setText(user.getName());
        if (userRole != null)
            userRole.setText(roleName);
        if (userNo != null)
            userNo.setText(user.getNo());
        if (userEmail != null)
            userEmail.setText(user.getEmail());
        if (userPhone != null)
            userPhone.setText(user.getPhone());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mEmptyView.show(false, refreshFail, errorMsg, clickRetry, v -> initCard());
    }
}
