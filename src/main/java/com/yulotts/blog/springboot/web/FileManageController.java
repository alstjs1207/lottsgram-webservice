package com.yulotts.blog.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class FileManageController {

    private final S3Uploader s3Uploader;

    @PostMapping("/api/v1/fileupload")
    public Map<String,Object> fileUpload(Model model, @RequestParam  MultipartFile upload) throws IOException { //넘겨주는 파일이름이 upload로 고정되어있음
        Map<String,Object> map = new HashMap<>();

        String url = s3Uploader.upload(upload,"static");
        // 응답값 필수
        map.put("uploaded",true);
        map.put("url",url);

        return map;
    }
}
