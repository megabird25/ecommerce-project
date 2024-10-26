package com.weihan.ecommerce_backend.service.impl;

import com.weihan.ecommerce_backend.service.S3Service;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class S3ServiceImpl implements S3Service {

    private static final String BUCKET_NAME = "weihan-ecommerce";

    public String uploadUserAvatar(File file, String fileName) {
        // 連接 S3 客戶端
        S3Client s3 = getS3Client();

        // S3 上傳文件的邏輯
        String key = "User/avatar/"+ fileName; // 存儲的文件名稱

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(key)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3.putObject(putObjectRequest, file.toPath());

        // 關閉 S3 客戶端
        s3.close();

        return "https://" + BUCKET_NAME + ".s3.amazonaws.com/User/avatar/" + fileName;
    }

    public List<String> getHomeBannerUrl() {
        S3Client s3 = getS3Client();

        // 指定存儲桶和資料夾 (prefix)
        String folderPrefix = "Home/banner/";  // 指定資料夾前綴，注意以 '/' 結尾

        // 創建 ListObjectsV2 請求
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(BUCKET_NAME)
                .prefix(folderPrefix)  // 使用資料夾作為前綴
                .build();

        // 發送請求並獲取回應
        ListObjectsV2Response listObjectsResponse = s3.listObjectsV2(listObjectsRequest);
        List<S3Object> objects = listObjectsResponse.contents();

        // 定義List並插入返回的URL
        List<String> bannerURL = new ArrayList<>();

        for (int i = 1; i < objects.size(); i++) {
            String objectKey = objects.get(i).key();
            String objectUrl = "https://" + BUCKET_NAME + ".s3.amazonaws.com/" + objectKey;
            bannerURL.add(objectUrl);
        }

        // 關閉 S3 客戶端
        s3.close();

        return bannerURL;
    }

    private static S3Client getS3Client() {
        // S3 客戶端，使用憑證，並返回
        return S3Client.builder()
                .region(Region.AP_NORTHEAST_1)  // 區域
                .build();
    }
}
