package cn.huanzi.qch.baseadmin.entity;

import java.util.Date;

public class Adas_scene {
    private Integer id;

    private String api;

    private String scene_ids;

    private String preset_id;

    private String source;

    private String user_id;

    private String remark;

    private Date create_time;

    private Date update_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api == null ? null : api.trim();
    }

    public String getScene_ids() {
        return scene_ids;
    }

    public void setScene_ids(String scene_ids) {
        this.scene_ids = scene_ids == null ? null : scene_ids.trim();
    }

    public String getPreset_id() {
        return preset_id;
    }

    public void setPreset_id(String preset_id) {
        this.preset_id = preset_id == null ? null : preset_id.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }


    public Adas_scene(){}

    public Adas_scene(Integer id, String api, String scene_ids, String preset_id, String source, String user_id, String remark, Date create_time, Date update_time) {
        this.id = id;
        this.api = api;
        this.scene_ids = scene_ids;
        this.preset_id = preset_id;
        this.source = source;
        this.user_id = user_id;
        this.remark = remark;
        this.create_time = create_time;
        this.update_time = update_time;
    }


    public Adas_scene(String api, String scene_ids, String preset_id, String source, String user_id, String remark, Date create_time, Date update_time) {
        this.api = api;
        this.scene_ids = scene_ids;
        this.preset_id = preset_id;
        this.source = source;
        this.user_id = user_id;
        this.remark = remark;
        this.create_time = create_time;
        this.update_time = update_time;
    }


    public Adas_scene(String api, String scene_ids, String preset_id, String source, String user_id) {
        this.api = api;
        this.scene_ids = scene_ids;
        this.preset_id = preset_id;
        this.source = source;
        this.user_id = user_id;
    }

    public Adas_scene(String api, String scene_ids, String preset_id, String source) {
        this.api = api;
        this.scene_ids = scene_ids;
        this.preset_id = preset_id;
        this.source = source;
    }
}