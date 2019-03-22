package project.ys.glasssystem_r1.data.bean;

public class UserBeanOrderByRole extends UserBeanOrderByName  {

    @Override
    public String getFieldIndexBy() {
        return roleName; // return 你需要根据该属性排序的field
    }

    @Override
    public void setFieldIndexBy(String indexField) {
        this.roleName = indexField; // 同上
    }


}
