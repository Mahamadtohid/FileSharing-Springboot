package com.example.FileSharing.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.FileSharing.Entity.FileEntity;
import com.example.FileSharing.model.FileModel;
import com.example.FileSharing.repository.FileRepository;

public class FileServiceImpl implements FileService{

    @Autowired
    private FileRepository fileRepository;

    private FileModel convertToModel(FileEntity entity){
        FileModel model = new FileModel();
        BeanUtils.copyProperties(entity, model);
        return model;
    }

    @Override
    public List<FileModel> getAllFiles() {
        // TODO Auto-generated method stub
       List<FileEntity> entities = fileRepository.findAll();

       return entities.stream().map(this::convertToModel).collect(Collectors.toList());
       
       
    }

    @Override
    public ResponseEntity<?> uploadFile(MultipartFile file, String uploadedBy) throws IOException {
        // TODO Auto-generated method stub
        FileEntity entity = new FileEntity();
        entity.setFilename(file.getOriginalFilename());
        entity.setUploadedBy(uploadedBy);
        entity.setExpiryTime(LocalDateTime.now().plusDays(1)); // Set expiry time to 1 day from now
        entity.setUploadTime(LocalDateTime.now());
        entity.setFileData(file.getBytes());
        fileRepository.save(entity);

        return ResponseEntity.ok().body(convertToModel(entity));
    }

    @Override
    public ResponseEntity<?> shareFile(int id) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'shareFile'");
    }

    @Override
    public ResponseEntity<?> deleteFile(int id) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFile'");
    }
    
}
