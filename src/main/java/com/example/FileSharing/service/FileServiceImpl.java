package com.example.FileSharing.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.FileSharing.Entity.FileEntity;
import com.example.FileSharing.exception.FileNotFoundException;
import com.example.FileSharing.model.FileModel;
import com.example.FileSharing.repository.FileRepository;

@Service
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
        Optional <FileEntity> entity = fileRepository.findById(id);
        if(entity.isPresent()){
            // FileModel model = ;
            return ResponseEntity.ok().body(convertToModel(entity.get()));
        }else{
            throw new FileNotFoundException("File not found with id: ");
        }

    }

    @Override
    public ResponseEntity<?> deleteFile(int id) throws IOException {

        Optional <FileEntity> entity = fileRepository.findById(id);
        if(entity.isPresent()){
            fileRepository.delete(entity.get());
            return ResponseEntity.ok().body("File deleted successfully with id: " + id);
        }else{
            throw new FileNotFoundException("File not found");
        }
        // TODO Auto-generated method stub
    }

    @Override
    public ResponseEntity<?> getFile(int id) throws IOException {
        
        Optional<FileEntity> entity = fileRepository.findById(id);
        if(entity.isPresent()){
            FileEntity fileEntity = entity.get();
            FileModel fileModel = new FileModel();
            BeanUtils.copyProperties(fileEntity, fileModel);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFilename() + "\"").body(fileModel.getFileData());
                    
        }
    }
    
}
