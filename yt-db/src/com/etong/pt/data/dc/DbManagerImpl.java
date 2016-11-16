/**
 * Created by wunan on 2015/4/16.
 */
package com.etong.pt.data.dc;

import com.etong.pt.db.DbManager;
import com.etong.pt.db.DbProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DbManagerImpl implements DbManager {
    private Logger logger = Logger.getLogger(DbManagerImpl.class);
    private SqlSessionFactory sqlSessionFactory;

    public DbProxy getDbProxy(boolean autoCommit) {
        return new DbProxyImpl(sqlSessionFactory.openSession(autoCommit));
    }

    public DbManagerImpl() {
        this.init("./config/mybatis-config.xml");
    }

    public boolean init(String config) {
        InputStream inputStream;

        try {
            inputStream = new FileInputStream(config);
        } catch (IOException e) {
            logger.error(e.getMessage());
            return false;
        }

        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        return true;
    }
}
