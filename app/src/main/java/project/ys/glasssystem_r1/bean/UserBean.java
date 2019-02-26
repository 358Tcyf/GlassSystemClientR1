package project.ys.glasssystem_r1.bean;

public class UserBean {
    private String no;
    private String password;
    private String name;
    private String phone;
    private String email;

    public UserBean() {
    }

    public UserBean(String no, String password) {
        this.no = no;
        this.password = password;
    }



    public UserBean(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
