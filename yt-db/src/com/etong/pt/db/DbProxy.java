/**
 * Created by wunan on 2015/4/16.
 */
package com.etong.pt.db;

public interface DbProxy {
    <T> T getMapper(Class<T> tClass);
    void commit();

    void close();
}
