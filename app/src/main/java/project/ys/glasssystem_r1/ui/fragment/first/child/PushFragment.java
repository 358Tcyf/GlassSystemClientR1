package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

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
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.FirstTabMenuEvent;
import project.ys.glasssystem_r1.common.event.FirstTabSelectedEvent;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.data.bean.PushSelectedBean;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.presenter.PushPresenter;
import project.ys.glasssystem_r1.ui.adapter.PushQuickAdapter;
import project.ys.glasssystem_r1.ui.fragment.common.SearchFragment;

import static project.ys.glasssystem_r1.common.constant.Constant.DEFAULT_LIMIT;
import static project.ys.glasssystem_r1.common.constant.Constant.FIRST;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_PUSH;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showTipDialog;

@EFragment(R.layout.fragment_push)
public class PushFragment extends SupportFragment implements PushContract.View {
    public static PushFragment newInstance() {
        return new PushFragment_();
    }

    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    LayoutInflater inflater;
    TextView listCountView;
    RelativeLayout selectBar;
    RadioButton allSelectRadio;
    QMUIRoundButton allSelectBtn;
    QMUIRoundButton deleteSelectBtn;
    QMUIRoundButton cancelSelectBtn;


    @DrawableRes(R.drawable.ic_user_search)
    Drawable icSearch;
    @DrawableRes(R.drawable.ic_user_sort)
    Drawable icSort;
    @DrawableRes(R.drawable.ic_user_refresh)
    Drawable icRefresh;

