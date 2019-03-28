package project.ys.glasssystem_r1.ui.fragment.second;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.alpha.QMUIAlphaImageButton;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.indexablerv.IndexableLayout;
import me.yokeyword.indexablerv.SimpleHeaderAdapter;
import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.common.event.TabSelectedEvent;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.mvp.contract.MemberContract;
import project.ys.glasssystem_r1.mvp.presenter.MemberPresenter;
import project.ys.glasssystem_r1.ui.adapter.UserPinyinAdapter;
import project.ys.glasssystem_r1.ui.fragment.common.SearchFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.AddUserFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.UserFragment;
import project.ys.glasssystem_r1.ui.fragment.third.child.UserEditFragment;

import static project.ys.glasssystem_r1.common.constant.Constant.SECOND;
import static project.ys.glasssystem_r1.common.constant.SearchConstant.SEARCH_USER;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITopBarHelper.addBtnItem;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITopBarHelper.addViews;

@EFragment(R.layout.fragment_member)
public class SecondTabFragment extends SupportFragment implements MemberContract.View {

    public static SecondTabFragment newInstance() {
        return new SecondTabFragment_();
    }

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;

    @ViewById(R.id.indexableLayout)
    IndexableLayout indexableLayout;

    QMUIAlphaImageButton addUser;
    QMUIAlphaImageButton searchUser;
    QMUIAlphaImageButton sortBtn;

    @DrawableRes(R.drawable.ic_user_add)
    Drawable icAdd;
    @DrawableRes(R.drawable.ic_user_search)
    Drawable icSearch;
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


    @StringRes(R.string.order_by)
    String strOrderBy;
    @StringRes(R.string.order_by_name)
    String orderByName;
    @StringRes(R.string.order_by_role)
    String orderByRole;
    private String order;

    @AfterInject
    void afterInject() {
        currentUser = CustomerApp.getInstance().getCurrentUser();
        memberPresenter = new MemberPresenter(this, _mActivity);
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initOrder();
        initAdapter();
        refreshView();
    }

    private UserBeanPlus currentUser;
    private MemberPresenter memberPresenter;
    private ArrayList<UserBeanPlus> mList = new ArrayList<>();
    private UserPinyinAdapter userAdapter;
    private boolean mInAtTop = true;
    private boolean mInAtBottom = false;
    private SimpleHeaderAdapter adapter1;
    private SimpleHeaderAdapter adapter2;
    private SimpleHeaderAdapter adapter3;

    private void initTopBar() {

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addUser = addBtnItem(_mActivity, R.drawable.ic_user_add);
        searchUser = addBtnItem(_mActivity, R.drawable.ic_search);
        sortBtn = addBtnItem(_mActivity, R.drawable.ic_sort);
        addUser.setOnClickListener(v -> action(null, strAdd));
        searchUser.setOnClickListener(v -> action(null, strSearch));
        sortBtn.setOnClickListener(v -> action(null, strSort));
        if (currentUser.getNo().startsWith("A"))
            mTopBar.addRightView(addViews(_mActivity, addUser, searchUser, sortBtn), R.id.btn, layoutParams);
        else
            mTopBar.addRightView(addViews(_mActivity, searchUser, sortBtn), R.id.btn, layoutParams);
    }




    private void initOrder() {
        order = orderByName;
    }

