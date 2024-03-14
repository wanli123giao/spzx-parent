package org.example.code;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

public class test {
    public static void toZip(File srcDir, File destZipFile) throws IOException {
        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new
                FileOutputStream(destZipFile))) {
            addFilesToZip(srcDir, "", zos);
        }
    }
    private static void addFilesToZip(File file, String parentDirPath, ZipArchiveOutputStream zos)
            throws IOException {
        String entryName = parentDirPath + file.getName();
        if (file.isFile()) {
            ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
            zos.putArchiveEntry(entry);
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                IOUtils.copy(bis, zos);
            }
            zos.closeArchiveEntry();
        } else {
            for (File subFile : file.listFiles()) {
                addFilesToZip(subFile, entryName+"/", zos);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        long startTimeZip = System.currentTimeMillis(); // 记录开始时间
        File srcDir = new File("D:\\document\\testCompress");
        File outZipFile = new File("D:\\document\\test\\test.zip");
        toZip(srcDir, outZipFile);
        long endTimeZip = System.currentTimeMillis(); // 记录结束时间
        long elapsedTimeZip = endTimeZip - startTimeZip; // 计算时间差
        System.out.println("zip压缩消耗时间："+elapsedTimeZip+"ms"+" "+"压缩目录："+outZipFile);
    }
}
