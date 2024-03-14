package org.example.service.Impl;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.example.service.CompressionService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class ZipCompressionService implements CompressionService {

    @Override
    public void compressDirectory(File srcDir, File outDir) throws IOException {
        if (!srcDir.exists() || srcDir.listFiles().length == 0) {
            System.out.println("源目录不存在，或者您所提供的目录中没有文件，无法进行压缩操作...");
            return;
        }

        if (!outDir.getParentFile().exists() || outDir.exists()) {
            System.out.println("压缩路径的父目录不存在，或者在压缩路径已经存在同名文件，请检查您的设置！");
            return;
        }
        //创建文件输出流
        try (FileOutputStream fos = new FileOutputStream(outDir);
             //Tar归档输出流
             ZipArchiveOutputStream zos = new ZipArchiveOutputStream(fos)) {
            //设置长文件名
            toZip(srcDir, zos, srcDir);
        }
    }

    private static void toZip(File srcFile, ZipArchiveOutputStream tos, File baseDir) throws IOException {
        //计算出当前文件或文件夹相对于基准目录的相对路径(用.toURL()方法的原因是为了确保文件路径操作的正确和跨平台的兼容性)
        String relativePath = baseDir.toURI().relativize(srcFile.toURI()).getPath();

        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            for (File file : files) {
                toZip(file, tos, baseDir);
            }
        } else {
            //创建了一个新的TarArchiveEntry实例，它代表了tar文件中的一个条目
            ZipArchiveEntry entry = new ZipArchiveEntry(relativePath);
//            entry.setSize(srcFile.length());
            tos.putArchiveEntry(entry);
            //创建一个新的输入流，用于从源文件srcFile中读取内容
            try (InputStream in = Files.newInputStream(srcFile.toPath())) {
                IOUtils.copy(in, tos);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tos.closeArchiveEntry();
        }
    }
}