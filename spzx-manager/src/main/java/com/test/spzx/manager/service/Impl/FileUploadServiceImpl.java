package com.test.spzx.manager.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.test.spzx.common.exception.GuiguException;
import com.test.spzx.manager.properties.MinioProperties;
import com.test.spzx.manager.service.FileUploadService;
import com.test.spzx.model.vo.common.ResultCodeEnum;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MinioProperties minioProperties;
    @Override
    public String upload(MultipartFile file) {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key.
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(minioProperties.getEndpointUrl())
                            .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                            .build();

            // Make 'asiatrip' bucket if not exist.
            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) {
                // Make a new bucket called 'asiatrip'.
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }

            // Upload '/home/user/Photos/asiaphotos.zip' as object name 'asiaphotos-2015.zip' to bucket
            // 'asiatrip'.
            //获得文件名称
           //获取当前日期，格式为yyyyMMdd
            String dateDir = DateUtil.format(new Date(),"yyyyMMdd");
            //生成uuid
            String uuid = UUID.randomUUID().toString().replace("-", "");
            //拼接文件名
            String filename = dateDir+"/"+uuid+file.getOriginalFilename();
            System.out.println(filename);
            //文件上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(minioProperties.getBucketName())
                            .object(filename)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .build());
            //获取问价路径
            String url=minioProperties.getEndpointUrl()+"/"+minioProperties.getBucketName()+"/"+filename;
            return url;
        } catch ( Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.SYSTEM_ERROR);
        }

    }
}