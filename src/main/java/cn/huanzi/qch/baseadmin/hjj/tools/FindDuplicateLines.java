package cn.huanzi.qch.baseadmin.hjj.tools;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class FindDuplicateLines {

    Log log = LogFactory.get();

    public static void main(String[] args) {
        FindDuplicateLines findDuplicateLines = new FindDuplicateLines();
        findDuplicateLines.mainTest();
    }


    public void mainTest() {
        String filename = "/Users/dengdagui/Documents/4444/3333/docker-compose.yml";
        Set<String> duplicateLines = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("image:")) {
                    String line1 = line.split("image:")[1];
                    duplicateLines.add(line1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        dockerSaveTar(duplicateLines);


    }



    public  void dockerSaveTar(Set<String> duplicateLines) {
        System.out.println("Duplicate sise:" + duplicateLines.size());
        int sum = 0;
        log.info("总共条数为:{}" , duplicateLines.size());
        for (String line : duplicateLines) {
         //   System.out.println(line.trim());
            sum ++;


            // 取出字符串：chinese-attack  harbor.ks.x/speedy-zhinengsuanfaguanlipingtai/chinese-attack:a798a034-20240411112327286
            String[] split = line.split("/");
            String imageName = split[split.length - 1];
            String[] split1 = imageName.split(":");
            String imageName1 = split1[0];

            // docker save -o chinese-attack.tar harbor.ks.x/speedy-zhinengsuanfaguanlipingtai/chinese-attack:a798a034-20240411112327286
            String cmd = "docker save -o " + imageName1 + ".tar " + line;


            // java 执行shell命令
            try {
              //  Runtime.getRuntime().exec(cmd);
                Thread.sleep(1000);
                System.out.println("docker save命令是:" + cmd);
               // log.info("docker save命令是:{}", cmd);
             //   log.info("生成第{}条，line:{}save sucess ", sum,line);
            } catch (Exception e) {
                log.error("line:{}save fail", line);
                e.printStackTrace();

            }


        }



    }






}
