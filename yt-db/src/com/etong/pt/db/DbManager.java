/**
 * Created by wunan on 2015/4/16.
 */
package com.etong.pt.db;

public interface DbManager {
    DbProxy getDbProxy(boolean autoCommit);
}
