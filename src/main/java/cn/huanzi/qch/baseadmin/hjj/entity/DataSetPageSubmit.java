package cn.huanzi.qch.baseadmin.hjj.entity;

public class DataSetPageSubmit {

    public String username;
    public String password;
    public String api;

    public String radio_type;
    public String visible_light_img;

    public String getVisible_light_img() {
        return visible_light_img;
    }

    public void setVisible_light_img(String visible_light_img) {
        this.visible_light_img = visible_light_img;
    }

    public String getVisible_light_detection() {
        return visible_light_detection;
    }

    public void setVisible_light_detection(String visible_light_detection) {
        this.visible_light_detection = visible_light_detection;
    }

    public String getVisible_light_tracking() {
        return visible_light_tracking;
    }

    public void setVisible_light_tracking(String visible_light_tracking) {
        this.visible_light_tracking = visible_light_tracking;
    }

    public String visible_light_detection;
    public String visible_light_tracking;
    public String red_outer_img;
    public String red_outer_detection;
    public String red_outer_tracking;
    public String text_analysis;
    public String text_named_entity;
    public String voicle_recognition;


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

    public String radar_signal_classification;
    public String visible_img_pose_estimation;




    public String visible_img_img_segmentation;
    public String video_target_detection ;
    public String video_target_trace ;
    public String structured_data_classification;
    public String structured_data_regression;


    public String visible_img_img_reid;

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

    public String getRadio_type() {
        return radio_type;
    }

    public void setRadio_type(String radio_type) {
        this.radio_type = radio_type;
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

    public void setVoicle_recognition(String voicle_recognition) {
        this.voicle_recognition = voicle_recognition;
    }



    public DataSetPageSubmit(){}


    public DataSetPageSubmit(String username, String password, String api, String radio_type, String visible_light_img, String visible_light_detection, String visible_light_tracking, String red_outer_img, String red_outer_detection, String red_outer_tracking, String text_analysis, String text_named_entity, String voicle_recognition, String radar_signal_classification, String visible_img_pose_estimation, String visible_img_img_segmentation, String video_target_detection, String video_target_trace, String structured_data_classification, String structured_data_regression, String visible_img_img_reid) {
        this.username = username;
        this.password = password;
        this.api = api;
        this.radio_type = radio_type;
        this.visible_light_img = visible_light_img;
        this.visible_light_detection = visible_light_detection;
        this.visible_light_tracking = visible_light_tracking;
        this.red_outer_img = red_outer_img;
        this.red_outer_detection = red_outer_detection;
        this.red_outer_tracking = red_outer_tracking;
        this.text_analysis = text_analysis;
        this.text_named_entity = text_named_entity;
        this.voicle_recognition = voicle_recognition;
        this.radar_signal_classification = radar_signal_classification;
        this.visible_img_pose_estimation = visible_img_pose_estimation;
        this.visible_img_img_segmentation = visible_img_img_segmentation;
        this.video_target_detection = video_target_detection;
        this.video_target_trace = video_target_trace;
        this.structured_data_classification = structured_data_classification;
        this.structured_data_regression = structured_data_regression;
        this.visible_img_img_reid = visible_img_img_reid;
    }
}



