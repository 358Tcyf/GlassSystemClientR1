package project.ys.glasssystem_r1.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.List;

public class BaseChart implements Parcelable {

    public BaseChart() {

    }

    public static final int line_chart = 0;
    public static final int bar_chart = 1;
    public static final int pie_chart = 2;
    public static final int ring_chart = 3;

    private String menu;

    private String sub;

    private String title;

    private String desc;

    private int type;

    private boolean only;

    private String label;

    private List<String> labels;

    private String[] xValues;

    private List<BaseEntry> yValues;

    private List<List<BaseEntry>> yListValues;

    protected BaseChart(Parcel in) {
        menu = in.readString();
        sub = in.readString();
        title = in.readString();
        desc = in.readString();
        type = in.readInt();
        only = in.readByte() != 0;
        label = in.readString();
        labels = in.createStringArrayList();
        xValues = in.createStringArray();
    }

    public static final Creator<BaseChart> CREATOR = new Creator<BaseChart>() {
        @Override
        public BaseChart createFromParcel(Parcel in) {
            return new BaseChart(in);
        }

        @Override
        public BaseChart[] newArray(int size) {
            return new BaseChart[size];
        }
    };

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isOnly() {
        return only;
    }

    public void setOnly(boolean only) {
        this.only = only;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String[] getxValues() {
        return xValues;
    }

    public void setxValues(String[] xValues) {
        this.xValues = xValues;
    }

    public List<BaseEntry> getyValues() {
        return yValues;
    }

    public void setyValues(List<BaseEntry> yValues) {
        this.yValues = yValues;
    }

    public List<List<BaseEntry>> getyListValues() {
        return yListValues;
    }

    public void setyListValues(List<List<BaseEntry>> yListValues) {
        this.yListValues = yListValues;
    }

    @Override
    public String toString() {
        return "BaseChart{" +
                "menu='" + menu + '\'' +
                ", sub='" + sub + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                ", only=" + only +
                ", label='" + label + '\'' +
                ", labels=" + labels +
                ", xValues=" + Arrays.toString(xValues) +
                ", yValues=" + yValues +
                ", yListValues=" + yListValues +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menu);
        dest.writeString(sub);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeInt(type);
        dest.writeByte((byte) (only ? 1 : 0));
        dest.writeString(label);
        dest.writeStringList(labels);
        dest.writeStringArray(xValues);
    }

}
