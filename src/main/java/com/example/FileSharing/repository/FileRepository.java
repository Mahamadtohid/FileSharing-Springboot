package com.example.FileSharing.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.FileSharing.Entity.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    List<FileEntity> findByExpiryDatebefore(LocalDateTime now);
    
}
