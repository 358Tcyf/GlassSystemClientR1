package project.ys.glasssystem_r1.ui.fragment.base;

import android.os.Handler;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import me.yokeyword.fragmentation.SupportFragment;

public class BaseMainFragment extends SupportFragment {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            QMUITipDialog tipDialog = new QMUITipDialog.Builder(_mActivity)
                    .setTipWord("再按一次退出")
                    .create();
            tipDialog.show();
            new Handler().postDelayed(tipDialog::dismiss, 200);
        }
        return true;
    }
}