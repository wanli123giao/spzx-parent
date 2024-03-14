package org.example.code;//package org.example.code;
//
//public static void toZip(File srcDir, File targetFile) throws IOException {
//        // 输入srcDir文件不存在时 直接返回
//        if (!srcDir.exists()) {
//        System.out.println("源目录不存在，请检查路径");
//        return;
//        }
//
//        File parentFile = targetFile.getParentFile();
//        if (!parentFile.exists()) {
//        System.out.println("父路径不存在,自动创建文件夹");
//        parentFile.mkdirs();
//        }
//
//        String baseName = targetFile.getName();
//        // 获取文件名，不含扩展名
//        String fileBaseName = baseName.substring(0, baseName.lastIndexOf("."));
//        // 获取文件扩展名
//        String fileExtension = baseName.substring(baseName.lastIndexOf(".") + 1, baseName.length());
//
//        // 创建以文件名命名的文件夹
//        File zipFolder = new File(parentFile, fileBaseName);
//        if (!zipFolder.exists()) {
//        zipFolder.mkdirs();
//        }
//
//        int conflictIndex = 0;
//        while (targetFile.exists() && targetFile.isFile()) {
//        conflictIndex++;
//        String newBaseName = fileBaseName + "(" + conflictIndex + ")";
//        String newTargetName = newBaseName + "." + fileExtension;
//        targetFile = new File(zipFolder, newTargetName);
//        }
//
//        File[] files = srcDir.listFiles();
//        if (files == null) {
//        System.out.println("源目录为空或无法读取");
//        return;
//        }
//
//        try (ZipArchiveOutputStream zos = new ZipArchiveOutputStream(new FileOutputStream(targetFile))) {
//        for (File file : files) {
//        if (file.isFile()) {
//        ZipArchiveEntry entry = new ZipArchiveEntry(file.getName());
//        zos.putArchiveEntry(entry);
//        try (FileInputStream fis = new FileInputStream(file);
//        BufferedInputStream bis = new BufferedInputStream(fis)) {
//        IOUtils.copy(bis, zos);
//        }
//        zos.closeArchiveEntry();
//        } else {
//        toZip(file, targetFile);
//        }
//        }
//        } catch (IOException e) {
//        System.out.println("压缩过程出错");
//        e.printStackTrace();
//        }
//        }