package project.ys.glasssystem_r1.ui.fragment.third.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
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
import org.androidannotations.annotations.res.StringArrayRes;
import org.androidannotations.annotations.res.StringRes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import project.ys.glasssystem_r1.CustomerApp;
import project.ys.glasssystem_r1.R;
import project.ys.glasssystem_r1.data.bean.AlarmTag;
import project.ys.glasssystem_r1.data.bean.PushSet;
import project.ys.glasssystem_r1.data.bean.UserBeanPlus;
import project.ys.glasssystem_r1.mvp.contract.PushSetContract;
import project.ys.glasssystem_r1.mvp.presenter.PushSetPresenter;
import project.ys.glasssystem_r1.ui.fragment.base.BaseBackFragment;
import project.ys.glasssystem_r1.ui.widget.customerdialog.AlarmTagDialogBuilder;

import static android.text.TextUtils.isEmpty;
import static com.applikeysolutions.cosmocalendar.utils.SelectionType.RANGE;
import static project.ys.glasssystem_r1.common.constant.PushConstant.PRODUCE_TAGS;
import static project.ys.glasssystem_r1.common.constant.PushConstant.PUSH_TAGS;
import static project.ys.glasssystem_r1.common.constant.PushConstant.PUSH_TIME;
import static project.ys.glasssystem_r1.common.constant.PushConstant.SALES_TAGS;
import static project.ys.glasssystem_r1.ui.widget.customeritem.AlarmTagView.createItem;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showFailDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showLoadingDialog;
import static project.ys.glasssystem_r1.ui.widget.qmui.QMUITipDialogUtils.showSuccessDialog;
import static project.ys.glasssystem_r1.util.utils.ToastUtils.showNormalToast;

@EFragment
public class PushSetFragment extends BaseBackFragment implements PushSetContract.View {


    public static PushSetFragment newInstance() {

        return new PushSetFragment_();
    }


    @ViewById(R.id.topBar)
    QMUITopBarLayout mTopBar;
    @ViewById(R.id.groupListView)
    QMUIGroupListView mGroupListView;

    @StringRes(R.string.cancel)
    String cancel;
    @StringRes(R.string.ok)
    String ok;
    @StringRes(R.string.submit)
    String submit;
    @StringRes(R.string.save)
    String strSave;
    @StringRes(R.string.saving)
    String saving;
    @StringRes(R.string.saveSuccess)
    String saveSuccess;
    @StringRes(R.string.switch_set)
    String switchSet;
    @StringRes(R.string.sound)
    String soundSet;
    @StringRes(R.string.vibrate)
    String vibrateSet;
    @StringRes(R.string.flags)
    String flagsSet;
    @StringRes(R.string.date_set)
    String dateSet;
    @StringRes(R.string.push_switch)
    String pushSwitch;
    @StringRes(R.string.push_time)
    String pushTime;
    @StringRes(R.string.push_tags)
    String pushTags;
    @StringRes(R.string.alarm_switch)
    String alarmSwitch;
    @StringRes(R.string.add_alarm_tag)
    String addTag;
    @StringRes(R.string.fail_rate)
    String failRate;
    @StringRes(R.string.elec_consu)
    String elecConsu;
    @StringRes(R.string.wtr_consu)
    String wtrConsu;
    @StringRes(R.string.coal_consu)
    String coalConsu;
    @ColorRes(R.color.colorText_Icon)
    int topbarText;


    @StringArrayRes(R.array.alarmTags)
    String[] arrayTags;

    private UserBeanPlus currentUser;
    private PushSetPresenter pushSetPresenter;
    private PushSet currentSet;
    private QMUITipDialog loading;
    private QMUIGroupListView.Section section1;
    private QMUIGroupListView.Section section2;
    private QMUIGroupListView.Section section3;

    private QMUICommonListItemView commonSwitchItem;
    private QMUICommonListItemView soundItem;
    private QMUICommonListItemView vibrateItem;
    private QMUICommonListItemView flgasItem;
    private QMUICommonListItemView dateSelectedItem;
    private QMUICommonListItemView pushSwitchItem;
    private QMUICommonListItemView pushTimeItem;
    private QMUICommonListItemView pushTagsItem;
    private QMUICommonListItemView alarmSwitchItem;
    private QMUICommonListItemView addAlarmTagItem;

    private List<AlarmTag> alarmTags = new ArrayList<>();
    private List<QMUICommonListItemView> alarmTagItems = new ArrayList<>();
    private int timeSelect;
    private String[] push_tags = {};

