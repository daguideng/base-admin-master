package cn.huanzi.qch.baseadmin.hjj.entity;

public class SchemePageSubmit {

    public String username;
    public String password;
    public String api ;
    public String category ;
    public String isTask ;
    public String radio_type ;
    public String isible_light_img ;
    public String isible_light_detection ;
    public String isible_light_tracking ;
    public String red_outer_img ;
    public String red_outer_detection ;
    public String red_outer_tracking ;
    public String text_analysis ;
    public String text_named_entity ;
    public String voicle_recognition ;

    public String visible_img_img_reid;
    public String visible_img_img_segmentation ;


   public String radar_signal_classification ;

    public String getRadar_signal_classification() {
        return radar_signal_classification;
    }

    public void setRadar_signal_classification(String radar_signal_classification) {
        this.radar_signal_classification = radar_signal_classification;
    }

    public String getVisible_img_pose_estimation() {
        return visible_img_pose_estimation;
    }

    public void setVisible_img_pose_estimation(String visible_img_pose_estimation) {
        this.visible_img_pose_estimation = visible_img_pose_estimation;
    }

    // 可见光-姿态估计
   public String visible_img_pose_estimation ;


    public String video_target_detection ;
    public String video_target_trace ;


    public String structured_data_classification ;
    public String structured_data_regression ;


    public String getStructured_data_cluster() {
        return structured_data_cluster;
    }

    public void setStructured_data_cluster(String structured_data_cluster) {
        this.structured_data_cluster = structured_data_cluster;
    }

    public String structured_data_cluster ;




    public void setVoicle_recognition(String voicle_recognition) {
        this.voicle_recognition = voicle_recognition;
    }

    public String getStructured_data_classification() {
        return structured_data_classification;
    }

    public void setStructured_data_classification(String structured_data_classification) {
        this.structured_data_classification = structured_data_classification;
    }

    public String getStructured_data_regression() {
        return structured_data_regression;
    }

    public void setStructured_data_regression(String structured_data_regression) {
        this.structured_data_regression = structured_data_regression;
    }






    public String getVideo_target_detection() {
        return video_target_detection;
    }

    public void setVideo_target_detection(String video_target_detection) {
        this.video_target_detection = video_target_detection;
    }

    public String getVideo_target_trace() {
        return video_target_trace;
    }

    public void setVideo_target_trace(String video_target_trace) {
        this.video_target_trace = video_target_trace;
    }




    public String getVisible_img_img_reid() {
        return visible_img_img_reid;
    }

    public void setVisible_img_img_reid(String visible_img_img_reid) {
        this.visible_img_img_reid = visible_img_img_reid;
    }

    public String getVisible_img_img_segmentation() {
        return visible_img_img_segmentation;
    }

    public void setVisible_img_img_segmentation(String visible_img_img_segmentation) {
        this.visible_img_img_segmentation = visible_img_img_segmentation;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsTask() {
        return isTask;
    }

    public void setIsTask(String isTask) {
        this.isTask = isTask;
    }

    public String getRadio_type() {
        return radio_type;
    }

    public void setRadio_type(String radio_type) {
        this.radio_type = radio_type;
    }

    public String getIsible_light_img() {
        return isible_light_img;
    }

    public void setIsible_light_img(String isible_light_img) {
        this.isible_light_img = isible_light_img;
    }

    public String getIsible_light_detection() {
        return isible_light_detection;
    }

    public void setIsible_light_detection(String isible_light_detection) {
        this.isible_light_detection = isible_light_detection;
    }

    public String getIsible_light_tracking() {
        return isible_light_tracking;
    }

    public void setIsible_light_tracking(String isible_light_tracking) {
        this.isible_light_tracking = isible_light_tracking;
    }

    public String getRed_outer_img() {
        return red_outer_img;
    }

    public void setRed_outer_img(String red_outer_img) {
        this.red_outer_img = red_outer_img;
    }

    public String getRed_outer_detection() {
        return red_outer_detection;
    }

    public void setRed_outer_detection(String red_outer_detection) {
        this.red_outer_detection = red_outer_detection;
    }

    public String getRed_outer_tracking() {
        return red_outer_tracking;
    }

    public void setRed_outer_tracking(String red_outer_tracking) {
        this.red_outer_tracking = red_outer_tracking;
    }

    public String getText_analysis() {
        return text_analysis;
    }

    public void setText_analysis(String text_analysis) {
        this.text_analysis = text_analysis;
    }

    public String getText_named_entity() {
        return text_named_entity;
    }

    public void setText_named_entity(String text_named_entity) {
        this.text_named_entity = text_named_entity;
    }

    public String getVoicle_recognition() {
        return voicle_recognition;
    }


    public SchemePageSubmit(String username, String password, String api, String category, String isTask, String radio_type, String isible_light_img, String isible_light_detection, String isible_light_tracking, String red_outer_img, String red_outer_detection, String red_outer_tracking, String text_analysis, String text_named_entity, String voicle_recognition, String visible_img_img_reid, String visible_img_img_segmentation, String radar_signal_classification, String visible_img_pose_estimation, String video_target_detection, String video_target_trace, String structured_data_classification, String structured_data_regression, String structured_data_cluster) {
        this.username = username;
        this.password = password;
        this.api = api;
        this.category = category;
        this.isTask = isTask;
        this.radio_type = radio_type;
        this.isible_light_img = isible_light_img;
        this.isible_light_detection = isible_light_detection;
        this.isible_light_tracking = isible_light_tracking;
        this.red_outer_img = red_outer_img;
        this.red_outer_detection = red_outer_detection;
        this.red_outer_tracking = red_outer_tracking;
        this.text_analysis = text_analysis;
        this.text_named_entity = text_named_entity;
        this.voicle_recognition = voicle_recognition;
        this.visible_img_img_reid = visible_img_img_reid;
        this.visible_img_img_segmentation = visible_img_img_segmentation;
        this.radar_signal_classification = radar_signal_classification;
        this.visible_img_pose_estimation = visible_img_pose_estimation;
        this.video_target_detection = video_target_detection;
        this.video_target_trace = video_target_trace;
        this.structured_data_classification = structured_data_classification;
        this.structured_data_regression = structured_data_regression;
        this.structured_data_cluster = structured_data_cluster;
    }

    public SchemePageSubmit(){}


}
