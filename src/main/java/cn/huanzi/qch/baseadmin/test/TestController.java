package cn.huanzi.qch.baseadmin.test;


import cn.huanzi.qch.baseadmin.entity.Model_table;
import cn.huanzi.qch.baseadmin.entity.One_click;
import cn.huanzi.qch.baseadmin.hjj.controller.DataSetIncrease;
import cn.huanzi.qch.baseadmin.hjj.controller.HjjUpload;
import cn.huanzi.qch.baseadmin.hjj.controller.Testscheme;
import cn.huanzi.qch.baseadmin.hjj.entity.MethodType;
import cn.huanzi.qch.baseadmin.hjj.entity.ModelResponseEntity;
import cn.huanzi.qch.baseadmin.hjj.entity.TableDataResponse;
import cn.huanzi.qch.baseadmin.hjj.entity.User;
import cn.huanzi.qch.baseadmin.hjj.service.ModelService;
import cn.huanzi.qch.baseadmin.hjj.service.ModelTableService;
import cn.huanzi.qch.baseadmin.hjj.service.OneclickService;
import cn.huanzi.qch.baseadmin.hjj.service.OneclickServiceV2;
import cn.huanzi.qch.baseadmin.hjj.util.ReadJson;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcraft.jsch.KnownHosts;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 一键运行上传模型：
 */

//api/v1/upload/types/page

@RestController
@RequestMapping("/api")
public class TestController {

    Log log = LogFactory.get();




    @GetMapping("/v1/upload/types/page")
    public Map<String, Object> oneRun(@RequestBody One_click one_click, HttpServletRequest request) throws Exception {

        JSONObject jsonObject = new JSONObject(one_click);

        log.info("前端请求body数据为:{}", jsonObject);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> data1 = new HashMap<>();
        Map<String, Object> listmap = new HashMap<>();
        Map<String, Object> datamap = new HashMap<>();

        List<String> list = new ArrayList<>();


        data1.put("id", 1);
        data1.put("name", "1");
        data1.put("code", "000");
        data1.put("status", 1);
        listmap.put("list", data1);

        datamap.put("data", listmap);
        datamap.put("total", 1);

        map.put("code", 200);
        map.put("msg", "success");
        map.put("data", datamap);
        return map;


    }
    }