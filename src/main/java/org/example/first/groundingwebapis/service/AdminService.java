package org.example.first.groundingwebapis.service;

import org.example.first.groundingwebapis.dto.AdminAssetFileListsResponse;
import org.example.first.groundingwebapis.dto.AdminAssetYnRequest;
import org.example.first.groundingwebapis.dto.AdminLoginRequest;
import org.example.first.groundingwebapis.entity.AdminUser;
import org.example.first.groundingwebapis.entity.AssetFiles;
import org.example.first.groundingwebapis.repository.AdminUserRepository;
import org.example.first.groundingwebapis.repository.AssetFilesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.first.groundingwebapis.service.kafka.KafkaProducer;

import java.util.List;
import java.util.stream.Collectors;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    private final AssetFilesRepository assetFilesRepository;
    private final AdminUserRepository adminUserRepository;

    @Transactional(readOnly = true)
    public List<AdminAssetFileListsResponse> getAssetFileLists(){
        var assetFiles = assetFilesRepository.findAll();
        return assetFiles.stream()
                .filter(assetFile -> assetFile.getDocumentType().equals("PDF"))
                .map(assetFile -> {
                    return new AdminAssetFileListsResponse(assetFile.getId(), assetFile.getUserId(), assetFile.getDocumentType(), assetFile.getFileName(), assetFile.getAdminYn());
                })
                .toList();
    }

    @Transactional
    public void setAssetYn(AdminAssetYnRequest request) throws Exception {
        var file = assetFilesRepository.findById(request.getAssetFileId()).get();
        if(file.getDocumentType().equals("PDF")){
            file.updateYn(request.getAdminYn());
        }else{
            throw new Exception("PDF 파일만 승인이 가능합니다.");
        }
    }

    @Transactional
    public boolean adminLogin(AdminLoginRequest request){
        AdminUser byUserName = adminUserRepository.findByUserName(request.getUserId());
        return byUserName.getPassword().equals(request.getPw());
    }
}