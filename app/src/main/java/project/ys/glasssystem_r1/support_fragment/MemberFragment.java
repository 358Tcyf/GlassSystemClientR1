package project.ys.glasssystem_r1.support_fragment;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
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
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.adapter.UserQuickAdapter;
import project.ys.glasssystem_r1.bean.UserBean;
import project.ys.glasssystem_r1.contract.MemberContract;
import project.ys.glasssystem_r1.presenter.MemberPresenter;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showTipDialog;

@EFragment(R.layout.fragment_member)
public class MemberFragment extends SupportFragment implements MemberContract.View {

    public static MemberFragment newInstance() {
        return new MemberFragment_();
    }

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @DrawableRes(R.drawable.ic_user_add)
    Drawable icAdd;
    @DrawableRes(R.drawable.ic_user_search)
    Drawable icSearch;
    @DrawableRes(R.drawable.ic_user_sort)
    Drawable icSort;
    @DrawableRes(R.drawable.ic_user_refresh)
    Drawable icRefresh;

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
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initAdapter();
        refreshView();
        initRefreshLayout();
    }

    private MemberPresenter memberPresenter;
    private ArrayList<UserBean> mList = new ArrayList<>();
    private BaseQuickAdapter mAdapter;

    private void initTopBar() {
        mTopBar.addRightImageButton(R.drawable.ic_more_vert, R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetList();
            }
        });

    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new VegaLayoutManager());
        mAdapter = new UserQuickAdapter(getActivity(), mList);
//        mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
//            userInfo(position);
            userManage(position);

        });
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                mSwipeRefreshLayout.setRefreshing(false);
//                refreshLoad();
            }, 1000);

        });
    }

    private void userManage(int i) {
        String no = mList.get(i).getNo();
        showTipDialog(getContext(), mList.get(i).getName());
        new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                .addItem(R.drawable.ic_user_details, strAccount + strDetail + ": " + mList.get(i).getName(), strDetail)
                .addItem(R.drawable.ic_user_edit, strEdit + strAccount + ": " + mList.get(i).getName(), strEdit)
                .addItem(R.drawable.ic_user_delete, strLogOff + strAccount + ": " + mList.get(i).getName(), strLogOff)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    toolbarAction(i, tag);
                })
                .build()
                .show();
    }

    private void userInfo(int i) {
        String no = mList.get(i).getNo();
        showTipDialog(getContext(), mList.get(i).getName());
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
                            toolbarAction(0, tag);
                        });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }

    private void toolbarAction(int i, String tag) {
        if (tag.equals(strAdd)) {
            //TODO 新建账号
            ((HomeFragmentNew) getParentFragment()).startBrotherFragment(AddUserFragment.newInstance());
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
            ((HomeFragmentNew) getParentFragment()).startBrotherFragment(UserDetailFragmentNew.newInstance());
//            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(UserDetailFragment.newInstance()));
        }
        if (tag.equals(strEdit)) {
            //TODO 修改账号
            showTipDialog(getContext(), tag);
        }
        if (tag.equals(strLogOff)) {
            // 注销账号
            showMessageNegativeDialog(getContext(), strLogOff + strAccount
                    , "确定要" + strLogOff + strAccount + ": " + mList.get(i).getName() + "吗？"
                    , cancel, strLogOff,
                    (dialog, index) -> {
                        memberPresenter.logOff(mList.get(i).getNo());
                    });
        }
    }

//    private void refreshLoad() {
////        setData(null);
//    }
//
//    private void loadMore() {
//        setData(null);
//    }
//
//    private void setData(ArrayList data) {
//        final int size = data == null ? 0 : data.size();
//        mAdapter.loadMoreEnd(false); //显示无更多数据
//    }

    @Override
    public void setMap(Map mMap) {
        mEmptyView.hide();
        List<UserBean> manager =
                parseArray(toJSONString(mMap.get("staffs")), UserBean.class);
        this.mList = (ArrayList) manager;
        setList();
    }

    @UiThread
    void setList() {
        mAdapter.setNewData(mList);
    }

    @Override
    public void refreshView() {
        mEmptyView.show(true);
        mAdapter.setNewData(null);
        refreshComplete();
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


}
