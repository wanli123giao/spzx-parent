package org.example.controller;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.example.other.FileCompressZip;


import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileCompress {

    public static void compressFilesToTarGz(List<File> files)  throws IOException {
        final  String nameTarGz="testHarDoopCompressTarGz.tar.gz";
        try (//1.创建一个文件输出流
             FileOutputStream outputStream = new FileOutputStream(nameTarGz);
             //2.创建一个缓冲输出流
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
             //3.创建一个Gzip压缩输出流
             GzipCompressorOutputStream gzipCompressorOutputStream = new GzipCompressorOutputStream(bufferedOutputStream);
             //4.创建一个归档输出流（ArchiveOutputStream），它连接到前面创建的Gzip压缩输出流 gzipOut
             ArchiveOutputStream archiveOutputStream = new TarArchiveOutputStream(gzipCompressorOutputStream);) {

            for (File file : files) {
                //5. 创建 TarArchiveEntry 对象表示每个文件
                TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(file, file.getName());
                archiveOutputStream.putArchiveEntry(tarArchiveEntry);
/**
 * tarOut.putArchiveEntry(tarEntry);: 这一行告诉 TarArchiveOutputStream 开始写入当前文件的内容。
 *
 * try (InputStream fis = new FileInputStream(file)) { ... }: 这是一个 try-with-resources 块，用于打开文件的输入流 (FileInputStream)。
 *
 * IOUtils.copy(fis, tarOut);: 这一行使用 Apache Commons IO 库的 IOUtils 类，将文件的内容从输入流 fis 复制到 .tar.gz 文件的输出流 tarOut 中。
 *
 * tarOut.closeArchiveEntry();: 这一行表示当前文件的内容已经写入 .tar.gz 文件，关闭当前 TarArchiveEntry。
 */
                // 使用 IOUtils 复制文件内容到输出流
                try (InputStream fis = new FileInputStream(file)) {
                    IOUtils.copy(fis, archiveOutputStream);
                }
//关闭
                archiveOutputStream.closeArchiveEntry();
            }
        }
    }
//    public static void compressFilesToZip(List<File> files) throws IOException {
////        final  String nameZip="test001CompressZip.zip";
//        try(
//                //1.创建一个文件输出流
//                FileOutputStream outputStreamZip = null;
//                //2.创建一个缓冲输出流
//                BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(outputStreamZip);
//                //3.创建一个zip压缩输出流
//                ZipArchiveOutputStream zipArchiveOutputStream=new ZipArchiveOutputStream(bufferedOutputStream)
//        ){
//            for (File file:files){
//                //5. 创建 ZipArchiveEntry 对象表示每个文件
//                ZipArchiveEntry zipArchiveEntry=new ZipArchiveEntry(file,file.getName());
//                //使用 Apache Commons IO 库的 IOUtils 类，将文件的内容从输入流 fis 复制到 .tar.gz 文件的输出流 tarOut 中。
//                zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
//                //打开输出流
//                InputStream fls=new FileInputStream(file);
//                //复制到归档输出流
//                IOUtils.copy(fls,zipArchiveOutputStream);
//                //关闭
//                zipArchiveOutputStream.closeArchiveEntry();
//            }
//        }
//    }


    // 创建要压缩的文件列表
    // 指定压缩后的输出文件名
    // 调用压缩方法
    // 打印压缩成功的消息
    // 如果发生异常，打印异常信息
    public static void main(String[] args) {
        // 创建要压缩的文件列表
        List<File> filesToCompress = Arrays.asList(new File("D:\\document\\testCompress\\设计模式.xmind"),
                new File("D:\\document\\testCompresss\\test1.avi"),new File("D:\\document\\testCompress\\hadoop-3.3.6"));
        // 指定压缩后的输出文件名
        long startTime = System.currentTimeMillis(); // 记录开始时间
        try {
            // 调用tar.gz压缩方法
            compressFilesToTarGz(filesToCompress);
            // 打印压缩成功的消息
            System.out.println("success_compressTar.gz");
        } catch (IOException e) {
            // 如果发生异常，打印异常信息
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis(); // 记录结束时间
        long elapsedTime = endTime - startTime; // 计算时间差
        System.out.println("耗费: " + elapsedTime + " 毫秒");

//        System.out.println("********************************************************************");
//        long startTime1 = System.currentTimeMillis(); // 记录开始时间
//        // 调用zip压缩方法
//        try {
//            FileCompressZip.compressFilesToZip(filesToCompress);
////            FileCompressZip.compressFilesToZip(filesToCompress1);
//            // 打印压缩成功的消息
//            System.out.println("success_compressZip");
//        } catch (IOException e) {
//            // 如果发生异常，打印异常信息
//            e.printStackTrace();
//        }
//        long endTime1 = System.currentTimeMillis(); // 记录结束时间
//        long elapsedTime1 = endTime1 - startTime1; // 计算时间差
//        System.out.println("耗费: " + elapsedTime1 + " 毫秒");
    }
}
