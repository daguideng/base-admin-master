package cn.huanzi.qch.baseadmin.hjj.entity;

public class OneClickUpload {

    public String ids[];
    public String username;
    public String password;
    public String api;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }


    public OneClickUpload(){}

    public OneClickUpload(String[] ids, String username, String password, String api) {
        this.ids = ids;
        this.username = username;
        this.password = password;
        this.api = api;
    }

    public OneClickUpload( String username, String password, String api) {
        this.username = username;
        this.password = password;
        this.api = api;
    }
}
