/**
 * Created by wunan on 2015/4/16.
 */
package com.etong.pt.data.dc;

import com.etong.pt.db.DbProxy;
import org.apache.ibatis.session.SqlSession;

public class DbProxyImpl implements DbProxy {
    private SqlSession sqlSession;

    public DbProxyImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    public void commit() {
        sqlSession.commit();
    }

    @Override
    public void close() {
        if (sqlSession != null) {
            sqlSession.close();
            sqlSession = null;
        }
    }

    public <T> T getMapper(Class<T> tClass) {
        return sqlSession.getMapper(tClass);
    }
}
