package com.shopmall.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传图片
     *
     * @param file file
     * @return String
     */
    String uploadUserImg(MultipartFile file);
}
