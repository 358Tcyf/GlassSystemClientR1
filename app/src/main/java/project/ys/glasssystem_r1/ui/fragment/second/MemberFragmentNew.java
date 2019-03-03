package project.ys.glasssystem_r1.ui.fragment.second;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.stone.vega.library.VegaLayoutManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.data.bean.UserWithRoleBean;
import project.ys.glasssystem_r1.mvp.contract.MemberContract;
import project.ys.glasssystem_r1.mvp.presenter.MemberPresenter;
import project.ys.glasssystem_r1.ui.adapter.UserPinyinAdapter;
import project.ys.glasssystem_r1.ui.adapter.UserQuickAdapter;
import project.ys.glasssystem_r1.ui.fragment.HomeFragment;
import project.ys.glasssystem_r1.ui.fragment.HomeFragmentNew;
import project.ys.glasssystem_r1.ui.fragment.second.child.AddUserFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.UserFragment;
import project.ys.glasssystem_r1.utils.ToastUtil;

import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showTipDialog;

@EFragment(R.layout.fragment_member_new)
public class MemberFragmentNew extends SupportFragment implements MemberContract.View {

    public static MemberFragmentNew newInstance() {
        return new MemberFragmentNew_();
    }

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    @DrawableRes(R.drawable.ic_user_add)
    Drawable icAdd;
    @DrawableRes(R.drawable.ic_user_search)
    Drawable icSearch;
    @DrawableRes(R.drawable.ic_user_sort)
    Drawable icSort;
    @DrawableRes(R.drawable.ic_user_refresh)
    Drawable icRefresh;

    @ColorRes(R.color.colorPrimary)
    int colorPrimary;

    @StringRes(R.string.add)
    String strAdd;
    @StringRes(R.string.search)
    String strSearch;
    @StringRes(R.string.sort)
    String strSort;
    @StringRes(R.string.refresh)
    String strRefresh;
    @StringRes(R.string.detail)
    String strDetail;
    @StringRes(R.string.edit)
    String strEdit;
    @StringRes(R.string.log_off)
    String strLogOff;
    @StringRes(R.string.account)
    String strAccount;
    @StringRes(R.string.cancel)
    String cancel;
    @StringRes(R.string.noData)
    String noData;
    @StringRes(R.string.refreshFail)
    String refreshFail;
    @StringRes(R.string.clickRetry)
    String clickRetry;

    @AfterInject
    void afterInject() {
        memberPresenter = new MemberPresenter(this);
    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initAdapter();
        refreshView();

    }

    private MemberPresenter memberPresenter;
    private ArrayList<UserWithRoleBean> mList = new ArrayList<>();
    private UserPinyinAdapter mAdapter;

    private void initTopBar() {
        mTopBar.addRightImageButton(R.drawable.ic_more_vert, R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetList();
            }
        });

    }


    private void initAdapter() {
        indexableLayout.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new UserPinyinAdapter(_mActivity);
        indexableLayout.setAdapter(mAdapter);
        indexableLayout.setOverlayStyle_MaterialDesign(colorPrimary);
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
        mAdapter.setOnItemContentClickListener((v, originalPosition, currentPosition, entity) -> {
//            userInfo(entity);
            userManage(entity);
        });

    }


    private void userManage(UserWithRoleBean user) {
        String no = user.getNo();
        new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                .addItem(R.drawable.ic_user_details, strAccount + strDetail + ": " + user.getName(), strDetail)
                .addItem(R.drawable.ic_user_edit, strEdit + strAccount + ": " + user.getName(), strEdit)
                .addItem(R.drawable.ic_user_delete, strLogOff + strAccount + ": " + user.getName(), strLogOff)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    action(user, tag);
                })
                .build()
                .show();
    }

    private void userInfo(UserWithRoleBean entity) {
        String no = entity.getNo();
    }


    private void showBottomSheetList() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
                new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                        .addItem(icAdd, strAdd)
                        .addItem(icSearch, strSearch)
                        .addItem(icSort, strSort)
                        .addItem(icRefresh, strRefresh)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            action(null, tag);
                        });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }

    private void action(UserWithRoleBean user, String tag) {
        if (tag.equals(strAdd)) {
            //TODO 新建账号
            if (getParentFragment() instanceof HomeFragmentNew)
                ((HomeFragmentNew) getParentFragment()).startBrotherFragment(AddUserFragment.newInstance());
            if (getParentFragment() instanceof HomeFragment)
                ((HomeFragment) getParentFragment()).startBrotherFragment(AddUserFragment.newInstance());
        }
        if (tag.equals(strSearch)) {
            //TODO 搜索
            showTipDialog(getContext(), tag);
        }
        if (tag.equals(strSort)) {
            //TODO 排序
            showTipDialog(getContext(), tag);
        }
        if (tag.equals(strRefresh)) {
            //TODO 重新加载
            refreshView();
        }
        if (tag.equals(strDetail)) {
            //TODO 查看详情
            if (getParentFragment() instanceof HomeFragmentNew)
                ((HomeFragmentNew) getParentFragment()).startBrotherFragment(UserFragment.newInstance(user.getNo(), user.getName()));
            if (getParentFragment() instanceof HomeFragment)
                ((HomeFragment) getParentFragment()).startBrotherFragment(UserFragment.newInstance(user.getNo(), user.getName()));
        }
        if (tag.equals(strEdit)) {
            //TODO 修改账号
            showTipDialog(getContext(), tag);
        }
        if (tag.equals(strLogOff)) {
            // 注销账号
            showMessageNegativeDialog(getContext(), strLogOff + strAccount
                    , "确定要" + strLogOff + strAccount + ": " + user.getName() + "吗？"
                    , cancel, strLogOff,
                    (dialog, index) -> {
                        memberPresenter.logOff(user.getNo());
                    });
        }
    }


    @Override
    public void refreshView() {
        mEmptyView.show(true);
        mAdapter.setDatas(null);
        new Handler().postDelayed(this::refreshComplete, 1000);
    }

    @UiThread
    void refreshComplete() {
        memberPresenter.userList();
    }

    @Override
    public void refreshFail() {
        mEmptyView.show(noData, null);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mEmptyView.show(false, refreshFail, errorMsg, clickRetry, v -> refreshView());
    }

    @Override
    public void setList(ArrayList list) {
        mEmptyView.hide();
        mAdapter.setDatas(list);
        mAdapter.notifyDataSetChanged();
    }
}
