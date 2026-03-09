package cn.huanzi.qch.baseadmin.hjj.tools;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadServletXXX extends HttpServlet {

    public static void doGet(String filePath,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // 设置响应的类型和编码
        response.setContentType("application/zip");
        response.setCharacterEncoding("UTF-8");

        // 文件名和路径，根据需要进行替换
      //  String filePath = "/path/to/your/file.zip";  // 替换为你的ZIP文件路径
      //  String fileName = "file.zip";  // 替换为你想要客户端保存的文件名

        File file = new File(filePath);

        // 设置响应头，告诉浏览器该文件作为附件下载，并指定下载时的文件名
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

        // 读取文件并写入输出流
        try (InputStream inputStream = new FileInputStream(file);
             OutputStream outputStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

