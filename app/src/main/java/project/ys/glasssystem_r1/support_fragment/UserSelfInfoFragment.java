package project.ys.glasssystem_r1.support_fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.stone.vega.library.VegaLayoutManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.adapter.MenuItemQuickAdapter;
import project.ys.glasssystem_r1.bean.MenuItemBean;

import static project.ys.glasssystem_r1.constant.UserConstant.USER_ACCOUNT;
import static project.ys.glasssystem_r1.constant.UserConstant.USER_NAME;

@EFragment(R.layout.fragment_self_detail)
public class UserSelfInfoFragment extends SupportFragment {

    public static UserSelfInfoFragment newInstance(String no, String name) {
        Bundle args = new Bundle();
        args.putString(USER_ACCOUNT, no);
        args.putString(USER_NAME, name);
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

    private String name;
    private String no;


    @AfterViews
    void afterViews() {
        initList();
        initAdapter();
    }


    private ArrayList<MenuItemBean> mList;
    private BaseQuickAdapter mAdapter;

    private void initList() {
        no = getArguments().getString(USER_ACCOUNT);
        name = getArguments().getString(USER_NAME);
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

}
