package project.ys.glasssystem_r1.ui.widget.customerdialog;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import project.ys.glasssystem_r1.R;

import static project.ys.glasssystem_r1.util.utils.ToastUtils.showNormalToast;

public class EditPwdDialogBuilder extends QMUIDialog.CustomDialogBuilder {

    @BindView(R.id.dialog_password)
    EditText password;

    @BindView(R.id.pwd_input_layout)
    TextInputLayout pwdInputLayout;

    @BindView(R.id.dialog_check_password)
    EditText checkPassword;

    @BindView(R.id.check_pwd_input_layout)
    TextInputLayout checkPwdInputLayout;

    @BindView(R.id.dialog_old_password)
    EditText oldPassword;

    @BindView(R.id.old_pwd_input_layout)
    TextInputLayout oldPwdInputLayout;

    private Context mContext;

    public EditPwdDialogBuilder(Context context) {
        super(context);
        this.mContext = context;

    }

    @Override
    protected void onCreateContent(QMUIDialog dialog, ViewGroup parent, Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_change_pwd, parent);
        ButterKnife.bind(this, view);
    }

    public String getOldPassword() {
        return oldPassword.getText().toString();
    }

    public String getPassword() {
        return password.getText().toString();
    }

    private void showError(TextInputLayout textInputLayout, EditText editText, String error) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error);
        editText.requestFocus();
    }

    private void showError(EditText editText, String error) {
        showNormalToast((Activity) mContext, error);
        editText.requestFocus();
    }

    public boolean validPassword() {
        if (oldPassword.getText().toString().equals("")) {
            showError(oldPassword, "请输入原密码");
            return false;
        }
        if (password.getText().toString().equals("")) {
            showError(password, "密码不能为空");
            return false;
        }
        if (!password.getText().toString().equals(checkPassword.getText().toString())) {
            showError(checkPassword, "密码不一致");
            return false;
        }
        return true;
    }

    @OnTextChanged(value = {R.id.dialog_old_password, R.id.dialog_password, R.id.dialog_check_password}, callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onTextChange() {
        pwdInputLayout.setErrorEnabled(false);
        checkPwdInputLayout.setErrorEnabled(false);
    }
}
