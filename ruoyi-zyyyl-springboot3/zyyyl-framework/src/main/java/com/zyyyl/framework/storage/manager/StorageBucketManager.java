package com.zyyyl.framework.storage.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyyyl.common.config.ZyyylConfig;
import com.zyyyl.common.core.storage.ZyyylStorageBucket;
import com.zyyyl.common.core.storage.base.StorageBucket;
import com.zyyyl.common.core.storage.base.StorageFactory;
import com.zyyyl.framework.storage.properties.DynamicStorageBootProperties;

@Configuration
public class StorageBucketManager implements InitializingBean {

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
            StorageFactory<?> storageFactory = storageFactoryMap.get(type);
            if (storageFactory == null) {
                throw new IllegalStateException("不存在该存储类型的工厂类：" + type);
            }
            StorageBucket bucket = storageFactory.buildBucket(name, props);
            targetBuckets.put(name, bucket);
            sbTypeHashMap.put(name, type);
        });
    }
}
