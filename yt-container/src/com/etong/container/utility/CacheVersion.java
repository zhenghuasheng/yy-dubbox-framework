/**
 * Created by wunan on 16-1-28.
 * 缓存数据版本管理类，通过memcache管理当前版本，使得服务可以实现分布式部署
 */
package com.etong.container.utility;

import com.google.code.ssm.api.InvalidateSingleCache;
import com.google.code.ssm.api.ParameterValueKeyProvider;
import com.google.code.ssm.api.ReadThroughSingleCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheVersion {
    private static Logger logger = LoggerFactory.getLogger(CacheVersion.class);

    @ReadThroughSingleCache(namespace = "MEM:KEY:VERSION", expiration = 3600 * 24)
    public String getVer(@ParameterValueKeyProvider(order = 0) String system
            , @ParameterValueKeyProvider(order = 1) String category) {
        String version =  Long.toString(System.currentTimeMillis());
        logger.info("获取缓存KEY版本，系统：{}，分类：{}，版本：{}"
                , system, category, version);
        return version;
    }

    @InvalidateSingleCache(namespace = "MEM:KEY:VERSION")
    public void changeVer(@ParameterValueKeyProvider(order = 0) String system
            , @ParameterValueKeyProvider(order = 1) String category) {
        logger.info("更改缓存KEY版本，系统：{}，分类：{}", system, category);
    }
}
