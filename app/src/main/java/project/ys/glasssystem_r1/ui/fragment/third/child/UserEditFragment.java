package project.ys.glasssystem_r1.ui.fragment.third.child;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lancewu.imagepicker.ImagePicker;
import com.lancewu.imagepicker.ImagePickerResult;
import com.lancewu.imagepicker.OnImagePickerCallback;
import com.lancewu.imagepicker.util.StreamUtils;
import com.qmuiteam.qmui.widget.QMUICollapsingTopBarLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorRes;
import org.androidannotations.annotations.res.StringRes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.mvp.contract.UserEditContract;
import project.ys.glasssystem_r1.mvp.presenter.UserEditPresenter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.widget.customerdialog.EditPwdDialogBuilder;

import static android.text.TextUtils.isEmpty;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.HTTP;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.PORT;
import static project.ys.glasssystem_r1.common.constant.HttpConstant.getURL;
import static project.ys.glasssystem_r1.util.utils.DateUtils.getNowTime;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showFailDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showSuccessDialog;
import static project.ys.glasssystem_r1.util.utils.ToastUtils.showNormalToast;

@EFragment
public class UserEditFragment extends BaseBackFragment implements UserEditContract.View {

    private static final String CHECK_USER = "check_user";

    public static UserEditFragment newInstance() {
        return new UserEditFragment_();
    }

    public static UserEditFragment newInstance(UserBeanPlus user) {
        Bundle args = new Bundle();
        args.putParcelable(CHECK_USER, user);
        UserEditFragment fragment = new UserEditFragment_();
        fragment.setArguments(args);
        return fragment;
    }


    @ViewById(R.id.collapsing_topbar_layout)
    QMUICollapsingTopBarLayout mCollapsingTopBarLayout;
    @ViewById(R.id.topbar)
    QMUITopBar mTopBar;
    @ViewById(R.id.imageView)
    ImageView userPic;
    @ViewById(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    @ColorRes(R.color.colorText_Icon)
    int topbarText;


    @StringRes(R.string.save)
    String strSave;
    @StringRes(R.string.saving)
    String saving;
    @StringRes(R.string.saveSuccess)
    String saveSuccess;
    @StringRes(R.string.openCamera)
    String openCamera;
    @StringRes(R.string.openGallery)
    String openGallery;
    @StringRes(R.string.uploadPic)
    String uploadPic;
    @StringRes(R.string.name)
    String strName;
    @StringRes(R.string.userNo)
    String strAccount;
    @StringRes(R.string.userSection)
    String strSection;
    @StringRes(R.string.email)
    String strEmail;
    @StringRes(R.string.phone)
    String strPhone;
    @StringRes(R.string.cancel)
    String cancel;
    @StringRes(R.string.ok)
    String ok;
    @StringRes(R.string.editPassword)
    String editPassword;
    private UserBeanPlus currentUser;
    private ImagePicker mPicker;
    private UserEditPresenter userEditPresenter;
    private Bitmap bitmap;
    private QMUITipDialog loading;

    @AfterInject
    void afterInject() {
        Bundle args = getArguments();
        if (args != null) {
            currentUser = args.getParcelable(CHECK_USER);
        }
        userEditPresenter = new UserEditPresenter(this);
    }

    @AfterViews
    void afterView() {
        initTopBar();
        initGroupList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_user_edit, container, false);
        return attachToSwipeBack(view);
    }

