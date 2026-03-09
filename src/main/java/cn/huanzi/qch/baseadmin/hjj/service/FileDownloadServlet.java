package cn.huanzi.qch.baseadmin.hjj.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class FileDownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response,String filePath)
            throws ServletException, IOException {

        // 获取文件路径参数
        if (filePath == null || filePath.isEmpty()) {
            throw new ServletException("File path parameter is required");
        }

        // 设置响应头
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + new File(filePath).getName() + "\"");

        // 读取文件并写入响应流
        try (FileInputStream fis = new FileInputStream(filePath);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = fis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            throw new ServletException("File could not be downloaded", e);
        }
    }
}
