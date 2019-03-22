package project.ys.glasssystem_r1.ui.fragment.common;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
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
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.data.bean.UserBeanOrderByName;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.SearchContract;
import project.ys.glasssystem_r1.mvp.presenter.SearchPresenter;
import project.ys.glasssystem_r1.ui.adapter.PushQuickAdapter;
import project.ys.glasssystem_r1.ui.adapter.UserPinyinAdapter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.fragment.first.child.ChartsFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.UserFragment;

import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_CLASS;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_PUSH;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_USER;
import static project.ys.glasssystem_r1.ui.fragment.second.MemberFragment.divideByRole;

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

    @ViewById(R.id.order_by)
    TextView orderBy;

    RecyclerView mRecyclerView;
    @ViewById(R.id.indexableLayout)
    IndexableLayout indexableLayout;

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
    @StringRes(R.string.order_by_name)
    String orderByName;
    @StringRes(R.string.order_by_role)
    String orderByRole;
    @StringRes(R.string.noData)
    String noData;
    @StringRes(R.string.refreshFail)
    String refreshFail;

    SearchView searchBar;
    private int searchClass;
    private SearchPresenter searchPresenter;
    private BaseQuickAdapter mAdapter;
    private UserPinyinAdapter userAdapter;
    private SimpleHeaderAdapter adapter1;
    private SimpleHeaderAdapter adapter2;
    private SimpleHeaderAdapter adapter3;

    private ArrayList<Push> pushList = new ArrayList<>();
    private String searchText;
    private String order;

    @AfterInject
    void afterInject() {
        searchPresenter = new SearchPresenter(this, _mActivity);
        searchClass = getArguments().getInt(SEARCH_CLASS);
    }

    @AfterViews
    void afterView() {
        initTopBar();
        initAdapter();
        mEmptyView.show(noData, "");
    }


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
        orderBy.setOnClickListener(v -> action(strSort));
    }



    private void initAdapter() {
        mRecyclerView = indexableLayout.getRecyclerView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        switch (searchClass) {
            case SEARCH_PUSH:
                mAdapter = new PushQuickAdapter(_mActivity, pushList);
                mAdapter.setOnItemClickListener((adapter, view, position) -> action(position, strDetail));
                mRecyclerView.setAdapter(mAdapter);
                break;
            case SEARCH_USER:
                indexableLayout.setLayoutManager(new LinearLayoutManager(_mActivity));
                userAdapter = new UserPinyinAdapter(_mActivity);
                indexableLayout.setAdapter(userAdapter);
                indexableLayout.setOverlayStyle_Center();
                indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
                userAdapter.setOnItemContentClickListener((v, originalPosition, currentPosition, entity) -> {
                    action(entity, strDetail);
                });
                break;
        }

    }

    private void searchText() {
        initOrderBar();
        switch (searchClass) {
            case SEARCH_PUSH:
                searchPresenter.searchPush(order, searchText);
                break;
            case SEARCH_USER:
                searchPresenter.searchUser(order, searchText);
                break;
        }

    }

    private void action(String tag) {
        if (tag.equals(strSort)) {
            //TODO 排序
            searchBar.clearFocus();
            QMUIBottomSheet.BottomListSheetBuilder builder =
                    new QMUIBottomSheet.BottomListSheetBuilder(_mActivity);
            switch (searchClass) {
                case SEARCH_PUSH:
                    builder.addItem(sortByDate)
                            .addItem(sortByRead);
                    builder.setOnSheetItemClickListener((dialog, itemView, position, tag1) -> {
                        order = tag1;
                        orderBy.setText(strOrderBy + " " + order + "v");
                        searchPresenter.sortList("", order, searchText);
                        dialog.dismiss();
                    });
                    break;
                case SEARCH_USER:
                    builder.addItem(orderByName)
                            .addItem(orderByRole);
                    builder.setOnSheetItemClickListener((dialog, itemView, position, tag1) -> {
                        order = tag1;
                        orderBy.setText(strOrderBy + " " + order + "v");
                        searchPresenter.searchUser( order, searchText);
                        dialog.dismiss();
                    });
                    break;
            }

            QMUIBottomSheet sheet = builder.build();
            sheet.show();
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
    }

    private void action(UserBeanOrderByName user, String tag) {
        if (tag.equals(strDetail)) {
            //TODO 查看详情
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(UserFragment.newInstance(user.getNo(), user.getName())));
        }
    }

    private void setRead(Push push) {
        searchPresenter.setRead(push);
    }


    @Override
    public void showNoData() {
        mEmptyView.show(noData, "查不到结果");
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        mEmptyView.show(refreshFail, errorMsg);
    }


    @Override
    public void setList(ArrayList list) {
        mEmptyView.hide();
        switch (searchClass) {
            case SEARCH_PUSH:
                this.pushList = list;
                mAdapter.setNewData(pushList);
                break;
            case SEARCH_USER:
                indexableLayout.removeHeaderAdapter(adapter1);
                indexableLayout.removeHeaderAdapter(adapter2);
                indexableLayout.removeHeaderAdapter(adapter3);
                if (order.equals(orderByName)) {
                    userAdapter.setDatas(list);
                } else if (order.equals(orderByRole)) {
                    List<List<UserBeanOrderByName>> lists = divideByRole(list);
                    adapter1 = new SimpleHeaderAdapter<>(userAdapter, "G", "管理员", lists.get(0));
                    adapter2 = new SimpleHeaderAdapter<>(userAdapter, "S", "生产人员", lists.get(1));
                    adapter3 = new SimpleHeaderAdapter<>(userAdapter, "X", "销售人员", lists.get(2));
                    userAdapter.getItems().clear();
                    indexableLayout.addHeaderAdapter(adapter1);
                    indexableLayout.addHeaderAdapter(adapter2);
                    indexableLayout.addHeaderAdapter(adapter3);
                }
                userAdapter.notifyDataSetChanged();
                break;
        }

    }


    private void initOrderBar() {
        switch (searchClass) {
            case SEARCH_PUSH:
                order = sortByDate;
                break;
            case SEARCH_USER:
                order = orderByName;
                break;
        }
        orderBy.setText(strOrderBy + " " + order + "v");
        orderBy.setOnClickListener(v -> action(strSort));
    }

}