    @StringRes(R.string.detail)
    String strDetail;
    @StringRes(R.string.sync)
    String strSync;
    @StringRes(R.string.upload)
    String upload;
    @StringRes(R.string.download)
    String download;
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
    @StringRes(R.string.manager)
    String strManager;
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
        currentUser = CustomerApp.getInstance().getCurrentUser();
    }

    @AfterViews
    void afterViews() {
        initAdapter();
        refreshView();
        initRefreshLayout();
    }

    private PushPresenter pushPresenter;
    private ArrayList<Push> mList = new ArrayList<>();
    private ArrayList<PushSelectedBean> selects = new ArrayList<>();
    private PushQuickAdapter mAdapter;
    private boolean mInAtTop = true;
    private boolean mInAtBottom = false;
    boolean selectShow = false;
    private UserBeanPlus currentUser;
    private int limit = DEFAULT_LIMIT;

    @Override
    public void refreshView() {
        mEmptyView.show(true);
        mList.clear();
        new Handler().postDelayed(this::refreshComplete, 1000);
    }

    @UiThread
    void refreshComplete() {
        pushPresenter.getList(currentUser.getNo(), limit);
    }


    @SuppressLint("SetTextI18n")
    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new PushQuickAdapter(_mActivity, selects);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                int total_count = pushPresenter.getTotal(currentUser.getNo());
                if (mList.size() >= total_count) {
                    //数据全部加载完毕
                    mAdapter.loadMoreEnd();
                } else {
                    mRecyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mList.size() >= total_count) {
                                //数据全部加载完毕
                                mAdapter.loadMoreEnd();
                            } else {
                                //成功获取更多数据
                                limit += DEFAULT_LIMIT;
                                pushPresenter.getList(currentUser.getNo(), limit);
                                mAdapter.loadMoreComplete();
                            }
                        }

                    }, 2000);
                }

            }
        }, mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动到底部
                mInAtBottom = !mRecyclerView.canScrollVertically(1);
                //滑动到顶部
                mInAtTop = !mRecyclerView.canScrollVertically(-1);
                if (mInAtBottom) {
                    limit += DEFAULT_LIMIT;
                    pushPresenter.getList(currentUser.getNo(), limit);
                }

            }
        });
        inflater = LayoutInflater.from(_mActivity);

        selectBar = (RelativeLayout) inflater.inflate(R.layout.head_select, null);
        allSelectRadio = selectBar.findViewById(R.id.all_select_radio);
        allSelectRadio.setEnabled(false);
        allSelectBtn = selectBar.findViewById(R.id.all_select_btn);
        deleteSelectBtn = selectBar.findViewById(R.id.delete_btn);
        cancelSelectBtn = selectBar.findViewById(R.id.cancel_btn);

        allSelectBtn.setOnClickListener(clickListener);
        deleteSelectBtn.setOnClickListener(clickListener);
        cancelSelectBtn.setOnClickListener(clickListener);

        mAdapter.setOnItemClickListener(normalItemChildClickListener);
        mAdapter.setOnItemLongClickListener(normalItemLongClickListener);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all_select_btn:
                    allSelectRadio.setChecked(!allSelectRadio.isChecked());
                    if (allSelectRadio.isChecked()) {
                        for (PushSelectedBean select : selects) {
                            select.setSelected(true);
                        }
                    } else {
                        for (PushSelectedBean select : selects) {
                            select.setSelected(false);
                        }
                    }
                    mAdapter.setNewData(selects);
                    break;
                case R.id.delete_btn:
                    for (PushSelectedBean select : selects) {
                        if (select.isSelected()) {
                            pushPresenter.deleteOne(select.getPush().getId());
                        }
                    }
                    cancelSelectBar();
                    break;
                case R.id.cancel_btn:
                    selects.clear();
                    cancelSelectBar();
                    break;
            }
        }
    };

    private void cancelSelectBar() {
        mAdapter.toggleSelected();
        mAdapter.notifyDataSetChanged();
        mAdapter.removeAllHeaderView();
        mAdapter.setHeaderView(listCountView);
        mAdapter.setOnItemClickListener(normalItemChildClickListener);
        mAdapter.setOnItemLongClickListener(normalItemLongClickListener);
        selectShow = false;
        allSelectRadio.setChecked(false);
        pushPresenter.getList(currentUser.getNo(), limit);
    }

    BaseQuickAdapter.OnItemClickListener normalItemChildClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            action(position, strDetail);

        }
    };
    BaseQuickAdapter.OnItemLongClickListener normalItemLongClickListener = new BaseQuickAdapter.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
            action(0, strManager);
            return false;
        }
    };
    BaseQuickAdapter.OnItemClickListener selectedItemChildClickListener = new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            selects.get(position).setSelected(!selects.get(position).isSelected());
            mAdapter.notifyDataSetChanged();
        }
    };
    BaseQuickAdapter.OnItemLongClickListener selectedItemLongClickListener = new BaseQuickAdapter.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            return false;
        }
    };


    private void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(QMUIResHelper
                .getAttrColor(_mActivity, R.attr.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            new Handler().postDelayed(() -> {
                mSwipeRefreshLayout.setRefreshing(false);
            }, 1000);

        });
    }


    private void action(int i, String tag) {
        if (tag.equals(strDetail)) {
           EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(PagersFragment.newInstance(mList.get(i))));
        }
        if (tag.equals(strDelete)) {
            showTipDialog(getContext(), tag);
            showMessageNegativeDialog(getContext(), strDelete + push
                    , "确定要" + strDelete + push + ": " + mList.get(i).getTitle() + "吗？"
                    , cancel, strDelete,
                    (dialog, index) -> {
                        pushPresenter.deleteOne(mList.get(i).getId());
                        dialog.dismiss();
                    });
        }
        if (tag.equals(strSync)) {
            QMUIBottomSheet.BottomListSheetBuilder builder =
                    new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                            .addItem(upload)
                            .addItem(download)
                            .setOnSheetItemClickListener((dialog, itemView, position, tag1) -> {
                                action(0, tag1);
                                dialog.dismiss();
                            });
            QMUIBottomSheet sheet = builder.build();
            sheet.show();
        }
        if (tag.equals(upload)) {
            pushPresenter.upload(currentUser.getNo());
        }
        if (tag.equals(download)) {
            pushPresenter.download(currentUser.getNo());
            refreshView();
        }
        if (tag.equals(strSearch)) {
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(SearchFragment.newInstance(SEARCH_PUSH)));
        }
        if (tag.equals(strSort)) {
            QMUIBottomSheet.BottomListSheetBuilder builder =
                    new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                            .addItem(sortByDate)
                            .addItem(sortByRead)
                            .setOnSheetItemClickListener((dialog, itemView, position, tag1) -> {
                                pushPresenter.sortList(currentUser.getNo(), limit, tag1);
                                dialog.dismiss();
                            });
            QMUIBottomSheet sheet = builder.build();
            sheet.show();
        }
        if (tag.equals(strManager)) {
            selects.clear();
            selectShow = true;
            for (Push push : mList) {
                selects.add(new PushSelectedBean(false, push));
            }
            mAdapter.toggleSelected();
            mAdapter.removeAllHeaderView();
            mAdapter.setHeaderView(selectBar);
            mAdapter.setOnItemClickListener(selectedItemChildClickListener);
            mAdapter.setOnItemLongClickListener(selectedItemLongClickListener);
            mAdapter.notifyDataSetChanged();
        }
        if (tag.equals(strRefresh)) {
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
        this.limit = list.size();
        setList();
    }

    @UiThread
    void setList() {
        selects.clear();
        for (Push push : mList) {
            selects.add(new PushSelectedBean(false, push));
        }
        mAdapter.setNewData(selects);
        if (listCountView == null) {
            listCountView = (TextView) inflater.inflate(R.layout.head_count, null);
            listCountView.setHeight(QMUIDisplayHelper.dp2px(_mActivity, 40));
            mAdapter.setHeaderView(listCountView);
        }
        listCountView.setText("共 " + mList.size() + " 条");
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
        if (event.position == FIRST) {
            if (mInAtTop) {
                swipeRefresh();
            } else {
                scrollToTop();
            }
        }
    }

    @Subscribe
    public void onFirstTabMenuEvent(FirstTabMenuEvent event) {
        if (event.position == FIRST) {
            if (event.tag != null)
                action(0, event.tag);
        }
    }

    @Subscribe
    public void onRefreshList(RefreshListEvent event) {
        pushPresenter.getList(currentUser.getNo(), limit);
    }

    @UiThread
    void swipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(() -> {
            pushPresenter.getList(currentUser.getNo(), limit);
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

    @Override
    public boolean onBackPressedSupport() {
        if (selectShow) {
            cancelSelectBar();
            return true;
        } else
            return super.onBackPressedSupport();
    }
}
