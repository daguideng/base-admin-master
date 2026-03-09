package cn.huanzi.qch.baseadmin.adas.controller;


import cn.hutool.core.io.BOMInputStream;
import cn.hutool.extra.spring.SpringUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;






/**
 * 清空Redis   /adas/cleanredis
 */
@RestController
@RequestMapping("/adas")
public class Cleanredis {





    @GetMapping("/cleanredis")
    public ModelAndView UpalodMethodMenu() {
        return new ModelAndView("adas/cleanredis");
    }


    //清空ADAS-redis：
    @PostMapping("/doclean")
    public Map<String, Object> adasCleanRedis(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        Map<String, Object> map = new HashMap<>();

        //1.连接 redis

        //2.删除 指定的Key

        //3. 返加 删除指定Key成功：


        return map;
    }


}




