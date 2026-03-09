package cn.huanzi.qch.baseadmin.hjj.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Component
public class ReadJson {

    /**
     * InputStream To File
     *
     * @return File
     */

    public void readFile(StringBuffer replaceResult, LinkedList<String> list) throws IOException {


        ClassPathResource classPathResource = new ClassPathResource("schemeJson/black/black-visible_img-target_detection.json");
        InputStream inputStream = classPathResource.getStream();
        File file = ReadJson.asFile(inputStream);//手动转换：InputStream To File


        // 指定要读取的文件路径
        String filePath = file.toString();

        try {
            // 使用FileUtil按行读取文件
            List<String> lines = FileUtil.readLines(new File(filePath), "UTF-8");

            // 遍历每一行内容
            for (String line : lines) {
                // 处理每一行的内容：
                System.out.println(line);


                // 替换相关变量：

                replaceResult.append(line);
            }
        } catch (Exception e) {
            // 处理文件读取异常
            e.printStackTrace();
        }

    }


    public static File asFileExcel(InputStream in) throws IOException {
        File tempFile = File.createTempFile("test", ".xlsx");
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in, out);
        return tempFile;
    }


    public static File asFile(InputStream in) throws IOException {
        File tempFile = File.createTempFile("whitelist", ".json");
        tempFile.deleteOnExit();
        FileOutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in, out);
        return tempFile;
    }


    /**
     * 第一种方法：
     */
    public static String getFistReportContent(String headPath, Map<String, String> replace_map) throws IOException {

        StringBuilder sbf = new StringBuilder(1000);

        InputStream sourceFile = null;
        BufferedReader bufReader = null;

        int sum = 0;

        try {

            //读入emailReport的主要head内容：
            //   sourceFile = JmeterInterReportImpl.class.getClassLoader().getResourceAsStream("templates/properties/report/head.html");
            sourceFile = ReadJson.class.getClassLoader().getResourceAsStream(headPath);
            bufReader = new BufferedReader(new InputStreamReader(sourceFile, "utf-8"));

            for (String line = null; (line = bufReader.readLine()) != null; ) {

                for (Map.Entry<String, String> entry : replace_map.entrySet()) {
                    // 一般遍历map就是获取key和value
                    String result = "key为：" + entry.getKey() + "，value为：" + entry.getValue();
                    // System.out.println("key为：" + entry.getKey());

                    String josn_key = line.split(":")[0];

                    if (josn_key.contains("\"" + entry.getKey() + "\"") && !josn_key.contains("\"description\"")) {
                        //对name只替换一次：
                        if (josn_key.contains("\"name\"") && sum == 0) {
                            line = (String.format("\"" + entry.getKey() + "\"" + ":" + "%s", "\"" + entry.getValue() + "\"" + ","));
                            sum = 100;

                        } else if (!josn_key.contains("\"name\"")) {
                            line = (String.format("\"" + entry.getKey() + "\"" + ":" + "%s", "\"" + entry.getValue() + "\"" + ","));
                        }

                        break;

                    } else if (josn_key.contains("\"" + entry.getKey() + "\"") && josn_key.contains("\"description\"")) {
                        line = (String.format("\"" + entry.getKey() + "\"" + ":" + "%s", "\"" + entry.getValue() + "\""));

                        break;
                    }


                }


                sbf.append(line);
                // line = null ;
                System.out.println(line);


            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufReader.close();
            sourceFile.close();
        }

        return sbf.toString();
    }


    /**
     * 现采用第二种方法：
     */
    public static StringBuffer getReportContent(String headPath, Map<String, String> replace_map) throws Exception {

        StringBuffer sbf = new StringBuffer(1000);

        InputStream sourceFile = null;
        BufferedReader bufReader = null;

        int sum = 0;

        try {

            //读入emailReport的主要head内容：
            //   sourceFile = JmeterInterReportImpl.class.getClassLoader().getResourceAsStream("templates/properties/report/head.html");
            sourceFile = ReadJson.class.getClassLoader().getResourceAsStream(headPath);
            bufReader = new BufferedReader(new InputStreamReader(sourceFile, "utf-8"));

            for (String line = null; (line = bufReader.readLine()) != null; ) {

                for (Map.Entry<String, String> entry : replace_map.entrySet()) {
                    // 一般遍历map就是获取key和value
                    // String result = "key为：" + entry.getKey() + "，value为：" + entry.getValue();
                    // System.out.println("key为：" + entry.getKey());

                    String josn_key = line.split(":")[0];

                    if (josn_key.contains("\"" + entry.getKey() + "\"") && !josn_key.contains("\"descript\"")) {
                        //对name只替换一次：
                        if (josn_key.contains("\"name\"") && sum == 0) {
                            line = (String.format("\"" + entry.getKey() + "\"" + ":" + "%s", "\"" + entry.getValue() + "\"" + ","));
                            sum = 100;

                        } else if (!josn_key.contains("\"name\"") && !josn_key.contains("descript")) {
                            line = (String.format("\"" + entry.getKey() + "\"" + ":" + "%s", "\"" + entry.getValue() + "\"" + ","));
                        }
                        ;

                        break;

                    } else if (josn_key.contains("descript")) {
                        line = (String.format("\"" + entry.getKey() + "\"" + ":" + "%s", "\"" + entry.getValue() + "\""));
                        break;
                    }


                }


                sbf.append(line);


            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufReader.close();
            sourceFile.close();
        }

        return sbf;
    }


    /**
     * 为一键化数据增广而写的工具方法
     */
    public static StringBuffer getMapValue(String headPath) throws Exception {

        StringBuffer sbf = new StringBuffer(1000);
        InputStream sourceFile = null;
        BufferedReader bufReader = null;

        try {

            //读入emailReport的主要head内容：
            sourceFile = ReadJson.class.getClassLoader().getResourceAsStream(headPath);
            bufReader = new BufferedReader(new InputStreamReader(sourceFile, "utf-8"));

            for (String line = null; (line = bufReader.readLine()) != null; ) {
                sbf.append(line);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufReader.close();
            sourceFile.close();
        }

        return sbf;
    }


    /**
     * adas运行定时器采用替换json方法：
     */
    public static StringBuffer adasSendInfo(String headPath, Map<String, String> replace_map) throws Exception {

        StringBuffer sbf = new StringBuffer(1000);

        InputStream sourceFile = null;
        BufferedReader bufReader = null;

        int sum = 0;

        try {

            //读入emailReport的主要head内容：
            //   sourceFile = JmeterInterReportImpl.class.getClassLoader().getResourceAsStream("templates/properties/report/head.html");
            sourceFile = ReadJson.class.getClassLoader().getResourceAsStream(headPath);
            bufReader = new BufferedReader(new InputStreamReader(sourceFile, "utf-8"));

            for (String line = null; (line = bufReader.readLine()) != null; ) {

                for (Map.Entry<String, String> entry : replace_map.entrySet()) {
                    // 一般遍历map就是获取key和value
                    // String result = "key为：" + entry.getKey() + "，value为：" + entry.getValue();
                    // System.out.println("key为：" + entry.getKey());

                    String josn_key = line.split(":")[0];
                    if (josn_key.contains("\"" + entry.getKey() + "\"")) {
                        //对name只替换一次：
                        line = (String.format("\"" + entry.getKey() + "\"" + ":" + "%s", "\"" + entry.getValue() + "\"" + ","));
                        break;
                    }


                }


                sbf.append(line);


            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bufReader.close();
            sourceFile.close();
        }

        return sbf;
    }










}
