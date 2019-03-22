package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.FirstTabMenuEvent;
import project.ys.glasssystem_r1.common.event.FirstTabSelectedEvent;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.presenter.PushPresenter;
import project.ys.glasssystem_r1.ui.adapter.PushQuickAdapter;
import project.ys.glasssystem_r1.ui.fragment.common.SearchFragment;

import static project.ys.glasssystem_r1.common.constant.Constant.FIRST;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_PUSH;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showTipDialog;

@EFragment(R.layout.fragment_push)
public class PushFragment extends SupportFragment implements PushContract.View {
    public static PushFragment newInstance() {
        return new PushFragment_();
    }

    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    TextView orderBy;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @DrawableRes(R.drawable.ic_user_search)
    Drawable icSearch;
    @DrawableRes(R.drawable.ic_user_sort)
    Drawable icSort;
    @DrawableRes(R.drawable.ic_user_refresh)
    Drawable icRefresh;


    @StringRes(R.string.detail)
    String strDetail;
    @StringRes(R.string.search)
    String strSearch;
    @StringRes(R.string.sort)
    String strSort;
    @StringRes(R.string.order_by)
    String strOrderBy;
    @StringRes(R.string.sort_by_date)
    String sortByDate;
    @StringRes(R.string.sort_by_read)
    String sortByRead;
    @StringRes(R.string.delete)
    String strDelete;
    @StringRes(R.string.push)
    String push;
    @StringRes(R.string.cancel)
    String cancel;
    @StringRes(R.string.refresh)
    String strRefresh;
    @StringRes(R.string.noData)
    String noData;
    @StringRes(R.string.refreshFail)
    String refreshFail;
    @StringRes(R.string.clickRetry)
    String clickRetry;

    @AfterInject
    void afterInject() {
        pushPresenter = new PushPresenter(this, _mActivity);
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterViews() {
        initAdapter();
        refreshView();
        initRefreshLayout();
    }

    private PushPresenter pushPresenter;
    private ArrayList<Push> mList = new ArrayList<>();
    private BaseQuickAdapter mAdapter;
    private boolean mInAtTop = true;
    private boolean mInAtBottom = false;


    @Override
    public void refreshView() {
        mEmptyView.show(true);
        mList.clear();
        new Handler().postDelayed(this::refreshComplete, 1000);
    }

    @UiThread
    void refreshComplete() {
        pushPresenter.getList("");
    }


    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new PushQuickAdapter(_mActivity, mList);
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
        LayoutInflater inflater = LayoutInflater.from(_mActivity);
        orderBy = (TextView) inflater.inflate(R.layout.head_push, null);
        orderBy.setText(strOrderBy + " " + sortByDate + "v");
        orderBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action(0, strSort);
            }
        });
        mAdapter.setHeaderView(orderBy);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            action(position, strDetail);
        });
        mAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            int i = position;
            new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                    .addItem(R.drawable.ic_push_details, strDetail, strDetail)
                    .addItem(R.drawable.ic_user_delete, strDelete, strDelete)
                    .setOnSheetItemClickListener((dialog, itemView, position1, tag) -> {
                        dialog.dismiss();
                        action(i, tag);
                    })
                    .build()
                    .show();
            return false;
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


    private void action(int i, String tag) {
        if (tag.equals(strDetail)) {
            new Handler().postDelayed(() -> EventBusActivityScope.getDefault(_mActivity).post(new RefreshListEvent()), 1000);
            //TODO 查看详情
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(ChartsFragment.newInstance(mList.get(i))));
            setRead(mList.get(i));
        }
        if (tag.equals(strDelete)) {
            //TODO 删除
            showTipDialog(getContext(), tag);
            showMessageNegativeDialog(getContext(), strDelete + push
                    , "确定要" + strDelete + push + ": " + mList.get(i).getTitle() + "吗？"
                    , cancel, strDelete,
                    (dialog, index) -> {
                        pushPresenter.deleteOne(mList.get(i).getId());
                        dialog.dismiss();
                    });
        }
        if (tag.equals(strSearch)) {
            //TODO 搜索
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(SearchFragment.newInstance(SEARCH_PUSH)));
        }
        if (tag.equals(strSort)) {
            //TODO 排序
            QMUIBottomSheet.BottomListSheetBuilder builder =
                    new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                            .addItem(sortByDate)
                            .addItem(sortByRead)
                            .setOnSheetItemClickListener((dialog, itemView, position, tag1) -> {
                                orderBy.setText(strOrderBy + " " + tag1 + "v");
                                pushPresenter.sortList("", tag1);
                                dialog.dismiss();
                            });
            QMUIBottomSheet sheet = builder.build();
            sheet.show();
        }

        if (tag.equals(strRefresh)) {
            //TODO 重新加载
            refreshView();
        }
    }

    private void setRead(Push push) {
        pushPresenter.setRead(push);
    }

    @Override
    public void setList(ArrayList list) {
        mEmptyView.hide();
        this.mList = list;
        setList();
    }

    @UiThread
    void setList() {
        mAdapter.setNewData(mList);
    }

    @Override
    public void refreshFail() {
        mEmptyView.show(noData, "可能时还没有发过推送");
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mEmptyView.show(false, refreshFail, errorMsg, clickRetry, v -> refreshView());
    }

    @Subscribe
    public void onTabSelectedEvent(FirstTabSelectedEvent event) {
        if (event.position != FIRST) return;
        if (mInAtTop) {
            swipeRefresh();
        } else {
            scrollToTop();
        }
    }

    @Subscribe
    public void onTabSelectedEvent(FirstTabMenuEvent event) {
        if (event.position != FIRST) return;
        if (event.tag == null) return;
        action(0, event.tag);
    }

    @Subscribe
    public void onRefreshList(RefreshListEvent event) {
        pushPresenter.getList("");
    }

    @UiThread
    void swipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(() -> {
            pushPresenter.getList("");
            mSwipeRefreshLayout.setRefreshing(false);
        }, 1000);
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
