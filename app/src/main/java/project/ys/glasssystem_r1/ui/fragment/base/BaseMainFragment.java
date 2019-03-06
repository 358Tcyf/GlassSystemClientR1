package project.ys.glasssystem_r1.ui.fragment.base;

import me.yokeyword.fragmentation.SupportFragment;

import static project.ys.glasssystem_r1.util.ToastUtils.showExitToast;


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
            showExitToast(_mActivity);
        }
        return true;
    }
}