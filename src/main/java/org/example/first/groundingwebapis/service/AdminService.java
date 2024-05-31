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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .map(assetFile -> {
                    return new AdminAssetFileListsResponse(assetFile.getId(), assetFile.getUserId(), assetFile.getDocumentType(), assetFile.getFileName(), assetFile.getAdminYn());
                })
                .toList();
    }

    @Transactional
    public void setAssetYn(AdminAssetYnRequest request){
        var file = assetFilesRepository.findById(request.getAssetFileId()).get();
        file.updateYn(request.getAdminYn());
    }

    @Transactional
    public boolean adminLogin(AdminLoginRequest request){
        AdminUser byUserName = adminUserRepository.findByUserName(request.getUserId());
        return byUserName.getPassword().equals(request.getPw());
    }
}