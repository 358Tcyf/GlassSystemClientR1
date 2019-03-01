package project.ys.glasssystem_r1.ui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.stone.vega.library.VegaLayoutManager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.ui.adapter.MenuItemQuickAdapter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.data.bean.MenuItemBean;
import project.ys.glasssystem_r1.data.bean.UserBean;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.mvp.presenter.UserDetailPresenter;

import static project.ys.glasssystem_r1.common.UserConstant.EMAIL;
import static project.ys.glasssystem_r1.common.UserConstant.NAME;
import static project.ys.glasssystem_r1.common.UserConstant.PHONE;
import static project.ys.glasssystem_r1.common.UserConstant.USER_ACCOUNT;

@EFragment(R.layout.fragment_self_detail)
public class UserSelfInfoFragment extends BaseBackFragment implements UserDetailContract.View {

    public static UserSelfInfoFragment newInstance(String no, String name) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        UserSelfInfoFragment fragment = new UserSelfInfoFragment_();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @DrawableRes(R.drawable.ic_icon_email)
    Drawable iconEmail;
    @DrawableRes(R.drawable.ic_icon_mobilephone)
    Drawable iconPhone;

    @StringArrayRes(R.array.userSelfDetails)
    String[] selfDetails;

    private String no;

    @AfterInject
    void afterInject() {
        userDetailPresenter = new UserDetailPresenter(this);
        no = getArguments().getString(USER_ACCOUNT);

    }


    @AfterViews
    void afterViews() {
        initList();
        initAdapter();
    }

    private UserDetailPresenter userDetailPresenter;
    private ArrayList<MenuItemBean> mList;
    private BaseQuickAdapter mAdapter;

    private void initList() {
        userDetailPresenter.getDetail(no);
        mList = new ArrayList<>();
        mList.add(new MenuItemBean(selfDetails[0], "xx"));
        mList.add(new MenuItemBean(selfDetails[1], "xx", iconEmail));
        mList.add(new MenuItemBean(selfDetails[2], "xx", iconPhone));
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new VegaLayoutManager());
        mAdapter = new MenuItemQuickAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setDetail(UserBean user, String roleName) {
        mList.get(NAME).setDetailText(user.getName());
        mList.get(EMAIL).setDetailText(user.getEmail());
        mList.get(PHONE).setDetailText(user.getPhone());
        resetList();
    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @UiThread
    void resetList() {
        mAdapter.setNewData(mList);
    }
}
