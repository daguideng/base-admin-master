package cn.huanzi.qch.baseadmin.hjj.entity;

public class User {

    public int id;
    public String username;
    public String email;
    public int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String joinDate;

    public User(int id, String username, String email, int age, String joinDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.age = age;
        this.joinDate = joinDate;
    }

}
