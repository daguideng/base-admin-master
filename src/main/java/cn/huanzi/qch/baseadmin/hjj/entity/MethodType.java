package cn.huanzi.qch.baseadmin.hjj.entity;

 /**

     //可见光图像分类
     "visible_img.img_classfication",

     //可见光目标检测
    "visible_img.target_detection",

     //红外线图像分类
      "infrared_ray.img_classfication",

      //红外线目标检测
     "infrared_ray.target_detection"


  **/


public  class  MethodType{

     //可见光图像分类
     public static String Visible_Img_Img_Classfication = "visible_img.img_classfication" ;

     //可见光目标检测：
     public static String Visible_Img_Target_Detection = "visible_img.target_detection" ;

     //红外线图像分类
     public static String Infrared_Ray_Img_Classfication = "infrared_ray.img_classfication" ;

      //红外线目标检测
      public static String Infrared_Ray_Target_Detection = "infrared_ray.target_detection" ;

      //视频目标检测:（视频）
      public static String Video_Detection = "video.target_detection";


     //目标跟踪：（视频）
      public static String Video_Target_Trace = "video.target_trace";

      //雷达：（信号分类）
      public static String Radar_Signal_Classification = "radar.signal_classification" ;



      //文本：（情感分析）
      public static String Text_Sen_Analysis = "text.sen_analysis" ;

      //文本：（命名实体识别）
      public static String Text_Entity_Recognition = "text.entity_recognition" ;

     //语音：（语音识别）
     public static String Asr_Asr_Recognition = "asr.asr_recognition" ;


     //目标跟踪：（可见光图像）
     public static String Visible_Img_Target_Trace = "visible_img.target_trace" ;

     //目标跟踪：（红外光图像）
     public static String Infrared_Ray_Target_trace = "infrared_ray.target_trace" ;

     //可见光图像：（姿态估计）
     public static String Visible_Img_Pose_Estimation = "visible_img.pose_estimation" ;

     //行人重识别：（可见光图像）
     public static String Visible_Img_Img_Reid = "visible_img.img_reid";

     //图像-语义分割
     public static String Visible_Img_Img_Segmentation = "visible_img.img_segmentation";

     //结构化数据-回归
     public static String Structured_Data_Regression = "structured_data.regression";


     //结构化数据-分类
     public static String Structured_Data_Classification = "structured_data.classification";


     //结构化数据-聚类
     public static String Structured_Data_Cluster = "structured_data.cluster";

     //白盒：
     public static String White = "white" ;

     //黑盒：
     public static String Black = "black" ;

     //数据集：
     public static String DataSet = "dataset" ;


     //可见光非对抗：
     public static String Extension_No_Attack = "extension_no_attack" ;


     //图像分类：
     public static String Img_Classfication = "img_classfication" ;

     //目标检测：
     public static String Target_Detection = "target_detection" ;

     //目标跟踪
     public static String Target_Trace = "target_trace" ;

     //结构化数据
     public static String Structured_Data = "structured_data";


 }
