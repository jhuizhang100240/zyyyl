package com.zyyyl.framework.storage.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zyyyl.common.config.ZyyylConfig;
import com.zyyyl.common.core.storage.ZyyylStorageBucket;
import com.zyyyl.common.core.storage.base.StorageBucket;
import com.zyyyl.common.core.storage.base.StorageFactory;
import com.zyyyl.framework.storage.properties.DynamicStorageBootProperties;

@Configuration
public class StorageBucketManager implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(StorageBucketManager.class);

    private Map<String, StorageBucket> targetBuckets = new HashMap<>();
    private Map<String, String> sbTypeHashMap = new HashMap<>();

    @Autowired
    DynamicStorageBootProperties storageBootProperties;

    @Autowired
    Map<String, StorageFactory<?>> storageFactoryMap;

    @Bean
    public ZyyylStorageBucket storageBucket() {
        String primary = storageBootProperties.getPrimary();
        StorageBucket primaryBucket = targetBuckets.get(primary);
        String primayType = sbTypeHashMap.get(primary);
        ZyyylStorageBucket storageBucket = new ZyyylStorageBucket(primary, primaryBucket, primayType);
        for (Map.Entry<String, StorageBucket> entry : targetBuckets.entrySet()) {
            String type = sbTypeHashMap.get(entry.getKey());
            storageBucket.addStorageBucket(entry.getKey(), entry.getValue(), type);
        }
        ZyyylConfig.setZyyylStorageBucket(storageBucket);
        return storageBucket;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        storageBootProperties.getBuckets().forEach((name, props) -> {
            String type = props.getProperty("type");
            if ("oss".equalsIgnoreCase(type) && !ossConfigured(props)) {
                logger.warn("OSS 参数配置不完整，存储桶 {} 自动回退本地磁盘", name);
                type = "local";
                props.setProperty("type", "local");
                props.setProperty("path",
                        props.getProperty("path", "D:/zyyyl/uploadPath"));
                props.setProperty("api",
                        props.getProperty("api", "/profile/files/master"));
            }
            StorageFactory<?> storageFactory = storageFactoryMap.get(type);
            if (storageFactory == null) {
                throw new IllegalStateException("不存在该存储类型的工厂类：" + type);
            }
            StorageBucket bucket = storageFactory.buildBucket(name, props);
            targetBuckets.put(name, bucket);
            sbTypeHashMap.put(name, type);
        });
    }

    /**
     * OSS 必需参数是否齐全。缺项时由调用方回退本地，确保后端可启动。
     */
    private boolean ossConfigured(Properties props) {
        return hasText(props.getProperty("accessKeyId"))
                && hasText(props.getProperty("accessKeySecret"))
                && hasText(props.getProperty("bucketName"))
                && hasText(props.getProperty("endpoint"));
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
