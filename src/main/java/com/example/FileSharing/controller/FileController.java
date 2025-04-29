package com.example.FileSharing.controller;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.FileSharing.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/login")
    public String login() {
        return "home";
    }

    @PostMapping("/upload")
    public String postMethodName(@RequestParam("file") MultipartFile file, @RequestParam("uploadedBy") String uploadedBy) throws IOException {
        fileService.uploadFile(file, uploadedBy);
        return "redirect:/files/home"; // Redirect to the index page after upload
    }
    

    @GetMapping("/home")
    public String index(Model model) {
    model.addAttribute("files", fileService.getAllFiles());
    return "list-files";
}

@GetMapping("/share/{id}")
public String shareFile(@PathVariable int id, Model model) throws IOException {
    ResponseEntity<?> fileModel = fileService.shareFile(id);
    if (fileModel.hasBody()) {
        model.addAttribute("file", fileModel.getBody());
        String currentUrl = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        model.addAttribute("shareUrl", currentUrl);
        return "share-file";
    } else {
        return "redirect:/files/home";
    }
}

    @PostMapping("/delete/{id}")
    public String deleteFile(@PathVariable int id) throws IOException {
        
        ResponseEntity <?> file = fileService.deleteFile(id);
        if(file.hasBody()){
            return "redirect:/files/home" ;
        }else{
            return "redirect:/files" ;
        }
         
    }
    

    @GetMapping("/share")
    public String share() {
        return "share-file";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable int id) throws IOException {
        return fileService.getFile(id);
    }
    
    
    
}
