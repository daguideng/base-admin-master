package cn.huanzi.qch.baseadmin.adas.service;

import cn.huanzi.qch.baseadmin.entity.Adas_move_scenario;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ReadExcelUtil {


    @Resource
    private SFTPUtils sFTPUtils;

    Log log = LogFactory.get();

    public void readExcel(String excelPath) {
        //根据表头名和实体对象字段名来匹配记录，并返回实体列表
       // File file = new File("E:/自测资料/test.xlsx");
       // File file = new File(excelPath);
      //  ExcelReader reader = ExcelUtil.getReader(FileUtil.file("test.xlsx"), 0);
        ExcelReader reader = ExcelUtil.getReader(FileUtil.file(excelPath), 0);
      //  ExcelReader reader = ExcelUtil.getReader(excelPath);
        /**
         *  第一个参数是表头所在行；第二个参数是读取数据开始行，会自动过滤掉表头行；第三个参数是实体类
         *  如果行记录所有列都为空，则会跳过该行记录，不创建对象。只要有一个列是不为空的，就会创建对象
         **/
        List<Adas_move_scenario> read = reader.read(0, 0, Adas_move_scenario.class);
        reader.close();
        for (Adas_move_scenario adas_move_scenario : read) {

            System.out.println("adas_move_scenario"+adas_move_scenario.getChoice_laws());

            String id = String.valueOf(adas_move_scenario.getId());
            String Scenario_editor_id = adas_move_scenario.getScenario_editor_id();
            String choice_laws = adas_move_scenario.getChoice_laws();
            String select_case = adas_move_scenario.getSelect_case();

           // System.out.println("id-->"+id);
           // System.out.println("Scenario_editor_id-->"+Scenario_editor_id);

            JSONObject  jsonObject = new JSONObject(adas_move_scenario);
            System.out.println("jsonObject:{}"+jsonObject);




        }


    }



    /**
     * 获取文件第三种方式
     */
    public File getFile(MultipartFile file) {

        Long begin = System.currentTimeMillis();

        File uploadfile = null;
        String fileExcelPath = System.getProperty("user.dir") + "/temp/generatorScene/";
        log.info("fileExcelPath:{}",fileExcelPath);

        if (!file.isEmpty()) {
            try {
                // 保存文件
                String fileName = file.getOriginalFilename();

                uploadfile = new File(fileExcelPath + fileName);
                if (!uploadfile.getParentFile().exists()) {
                    uploadfile.getParentFile().mkdirs();
                }
                file.transferTo(uploadfile);
                return uploadfile;
            } catch (IOException e) {
                log.info("上传失败");
                e.printStackTrace();

            }
        }

        long end = System.currentTimeMillis();
        log.info("上传文件耗时:{} 毫秒", (end - begin));

        return uploadfile;
    }

//    public static void main(String[] args) {
//
//        String path = "/Users/dengdagui/Documents/install/test/test.xlsx" ;
//        ReadExcelUtil  xx = new ReadExcelUtil();
//        xx.readExcel(path);
//
//    }


}
