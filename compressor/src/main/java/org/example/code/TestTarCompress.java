package org.example.code;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

/**
 * 1.代码格式 ctrl alt l
 * 2.需要判断srcDir是否存在，怎么解决？
 * 3.listFiles可能是null怎么解决
 * 4.targetFile如果有一模一样的文件怎么办？targetFile文件路径不存在怎么办？
 * 5.targetFile父路径不存在怎么办
 * 6.压缩过程中代码错误怎么办，输出流怎么处理 |代码报错自动关闭try (resource),结束资源
 * 7.IOUtils操作完成后输入流是否释放
 * 8.ZipArchiveOutputStream多次创建 走else 会再次创建一次怎么解决
 *
 * @author 1
 */
public class TestTarCompress {
    //    public static void main(String[] args) throws IOException {
//        long startTimeTar = System.currentTimeMillis();
//        // 源文件夹
////        File srcDir = new File("D:\\document\\testCompress\\test");
//        File srcDir = new File("D:\\document\\hadoop-3.3.6");
//        String outputPath = "D:\\document\\tar";
//        // 获取文件夹的名称并附加.zip后缀
//        String targetFileName = srcDir.getName() + ".tar.gz";
//        //testCompress.zip
//        // 创建目标文件
//        File outDir = new File(outputPath + "\\" + targetFileName);
//        //判断给定的需要压缩路径是否存在,存在判断有没有文件
//        if (!srcDir.exists()) {
//            System.out.println("源目录不存在，请检查路径");
//            return;
//        } else if (srcDir.listFiles().length == 0) {
//            System.out.println("您提供的目录压缩下没有文件,请检查");
//            return;
//        }
//        //targetFile父路径不存在则创建,存在父目录判断父目录下有没有同名文件
//        if (!outDir.getParentFile().exists()) {
//            outDir.getParentFile().mkdirs();
//            System.out.println("父路径不存在,自动创建文件夹:" + " " + outDir.getParentFile());
//        } else if (outDir.exists()) {
//            System.out.println("压缩路径已经有同名文件，请检查！");
//            return;
//        }
//        try (TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(outDir))) {
//            toTarGz(srcDir, tos, "");
//        }
//        long endTimeTar = System.currentTimeMillis();
//        long elapsedTimeTar = endTimeTar - startTimeTar;
//        System.out.println("zip压缩消耗时间：" + elapsedTimeTar + "ms" + " " + "压缩目录：" + outDir);
//    }
//
//    public static void toTarGz(File srcFile, TarArchiveOutputStream tos, String basePath) throws IOException {
//        if (srcFile.isDirectory()) {
//            File[] files = srcFile.listFiles();
//            for (File file : files) {
//                toTarGz(file, tos, basePath + srcFile.getName() + "/");
//            }
//        } else {
//            try (FileInputStream fis = new FileInputStream(srcFile);
//                 BufferedInputStream bis = new BufferedInputStream(fis);
//            ) {
//                TarArchiveEntry tarEntry = new TarArchiveEntry(basePath + srcFile.getName());
//                tarEntry.setSize(srcFile.length());
//                tos.putArchiveEntry(tarEntry);
//                IOUtils.copy(bis, tos);
//                tos.closeArchiveEntry();
//            } catch (IOException e) {
//                System.out.println("压缩出错");
//                e.printStackTrace();
//            }
//        }
//    }
    public static void main(String[] args) throws IOException {
        long startTimeTar = System.currentTimeMillis();
        File srcDir = new File("D:\\document\\hadoop-3.3.6");
        String outputPath = "D:\\document\\zip";
        String targetFileName = srcDir.getName() + ".tar.gz";
        File outDir = new File(outputPath + "\\" + targetFileName);

        if (!srcDir.exists() || srcDir.listFiles().length == 0) {
            System.out.println("源目录不存在，或者您所提供的目录中没有文件，无法进行压缩操作...");
            return;
        } else if (!outDir.getParentFile().exists() || outDir.exists()) {
            System.out.println("压缩路径的父目录不存在，或者在压缩路径已经存在同名文件，请检查您的设置！");
            return;
        }

        try (FileOutputStream fos = new FileOutputStream(outDir);
             GzipCompressorOutputStream gcos = new GzipCompressorOutputStream(fos);
             TarArchiveOutputStream tos = new TarArchiveOutputStream(gcos)) {
            // 处理文件名过长问题，打开长文件模式。
            tos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
            toTarGz(srcDir, tos, srcDir);
        }

        long endTimeTar = System.currentTimeMillis();
        System.out.println("tar.gz压缩消耗时间：" + (endTimeTar - startTimeTar) + "ms，压缩目录：" + outDir);
    }
    public static void toTarGz(File srcFile, TarArchiveOutputStream tos, File baseDir) throws IOException {

        String relativePath = baseDir.toURI().relativize(srcFile.toURI()).getPath();

        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            for (File file : files) {
                toTarGz(file, tos, baseDir);
            }
        } else {
            TarArchiveEntry tarEntry = new TarArchiveEntry(relativePath);
            tarEntry.setSize(srcFile.length());
            try (FileInputStream fis = new FileInputStream(srcFile);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                tos.putArchiveEntry(tarEntry);
                IOUtils.copy(bis, tos);
                tos.closeArchiveEntry();
            } catch (IOException e) {
                System.out.println("压缩出错");
                e.printStackTrace();
            }
        }
    }

//    public static void toTarGz(File srcFile, TarArchiveOutputStream tos, String relativePath) throws IOException {
//        String entryPath = new File(relativePath).toURI().relativize(srcFile.toURI()).getPath();
//
//        if (srcFile.isDirectory()) {
//            File[] files = srcFile.listFiles();
//            for (File file : files) {
//                toTarGz(file, tos, entryPath);
//            }
//        } else {
//            TarArchiveEntry tarEntry = new TarArchiveEntry(entryPath);
//            tarEntry.setSize(srcFile.length());
//            try (FileInputStream fis = new FileInputStream(srcFile);
//                 BufferedInputStream bis = new BufferedInputStream(fis)) {
//                tos.putArchiveEntry(tarEntry);
//                IOUtils.copy(bis, tos);
//                tos.closeArchiveEntry();
//            } catch (IOException e) {
//                System.out.println("压缩出错");
//                e.printStackTrace();
//            }
//        }
//    }
}
