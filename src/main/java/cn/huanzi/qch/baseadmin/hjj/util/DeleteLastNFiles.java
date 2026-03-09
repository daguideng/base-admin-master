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




public class DeleteLastNFiles {

    public static void main(String[] args) throws Exception{
        String directoryPath = "/Users/dengdagui/Documents/work/me/项目/项目原型及需求/算法平台/数据集分割测试/图像-目标检测/数据集/multiple_infrared_ray_target_detection_data"; // 替换为实际的目录路径
        int filesToDelete = 20;

        Map<String, Integer> remainingFilesCount = countAndDeleteFiles(directoryPath, filesToDelete);

        remainingFilesCount.forEach((folder, count) -> {
            System.out.println("Folder: " + folder + ", Remaining files: " + count);
        });
    }

    public static Map<String, Integer> countAndDeleteFiles(String directoryPath, int filesToDelete) throws IOException {
        File directory = new File(directoryPath);
        Map<String, Integer> remainingFilesCount = new HashMap<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] folders = directory.listFiles(File::isDirectory);

            if (folders != null) {
                Arrays.sort(folders, Comparator.comparing(File::getName));

                for (File folder : folders) {
                    String folderName = folder.getName();
                    File[] files = folder.listFiles();

                    if (files != null) {
                        remainingFilesCount.put(folderName, files.length);

                        // Sort files by name (you can change sorting criteria if needed)
                        Arrays.sort(files, Comparator.comparing(File::getName));

                        // Delete the last 'filesToDelete' files
                        int numFilesToDelete = Math.min(filesToDelete, files.length);
                        for (int i = 0; i < numFilesToDelete; i++) {
                            if (!files[i].delete()) {
                                String absolutePath = files[i].getAbsolutePath();
                                setFileWritable(absolutePath);
                               // Path path = Paths.get(absolutePath);
                                forceDelete(absolutePath);
                                System.out.println("文件删除成功");
                                System.err.println("delete file: " + files[i].getName());
                            }
                        }
                    } else {
                        remainingFilesCount.put(folderName, 0); // No files in folder
                    }
                }
            }
        } else {
            System.err.println("Invalid directory path or directory does not exist.");
        }

        return remainingFilesCount;
    }


    /**
     * 设置文件可写权限
     */

    public static void setFileWritable(String filePath) {
        // 创建 File 对象
        File file = new File(filePath);

        // 尝试设置文件可写
        if (file.setWritable(true)) {
            // 删除文件
            if (file.delete()) {
                System.out.println("文件删除成功");
            } else {
                System.out.println("文件删除失败");
            }
        } else {
            System.out.println("无法设置文件为可写，无法删除文件");
        }

    }


    /**
     * 强制命令进行删除
     */
    public static void forceDelete(String fileName) {


        ProcessBuilder pb = new ProcessBuilder("sudo", "rm", fileName);

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


}
