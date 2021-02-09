package com.yulotts.blog.springboot.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class FileManageController {

    @Value("${images.load.path}")
    String imagesPath;

    @Value("${images.save.path}")
    String savePath;

    @PostMapping("/api/v1/fileupload")
    public Map<String,Object> fileUpload(Model model, @RequestParam  MultipartFile upload) { //넘겨주는 파일이름이 upload로 고정되어있음
        Map<String,Object> map = new HashMap<>();

        try {
            String filename = upload.getOriginalFilename();
            System.out.println("origin File name == "+filename);
            String newFileName = UUID.randomUUID()+"_"+filename;
//            if (!new File(savePath).exists()) {
//                try {
//                    new File(savePath).mkdir();
//                } catch (Exception e) {
//                    System.out.println(e.toString());
//                }
//            }
            String filePath = imagesPath + File.separator + newFileName;
            System.out.println(filePath);
            upload.transferTo(new File(filePath));
            // 응답값 필수
            map.put("uploaded",true);
            map.put("url",savePath+"/"+newFileName);
        }
        catch(Exception e) {
            System.out.println(e.toString());
        }

        return map;
    }
}