    private void initTopBar() {
        mCollapsingTopBarLayout.setTitle(currentUser.getName());
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
        setUserPic();
        userPic.setOnClickListener(v -> showLogoutSheet());
        Button saveBtn = mTopBar.addRightTextButton(R.string.save, R.id.save);
        saveBtn.setTextColor(topbarText);
        saveBtn.setOnClickListener(v -> {
            action(strSave);

        });
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

    QMUICommonListItemView emailItem;
    QMUICommonListItemView phoneItem;

    private void initGroupList() {
        QMUICommonListItemView nameItem = mGroupListView.createItemView(
                null,
                strName,
                currentUser.getName(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUICommonListItemView accountItem = mGroupListView.createItemView(
                null,
                strAccount,
                currentUser.getNo(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        QMUICommonListItemView sectionItem = mGroupListView.createItemView(
                null,
                strSection,
                currentUser.getRoleName(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_NONE);
        addNewSection("身份信息", "以上信息不可修改", nameItem, accountItem, sectionItem);

        emailItem = mGroupListView.createItemView(
                null,
                strEmail,
                currentUser.getEmail(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        phoneItem = mGroupListView.createItemView(
                null,
                strPhone,
                currentUser.getPhone(),
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        QMUICommonListItemView passwordItem = mGroupListView.createItemView(
                null,
                editPassword,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        addNewSection("个人信息", "", emailItem, phoneItem, passwordItem);

    }

    private void addNewSection(String title, String description, QMUICommonListItemView... itemView) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext());
        if (!isEmpty(title))
            section.setTitle(title);
        if (!isEmpty(description))
            section.setDescription(description);
        for (int i = 0; i < itemView.length; i++) {
            section.addItemView(itemView[i], onClickListener);
        }
        section.addTo(mGroupListView);
    }

    private View.OnClickListener onClickListener = this::onClick;

    private void onClick(View v) {
        if (v instanceof QMUICommonListItemView) {
            CharSequence tag = ((QMUICommonListItemView) v).getText();
            action(tag.toString());
        }
    }

    private void showLogoutSheet() {
        new QMUIBottomSheet.BottomListSheetBuilder(getContext())
                .addItem(openCamera)
                .addItem(openGallery)
                .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                    dialog.dismiss();
                    action(tag);
                })
                .build()
                .show();
    }

    private void action(String tag) {
        if (tag.equals(openCamera)) {
            //TODO 打开相机
            clickCameraCrop();
        }
        if (tag.equals(openGallery)) {
            //TODO 打开相册
            clickGalleryCrop();
        }
        if (tag.equals(strEmail)) {
            //TODO 修改邮箱
            showEditTextDialog(strEmail);
        }
        if (tag.equals(strPhone)) {
            //TODO 修改手机
            showEditTextDialog(strPhone);
        }
        if (tag.equals(editPassword)) {
            //TODO 修改密码
            InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            showEditPwdDialog();
        }
        if (tag.equals(strSave)) {
            //TODO 保存
            loading = showLoadingDialog(getContext(), saving);
            loading.show();
            userEditPresenter.updateUser(currentUser.getNo(), currentUser.getEmail(), currentUser.getPhone());
        }
        if (tag.equals(uploadPic)) {
            //TODO 上传相片
            loading = showLoadingDialog(getContext(), saving);
            loading.show();
            if (bitmap != null)
                userEditPresenter.uploadPic(bitmap, currentUser.getNo());
        }
    }

    private void clickCameraCrop() {
        // 从相机拍照并裁剪
        File file = new File(_mActivity.getExternalCacheDir(), "camera_crop.jpg");
        // 创建裁剪参数
        ImagePicker.CropConfigBuilder cropConfigBuilder = new ImagePicker.CropConfigBuilder()
                .aspect(1, 1) // 比例1：2
                .outputSize(400, 400) // 输出大小200*400
                .outputFile(file);  // 最终文件保存路径
        // 创建选择器
        mPicker = new ImagePicker.Builder(_mActivity)
                .fromCamera(file) // 表示从相机选择，并设置拍照保存文件
                .withCrop(cropConfigBuilder) // 拍照完紧接裁剪
                .build();
        // 调用选图
        mPicker.pick(mCallback);
    }

    public void clickGalleryCrop() {
        File file = new File(_mActivity.getExternalCacheDir(), "gallery_crop.jpg");
        ImagePicker.CropConfigBuilder cropConfigBuilder = new ImagePicker.CropConfigBuilder()
                .aspect(1, 1)
//                .outputSize(100, 100) // 采用默认大小200
                .outputFile(file);
        mPicker = new ImagePicker.Builder(_mActivity)
                .fromGallery()
                .withCrop(cropConfigBuilder)
                .build();
        mPicker.pick(mCallback);
    }

    private OnImagePickerCallback mCallback = new OnImagePickerCallback() {
        @Override
        public void onPickError(@ErrorCode int errorCode) {
            // 发生错误，具体错误参考：@ErrorCode
            showToast("ImagePicker-发生错误：" + errorCode);
        }

        @Override
        public void onPickSuccess(@NonNull ImagePickerResult result) {
            // 选图/裁剪回调
            InputStream inputStream = null;
            try {
                // 从选择结果中取出文件Uri，进行想要的处理，这边直接显示
                inputStream = _mActivity.getContentResolver().openInputStream(result.getImageUri());
                bitmap = BitmapFactory.decodeStream(inputStream);
                userPic.setImageBitmap(bitmap);
                action(uploadPic);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                StreamUtils.close(inputStream);
            }
        }

        @Override
        public void onPickCancel() {
            // 主动取消选择/裁剪时回调
//            showToast("ImagePicker-取消选择");
        }

        void showToast(String msg) {
            showNormalToast(_mActivity, msg);
        }
    };

    private void showEditTextDialog(String str) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("修改" + str)
                .setPlaceholder("在此输入新的" + str)
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction(cancel, (dialog, index) -> dialog.dismiss())
                .addAction(ok, (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        if (str.equals(strEmail)) {
                            currentUser.setEmail(text.toString());
                            emailItem.setDetailText(text);
                        }
                        if (str.equals(strPhone)) {
                            currentUser.setPhone(text.toString());
                            phoneItem.setDetailText(text);
                        }

                        dialog.dismiss();
                    } else {
                        showNormalToast(_mActivity, "请填入" + str);
                    }
                })
                .create().show();
    }

    public void showEditPwdDialog() {
        EditPwdDialogBuilder editPwdBuilder = new EditPwdDialogBuilder(_mActivity);
        editPwdBuilder.setTitle(editPassword)
                .addAction(cancel, ((dialog, index) -> dialog.dismiss()))
                .addAction(ok, ((dialog, index) -> {
                    if (editPwdBuilder.validPassword()) {
                        String oldPwd = editPwdBuilder.getOldPassword();
                        String password = editPwdBuilder.getPassword();
                        loading = showLoadingDialog(getContext(), saving);
                        loading.show();
                        userEditPresenter.updatePassword(currentUser.getNo(), oldPwd, password);
                        dialog.dismiss();
                    }
                })).show();
    }

    @Override
    public void saveSuccess() {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showSuccessDialog(getContext(), saveSuccess);
        }, 500);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            loading.dismiss();
            showFailDialog(getContext(), errorMsg);
        }, 500);
    }
}