    @AfterInject
    void afterInject() {
        currentUser = CustomerApp.getInstance().getCurrentUser();
        currentSet = CustomerApp.getInstance().getPushSet();
        pushSetPresenter = new PushSetPresenter(this, _mActivity);
        if (currentUser.getRoleName().equals("生产部门"))
            push_tags = PRODUCE_TAGS;
        else if (currentUser.getRoleName().equals("销售部门"))
            push_tags = SALES_TAGS;
        else
            push_tags = PUSH_TAGS;

    }

    @AfterViews
    void afterViews() {
        initTopBar();
        initGroupList();
        pushSetPresenter.getAlarmTags(currentUser.getNo());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_push_set, container, false);
        return attachToSwipeBack(view);
    }

    private void initTopBar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> {
            pop();
        });
        mTopBar.setTitle(R.string.push_set).setTextColor(topbarText);
        Button saveBtn = mTopBar.addRightTextButton(R.string.save, R.id.save);
        saveBtn.setTextColor(topbarText);
        saveBtn.setOnClickListener(v -> {
            action(strSave);
        });

    }

    @UiThread
    void initGroupList() {
        commonSwitchItem = mGroupListView.createItemView(
                null,
                switchSet,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        commonSwitchItem.getSwitch().setChecked(currentSet.isCommonSwitch());
        commonSwitchItem.getSwitch().setEnabled(true);
        commonSwitchItem.getSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> action(switchSet));
        soundItem = mGroupListView.createItemView(
                null,
                soundSet,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        soundItem.getSwitch().setChecked(currentSet.isSound());
        soundItem.getSwitch().setEnabled(true);
        soundItem.getSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> action(soundSet));

        vibrateItem = mGroupListView.createItemView(
                null,
                vibrateSet,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        vibrateItem.getSwitch().setChecked(currentSet.isVibrate());
        vibrateItem.getSwitch().setEnabled(true);
        vibrateItem.getSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> action(vibrateSet));

        flgasItem = mGroupListView.createItemView(
                null,
                flagsSet,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        flgasItem.getSwitch().setChecked(currentSet.isFlags());
        flgasItem.getSwitch().setEnabled(true);
        flgasItem.getSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> action(flagsSet));

        dateSelectedItem = mGroupListView.createItemView(
                null,
                dateSet,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        pushSwitchItem = mGroupListView.createItemView(
                null,
                pushSwitch,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        pushSwitchItem.getSwitch().setChecked(currentSet.isPushSwitch());
        pushSwitchItem.getSwitch().setEnabled(true);
        pushSwitchItem.getSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> action(pushSwitch));


        pushTimeItem = mGroupListView.createItemView(
                null,
                pushTime,
                PUSH_TIME[currentSet.getTime()],
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        pushTagsItem = mGroupListView.createItemView(
                null,
                pushTags,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        alarmSwitchItem = mGroupListView.createItemView(
                null,
                alarmSwitch,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        alarmSwitchItem.getSwitch().setChecked(currentSet.isAlarmSwitch());
        alarmSwitchItem.getSwitch().setOnCheckedChangeListener((buttonView, isChecked) -> action(alarmSwitch));
        alarmSwitchItem.getSwitch().setEnabled(true);
        addAlarmTagItem = mGroupListView.createItemView(
                null,
                addTag,
                "",
                QMUICommonListItemView.HORIZONTAL,
                QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        ImageView addBtn = new ImageView(_mActivity);
        addBtn.setImageResource(R.drawable.ic_icon_roundadd_fill);
        addAlarmTagItem.addAccessoryCustomView(addBtn);
        createSection1();

    }

    private QMUIGroupListView.Section addNewSection(String title, String description, QMUICommonListItemView... itemView) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext());
        if (!isEmpty(title))
            section.setTitle(title);
        if (!isEmpty(description))
            section.setDescription(description);
        for (int i = 0; i < itemView.length; i++) {
            section.addItemView(itemView[i], onClickListener);
        }
        section.addTo(mGroupListView);
        return section;
    }

    private QMUIGroupListView.Section addNewSection(String title, String description, List<QMUICommonListItemView> itemViews) {
        QMUIGroupListView.Section section = QMUIGroupListView.newSection(getContext());
        if (!isEmpty(title))
            section.setTitle(title);
        if (!isEmpty(description))
            section.setDescription(description);
        section.addItemView(alarmSwitchItem, onClickListener);
        section.addItemView(addAlarmTagItem, onClickListener);
        for (QMUICommonListItemView itemView : itemViews) {
            section.addItemView(itemView, onClickListener);
        }
        section.addTo(mGroupListView);
        return section;
    }


    private View.OnClickListener onClickListener = this::onClick;

    private void onClick(View v) {
        if (v instanceof QMUICommonListItemView) {
            CharSequence tag = ((QMUICommonListItemView) v).getText();
            action(tag.toString());
        }
    }

    private void action(String tag) {
        if (tag.equals(switchSet)) {
            //TODO 接受通知
            if (commonSwitchItem.getSwitch().isChecked()) {
                createSection2();
            } else {
                if (section2 != null)
                    section2.removeFrom(mGroupListView);
                if (section3 != null)
                    section3.removeFrom(mGroupListView);
            }
        }
        if (tag.equals(soundSet)) {
            //TODO 声音
        }
        if (tag.equals(vibrateSet)) {
            //TODO 震动
        }
        if (tag.equals(flagsSet)) {
            //TODO 指示灯
        }
        if (tag.equals(dateSet)) {
            //TODO 日期设置
            showDateChoice();
        }
        if (tag.equals(pushSwitch)) {
            //TODO 接受数据推送
            createSection2();
        }

        if (tag.equals(pushTime)) {
            //TODO 推送时间
            showTimeChoice();
        }

        if (tag.equals(pushTags)) {
            //TODO 推送标签
            pushSetPresenter.getTags(currentUser.getNo());
        }

        if (tag.equals(alarmSwitch)) {
            //TODO 接受数据预警
            createSection3();
        }
        if (tag.equals(addTag)) {
            //TODO 添加标签
            showAddSpinner();
        }
        if (tag.equals(failRate)) {
            //TODO 删除残片率
            deleteTag(failRate);
        }
        if (tag.equals(elecConsu)) {
            //TODO 删除电消耗
            deleteTag(elecConsu);
        }
        if (tag.equals(wtrConsu)) {
            //TODO 删除水消耗
            deleteTag(wtrConsu);
        }
        if (tag.equals(coalConsu)) {
            //TODO 删除煤消耗
            deleteTag(coalConsu);
        }
        if (tag.equals(strSave)) {
            //TODO 保存
            currentSet.setCommonSwitch(commonSwitchItem.getSwitch().isChecked());
            currentSet.setSound(soundItem.getSwitch().isChecked());
            currentSet.setVibrate(vibrateItem.getSwitch().isChecked());
            currentSet.setFlags(flgasItem.getSwitch().isChecked());
            currentSet.setPushSwitch(pushSwitchItem.getSwitch().isChecked());
            currentSet.setAlarmSwitch(alarmSwitchItem.getSwitch().isChecked());
            currentSet.setTime(timeSelect);
            CustomerApp.getInstance().setPushSet(currentSet);
            loading = showLoadingDialog(getContext(), saving);
            loading.show();
            pushSetPresenter.updateSets(currentUser.getNo(), currentSet);
        }
    }

    private void deleteTag(String content) {
        deleteOneTag(content);
        createSection3();
        if (alarmTags.size() >= 0) {
            pushSetPresenter.uploadAlarmTags(currentUser.getNo(), alarmTags);
        }
    }

    private void deleteOneTag(String content) {
        for (int i = 0; i < alarmTags.size(); i++) {
            if (alarmTags.get(i).getContent().equals(content)) {
                alarmTags.remove(i);
                alarmTagItems.remove(i);
                return;
            }
        }
    }

    @UiThread
    void createSection1() {
        section1 = addNewSection("通用", "", commonSwitchItem, soundItem, vibrateItem, dateSelectedItem);
        if (currentSet.isCommonSwitch())
            createSection2();
    }

    @UiThread
    void createSection2() {
        if (section2 != null)
            section2.removeFrom(mGroupListView);
        if (pushSwitchItem.getSwitch().isChecked()) {
            section2 = addNewSection("数据推送设置", "", pushSwitchItem, pushTimeItem, pushTagsItem);
        } else {
            section2 = addNewSection("数据推送设置", "", pushSwitchItem);
        }
        createSection3();
    }

    @UiThread
    void createSection3() {
        if (section3 != null)
            section3.removeFrom(mGroupListView);
        if (alarmSwitchItem.getSwitch().isChecked()) {
            if (alarmTags.size() == 0)
                section3 = addNewSection("数据预警设置", "", alarmSwitchItem, addAlarmTagItem);
            else {
                section3 = addNewSection("数据预警设置", "", alarmTagItems);
            }
        } else {
            section3 = addNewSection("数据预警设置", "", alarmSwitchItem);
        }
    }

    private void showDateChoice() {
        CalendarDialog calendarDialog = new CalendarDialog(_mActivity, selectedDays -> {
            if (selectedDays != null) {
                if (selectedDays.size() > 0) {
                    Date start = selectedDays.get(0).getCalendar().getTime();
                    Date end = selectedDays.get(selectedDays.size() - 1).getCalendar().getTime();
                    start.setHours(0);
                    start.setMinutes(0);
                    start.setSeconds(0);
                    end.setHours(23);
                    end.setMinutes(59);
                    end.setSeconds(59);
                    currentSet.setStart(start.getTime());
                    currentSet.setEnd(end.getTime());
                    CustomerApp.getInstance().setPushSet(currentSet);
                    loading = showLoadingDialog(getContext(), saving);
                    loading.show();
                    pushSetPresenter.updateSets(currentUser.getNo(), currentSet);
                }
            }

        });
        calendarDialog.show();
        calendarDialog.setSelectionType(RANGE);
        calendarDialog.setShowDaysOfWeek(true);
        calendarDialog.setShowDaysOfWeekTitle(true);
        Set<Long> longs = new HashSet<>();
        for (long i = currentSet.getStart() / 1000; i < currentSet.getEnd() / 1000; i += 86400)
            longs.add(i * 1000);
        calendarDialog.setConnectedCalendarDays(longs);
        calendarDialog.setConnectedDayTextColor(_mActivity.getColor(R.color.colorPrimaryDark));
    }

    private void showTimeChoice() {
        new QMUIDialog.CheckableDialogBuilder(_mActivity)
                .setCheckedIndex(currentSet.getTime())
                .addItems(PUSH_TIME, (dialog, which) -> {
                    timeSelect = which;
                    pushTimeItem.setDetailText(PUSH_TIME[which]);
                    dialog.dismiss();
                })
                .create().show();
    }

    @Override
    public void showTagsChoices(List<Integer> checks) {
        final QMUIDialog.MultiCheckableDialogBuilder builder =
                new QMUIDialog.MultiCheckableDialogBuilder(_mActivity)
                        .addItems(push_tags, (dialog, which) -> {
                        });
        if (checks != null) {
            int[] checkedIndexes = new int[checks.size()];
            for (int i = 0; i < checks.size(); i++) {
                checkedIndexes[i] = checks.get(i);
            }
            builder.setCheckedItems(checkedIndexes);
        }
        builder.addAction(cancel, (dialog, index) -> dialog.dismiss());
        builder.addAction(submit, (dialog, index) -> {
            List<String> tags = new ArrayList<>();
            for (int i = 0; i < builder.getCheckedItemIndexes().length; i++) {
                tags.add(push_tags[builder.getCheckedItemIndexes()[i]]);
            }
            pushSetPresenter.updateTags(currentUser.getNo(), tags);
            dialog.dismiss();
        });
        builder.create().show();
    }

    public void showAddSpinner() {
        if (alarmTags.size() == arrayTags.length) {
            showNormalToast(_mActivity, "标签足够多了");
        } else {
            AlarmTagDialogBuilder alarmTagBuilder = new AlarmTagDialogBuilder(_mActivity, alarmTags);
            alarmTagBuilder.setTitle(addTag)
                    .addAction(cancel, ((dialog, index) -> dialog.dismiss()))
                    .addAction(ok, ((dialog, index) -> {
                        AlarmTag alarmTag = alarmTagBuilder.getAlarmTag();
                        if (alarmTag != null) {
                            alarmTags.add(alarmTag);
                            QMUICommonListItemView oneTag = mGroupListView.createItemView(
                                    null,
                                    alarmTagBuilder.getAlarmTag().getContent(),
                                    "",
                                    QMUICommonListItemView.HORIZONTAL,
                                    QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
                            View tagView = createItem(_mActivity, alarmTag.getMin() + "-" + alarmTag.getMax());
                            oneTag.addAccessoryCustomView(tagView);
                            alarmTagItems.add(oneTag);
                            createSection3();
                            uploadAlarmTags();
                        }
                        dialog.dismiss();
                    })).show();
        }
    }

    @Override
    public void setAlarmTags(List<AlarmTag> alarmTags) {
        this.alarmTags = alarmTags;
        for (AlarmTag alarmTag : alarmTags) {
            QMUICommonListItemView oneTag = mGroupListView.createItemView(
                    null,
                    alarmTag.getContent(),
                    "",
                    QMUICommonListItemView.HORIZONTAL,
                    QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);

            View tagView = createItem(_mActivity, alarmTag.getMin() + "-" + alarmTag.getMax());
            oneTag.addAccessoryCustomView(tagView);
            alarmTagItems.add(oneTag);
        }
        if (currentSet.isCommonSwitch())
            createSection3();
    }

    private void uploadAlarmTags() {
        pushSetPresenter.uploadAlarmTags(currentUser.getNo(), alarmTags);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        new Handler().postDelayed(() -> {
            if (loading != null)
                loading.dismiss();
            showFailDialog(getContext(), errorMsg);
        }, 2000);
    }

    @Override
    public void showSuccess() {
        new Handler().postDelayed(() -> {
            if (loading != null)
                loading.dismiss();
            showSuccessDialog(_mActivity, saveSuccess);
        }, 2000);
    }
}
