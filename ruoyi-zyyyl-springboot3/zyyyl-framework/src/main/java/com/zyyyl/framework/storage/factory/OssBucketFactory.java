package com.zyyyl.framework.storage.factory;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zyyyl.common.core.storage.base.StorageFactory;
import com.zyyyl.common.exception.ServiceException;
import com.zyyyl.framework.storage.domain.OssBucket;

/**
 * 阿里云 OSS 存储桶工厂。参数缺失时由配置层回退本地，不在启动期抛异常。
 */
@Configuration("oss")
public class OssBucketFactory extends StorageFactory<OssBucket> {

    private static final Logger logger = LoggerFactory.getLogger(OssBucketFactory.class);

    @Override
    protected OssBucket createBucket(String name, Properties props) {
        String accessKeyId = props.getProperty("accessKeyId");
        String accessKeySecret = props.getProperty("accessKeySecret");
        String endpoint = props.getProperty("endpoint");
        String ossBucketName = props.getProperty("bucketName");
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OssBucket bucket = OssBucket.builder()
                .bucketName(name)
                .ossBucketName(ossBucketName)
                .endpoint(endpoint)
                .permission(props.getProperty("permission", "public"))
                .api(props.getProperty("api", "/profile/files/oss"))
                .ossClient(client)
                .build();
        logger.info("OSS 数据桶：{}  - 创建成功，bucket={}", name, ossBucketName);
        return bucket;
    }

    @Override
    protected void validateBucket(OssBucket bucket) {
        if (bucket.getOssClient() == null) {
            throw new ServiceException("OSS 客户端初始化失败");
        }
    }
}
