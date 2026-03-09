package cn.huanzi.qch.baseadmin.hjj.controller;

import cn.huanzi.qch.baseadmin.hjj.service.DataSetUploadService;
import cn.huanzi.qch.baseadmin.hjj.service.UploadModelService;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;



@RestController
@RequestMapping("/delete")
public class DataSetTools {

        Log log = LogFactory.get();

        @Resource
        private DataSetUploadService dataSetUploadService;

        @Resource
        private UploadModelService uploadModelService;

        @GetMapping("/datasetfile")
        public ModelAndView UpalodMethodMenu() {
            return new ModelAndView("delete/datasetfile");
        }





}
