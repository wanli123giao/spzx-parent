package org.example.other;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileCompressZip {
    /**
     *
     * @param files:文件对象
//     * @param outputZip:压缩后的输出文件的名称，以.zip结尾
     */
    public static void compressFilesToZip(List<File> files) throws IOException {
        final  String name="test001CompressZip.zip";
        try(
                //1.创建一个文件输出流
                FileOutputStream outputStreamZip = new FileOutputStream(name);
                //2.创建一个缓冲输出流
                BufferedOutputStream bufferedOutputStream=new BufferedOutputStream(outputStreamZip);
                //3.创建一个zip压缩输出流
                ZipArchiveOutputStream zipArchiveOutputStream=new ZipArchiveOutputStream(bufferedOutputStream)
        ){
            for (File file:files){
                //5. 创建 ZipArchiveEntry 对象表示每个文件
                ZipArchiveEntry zipArchiveEntry=new ZipArchiveEntry(file,file.getName());
                //使用 Apache Commons IO 库的 IOUtils 类，将文件的内容从输入流 fis 复制到 .tar.gz 文件的输出流 tarOut 中。
                zipArchiveOutputStream.putArchiveEntry(zipArchiveEntry);
                //打开输出流
                InputStream fls=new FileInputStream(file);
                //复制到归档输出流
                IOUtils.copy(fls,zipArchiveOutputStream);
                //关闭
                zipArchiveOutputStream.closeArchiveEntry();
            }
        }
    }

    public static void main(String[] args) {
        // 创建要压缩的文件列表
        List<File> filesToCompress = Arrays.asList(new File("D:\\document\\testCompress\\设计模式.xmind"),
                new File("D:\\document\\testCompresss\\test1.avi"));
        // 指定压缩后的输出文件名
//        String outPutZip="compressZip.zip";
        long startTime = System.currentTimeMillis(); // 记录开始时间

        // 调用压缩方法
        try {
            FileCompressZip.compressFilesToZip(filesToCompress);
            // 打印压缩成功的消息
            System.out.println("success_compressZip");
        } catch (IOException e) {
            // 如果发生异常，打印异常信息
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis(); // 记录结束时间
        long elapsedTime = endTime - startTime; // 计算时间差

        System.out.println("耗费: " + elapsedTime + " 毫秒");
    }
}

