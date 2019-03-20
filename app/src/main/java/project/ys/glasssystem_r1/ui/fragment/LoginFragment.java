package project.ys.glasssystem_r1.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.tencent.mmkv.MMKV;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.Date;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.KeyboardChangeListener;
import project.ys.glasssystem_r1.mvp.contract.LoginContract;
import project.ys.glasssystem_r1.mvp.presenter.LoginPresenter;
import project.ys.glasssystem_r1.ui.activity.HomeActivity_;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.URL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.changeURL;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showFailDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showSuccessDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showTipDialog;

@EFragment(R.layout.fragment_login_new)
public class LoginFragment extends SupportFragment implements LoginContract.View {
    public static LoginFragment newInstance() {
        return new LoginFragment_();
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
    @ViewById(R.id.inputPasswordLayout)
    TextInputLayout inputPasswordLayout;
    @ViewById(R.id.inputPassword)
    EditText inputPassword;
    @ViewById(R.id.loginBtn)
    Button loginBtn;

    @ViewById(R.id.bottom_view)
    View bottom;

    @StringRes(R.string.logining)
    String logining;
    @StringRes(R.string.updating)
    String updating;
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
        inputPasswordLayout.setPasswordVisibilityToggleDrawable(R.drawable.icon_pwd_selector);
        MMKV user = MMKV.defaultMMKV();
        account = user.decodeString("userAccount");
        password = user.decodeString("userPassword");
        inputAccount.setText(account);
        inputPassword.setText(password);

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

    @Click(R.id.forgot_password)
    void resetPassword() {
        account = inputAccount.getText().toString();
        if (!isEmpty(account)) {
            new QMUIDialog.MessageDialogBuilder(getActivity())
                    .setTitle("这将会重置密码")
                    .setMessage("确定要重置吗？")
                    .addAction("取消", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            dialog.dismiss();
                        }
                    })
                    .addAction(0, "重置", QMUIDialogAction.ACTION_PROP_NEGATIVE, new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            loading = showLoadingDialog(getContext(), updating);
                            loading.show();
                            loginPresenter.resetPassword(account);
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }

    }


    @Click(R.id.changeUrl)
    void changeUrl() {
        showEditTextDialog();
    }

    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("修改网络通信地址")
                .setDefaultText(getURL())
//                .setPlaceholder(URL)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("默认", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        builder.getEditText().setText(URL);
                        changeURL(URL);
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        changeURL(text.toString());
                        dialog.dismiss();
                    }
                });
        builder.create();
        builder.show();
    }

    @Override
    public boolean validInput() {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        if (isEmpty(account)) {
            showTipDialog(getContext(), noEmpty);
            inputAccount.startAnimation(shake);
            return false;
        } else if (isEmpty(password)) {
            showTipDialog(getContext(), passwordEmpty);
            inputPassword.startAnimation(shake);
            return false;
        }
        return true;
    }

    @Override
    public void toHome() {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            toHomeActivity();
//            toHomeFragment();
        }, 2000);
    }


    public void toHomeActivity() {
        MMKV user = MMKV.defaultMMKV();
        user.encode("userAccount", inputAccount.getText().toString());
        user.encode("userPassword", inputPassword.getText().toString());
        Intent intent = new Intent(getContext(), HomeActivity_.class);
        startActivity(intent);
        getActivity().finish();
    }

    public void toHomeFragment() {
        MMKV user = MMKV.defaultMMKV();
        user.encode("userAccount", inputAccount.getText().toString());
        user.encode("userPassword", inputPassword.getText().toString());
        _mActivity.setTheme(R.style.AppTheme);
        startWithPop(HomeFragment.newInstance(user.decodeString("userAccount")));
    }


    @Override
    public void showOkMsg(String msg) {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showSuccessDialog(getContext(), msg);
        }, 2000);
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
