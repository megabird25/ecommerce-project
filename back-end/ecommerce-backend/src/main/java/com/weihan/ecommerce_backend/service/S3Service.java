package com.weihan.ecommerce_backend.service;

import java.io.File;
import java.util.List;

public interface S3Service {

    String uploadUserAvatar(File file, String fileName);

    List<String> getHomeBannerUrl();
}
