package project.ys.glasssystem_r1.ui.fragment.second.child.user_hild;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.mvp.contract.UserDetailContract;
import project.ys.glasssystem_r1.mvp.presenter.UserDetailPresenter;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;
import static project.ys.glasssystem_r1.util.utils.DateUtils.getNowTime;


@EFragment(R.layout.fragment_section_detail)
public class SectionInfoFragment extends SupportFragment implements UserDetailContract.View {

    private static final String CHECK_USER = "check_user";

    public static SectionInfoFragment newInstance(UserBeanPlus user) {
        Bundle args = new Bundle();
        args.putParcelable(CHECK_USER, user);
        SectionInfoFragment fragment = new SectionInfoFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @ViewById(R.id.user_name)
    TextView userName;
    @ViewById(R.id.user_role)
    TextView userRole;
    @ViewById(R.id.user_no)
    TextView userNo;
    @ViewById(R.id.user_pic)
    QMUIRadiusImageView userPic;
    @ViewById(R.id.user_email)
    QMUILinkTextView userEmail;
    @ViewById(R.id.user_phone)
    QMUILinkTextView userPhone;


    private UserDetailPresenter userDetailPresenter;
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
        initCard();
    }

    private void initCard() {
        userDetailPresenter.getDetail(currentUser.getNo());

    }

    @Override
    public void setDetail(UserBeanPlus user) {
        this.currentUser = user;
        resetCard();
    }

    @Override
    public void showErrorView(String errorMsg) {

    }

    @UiThread
    void resetCard() {
        if (userName != null) {
            userName.setText(currentUser.getName());
        }
        if (userRole != null) {
            userRole.setText(currentUser.getRoleName());
        }
        if (userNo != null)
            userNo.setText(currentUser.getNo());
        setUserPic();
        if (userEmail != null) {
            userEmail.setText(currentUser.getEmail());
        }
        if (userPhone != null) {
            userPhone.setText(currentUser.getPhone());
        }
    }

    @UiThread
    void setUserPic() {
        if (!isEmpty(currentUser.getPicPath())) {
            Glide.with(this)
                    .load(Uri.parse(HTTP + getURL() + PORT + currentUser.getPicPath() + "/" + getNowTime()))
                    .apply(new RequestOptions().error(R.mipmap.ic_account_circle))
                    .into(userPic);
        }
    }
}
