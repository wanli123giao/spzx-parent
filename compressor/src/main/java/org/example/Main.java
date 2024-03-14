package org.example;

import org.example.service.CompressionService;
import org.example.service.Impl.TarGzCompressionService;
import org.example.service.Impl.ZipCompressionService;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        CompressionService compressionService = new TarGzCompressionService();
        CompressionService compressionService = new ZipCompressionService();

        // 定义你要压缩的文件夹
        File sourceDirectory = new File("D:\\document\\hadoop-3.3.6");
//        String file=sourceDirectory.getName()+".tar.gz";
        String file="\\"+sourceDirectory.getName()+".zip";
        System.out.println(file);
        // 定义压缩后的文件
        File targetFile = new File("D:\\document\\tar"+file);

        // 压缩文件夹
        try {
            compressionService.compressDirectory(sourceDirectory, targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}