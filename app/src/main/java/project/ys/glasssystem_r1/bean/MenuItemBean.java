package project.ys.glasssystem_r1.bean;

import android.graphics.drawable.Drawable;

public class MenuItemBean {

    public MenuItemBean() {
    }

    public MenuItemBean(String text, String detailText) {
        this.text = text;
        this.detailText = detailText;
    }

    public MenuItemBean(String text, String detailText, Drawable rightIcon) {
        this.text = text;
        this.detailText = detailText;
        this.rightIcon = rightIcon;
    }

    String text;

    String detailText;

    Drawable rightIcon;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public Drawable getRightIcon() {
        return rightIcon;
    }

    public void setRightIcon(Drawable rightIcon) {
        this.rightIcon = rightIcon;
    }
}
