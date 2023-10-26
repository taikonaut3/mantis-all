package io.github.astro.mantis.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author WenBo Zhou
 * @Date 2023/9/7 14:36
 */
public class FileUtils {

    public static void writeLineFile(List<String> content, File targetFile) {
        try {
            if (content.isEmpty()) {
                return;
            }
            if (!targetFile.exists()) {
                FileUtils.createFileWithParentDirectory(targetFile.getAbsolutePath());
            }
            try (RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
                 FileChannel channel = file.getChannel();
                 FileLock lock = channel.lock()) {

                // 读取文件内容
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = file.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                String fileContents = sb.toString();

                // 添加新行
                for (String lineToAdd : content) {
                    if (!fileContents.contains(lineToAdd)) {
                        fileContents += lineToAdd + "\n";
                    }
                }

                // 将内容写回文件
                file.setLength(0);
                file.write(fileContents.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeLineFile(String content, File targetFile) {
        try {
            if (content.isEmpty()) {
                return;
            }
            if (!targetFile.exists()) {
                FileUtils.createFileWithParentDirectory(targetFile.getAbsolutePath());
            }
            try (RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
                 FileChannel channel = file.getChannel();
                 FileLock lock = channel.lock()) {

                // 读取文件内容
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = file.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                String fileContents = sb.toString();

                // 添加新行
                if (!fileContents.contains(content)) {
                    fileContents += content + "\n";
                }
                // 将内容写回文件
                file.setLength(0);
                file.write(fileContents.getBytes());

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readLineFile(String path) {
        File file = new File(path);
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public static void createFileWithParentDirectory(String filePath) {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        // 递归创建所有缺失的父目录
        createParentDirectories(parentDir);

        // 创建文件
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createParentDirectories(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File parentDir = directory.getParentFile();
        if (parentDir != null) {
            createParentDirectories(parentDir);
        }
    }
}
