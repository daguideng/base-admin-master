package cn.huanzi.qch.baseadmin.adas.entity;

public class Scene {

    public String api;
    public String scene_ids;
    public String preset_id ;
    public String source ;



    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getScene_ids() {
        return scene_ids;
    }

    public void setScene_ids(String scene_ids) {
        this.scene_ids = scene_ids;
    }

    public String getPreset_id() {
        return preset_id;
    }

    public void setPreset_id(String preset_id) {
        this.preset_id = preset_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public Scene(){}

    public Scene(String api, String scene_ids, String preset_id, String source) {
        this.api = api;
        this.scene_ids = scene_ids;
        this.preset_id = preset_id;
        this.source = source;
    }
}
