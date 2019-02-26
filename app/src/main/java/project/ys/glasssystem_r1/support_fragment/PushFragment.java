package project.ys.glasssystem_r1.support_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.stone.vega.library.VegaLayoutManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.adapter.PushQuickAdapter;
import project.ys.glasssystem_r1.bean.PushBean;

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

    private ArrayList<PushBean> mList = new ArrayList<>();
    private BaseQuickAdapter mAdapter;

    @AfterViews
    void afterViews() {
        initList();
        initTopBar();
        initAdapter();
        initRefreshLayout();
    }

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
        for (int i = 0; i < 3; i++)
            mList.add(new PushBean());
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new VegaLayoutManager());
        mAdapter = new PushQuickAdapter(getActivity(), mList);
        mAdapter.setOnLoadMoreListener(this::loadMore, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                mSwipeRefreshLayout.setRefreshing(false);
                refreshLoad();
            }, 100);

        });
    }

    private void refreshLoad() {
        setData(null);
    }

    private void loadMore() {
        setData(null);

    }

    private void setData(ArrayList data) {
        final int size = data == null ? 0 : data.size();
        mAdapter.loadMoreEnd(false); //显示无更多数据

    }

}
