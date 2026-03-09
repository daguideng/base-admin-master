package cn.huanzi.qch.baseadmin.entity;

import java.util.Date;

public class Model_table {
    private Integer id;

    private String model_type;

    private String model_name;

    private String sort;

    private String version;

    private String model_path;

    private Date update_time;

    private String type ;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModel_type() {
        return model_type;
    }

    public void setModel_type(String model_type) {
        this.model_type = model_type == null ? null : model_type.trim();
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name == null ? null : model_name.trim();
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getModel_path() {
        return model_path;
    }

    public void setModel_path(String model_path) {
        this.model_path = model_path == null ? null : model_path.trim();
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }




    public Model_table() {
    }

    public Model_table(String model_type, String model_name, String sort, String version, String model_path, Date update_time, String type) {
        this.model_type = model_type;
        this.model_name = model_name;
        this.sort = sort;
        this.version = version;
        this.model_path = model_path;
        this.update_time = update_time;
        this.type = type;
    }
}