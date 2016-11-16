/**
 * Created by wunan on 16-1-28.
 */
package com.etong.container.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheKey {
    private CacheVersion cacheVersion;
    private static Logger logger = LoggerFactory.getLogger(CacheKey.class);

    public void setCacheVersion(CacheVersion cacheVersion) {
        this.cacheVersion = cacheVersion;
    }

    public String getKey(String system, String category, String... params) {
        String version;

        if (cacheVersion == null) {
            logger.error("获取缓存KEY错误，缓存版本管理器对象为空");
            version = Long.toString(System.currentTimeMillis());
        } else {
            version = cacheVersion.getVer(system, category);
        }

        String key = system + ":" + category + ":" + version;

        for (String param : params) {
            if (param != null) {
                key += ":" + param;
            }
        }

        logger.debug("获取缓存KEY：{}", key);
        return key;
    }

    public String getKeyByPage(String system, String category
            , Integer start, Integer limit, String... params) {
        if ((start == null) || (limit == null)) {
            return getKey(system, category, params);
        }

        String key = getKey(system, category, params) + ":" + start + ":" + limit;
        logger.debug("获取分页缓存KEY：{}", key);
        return key;
    }

    public void changeVer(String system, String category) {
        if (cacheVersion != null) {
            cacheVersion.changeVer(system, category);
        } else {
            logger.error("更新缓存KEY错误，缓存版本管理器对象为空");
        }
    }
}

