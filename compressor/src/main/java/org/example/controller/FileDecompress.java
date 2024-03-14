package org.example.controller;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileDecompress {
    /**
     * @param files 文件
     * @throws IOException
     */
    public static void decompressTarGzFile(String files) throws IOException {

//        String inputStream="D:\\document\\testDecompress\\test001CompressTarGz.tar.gz";
//        String inputStream="D:\\document\\testDecompress\\hadoop-3.3.6.tar.gz";
//        final String nameTarGz = "D:\\document\\testDecompress\\test001CompressTarGz.tar.gz";
        try (//1.创建了一个文件输入流 (FileInputStream)
             FileInputStream fileInputStream = new FileInputStream(files);
             //2.创建了一个缓冲输入流 (BufferedInputStream)
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             //3.创建了一个Gzip解压缩输入流 (GzipCompressorInputStream)
             GzipCompressorInputStream gzipCompressorInputStream = new GzipCompressorInputStream(bufferedInputStream);
             //4.创建归档输入流 (ArchiveInputStream)
             ArchiveInputStream archiveInputStream = new TarArchiveInputStream(gzipCompressorInputStream);
        ) {//从压缩文件中读取每个条目，并将其作为 ArchiveEntry 对象返回。
            // 通过 ArchiveEntry，我们可以获取有关条目的信息，例如条目的名称、大小、权限等。
            ArchiveEntry entry;
            //迭代.tar.gz文件中的条目
            while ((entry = archiveInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    File outPutTarGz = new File("D:\\code\\test\\testDecompressTarGz", Paths.get(entry.getName()).normalize().toString());
                    if (!outPutTarGz.isDirectory() && !outPutTarGz.mkdirs()) {
//                        如果目录不存在且创建目录结构失败,抛出异常
                        throw new IOException("创建文件夹失败：" + outPutTarGz);
                    }
                } else {
                    File outPutTarGz = new File("D:\\code\\test\\testDecompressTarGz", Paths.get(entry.getName()).normalize().toString());
                    try (//输出流
                         OutputStream outputStream = new FileOutputStream(outPutTarGz)) {
                        IOUtils.copy(archiveInputStream, outputStream);
                    }
                }
            }
        }
    }

    public static void DecompressZip(String files) throws IOException {
        try (//1.创建了一个文件输入流 (FileInputStream
             FileInputStream fileInputStream = new FileInputStream(files);
             //2.创建了一个缓冲输入流 (BufferedInputStream)
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             //3.创建了一个zip解压缩输入流 (zipCompressorInputStream))
             ZipInputStream zipInputStream = new ZipInputStream(bufferedInputStream)) {
            ZipEntry entry;
//            while ((entry = zipInputStream.getNextEntry()) != null) {
//                if (entry.isDirectory()) {
//                    continue;
//                }
////                File outPutZip = new File("D:\\code\\test\\testDecompressZip", entry.getName());
//                File outPutZip = new File("D:\\code\\test\\testDecompressZip", Paths.get(entry.getName()).normalize().toString());
//                if (entry.isDirectory()) {
////                    检查解压后的目标目录是否已经存在
//                    if (!outPutZip.isDirectory() && outPutZip.mkdirs()) {
////                        如果目录不存在且创建目录结构失败,抛出异常
//                        throw new IOException("创建文件夹失败：" + outPutZip);
//                    }
//                } else {
//                    try (//输出流
//                         OutputStream outputStream = new FileOutputStream(outPutZip)) {
//                        IOUtils.copy(zipInputStream, outputStream);
//
//                    }
//                }
//            }

            //获取 ZIP 文件中的下一个条目。如果没有更多的条目，循环将终止。
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) {//判断当前条目是否是一个目录。如果是目录，会创建相应的目录结构。
                    // 创建目录结构
                    //使用 Java 的 Paths 类来处理路径。它会规范化路径，确保没有冗余的元素。例如，可以避免路径中的 . 或 ..。
                    File outPutZip = new File("D:\\code\\test\\testDecompressZip", Paths.get(entry.getName()).normalize().toString());
                    //检查目录是否已经存在，如果不存在则尝试创建整个目录结构。
                    if (!outPutZip.isDirectory() && !outPutZip.mkdirs()) {
                        throw new IOException("创建文件夹失败：" + outPutZip);
                    }
                } else {
                    // 创建文件
                    File outPutZip = new File("D:\\code\\test\\testDecompressZip", Paths.get(entry.getName()).normalize().toString());
                    try (OutputStream outputStream = new FileOutputStream(outPutZip)) {
                        IOUtils.copy(zipInputStream, outputStream);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
//          String inputTarGz = "test001decompressTarGZ.tar.gz";
        List<String> inputFiles = new ArrayList<>();
        inputFiles.add("D:\\document\\testDecompress\\test001CompressTarGz.tar.gz");
        inputFiles.add("D:\\document\\testDecompress\\hadoop-3.3.6.tar.gz");
        long startTimeTarGz = System.currentTimeMillis(); // 记录开始时间
        for (String inputFile : inputFiles) {
            try {
                FileDecompress.decompressTarGzFile(inputFile);
                System.out.println("Files decompressed successfully: " + inputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis(); // 记录结束时间
            long elapsedTime = endTime - startTimeTarGz; // 计算时间差
            System.out.println("耗费: " + elapsedTime + " 毫秒");
        }
//            long startTimeTarGz = System.currentTimeMillis(); // 记录开始时间
//            try {
//                decompressTarGzFile(inputFiles);
//                System.out.println("Files decompressedTarGz successfully.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            long endTime = System.currentTimeMillis(); // 记录结束时间
//            long elapsedTime = endTime - startTimeTarGz; // 计算时间差
//            System.out.println("耗费: " + elapsedTime + " 毫秒");
        System.out.println("************************************");
//            String inputZip = "test";
//            String outputDirectoryZip = "D:\\";
//            long startTimeZip = System.currentTimeMillis(); // 记录开始时间
//            try {
//                DecompressZip(inputTarGz);
//                System.out.println("Files decompressedZip successfully.");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            long endTime1 = System.currentTimeMillis(); // 记录结束时间
//            long elapsedTime1 = endTime1 - startTimeZip; // 计算时间差
//            System.out.println("耗费: " + elapsedTime1 + " 毫秒");
        List<String> inputFilesZip = new ArrayList<>();
//            inputFilesZip.add("D:\\document\\testDecompress\\test001CompressZip.zip");
        inputFilesZip.add("D:\\document\\testDecompress\\apache-tomcat-11.0.0-M15-windows-x64.zip");
        inputFiles.add("D:\\document\\testDecompress\\hadoop-3.3.6.tar.gz");
        long startTimeZip = System.currentTimeMillis(); // 记录开始时间
        for (String inputFile : inputFilesZip) {
            try {
                FileDecompress.DecompressZip(inputFile);
                System.out.println("Files decompressed successfully: " + inputFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime1 = System.currentTimeMillis(); // 记录结束时间
            long elapsedTime1 = endTime1 - startTimeZip; // 计算时间差
            System.out.println("耗费: " + elapsedTime1 + " 毫秒");
        }
    }
}
