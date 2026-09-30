package com.zyyyl.framework.storage.domain;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.zyyyl.common.core.storage.base.StorageBucket;
import com.zyyyl.common.core.storage.base.StorageEntity;
import com.zyyyl.common.core.storage.domain.SysFilePartETag;
import com.zyyyl.common.exception.ServiceException;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 阿里云 OSS 存储桶。Object 路径使用相对路径，对外访问地址按 endpoint 规则拼接。
 */
@Slf4j
@Builder
public class OssBucket implements StorageBucket {

    private String bucketName;
    private String ossBucketName;
    private String permission;
    private String api;
    private String endpoint;
    @Getter
    private OSS ossClient;

    @Override
    public void put(String filePath, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            if (file.getContentType() != null) {
                metadata.setContentType(file.getContentType());
            }
            ossClient.putObject(new PutObjectRequest(ossBucketName, filePath, inputStream, metadata));
        } catch (Exception e) {
            log.error("OSS 上传失败 bucket={} object={}", ossBucketName, filePath, e);
            throw new ServiceException("OSS 上传失败：" + e.getMessage());
        }
    }

    @Override
    public StorageEntity get(String filePath) {
        try {
            StorageEntity entity = new StorageEntity();
            entity.setFilePath(filePath);
            entity.setInputStream(ossClient.getObject(ossBucketName, filePath).getObjectContent());
            return entity;
        } catch (Exception e) {
            log.error("OSS 读取失败 bucket={} object={}", ossBucketName, filePath, e);
            throw new ServiceException("OSS 读取失败：" + e.getMessage());
        }
    }

    @Override
    public void remove(String filePath) {
        try {
            ossClient.deleteObject(ossBucketName, filePath);
        } catch (Exception e) {
            log.error("OSS 删除失败 bucket={} object={}", ossBucketName, filePath, e);
            throw new ServiceException("OSS 删除失败：" + e.getMessage());
        }
    }

    @Override
    public URL generatePresignedUrl(String filePath, int expireTime) {
        try {
            Date expiration = new Date(System.currentTimeMillis() + expireTime * 1000L);
            GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(ossBucketName, filePath);
            request.setExpiration(expiration);
            return ossClient.generatePresignedUrl(request);
        } catch (Exception e) {
            throw new ServiceException("OSS 生成签名地址失败：" + e.getMessage());
        }
    }

    @Override
    public URL generatePublicUrl(String filePath) {
        try {
            if (endpoint == null || endpoint.isBlank()) {
                throw new ServiceException("OSS endpoint 未配置");
            }
            String scheme = endpoint.startsWith("http://") ? "http://" : "https://";
            String host = endpoint.replaceFirst("^https?://", "");
            return new URL(scheme + ossBucketName + "." + host + "/" + filePath.replace("\\", "/"));
        } catch (ServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("OSS 生成公开地址失败：" + e.getMessage());
        }
    }

    @Override
    public String getPermission() {
        return permission;
    }

    public String getApi() {
        return api;
    }

    public String getBucketName() {
        return bucketName;
    }

    @Override
    public String initMultipartUpload(String filePath) {
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(
                new InitiateMultipartUploadRequest(ossBucketName, filePath));
        return result.getUploadId();
    }

    @Override
    public SysFilePartETag uploadPart(String filePath, String uploadId, int partNumber, long partSize,
            InputStream inputStream) {
        UploadPartRequest request = new UploadPartRequest();
        request.setBucketName(ossBucketName);
        request.setKey(filePath);
        request.setUploadId(uploadId);
        request.setPartNumber(partNumber);
        request.setInputStream(inputStream);
        request.setPartSize(partSize);
        UploadPartResult result = ossClient.uploadPart(request);
        return new SysFilePartETag(partNumber, result.getPartETag().getETag(), partSize, null);
    }

    @Override
    public String completeMultipartUpload(String filePath, String uploadId, List<SysFilePartETag> partETags) {
        List<PartETag> tags = new ArrayList<>();
        if (partETags != null) {
            for (SysFilePartETag item : partETags) {
                tags.add(new PartETag(item.getPartNumber(), item.getETag()));
            }
        }
        CompleteMultipartUploadRequest request = new CompleteMultipartUploadRequest(
                ossBucketName, filePath, uploadId, tags);
        ossClient.completeMultipartUpload(request);
        return filePath;
    }
}
