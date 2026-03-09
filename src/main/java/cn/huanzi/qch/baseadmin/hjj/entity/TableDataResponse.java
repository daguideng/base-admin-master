package cn.huanzi.qch.baseadmin.hjj.entity;

import java.util.List;

public class TableDataResponse {

    public int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public String msg;
    public int count;
    public List<User> data;

    public TableDataResponse(int code, String msg, int count, List<User> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }



}
