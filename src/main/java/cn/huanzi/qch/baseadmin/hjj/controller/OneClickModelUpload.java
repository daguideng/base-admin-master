package cn.huanzi.qch.baseadmin.hjj.controller;

import cn.huanzi.qch.baseadmin.entity.Model_table;
import cn.huanzi.qch.baseadmin.hjj.entity.ModelResponseEntity;
import cn.huanzi.qch.baseadmin.hjj.service.ModelService;
import cn.huanzi.qch.baseadmin.hjj.service.ModelTableService;
import cn.huanzi.qch.baseadmin.hjj.service.OneclickService;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * 一键运行上传模型：
 */

@RestController
@RequestMapping("/oneclick")
public class OneClickModelUpload {

    Log log = LogFactory.get();

    @Resource
    private OneclickService oneclickService;

    @Resource
    private HjjUpload hjjUpload;

    @Resource
    private DataSetIncrease dataSetIncrease;

    @Resource
    private ModelService modelService;

    @Resource
    private ModelTableService modelTableService;



    @GetMapping("/modelupload")
    public ModelAndView testsetMethodMenu() {

        return new ModelAndView("oneclick/modelupload");
    }

    /***
     *展现算法模型的表格的数据
     */








}
