package project.ys.glasssystem_r1.ui.fragment.first.child;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringRes;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.common.event.FirstTabMenuEvent;
import project.ys.glasssystem_r1.common.event.FirstTabSelectedEvent;
import project.ys.glasssystem_r1.common.event.RefreshListEvent;
import project.ys.glasssystem_r1.common.event.StartBrotherEvent;
import project.ys.glasssystem_r1.data.entity.Push;
import project.ys.glasssystem_r1.mvp.contract.PushContract;
import project.ys.glasssystem_r1.mvp.presenter.PushPresenter;
import project.ys.glasssystem_r1.ui.adapter.PushQuickAdapter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;

import static project.ys.glasssystem_r1.common.constant.Constant.SECOND;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showMessageNegativeDialog;
import static project.ys.glasssystem_r1.util.utils.TipDialogUtils.showTipDialog;

@EFragment(R.layout.fragment_alarm)
public class AlarmFragment extends SupportFragment  {
    public static AlarmFragment newInstance() {
        return new AlarmFragment_();
    }


}