    private void initAdapter() {
        indexableLayout.setLayoutManager(new LinearLayoutManager(_mActivity));
        userAdapter = new UserPinyinAdapter(_mActivity);
        indexableLayout.setAdapter(userAdapter);
        indexableLayout.setOverlayStyle_Center();
        indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);
        indexableLayout.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动到底部
                mInAtBottom = !indexableLayout.getRecyclerView().canScrollVertically(1);
                //滑动到顶部
                mInAtTop = !indexableLayout.getRecyclerView().canScrollVertically(-1);

            }
        });
        userAdapter.setOnItemContentClickListener((v, originalPosition, currentPosition, entity) -> {
            action(entity, strDetail);
        });
        userAdapter.setOnItemContentLongClickListener((v, originalPosition, currentPosition, entity) -> {
            if (!currentUser.getNo().startsWith("A"))
                action(entity, strDetail);
            else
                userManage(entity);
            return false;
        });

    }


    private void userManage(UserBeanPlus user) {
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


    private void action(UserBeanPlus user, String tag) {
        if (tag.equals(strAdd)) {
            //TODO 新建账号
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(AddUserFragment.newInstance()));
        }
        if (tag.equals(strSearch)) {
            //TODO 搜索
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(SearchFragment.newInstance(SEARCH_USER)));
        }
        if (tag.equals(strSort)) {
            //TODO 排序
            QMUIBottomSheet.BottomListSheetBuilder builder =
                    new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                            .addItem(orderByName)
                            .addItem(orderByRole)
                            .setOnSheetItemClickListener((dialog, itemView, position, tag1) -> {
                                order = tag1;
                                memberPresenter.userList();
                                dialog.dismiss();
                            });
            QMUIBottomSheet sheet = builder.build();
            sheet.show();
        }
        if (tag.equals(strRefresh)) {
            //TODO 重新加载
            refreshView();
        }
        if (tag.equals(strDetail)) {
            //TODO 查看详情
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(UserFragment.newInstance(user)));
        }
        if (tag.equals(strEdit)) {
            //TODO 修改账号
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(UserEditFragment.newInstance(user)));
        }
        if (tag.equals(strLogOff)) {
            //TODO 注销账号
            showMessageNegativeDialog(getContext(), strLogOff + strAccount
                    , "确定要" + strLogOff + strAccount + ": " + user.getName() + "吗？"
                    , cancel, strLogOff,
                    (dialog, index) -> {
                        memberPresenter.logOff(user.getNo());
                    });
        }
    }

    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != SECOND) return;
        if (!mInAtTop)
            indexableLayout.getRecyclerView().smoothScrollToPosition(0);
    }

    @Subscribe
    public void onRefreshList(RefreshListEvent event) {
        memberPresenter.userList();
    }

    @Override
    public void refreshView() {
        mEmptyView.show(true);
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
        list = removeMySelf(list);
        mEmptyView.hide();
        indexableLayout.removeHeaderAdapter(adapter1);
        indexableLayout.removeHeaderAdapter(adapter2);
        indexableLayout.removeHeaderAdapter(adapter3);
        if (order.equals(orderByName)) {
            userAdapter.setDatas(list);
        } else if (order.equals(orderByRole)) {
            List<List<UserBeanPlus>> lists = divideByRole(list);
            adapter1 = new SimpleHeaderAdapter<>(userAdapter, "G", "管理员", lists.get(0));
            adapter2 = new SimpleHeaderAdapter<>(userAdapter, "S", "生产人员", lists.get(1));
            adapter3 = new SimpleHeaderAdapter<>(userAdapter, "X", "销售人员", lists.get(2));
            userAdapter.getItems().clear();
            indexableLayout.addHeaderAdapter(adapter3);
            indexableLayout.addHeaderAdapter(adapter2);
            indexableLayout.addHeaderAdapter(adapter1);
        }
        userAdapter.notifyDataSetChanged();
    }

    public static ArrayList removeMySelf(ArrayList list) {
        UserBeanPlus currentUser = CustomerApp.getInstance().getCurrentUser();
        List<UserBeanPlus> list0 = list;
        for (int i = 0; i < list0.size(); i++) {
            if (currentUser.getNo().equals(list0.get(i).getNo())) {
                list.remove(i);
            }
        }
        return list;
    }

    public static List<List<UserBeanPlus>> divideByRole(ArrayList list) {
        List<List<UserBeanPlus>> lists = new ArrayList<>();
        List<UserBeanPlus> list1 = new ArrayList<>();
        List<UserBeanPlus> list2 = new ArrayList<>();
        List<UserBeanPlus> list3 = new ArrayList<>();
        for (UserBeanPlus user : (ArrayList<UserBeanPlus>) list) {
            if (user.getRoleName().startsWith("管"))
                list1.add(user);
            if (user.getRoleName().startsWith("生"))
                list2.add(user);
            if (user.getRoleName().startsWith("销"))
                list3.add(user);
        }
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        return lists;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
}
