package com.example.FileSharing.controller;

import java.util.List;

import org.apache.tomcat.jni.FileInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping()
public class FileController {

    @GetMapping("/files")
    public String login() {
        return "home";
    }

    @GetMapping("/list")
    public String files() {
        return "list-files"; // 
    }

    @GetMapping("/share")
    public String share() {
        return "share-files";
    }
    
    
}
