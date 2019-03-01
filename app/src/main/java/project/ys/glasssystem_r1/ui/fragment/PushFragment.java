package project.ys.glasssystem_r1.ui.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.stone.vega.library.VegaLayoutManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.PushBean;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.mvp.presenter.MemberPresenter;
import project.ys.glasssystem_r1.ui.adapter.PushQuickAdapter;

import static com.alibaba.fastjson.JSON.parseArray;
import static com.alibaba.fastjson.JSON.toJSONString;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showTipDialog;

@EFragment(R.layout.fragment_push)
public class PushFragment extends SupportFragment {
    public static PushFragment newInstance() {
        return new PushFragment_();
    }

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;

    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @StringRes(R.string.detail)
    String strDetail;

    @StringRes(R.string.noData)
    String noData;
    @StringRes(R.string.refreshFail)
    String refreshFail;
    @StringRes(R.string.clickRetry)
    String clickRetry;


    @AfterViews
    void afterViews() {
        initTopBar();
        initList();
        initAdapter();
//        refreshView();
        initRefreshLayout();
    }

    private ArrayList<PushBean> mList = new ArrayList<>();

    private BaseQuickAdapter mAdapter;

    private void initTopBar() {
        mTopBar.addRightImageButton(R.drawable.ic_more_vert, R.id.more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showBottomSheetList();
            }
        });

    }

    private void initList() {
        mEmptyView.show(true);
        new Handler().postDelayed(mEmptyView::hide, 1000);
        mList.clear();
        for (int i = 1; i <= 10; i++) {
            PushBean push = new PushBean();
            push.setTitle("Push" + i);
            mList.add(push);
        }
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new VegaLayoutManager());
        mAdapter = new PushQuickAdapter(_mActivity, mList);
//        mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            action(0, strDetail);
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


    private void showBottomSheetList() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
                new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            action(0, tag);
                        });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }

    private void action(int i, String tag) {
        if (tag.equals(strDetail)) {
            //TODO 查看详情
            if (getParentFragment() instanceof HomeFragmentNew)
                ((HomeFragmentNew) getParentFragment()).startBrotherFragment(DataChartsMainFragment.newInstance());
            if (getParentFragment() instanceof HomeFragment)
                ((HomeFragment) getParentFragment()).startBrotherFragment(DataChartsMainFragment.newInstance());
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


    public void setList(ArrayList list) {
        mEmptyView.hide();
        this.mList = list;
        setList();
    }

    @UiThread
    void setList() {
        mAdapter.setNewData(mList);
    }

    public void refreshView() {
        mEmptyView.show(true);
        mAdapter.setNewData(null);
    }


    public void refreshFail() {
        mEmptyView.show(noData, null);
    }

    public void showErrorMsg(String errorMsg) {
        mEmptyView.show(false, refreshFail, errorMsg, clickRetry, v -> refreshView());

    }

}
