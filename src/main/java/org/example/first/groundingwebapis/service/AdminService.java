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
        // AWS Credentials
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                System.getenv("AWS_ACCESS_KEY"),
                System.getenv("AWS_SECRET_KEY")
        );

        // S3 Client
        S3Client s3 = S3Client.builder()
                .region(Region.of(System.getenv("AWS_REGION"))) // Your region
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
                .build();

        // List objects in S3 bucket
        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
                .bucket(System.getenv("AWS_BUCKET_NAME"))
                .build();

        ListObjectsV2Response listObjResponse = s3.listObjectsV2(listObjectsReqManual);
        List<S3Object> objects = listObjResponse.contents();

        // CloudFront URL
        String cloudFrontUrl = System.getenv("CLOUDFRONT_URL");

        return objects.stream()
                .map(object -> {
                    String fileName = object.key();
                    String fileUrl = cloudFrontUrl + "/" + fileName;
                    return new AdminAssetFileListsResponse(null, null, null, fileName, null, fileUrl);
                })
                .collect(Collectors.toList());
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