package project.ys.glasssystem_r1.support_fragment;

import android.content.Intent;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.contract.LoginContract;
import project.ys.glasssystem_r1.presenter.LoginPresenter;
import project.ys.glasssystem_r1.support_activity.HomeActivity_;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showFailDialog;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showLoadingDialog;
import static project.ys.glasssystem_r1.utils.QMUITipDialogUtil.showTipDialog;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends SupportFragment implements LoginContract.View, View.OnFocusChangeListener {
    public static LoginFragment newInstance() {
        return new LoginFragment_();
    }

    @ViewById(R.id.inputAccount)
    EditText inputAccount;
    @ViewById(R.id.inputPassword)
    EditText inputPassword;
    @ViewById(R.id.accountIcon)
    ImageView accountIcon;
    @ViewById(R.id.passwordIcon)
    ImageView passwordIcon;

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
    private boolean hidden;
    private QMUITipDialog loading;

    @AfterInject
    void afterInject() {
        loginPresenter = new LoginPresenter(this);
    }

    @AfterViews
    void afterViews() {
        hidden = true;
        inputAccount.setOnFocusChangeListener(this);
        inputPassword.setOnFocusChangeListener(this);
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

    @Click(R.id.passwordIcon)
    void passwordHidden() {
        hidden = !hidden;
        if (inputPassword.getInputType() == InputType.TYPE_CLASS_NUMBER) {
            inputPassword.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        } else {
            inputPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        passwordIcon.setImageResource(hidden ?
                (inputPassword.isFocused() ? R.drawable.ic_invisible_selected
                        : R.drawable.ic_invisible)
                : (inputPassword.isFocused() ? R.drawable.ic_visible_selected
                : R.drawable.ic_visible));
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
        getActivity().setTheme(R.style.AppTheme);
        start(HomeFragment.newInstance());
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showFailDialog(getContext(), errorMsg);
        }, 2000);

    }


    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        EditText editText = (EditText) view;
        if (hasFocus) {
            editText.setTextColor(colorPrimaryText);
            editText.setHintTextColor(colorPrimaryText);
        } else {
            editText.setTextColor(colorSecondaryText);
            editText.setHintTextColor(colorSecondaryText);
        }
        switch (view.getId()) {
            case R.id.inputAccount:
                if (hasFocus) {
                    accountIcon.setImageResource(R.drawable.ic_account_selected);
                } else {
                    accountIcon.setImageResource(R.drawable.ic_account);
                }
                break;
            case R.id.inputPassword:
                if (hasFocus) {
                    passwordIcon.setImageResource(hidden ? R.drawable.ic_invisible_selected : R.drawable.ic_visible_selected);
                } else {
                    passwordIcon.setImageResource(hidden ? R.drawable.ic_invisible : R.drawable.ic_visible);
                }
                break;
            default:
        }
    }
}
