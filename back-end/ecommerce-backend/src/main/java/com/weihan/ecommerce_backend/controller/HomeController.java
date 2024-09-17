package com.weihan.ecommerce_backend.controller;

import com.weihan.ecommerce_backend.model.dto.Result;
import com.weihan.ecommerce_backend.service.S3Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final S3Service s3Service;

    public HomeController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @GetMapping("/banner")
    public Result<List> getBanner() {
        List<String> homeBannerUrl = s3Service.getHomeBannerUrl();

        return Result.success("獲取成功", homeBannerUrl);
    }
}
