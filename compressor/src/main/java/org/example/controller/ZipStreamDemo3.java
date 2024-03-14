package org.example.controller;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;


import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class ZipStreamDemo3 {
    public static void main(String[] args) throws IOException {

        long startTimeZip = System.currentTimeMillis(); // 记录开始时间
        String name = "D:\\document\\wanli";
        //1.创建File对象表示要压缩的文件夹
        File src = new File("D:\\document\\testCompress");
        //2.创建File对象表示压缩包放在哪里（压缩包的父级路径）
        File destParent = new File(name);//D:\\
        //3.创建File对象表示压缩包的路径
        File dest = new File(destParent, src.getName() + ".zip");
        //4.创建压缩流关联压缩包
//        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest));
        ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(dest));
        //5.获取src里面的每一个文件，变成ZipEntry对象，放入到压缩包当中
        toZip(src, zos, src.getName());//aaa
        long endTimeZip = System.currentTimeMillis(); // 记录结束时间
        long elapsedTimeZip = endTimeZip - startTimeZip; // 计算时间差
        System.out.println("zip压缩消耗时间：" + elapsedTimeZip + "ms" + " " + "压缩目录：" + destParent);
        //6.释放资源
        zos.close();
        System.out.println("/////////////////////////////////////////////////////////");

//        long startTimeTar=System.currentTimeMillis();
//        String nameTar="D:\\document\\test";
//        File srcTar = new File("D:\\document\\testCompresss");
//        File destParentTar = new File(nameTar);//D:\\
//        File destTar = new File(destParentTar,srcTar.getName() + ".tar.gz");
//        TarArchiveOutputStream tos = new TarArchiveOutputStream(new FileOutputStream(destTar));
//        toTarGz(srcTar,tos,srcTar.getName());
//        long endTimeTar = System.currentTimeMillis(); // 记录结束时间
//        long elapsedTimeTar = endTimeTar - startTimeTar; // 计算时间差
//        System.out.println("zip压缩消耗时间："+elapsedTimeTar+"ms"+" "+"压缩目录："+destParentTar);
    }
    /*
     *   作用：获取src里面的每一个文件，变成ZipEntry对象，放入到压缩包当中
     *   参数一：数据源
     *   参数二：压缩流
     *   参数三：压缩包内部的路径
     * */
//    public static void toZip(File src,ZipOutputStream zos,String name) throws IOException {
//
//        //1.进入src文件夹
//        File[] files = src.listFiles();
//        //2.遍历数组
//        for (File file : files) {
//            if(file.isFile()){
//                //3.判断-文件，变成ZipEntry对象，放入到压缩包当中
//                ZipEntry entry = new ZipEntry(name + "\\" + file.getName());//aaa\\no1\\a.txt
//                zos.putNextEntry(entry);
//                //创建文件输出流，读取文件中的数据，写到压缩包
//                FileInputStream fis = new FileInputStream(file);
//                int b;
//                while((b = fis.read()) != -1){
//                    zos.write(b);
//                }
//                fis.close();
//                zos.closeEntry();
//            }else{
//                //4.判断-文件夹，递归
//                toZip(file,zos,name + "\\" + file.getName());
//                //     no1            aaa   \\   no1
//            }
//        }

    public static void toZip(File srcDir, ZipArchiveOutputStream zos, String name) throws IOException {
        //1.进入src文件夹
        File[] files = srcDir.listFiles();
//        FileOutputStream fileOutputStream=new
        //2.遍历数组
        for (File file : files) {
            if (file.isFile()) {
                //3.判断-文件，变成ZipEntry对象，放入到压缩包当中
                ZipArchiveEntry entry = new ZipArchiveEntry(name + "\\" + file.getName());//aaa\\no1\\a.txt
                zos.putArchiveEntry(entry);
                //创建文件输出流，读取文件中的数据，写到压缩包
                FileInputStream fis = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);
//                ZipArchiveInputStream zipIn = new ZipArchiveInputStream(bis);
                IOUtils.copy(bis, zos);
//                int b;
//                while((b = zipIn.read()) != -1){
//                    zos.write(b);
//                }
//                fis.close();
                zos.closeArchiveEntry();
            } else {
                //4.判断-文件夹，递归
                toZip(file, zos, name + "\\" + file.getName());
                //     no1            aaa   \\   no1
            }
        }
    }

    public static void toTarGz(File src, TarArchiveOutputStream tos, String name) throws IOException {
        File[] filesTar = src.listFiles();
        for (File file : filesTar) {
            if (file.isFile()) {
                TarArchiveEntry entryTar = new TarArchiveEntry(name + "\\" + file.getName());
                tos.putArchiveEntry(entryTar);

                FileInputStream fls = new FileInputStream(file);
                int b;
                while ((b = fls.read()) != -1) {
                    tos.write(b);
                }
                fls.close();
                tos.closeArchiveEntry();
            } else {
                toTarGz(file, tos, name + "\\" + file.getName());
            }
        }
    }
}