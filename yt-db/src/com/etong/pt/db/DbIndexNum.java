/**
 * Created by wunan on 2015/4/22.
 */
package com.etong.pt.db;

import com.etong.pt.utility.PtResult;

public interface DbIndexNum {
    PtResult generateIndexNum(String type);
    PtResult generateIndexNum(String type, DbProxy dbProxy);
}
