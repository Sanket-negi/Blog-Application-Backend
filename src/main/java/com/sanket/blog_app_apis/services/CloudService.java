package com.sanket.blog_app_apis.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface CloudService {
         String uploadFile(MultipartFile file);
}
