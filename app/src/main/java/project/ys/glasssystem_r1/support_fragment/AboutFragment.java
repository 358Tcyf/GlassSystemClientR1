package project.ys.glasssystem_r1.support_fragment;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.View;

import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;

@EFragment(R.layout.fragment_about)
public class AboutFragment extends SupportFragment {
    public static AboutFragment newInstance() {
        return new AboutFragment_();
    }

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

    @AfterViews
    void afterViews() {
        initView();
        initGroupListView();
    }

    private void initView() {
        mEmptyView.show(true);
        new Handler().postDelayed(mEmptyView::hide, 100);
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
        addNewSection(userInfoItem);
        addNewSection(pushSettingItem);
        addNewSection(appSettingItem);
        addNewSection(aboutSystemItem);
        addNewSection(logoutItem);

    }

    private void addNewSection(QMUICommonListItemView itemView) {
        QMUIGroupListView.newSection(getContext())
                .addItemView(itemView, onClickListener)
                .addTo(mGroupListView);
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
}
