package project.ys.glasssystem_r1.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

public class QMUITipDialogUtil {
    /*简单提示*/
    public static void showTipDialog(Context context, String tipWord) {
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setTipWord(tipWord)
                .create();
        tipDialog.show();
        new Handler().postDelayed(tipDialog::dismiss, 1000);
    }

    /*进度条提示*/
    public static QMUITipDialog showLoadingDialog(Context context, String tipWord) {
        return new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(tipWord)
                .create();
    }

    /*失败提示*/
    public static void showFailDialog(Context context, String tipWord) {
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(tipWord)
                .create();
        tipDialog.show();
        new Handler().postDelayed(tipDialog::dismiss, 1000);
    }

    /*成功提示*/
    public static void showSuccessDialog(Context context, String tipWord) {
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(tipWord)
                .create();
        tipDialog.show();
        new Handler().postDelayed(tipDialog::dismiss, 1000);
    }

    // ================================ 生成不同类型的对话框
    public static void showMessagePositiveDialog(Context context, String title, String msg, String act1, String act2, QMUIDialogAction.ActionListener listener) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(msg)
                .addAction(act1, (dialog, index) -> dialog.dismiss())
                .addAction(act2, listener)
                .create().show();
    }

    public static void showMessageNegativeDialog(Context context, String title, String msg, String act1, String act2, QMUIDialogAction.ActionListener listener) {
        new QMUIDialog.MessageDialogBuilder(context)
                .setTitle(title)
                .setMessage(msg)
                .addAction(act1, (dialog, index) -> dialog.dismiss())
                .addAction(0, act2, QMUIDialogAction.ACTION_PROP_NEGATIVE, listener)
                .create().show();
    }
}
