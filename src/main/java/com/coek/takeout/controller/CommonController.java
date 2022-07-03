package com.coek.takeout.controller;

import com.coek.takeout.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author MaakCheukVing
 * @create 2022-06-29 12:18
 */
@RestController
@RequestMapping("common")
@Slf4j
public class CommonController {

    // 图片保存的路径
    @Value("${reggie.path}")
    private String path;

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        log.info("图片上传");
        // 图片原始名称
        String originalFilename = file.getOriginalFilename();

        // 获取图片后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 新的图片名
        String fileName = UUID.randomUUID().toString() + suffix;

        File dir = new File(path);
        if (!dir.exists()) {
            // 创建文件夹
            dir.mkdir();
        }


        try {
            //将临时文件转存到指定位置
            file.transferTo(new File(path + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回文件名给前端
        return R.success(fileName);
    }

    /**
     * 图片下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        log.info("图片下载：{}", name);

        // 输入流读取文件
        FileInputStream inputStream = null;
        // 输出流,输出到浏览器
        ServletOutputStream outputStream = null;
        try {

            inputStream = new FileInputStream(new File(path + name));
            outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            // 先读后输出
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {

                outputStream.write(bytes,0,len);
                //刷新
                outputStream.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 关闭
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}