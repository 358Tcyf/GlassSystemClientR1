package project.ys.glasssystem_r1.ui.fragment;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.time.LocalTime;
import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.KeyboardChangeListener;
import project.ys.glasssystem_r1.mvp.contract.LoginContract;
import project.ys.glasssystem_r1.mvp.presenter.LoginPresenter;
import project.ys.glasssystem_r1.ui.activity.HomeActivity_;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.util.TipDialogUtils.showFailDialog;
import static project.ys.glasssystem_r1.util.TipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.util.TipDialogUtils.showTipDialog;

@EFragment(R.layout.fragment_login_new)
public class LoginFragmentNew extends SupportFragment implements LoginContract.View {
    public static LoginFragmentNew newInstance() {
        return new LoginFragmentNew_();
    }

    @ViewById(R.id.login_root)
    RelativeLayout root;
    @ViewById(R.id.tipLayout)
    RelativeLayout tipLayout;
    @ViewById(R.id.backImage)
    ImageView backImage;
    @ViewById(R.id.timeText)
    TextView timeText;
    @ViewById(R.id.inputAccount)
    EditText inputAccount;
    @ViewById(R.id.inputPassword)
    EditText inputPassword;
    @ViewById(R.id.loginBtn)
    Button loginBtn;

    @ViewById(R.id.bottom_view)
    View bottom;

    @StringRes(R.string.logining)
    String logining;
    @StringRes(R.string.noEmpty)
    String noEmpty;
    @StringRes(R.string.passwordEmpty)
    String passwordEmpty;

    @ColorRes(R.color.colorPrimaryText)
    int colorPrimaryText;
    @ColorRes(R.color.colorSecondaryText)
    int colorSecondaryText;
    @ColorRes(R.color.colorText_Icon)
    int colorText_Icon;

    private LoginPresenter loginPresenter;
    private String account;
    private String password;
    private QMUITipDialog loading;

    @AfterInject
    void afterInject() {
        loginPresenter = new LoginPresenter(this);
    }

    @AfterViews
    void afterViews() {
        changeImage();
        new KeyboardChangeListener(_mActivity).setKeyBoardListener(new KeyboardChangeListener.KeyBoardListener() {
            @Override
            public void onKeyboardChange(boolean isShow, int keyboardHeight) {
                RelativeLayout.LayoutParams Params =
                        (RelativeLayout.LayoutParams) bottom.getLayoutParams();
                if (isShow) {
                    tipLayout.setVisibility(View.GONE);
                } else {
                    tipLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @UiThread
    void changeImage() {
        Date time = new Date();
        int hour = time.getHours();
        switch (hour) {
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
                timeText.setText("Morning");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 11:
            case 12:
            case 13:
                timeText.setText("Noon");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 14:
            case 15:
            case 16:
                timeText.setText("Afternoon");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 17:
            case 18:
                timeText.setText("Dawn");
                backImage.setImageResource(R.mipmap.good_morning_img);
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
                timeText.setText("Evening");
                backImage.setImageResource(R.mipmap.good_night_img);
                break;
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                timeText.setText("Night");
                backImage.setImageResource(R.mipmap.good_night_img);
                break;
        }
    }

    @Click(R.id.loginBtn)
    void loginBtn() {
        account = inputAccount.getText().toString();
        password = inputPassword.getText().toString();
        if (validInput()) {
            loading = showLoadingDialog(getContext(), logining);
            loading.show();
            loginPresenter.login(account, password);
        }
    }


    @Override
    public boolean validInput() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        if (isEmpty(account)) {
            showTipDialog(getContext(), noEmpty);
//            inputAccount.startAnimation(shake);
            return false;
        } else if (isEmpty(password)) {
            showTipDialog(getContext(), passwordEmpty);
//            inputPassword.startAnimation(shake);
            return false;
        }
        return true;
    }

    @Override
    public void toHome() {
        new Handler().postDelayed(() -> {
            loading.dismiss();
//            toHomeActivity();
            toHomeFragment();
        }, 2000);
    }

    public void toHomeActivity() {
        Intent intent = new Intent(getContext(), HomeActivity_.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void toHomeFragment() {
        _mActivity.setTheme(R.style.AppTheme);
        startWithPop(HomeFragment.newInstance());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showFailDialog(getContext(), errorMsg);
        }, 2000);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
