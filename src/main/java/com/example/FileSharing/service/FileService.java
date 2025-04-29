package com.example.FileSharing.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.FileSharing.model.FileModel;

public interface FileService {

    public List<FileModel> getAllFiles();
    public ResponseEntity<?> uploadFile(MultipartFile file, String uploadBy) throws IOException;
    public ResponseEntity<?> shareFile(int id) throws IOException;
    public ResponseEntity<?> deleteFile(int id) throws IOException;
    public ResponseEntity<?> getFile(int id) throws IOException;
    public void deleteExpiredFiles();

    
}
