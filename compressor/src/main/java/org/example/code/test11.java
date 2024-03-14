package org.example.code;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

public class test11 {
    public static void main(String[] args) throws IOException {
//        long startTimeZip = System.currentTimeMillis();
        File srcDir = new File("D:\\document\\testCompress");
        String outPUtPath = "D:\\document\\testc";
        String targetFileName = srcDir.getName() + ".zip";
        File outDir = new File(outPUtPath, targetFileName);
        toZip(srcDir, outDir);
//        long endTimeZip = System.currentTimeMillis();
//        long elapsedTimeZip = endTimeZip - startTimeZip;
//        System.out.println("zip压缩消耗时间：" + elapsedTimeZip + "ms" + " " + "压缩目录：" + outDir);
//        System.out.println("************************************************");
    }
    public static void toZip(File srcDir, File targetFile) throws IOException {
        int forTimes=0;
        //输入srcDir文件不存在时 直接返回
        if (!srcDir.exists()) {
            System.out.println("源目录不存在，请检查路径");
            return;
        }
        //targetFile父目录不存在的时候自动创建文件夹
        File parentFile = targetFile.getParentFile();
        if (!parentFile.exists()) {
            System.out.println("父路径不存在,自动创建文件夹");
            parentFile.mkdirs();
        }
        //1.存在同名文件应该修改输入文件的名称不是删除文件，万一文件有用呢
        if (targetFile.exists() && targetFile.isFile()) {
            System.out.println("存在同名文件");
            return;
//            targetFile.delete();
        }
//        String baseName = targetFile.getName();
//        // 获取文件名，不含扩展名
//        String fileBaseName = baseName.substring(0, baseName.lastIndexOf("."));
//        // 获取文件扩展名
//        String fileExtension = baseName.substring(baseName.lastIndexOf(".") + 1, baseName.length());
//        // 创建以文件名命名的文件夹
//        File zipFolder = new File(parentFile, fileBaseName);
//        if (!zipFolder.exists()) {
//            zipFolder.mkdirs();
//        }
//        int conflictIndex = 0;
//
//        while (targetFile.exists() && targetFile.isFile()) {
//            conflictIndex++;
//            String newBaseName = fileBaseName + "(" + conflictIndex + ")";
//            String newTargetName = newBaseName + "." + fileExtension;
//            targetFile = new File(parentFile, newTargetName);
//        }
        File[] files = srcDir.listFiles();
        //输入的目录路径不存在直接返回
        if (files == null) {
            System.out.println("源目录为空或无法读取");
            return;
        }
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(targetFile));) {
            for (File file : files) {
                if (file.isFile()) {
                    ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
                    zos.putArchiveEntry(entry);
                    try (FileInputStream fis = new FileInputStream(file);
                         BufferedInputStream bis = new BufferedInputStream(fis)) {
                        IOUtils.copy(bis, zos);
                    }
                    zos.closeArchiveEntry();
                } else {//进入文件夹，里面还有文件夹创建该文件夹的.zip文件在父目录.zip
//                    toZip(file, targetFile);
//                    for (File file1 : files) {
//                        if (file1.isFile()) {
//                            ZipArchiveEntry entry = new ZipArchiveEntry(file1.getName());
//                            zos.putArchiveEntry(entry);
//                            try (FileInputStream fis = new FileInputStream(file1);
//                                 BufferedInputStream bis = new BufferedInputStream(fis)) {
//                                IOUtils.copy(bis, zos);
//                            }
//                            zos.closeArchiveEntry();
//                        }
//                    }

                }
            }
        } catch (IOException e) {
            System.out.println("压缩过程出错");
            e.printStackTrace();
        }
    }

    private static void Zos(File targetFile) throws FileNotFoundException {
        ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(targetFile));
    }
}