package cn.huanzi.qch.baseadmin.hjj.entity;

import cn.huanzi.qch.baseadmin.entity.Model_table;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class ModelResponseEntity {

    private Integer code;

    private String msg;

    private Integer count;

    private PageInfo<Model_table> Data;


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }


    public PageInfo<Model_table> getData() {
        return Data;
    }

    public void setData(PageInfo<Model_table> data) {
        Data = data;
    }

    public ModelResponseEntity(Integer code, String msg, Integer count, PageInfo<Model_table> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        Data = data;
    }

    public ModelResponseEntity() {
    }





}
