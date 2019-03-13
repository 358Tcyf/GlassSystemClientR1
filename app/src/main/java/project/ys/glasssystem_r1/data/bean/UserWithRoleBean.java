package project.ys.glasssystem_r1.data.bean;

import me.yokeyword.indexablerv.IndexableEntity;

public class UserWithRoleBean extends UserBean implements IndexableEntity {

    private String pinyin;
    private String roleName;

    public UserWithRoleBean() {
    }

    public UserWithRoleBean(String name, String role) {
        this.name = name;
        this.roleName = role;
    }


    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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
        this.pinyin = pinyin; // 保存排序field的拼音,在执行比如搜索等功能时有用 （若不需要，空实现该方法即可）
    }
}
