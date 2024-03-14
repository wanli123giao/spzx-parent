package org.example.service.Impl;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.example.service.CompressionService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class TarGzCompressionService implements CompressionService {

    /**
     * @param srcDir 源文件夹（srcDir）
     * @param outDir 目标压缩文件路径（outDir）
     * @throws IOException
     */
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
             //GZIP压缩输出流
             GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(fos);
             //Tar归档输出流
             TarArchiveOutputStream tos = new TarArchiveOutputStream(gcos)) {
            //设置长文件名
            tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            toTarGz(srcDir, tos, srcDir);
        }
    }

    /**
     * @param srcFile 当前操作的文件或文件夹
     * @param tos     tar归档输出流
     * @param baseDir
     * @throws IOException
     */
    private static void toTarGz(File srcFile, TarArchiveOutputStream tos, File baseDir) throws IOException {
        //计算出当前文件或文件夹相对于基准目录的相对路径(用.toURL()方法的原因是为了确保文件路径操作的正确和跨平台的兼容性)
        String relativePath = baseDir.toURI().relativize(srcFile.toURI()).getPath();

        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            for (File file : files) {
                toTarGz(file, tos, baseDir);
            }
        } else {
            //创建了一个新的TarArchiveEntry实例，它代表了tar文件中的一个条目
            TarArchiveEntry entry = new TarArchiveEntry(relativePath);
            entry.setSize(srcFile.length());
            //将之前创建的TarArchiveEntry实例添加到tar输出流中
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
