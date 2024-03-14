package org.example.code;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.*;

public class TestZipCompress {

    public static void main(String[] args) throws IOException {
        long startTimeZip = System.currentTimeMillis();
        // 源文件夹
//        File srcDir = new File("D:\\document\\testCompress\\test");
        //listFiles 为空
        File srcDir = new File("D:\\document\\hadoop-3.3.6");
        String outputPath = "D:\\document\\testZipCompress";
        // 获取文件夹的名称并附加.zip后缀
        String targetFileName = srcDir.getName() + ".zip";
        // 创建目标文件
        File outDir = new File(outputPath + "\\" + targetFileName);
        //判断给定的需要压缩路径是否存在,存在判断有没有文件
        if (!srcDir.exists()) {
            System.out.println("源目录不存在，请检查路径");
            return;
        } else if (srcDir.listFiles().length == 0) {
            System.out.println("您提供的目录压缩下没有文件,请检查");
            return;
        }
        //targetFile父路径不存在则创建,存在父目录判断父目录下有没有同名文件
        if (!outDir.getParentFile().exists()) {
            outDir.getParentFile().mkdirs();
            System.out.println("父路径不存在,自动创建文件夹:" + " " + outDir.getParentFile());
        } else if (outDir.exists()) {
            System.out.println("压缩路径已经有同名文件，请检查！");
            return;
        }

        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(outDir))) {
            toZip(srcDir, zos, "");
        }
        long endTimeZip = System.currentTimeMillis();
        long elapsedTimeZip = endTimeZip - startTimeZip;
        System.out.println("zip压缩消耗时间：" + elapsedTimeZip + "ms" + " " + "压缩目录：" + outDir);
//        System.out.println("************************************************");
//        long startTimeTar = System.currentTimeMillis();
//        // 源文件夹
////        File srcDir = new File("D:\\document\\testCompress\\test");
//        File srcDir = new File("D:\\document\\testCompresss");
//        String outputPath = "D:\\document\\testc";
//        // 获取文件夹的名称并附加.zip后缀
//        String targetFileName = srcDir.getName() + ".tar.gz";
//        //testCompress.zip
//        System.out.println("targetFileName:" + targetFileName);
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
////            srcDir.delete();
//            return;
//        }
//
//        //D:\document\testc\testCompress.zip
//        System.out.println("outDir:" + outDir);
//        try (TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(outDir))) {
//            toTarGz(srcDir, tos, "");
//        }
//
//        long endTimeTar = System.currentTimeMillis();
//        long elapsedTimeTar = endTimeTar - startTimeTar;
//        System.out.println("zip压缩消耗时间：" + elapsedTimeTar + "ms" + " " + "压缩目录：" + outDir);

    }

    /**
     * @param srcDir   需要输入的压缩路径
     * @param zos      文件输出流
     * @param basePath 压缩时的基本路径，用于构建相对路径
     * @throws IOException
     */
    public static void toZip(File srcDir, ZipArchiveOutputStream zos, String basePath) throws IOException {
        if (srcDir.isDirectory()) {
            File[] files = srcDir.listFiles();
            if (files == null) {
                System.out.println("源目录为空或无法读取");
                return;
            }
            for (File file : files) {
                toZip(file, zos, basePath + srcDir.getName() + "\\");
            }
        } else {
            try (FileInputStream fis = new FileInputStream(srcDir);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
//                System.out.println(bis);
                String entryName = basePath + srcDir.getName();
                System.out.println("entryName:" + entryName);
                ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
                System.out.println("entry:" + entry);
                zos.putArchiveEntry(entry);
                IOUtils.copy(bis, zos);
                fis.close();
                bis.close();
                zos.closeArchiveEntry();
            }
        }
    }

//    public static void toTar(File srcDir, TarArchiveOutputStream tos, String basePath) throws IOException {
//        if (srcDir.isDirectory()) {
//            File[] files = srcDir.listFiles();
//            if (files == null) {
//                System.out.println("源目录为空或无法读取");
//                return;
//            }
//            for (File file : files) {
//                toTar(file, tos, basePath + srcDir.getName() + "\\");
//            }
//        } else {
//            try (FileInputStream fis = new FileInputStream(srcDir);
//                 BufferedInputStream bis = new BufferedInputStream(fis);
//                 GzipCompressorInputStream gis = new GzipCompressorInputStream(bis)) {
//                String entryName = basePath + srcDir.getName();
//                TarArchiveEntry entry = new TarArchiveEntry(entryName);
//                tos.putArchiveEntry(entry);
//                IOUtils.copy(gis, tos);
//                bis.close();
//                tos.close();
//                tos.closeArchiveEntry();
//            }
//        }
//    }

//    public class test11 {
//        public static void main(String[] args) throws IOException {
//            long startTimeTarGz = System.currentTimeMillis();
//            // 源文件夹
//            File srcDir = new File("D:\\document\\hadoop-3.3.6");
//            String outputPath = "D:\\document\\test\\";
//
//            // 获取文件夹名称并附加.tar.gz后缀
//            String targetFileName = srcDir.getName() + ".tar.gz";
//
//            // 创建目标文件
//            File outDir = new File(outputPath + targetFileName);
//
//            try (GzipCompressorOutputStream gzipOut = new GzipCompressorOutputStream(new FileOutputStream(outDir));
//                 TarArchiveOutputStream tarGzOut = new TarArchiveOutputStream(gzipOut)) {
//                toTarGz(srcDir, tarGzOut, "");
//            }
//
//            long endTimeTarGz = System.currentTimeMillis();
//            long elapsedTimeTarGz = endTimeTarGz - startTimeTarGz;
//
//            System.out.println("tar.gz压缩消耗时间：" + elapsedTimeTarGz + "ms" + " " + "压缩目录：" + outDir);
//            System.out.println("************************************************");
//        }

//    public static void toTarGz(File srcFile, TarArchiveOutputStream tarGzOut, String basePath) throws IOException {
//        if (srcFile.isDirectory()) {
//            File[] files = srcFile.listFiles();
//            for (File file : files) {
//                toTarGz(file, tarGzOut, basePath + srcFile.getName() + "/");
//            }
//        } else {
//            try (FileInputStream fis = new FileInputStream(srcFile);
//                 BufferedInputStream bis = new BufferedInputStream(fis);
////                 TarArchiveInputStream tis = new TarArchiveInputStream(bis)
//            ) {
//                TarArchiveEntry tarEntry = new TarArchiveEntry(basePath + srcFile.getName());
//                tarEntry.setSize(srcFile.length());
//                tarGzOut.putArchiveEntry(tarEntry);
//                IOUtils.copy(bis, tarGzOut);
////                fis.close();
////                bis.close();
//                tarGzOut.closeArchiveEntry();
//            }catch (IOException e){
//                System.out.println("压缩出错");
//                e.printStackTrace();
//            }
//        }
//    }
}
