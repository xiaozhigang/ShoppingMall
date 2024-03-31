package com.shopmall.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.shopmall.config.OSSConfig;
import com.shopmall.service.FileService;
import com.shopmall.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 文件服务类
 *
 * @author xiao
 * @data 2024/3/31 9:53
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private OSSConfig ossConfig;

    @Override
    public String uploadUserImg(MultipartFile file) {
        // 获取相关配置
        String bucketName = ossConfig.getBucketName();
        String endpoint = ossConfig.getEndpoint();
        String accessKeyId = ossConfig.getAccessKeyId();
        String accessKeySecret = ossConfig.getAccessKeySecret();
        // 创建OSS对象
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //获取原生文件名  xxx.jpg
        String filename = file.getOriginalFilename();
        LocalDate time = LocalDate.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // 拼装路径,oss上存储的路径  user/2022/12/1/sdfdsafsdfdsf.jpg
        String formatTime = pattern.format(time);
        String uuid = CommonUtil.generateUUID();
        String extension = filename.substring(uuid.lastIndexOf("."));
        String newFileName = "user/" + formatTime + "/" + uuid + extension;

        try {
            PutObjectResult putObjectResult = client.putObject(bucketName, newFileName, file.getInputStream());
            if(putObjectResult != null){
               return  "https://" + bucketName + "." + endpoint + "/" + newFileName;
            }
        } catch (IOException e) {
            log.error("文件上传失败:{}", e);
        }finally {
            // oss关闭服务，不然会造成OOM
            client.shutdown();
        }
        return null;
    }
}
