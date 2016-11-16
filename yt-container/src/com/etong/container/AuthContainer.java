/**
 * Created by wunan on 15-11-4.
 */
package com.etong.container;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConfigUtils;
import com.alibaba.dubbo.container.Container;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class AuthContainer implements Container {
    private static final Logger logger = LoggerFactory.getLogger(AuthContainer.class);

    public static final String SPRING_CONFIG = "dubbo.spring.config";

    public static final String DEFAULT_SPRING_CONFIG = "config/spring-config.xml";

    static FileSystemXmlApplicationContext context;

    public static FileSystemXmlApplicationContext getContext() {
        return context;
    }

    @Override
    public void start() {
        String configPath = ConfigUtils.getProperty(SPRING_CONFIG);

        if (configPath == null || configPath.length() == 0) {
            configPath = DEFAULT_SPRING_CONFIG;
        }

        context = new FileSystemXmlApplicationContext(configPath.split("[,\\s]+"));
        context.start();
    }

    @Override
    public void stop() {
        if (context == null) {
            return;
        }

        try {
            context.stop();
            context.close();
            context = null;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
        }
    }
}
