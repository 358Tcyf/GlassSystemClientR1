package project.ys.glasssystem_r1.data.bean;

import me.yokeyword.indexablerv.IndexableEntity;

public class UserBeanOrderByName extends UserBean implements IndexableEntity {

    protected String roleName;

    public UserBeanOrderByName() {
    }

    public UserBeanOrderByName(String name, String role) {
        this.name = name;
        this.roleName = role;
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
    }
}
