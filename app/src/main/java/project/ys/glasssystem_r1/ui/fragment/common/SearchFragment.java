package project.ys.glasssystem_r1.ui.fragment.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.SearchContract;
import project.ys.glasssystem_r1.mvp.presenter.SearchPresenter;
import project.ys.glasssystem_r1.ui.adapter.PushQuickAdapter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.ChartsFragment;

import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_CLASS;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_PUSH;

@EFragment(R.layout.fragment_search)
public class SearchFragment extends BaseBackFragment implements SearchContract.View {
    public static SearchFragment newInstance() {
        return new SearchFragment_();
    }

    public static SearchFragment newInstance(int searchClass) {
        Bundle args = new Bundle();
        args.putInt(SEARCH_CLASS, searchClass);
        SearchFragment fragment = new SearchFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;

    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;


    @StringRes(R.string.detail)
    String strDetail;
    @StringRes(R.string.sort)
    String strSort;
    @StringRes(R.string.order_by)
    String strOrderBy;
    @StringRes(R.string.sort_by_date)
    String sortByDate;
    @StringRes(R.string.sort_by_read)
    String sortByRead;
    @StringRes(R.string.noData)
    String noData;
    @StringRes(R.string.refreshFail)
    String refreshFail;

    SearchView searchBar;
    TextView orderBy;
    private int searchClass;
    private SearchPresenter searchPresenter;
    private BaseQuickAdapter mAdapter;
    private ArrayList<Push> pushList = new ArrayList<>();
    private String searchText;

    @AfterInject
    void afterInject() {
        searchPresenter = new SearchPresenter(this, _mActivity);
        searchClass = getArguments().getInt(SEARCH_CLASS);
    }

    @AfterViews
    void afterView() {
        initTopBar();
        initAdapter();
        mEmptyView.show(noData, "");    }


    private void initTopBar() {
        mEmptyView.hide();
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
        mTopBar.setTitle(R.string.search).setTextColor(_mActivity.getColor(R.color.colorText_Icon));
        LayoutInflater inflater = LayoutInflater.from(_mActivity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMarginStart(QMUIDisplayHelper.dp2px(_mActivity, 48));
        searchBar = (SearchView) inflater.inflate(R.layout.search_bar, null);
        mTopBar.addRightView(searchBar, R.id.search, layoutParams);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchText = s;
                searchText();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        if (searchClass == SEARCH_PUSH) {
            mAdapter = new PushQuickAdapter(_mActivity, pushList);
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    private void searchText() {
        addHeaderView();
        if (searchClass == SEARCH_PUSH) {
            searchPresenter.searchPush("", searchText);
        }
    }

    private void action(int i, String tag) {
        if (tag.equals(strDetail)) {
            new Handler().postDelayed(() -> EventBusActivityScope.getDefault(_mActivity).post(new RefreshListEvent()), 1000);
            //TODO 查看详情
            if (searchClass == SEARCH_PUSH) {
                EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(ChartsFragment.newInstance(pushList.get(i))));
                setRead(pushList.get(i));
            }
        }
        if (tag.equals(strSort)) {
            //TODO 排序
            searchBar.clearFocus();
            QMUIBottomSheet.BottomListSheetBuilder builder =
                    new QMUIBottomSheet.BottomListSheetBuilder(_mActivity);
            if (searchClass == SEARCH_PUSH) {
                builder.addItem(sortByDate)
                        .addItem(sortByRead);
            }
            builder.setOnSheetItemClickListener((dialog, itemView, position, tag1) -> {
                orderBy.setText(strOrderBy + " " + tag1 + "v");
                searchPresenter.sortList("", tag1, searchText);
                dialog.dismiss();
            });
            QMUIBottomSheet sheet = builder.build();
            sheet.show();
        }
    }

    private void setRead(Push push) {
        searchPresenter.setRead(push);
    }


    @Override
    public void showNoData() {
        mAdapter.removeAllHeaderView();
        mEmptyView.show(noData, "查不到结果");
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mEmptyView.show(refreshFail, errorMsg);
    }


    @Override
    public void setList(ArrayList list) {
        mEmptyView.hide();
        this.pushList = list;
        mAdapter.setNewData(pushList);
    }


    private void addHeaderView() {
        LayoutInflater inflater = LayoutInflater.from(_mActivity);
        if (searchClass == SEARCH_PUSH) {
            orderBy = (TextView) inflater.inflate(R.layout.head_push, null);
            orderBy.setText(strOrderBy + " " + sortByDate + "v");
            orderBy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    action(0, strSort);
                }
            });
            mAdapter.setHeaderView(orderBy);
        }
    }

}
