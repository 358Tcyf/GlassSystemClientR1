package project.ys.glasssystem_r1.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

import me.yokeyword.indexablerv.IndexableEntity;

public class UserBeanPlus extends UserBean implements IndexableEntity, Parcelable {

    private String roleName;

    private String picPath;

    public UserBeanPlus() {
    }

    public UserBeanPlus(String account, String password) {
        super(account, password);
    }


    protected UserBeanPlus(Parcel in) {
        roleName = in.readString();
        picPath = in.readString();
    }

    public static final Creator<UserBeanPlus> CREATOR = new Creator<UserBeanPlus>() {
        @Override
        public UserBeanPlus createFromParcel(Parcel in) {
            return new UserBeanPlus(in);
        }

        @Override
        public UserBeanPlus[] newArray(int size) {
            return new UserBeanPlus[size];
        }
    };

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String getFieldIndexBy() {
        return name; // return 你需要根据该属性排序的field
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.name = indexField; // 同上
    }

    @Override
    public void setFieldPinyinIndexBy(String pinyin) {
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(no);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(roleName);
        dest.writeString(picPath);
    }

    @Override
    public String toString() {
        return "UserBeanPlus{" +
                "roleName='" + roleName + '\'' +
                ", picPath='" + picPath + '\'' +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
