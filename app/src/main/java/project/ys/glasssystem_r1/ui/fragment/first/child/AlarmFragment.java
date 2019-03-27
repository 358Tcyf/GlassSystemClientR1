package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;

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
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.FirstTabMenuEvent;
import project.ys.glasssystem_r1.common.event.FirstTabSelectedEvent;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.data.entity.Alarm;
import project.ys.glasssystem_r1.mvp.contract.AlarmContract;
import project.ys.glasssystem_r1.mvp.presenter.AlarmPresenter;
import project.ys.glasssystem_r1.ui.adapter.AlarmQuickAdapter;

import static project.ys.glasssystem_r1.common.constant.Constant.FIRST;

@EFragment(R.layout.fragment_alarm)
public class AlarmFragment extends SupportFragment implements AlarmContract.View {
    public static AlarmFragment newInstance() {
        return new AlarmFragment_();
    }

    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @ViewById(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private AlarmPresenter alarmPresenter;
    private ArrayList<Alarm> mList = new ArrayList<>();
    private AlarmQuickAdapter mAdapter;
    private boolean mInAtTop = true;
    private boolean mInAtBottom = false;
    private UserBeanPlus currentUser;

    @StringRes(R.string.detail)
    String strDetail;
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
        alarmPresenter = new AlarmPresenter(this, _mActivity);
        EventBusActivityScope.getDefault(_mActivity).register(this);
        currentUser = CustomerApp.getInstance().getCurrentUser();
    }

    @AfterViews
    void afterViews() {
        initAdapter();
        refreshView();
        initRefreshLayout();
    }


    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new AlarmQuickAdapter(_mActivity, mList);
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

    @Override
    public void refreshView() {
        mEmptyView.show(true);
        mList.clear();
        new Handler().postDelayed(this::refreshComplete, 1000);

    }


    private void action(int i, String tag) {
        if (tag.equals(strDetail)) {
            new Handler().postDelayed(() -> EventBusActivityScope.getDefault(_mActivity).post(new RefreshListEvent()), 1000);
            //TODO 查看详情
            setRead(mList.get(i));
        }

        if (tag.equals(strRefresh)) {
            //TODO 重新加载
            refreshView();
        }
    }

    private void setRead(Alarm alarm) {
        alarmPresenter.setRead(alarm);
    }

    @UiThread
    void refreshComplete() {
        alarmPresenter.getList(currentUser.getNo());
    }

    @Override
    public void refreshFail() {
        mEmptyView.show(noData, "可能时还没有发过推送");
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mEmptyView.show(false, refreshFail, errorMsg, clickRetry, v -> refreshView());

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
    public void onFirstTabMenuEvent(FirstTabMenuEvent event) {
        if (event.position != FIRST) return;
        if (event.tag == null) return;
        action(0, event.tag);
    }

    @Subscribe
    public void onRefreshList(RefreshListEvent event) {
        alarmPresenter.getList("");
    }

    @UiThread
    void swipeRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(() -> {
            alarmPresenter.getList(currentUser.getNo());
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
