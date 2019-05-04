package project.ys.glasssystem_r1.ui.fragment.second.child.child;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.MenuItemBean;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.mvp.presenter.UserDetailPresenter;
import project.ys.glasssystem_r1.ui.adapter.MenuItemQuickAdapter;

import static project.ys.glasssystem_r1.common.constant.UserConstant.EMAIL;
import static project.ys.glasssystem_r1.common.constant.UserConstant.NAME;
import static project.ys.glasssystem_r1.common.constant.UserConstant.PHONE;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showTipDialog;
import static project.ys.glasssystem_r1.util.utils.AccountValidatorUtil.isChinese;
import static project.ys.glasssystem_r1.util.utils.AccountValidatorUtil.isEmail;
import static project.ys.glasssystem_r1.util.utils.AccountValidatorUtil.isMobile;
import static project.ys.glasssystem_r1.util.utils.ToastUtils.showNormalToast;

@EFragment(R.layout.fragment_self_detail)
public class SelfInfoFragment extends SupportFragment implements UserDetailContract.View {

    private static final String CHECK_USER = "check_user";

    public static SelfInfoFragment newInstance(UserBeanPlus user) {
        Bundle args = new Bundle();
        args.putParcelable(CHECK_USER, user);
        SelfInfoFragment fragment = new SelfInfoFragment_();
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
    @StringArrayRes(R.array.userSelfAction)
    String[] actions;


    private UserBeanPlus currentUser;

    @AfterInject
    void afterInject() {
        userDetailPresenter = new UserDetailPresenter(this);
        Bundle args = getArguments();
        if (args != null) {
            currentUser = args.getParcelable(CHECK_USER);
        }

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
        userDetailPresenter.getDetail(currentUser.getNo());
        mList = new ArrayList<>();
        mList.add(new MenuItemBean(selfDetails[0], currentUser.getName()));
        mList.add(new MenuItemBean(selfDetails[1], "", iconEmail));
        mList.add(new MenuItemBean(selfDetails[2], "", iconPhone));
    }

    private void initAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new MenuItemQuickAdapter(getActivity(), mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (isChinese(mList.get(position).getDetailText())) {

                }
                if (isEmail(mList.get(position).getDetailText())) {
                    new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                            .addItem(actions[0])
                            .setOnSheetItemClickListener((dialog, itemView, index, tag) -> {
                                action(tag,mList.get(position).getDetailText());
                                dialog.dismiss();
                            }).build().show();
                }
                if (isMobile(mList.get(position).getDetailText())) {
                    new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                            .addItem(actions[1])
                            .addItem(actions[2])
                            .setOnSheetItemClickListener((dialog, itemView, index, tag) -> {
                                action(tag,mList.get(position).getDetailText());
                                dialog.dismiss();
                            }).build().show();
                }
            }
        });
    }
    private void action(String tag,String detail) {
        if (tag.equals(actions[0])) {
            //TODO 发送邮件
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,
                    new String[] {detail});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            startActivity(Intent.createChooser(intent,
                    ""));

        }
        if (tag.equals(actions[1])) {
            //TODO 拨打电话
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + detail);
            intent.setData(data);
            startActivity(intent);
        }
        if (tag.equals(actions[2])) {
            //TODO 发送短信
            Uri smsToUri = Uri.parse("smsto:"+detail);
            Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
            intent.putExtra("sms_body", "");
            startActivity(intent);
        }

    }
    @Override
    public void setDetail(UserBeanPlus user) {
        mList.get(NAME).setDetailText(user.getName());
        mList.get(EMAIL).setDetailText(user.getEmail());
        mList.get(PHONE).setDetailText(user.getPhone());
        resetList();
    }

    @Override
    public void showErrorView(String errorMsg) {

    }

    @UiThread
    void resetList() {
        mAdapter.setNewData(mList);
    }
}
