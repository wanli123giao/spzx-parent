package org.example.other;

import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;


public class FileCompressorTarZg {
    /**
     *
     * @param files:文件对象
     * @param outputTarGz:压缩后的输出文件的名称，以.tar.gz结尾
     */
    public static void compressFilesToTarGz(List<File> files)  throws IOException {
        final  String name="test001CompressTarGz.tar.gz";
        try (//1.创建一个文件输出流
             FileOutputStream outputStream = new FileOutputStream(name);
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


    // 创建要压缩的文件列表
    // 指定压缩后的输出文件名
    // 调用压缩方法
    // 打印压缩成功的消息
    // 如果发生异常，打印异常信息
    public static void main(String[] args) {
        // 创建要压缩的文件列表
        List<File> filesToCompress = Arrays.asList(new File("D:\\document\\testCompress\\设计模式.xmind"),
                new File("D:\\document\\testCompresss\\test1.avi"));
        // 指定压缩后的输出文件名
//        String outputTarGz =
        long startTime = System.currentTimeMillis(); // 记录开始时间
        try {
            // 调用压缩方法
            compressFilesToTarGz(filesToCompress);
            // 打印压缩成功的消息
            System.out.println("Files compressed successfully.");
        } catch (IOException e) {
            // 如果发生异常，打印异常信息
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis(); // 记录结束时间
        long elapsedTime = endTime - startTime; // 计算时间差

        System.out.println("耗费: " + elapsedTime + " 毫秒");
    }
}
