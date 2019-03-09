package project.ys.glasssystem_r1.ui.fragment.first;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.stone.vega.library.VegaLayoutManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.TabSelectedEvent;
import project.ys.glasssystem_r1.data.bean.PushBean;
import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.presenter.MemberPresenter;
import project.ys.glasssystem_r1.mvp.presenter.PushPresenter;
import project.ys.glasssystem_r1.ui.adapter.PushQuickAdapter;
import project.ys.glasssystem_r1.ui.fragment.HomeFragment;
import project.ys.glasssystem_r1.ui.fragment.HomeFragmentNew;
import project.ys.glasssystem_r1.ui.fragment.first.child.ChartsFragment;

@EFragment(R.layout.fragment_push)
public class PushFragment extends SupportFragment implements PushContract.View {
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


    @AfterInject
    void afterInject() {
        pushPresenter = new PushPresenter(this,_mActivity);
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initList();
        initAdapter();
//        refreshView();
        initRefreshLayout();
    }

    private PushPresenter pushPresenter;
    private ArrayList<PushBean> mList = new ArrayList<>();
    private BaseQuickAdapter mAdapter;
    private boolean mInAtTop = true;
    private boolean mInAtBottom = false;

    private void initTopBar() {
        mTopBar.addRightImageButton(R.drawable.ic_more_vert, R.id.more)
                .setOnClickListener(view -> {
                    scrollToTop();
                    //                showBottomSheetList();
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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new PushQuickAdapter(_mActivity, mList);
//        mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动到底部
                mInAtBottom = !mRecyclerView.canScrollVertically(1);
                //滑动到顶部
                mInAtTop = !mRecyclerView.canScrollVertically(-1);

            }
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            action(0, strDetail);
        });
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(QMUIResHelper
                .getAttrColor(_mActivity, R.attr.colorPrimary));
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
                ((HomeFragmentNew) getParentFragment()).startBrotherFragment(ChartsFragment.newInstance());
            if (getParentFragment() instanceof HomeFragment)
                ((HomeFragment) getParentFragment()).startBrotherFragment(ChartsFragment.newInstance());
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

    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != HomeFragmentNew.FIRST) return;
        if (mInAtTop) {
            swipeRefresh();
        } else {
            scrollToTop();
        }
    }

    @UiThread
    void swipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(() -> mSwipeRefreshLayout.setRefreshing(false), 1000);
    }

    @UiThread
    void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }

}
