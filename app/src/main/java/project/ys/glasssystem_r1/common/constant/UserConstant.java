package project.ys.glasssystem_r1.common.constant;

import com.orhanobut.logger.Logger;

public class UserConstant {
    public static final int MANAGEMENT_SECTION = 1;
    public static final int PRODUCT_SECTION = 2;
    public static final int SALE_SECTION = 3;
    public static final String DEFAULT_PASSWORD = "123456";

    public static final String USER_NAME = "user_name";
    public static final String USER_ACCOUNT = "user_account";

    public static final String USER_PIC_PATH = "user_pic_path";

    public static final int NAME = 0;
    public static final int EMAIL = 1;
    public static final int PHONE = 2;

    public static final String 行政部门 = "A";
    public static final String 研发部门 = "D";
    public static final String 采购部门 = "U";
    public static final String 生产部门 = "R";
    public static final String 仓储部门 = "W";
    public static final String 质检部门 = "Q";
    public static final String 销售部门 = "S";
    public static final String 财务部门 = "F";
    public static final String[] Account_Index = {
            行政部门,
            研发部门,
            采购部门,
            生产部门,
            仓储部门,
            质检部门,
            销售部门,
            财务部门};

    public static final String[] Role_Index = {
            "行政部门",
            "研发部门",
            "采购部门",
            "生产部门",
            "仓储部门",
            "质检部门",
            "销售部门",
            "财务部门"};

    public static int getRoleId(String roleName) {
        int id =1;
        for (int i = 0; i < Role_Index.length; i++) {
            if (roleName.equals(Role_Index[i]))
                id = i+2;
        }
        return id;
    }
}


