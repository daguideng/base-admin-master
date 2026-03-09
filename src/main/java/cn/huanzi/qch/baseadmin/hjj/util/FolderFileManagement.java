package cn.huanzi.qch.baseadmin.hjj.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class FolderFileManagement {

    //public static void main(String[] args) throws IOException {

    public static Map<String,Object> deleteDataSetFile(String directoryPath,int deleteLine) throws IOException {
       // String directoryPath = "/path/to/your/directory"; // 替换成你的目录路径

        Map<String, Integer> folderFileCounts = new HashMap<>();

        Map<String,Object> map = new HashMap<>();

        // 获取目录下的文件夹列表，并统计每个文件夹的文件数
        Files.list(Paths.get(directoryPath))
                .filter(Files::isDirectory)
                .forEach(dir -> {
                    try {
                        int fileCount = (int) Files.list(dir).count();
                        folderFileCounts.put(dir.toString(), fileCount);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        // 按文件夹名排序
        folderFileCounts.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(entry -> {
                    String folderPath = entry.getKey();
                    int initialFileCount = entry.getValue();
                    System.out.println("Folder: " + folderPath + ", Initial File Count: " + initialFileCount);

                    // 删除每个文件夹中的最后20个文件



                    try {
                        File[] files = new File(folderPath).listFiles();

                        map.put("sumfiles",files.length);



                        if (files != null && files.length > deleteLine) {
                            // 按文件名排序
                            Arrays.sort(files, Comparator.comparing(File::getName));

                            for (int i =  deleteLine; i < files.length; i++) {

                            // 删除最后20个文件
                          //  for (int i = files.length - deleteLine; i < files.length; i++) {
                               // files[i].delete();
                                forceDelete(files[i].getAbsolutePath());
                                System.out.printf("Deleted file: %s%n", files[i].getName());
                            }

                            // 计算剩余文件数
                            int remainingFiles = (int) Files.list(Paths.get(folderPath)).count();
                            System.out.println("After deletion, Remaining File Count: " + remainingFiles);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        return map ;
    }



    /**
     * 强制命令进行删除
     */
    public static void forceDelete(String fileName) {


        ProcessBuilder pb = new ProcessBuilder("sudo", "rm","-rf", fileName);

        try {
            Process process = pb.start();
            int exitCode = process.waitFor(); // Wait for the process to finish

            if (exitCode == 0) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file. Exit code: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    /***
     * 强制删除文件夹
     */
    public static void forceDeleteFolder(String folderName) {
       // String folderPath = "/path/to/your/folder";
        File folder = new File(folderName);

        if (!folder.exists()) {
            System.out.println("Folder not found: " + folderName);
            return;
        }

        try {
            deleteFolder(folder);
            System.out.println("Folder is deleted.");
        } catch (Exception e) {
            System.err.println("Failed to delete folder: " + e.getMessage());
        }
    }

    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    if (!file.delete()) {
                        System.err.println("Failed to delete file: " + file);
                    }
                }
            }
        }
        if (!folder.delete()) {
            System.err.println("Failed to delete folder: " + folder);
        }
    }



}
