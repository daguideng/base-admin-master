package cn.huanzi.qch.baseadmin.entity;

import java.util.Date;

public class Dataset_table {
    private Integer id;

    private String dataset_type;

    private String dataset_name;

    private String sort;

    private String version;

    private String dataset_path;

    private Date update_time;

    private String temp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDataset_type() {
        return dataset_type;
    }

    public void setDataset_type(String dataset_type) {
        this.dataset_type = dataset_type == null ? null : dataset_type.trim();
    }

    public String getDataset_name() {
        return dataset_name;
    }

    public void setDataset_name(String dataset_name) {
        this.dataset_name = dataset_name == null ? null : dataset_name.trim();
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

    public String getDataset_path() {
        return dataset_path;
    }

    public void setDataset_path(String dataset_path) {
        this.dataset_path = dataset_path == null ? null : dataset_path.trim();
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp == null ? null : temp.trim();
    }
}