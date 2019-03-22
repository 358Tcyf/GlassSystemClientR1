package project.ys.glasssystem_r1.ui.fragment.second;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.indexablerv.IndexableLayout;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.common.event.TabSelectedEvent;
import project.ys.glasssystem_r1.data.bean.UserWithRoleBean;
import project.ys.glasssystem_r1.mvp.contract.MemberContract;
import project.ys.glasssystem_r1.mvp.presenter.MemberPresenter;
import project.ys.glasssystem_r1.ui.adapter.UserPinyinAdapter;
import project.ys.glasssystem_r1.ui.fragment.common.HomeFragment;
import project.ys.glasssystem_r1.ui.fragment.common.HomeFragmentNew;
import project.ys.glasssystem_r1.ui.fragment.first.child.ChartsFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.AddUserFragment;
import project.ys.glasssystem_r1.ui.fragment.second.child.UserFragment;

import static project.ys.glasssystem_r1.common.constant.Constant.SECOND;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showTipDialog;

@EFragment(R.layout.fragment_member)
public class MemberFragment extends SupportFragment implements MemberContract.View {

    public static MemberFragment newInstance() {
        return new MemberFragment_();
    }

    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.emptyView)
    QMUIEmptyView mEmptyView;
    @ViewById(R.id.indexableLayout)
    IndexableLayout indexableLayout;

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

    @AfterInject
    void afterInject() {
        memberPresenter = new MemberPresenter(this);
        EventBusActivityScope.getDefault(_mActivity).register(this);
    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initAdapter();
        refreshView();
    }

    private MemberPresenter memberPresenter;
    private ArrayList<UserWithRoleBean> mList = new ArrayList<>();
    private UserPinyinAdapter mAdapter;
    private boolean mInAtTop = true;
    private boolean mInAtBottom = false;

    private void initTopBar() {
        mTopBar.addLeftImageButton(R.drawable.ic_icon_workmore, R.id.more)
                .setOnClickListener(view ->
                        showBottomSheetList());

    }


    private void initAdapter() {
        indexableLayout.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new UserPinyinAdapter(_mActivity);
        indexableLayout.setAdapter(mAdapter);
        indexableLayout.setOverlayStyle_MaterialDesign(colorPrimary);
        indexableLayout.setCompareMode(IndexableLayout.MODE_ALL_LETTERS);
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
        mAdapter.setOnItemContentClickListener((v, originalPosition, currentPosition, entity) -> {
            action(entity, strDetail);
        });
        mAdapter.setOnItemContentLongClickListener((v, originalPosition, currentPosition, entity) -> {
            //            userInfo(entity);
            userManage(entity);
            return false;
        });

    }


    private void userManage(UserWithRoleBean user) {
        String no = user.getNo();
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

    private void userInfo(UserWithRoleBean entity) {
        String no = entity.getNo();
    }


    private void showBottomSheetList() {
        QMUIBottomSheet.BottomListSheetBuilder builder =
                new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                        .addItem(icAdd, strAdd)
                        .addItem(icSearch, strSearch)
                        .addItem(icRefresh, strRefresh)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            action(null, tag);
                        });
        QMUIBottomSheet sheet = builder.build();
        sheet.show();
    }

    private void action(UserWithRoleBean user, String tag) {
        if (tag.equals(strAdd)) {
            //TODO 新建账号
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(AddUserFragment.newInstance()));
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
            EventBusActivityScope.getDefault(_mActivity).post(new StartBrotherEvent(UserFragment.newInstance(user.getNo(), user.getName())));
        }
        if (tag.equals(strEdit)) {
            //TODO 修改账号
            showTipDialog(getContext(), tag);
        }
        if (tag.equals(strLogOff)) {
            // 注销账号
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
        mAdapter.setDatas(null);
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
        mEmptyView.hide();
        mAdapter.setDatas(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
    }
}
