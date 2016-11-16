/**
 * Created by wunan on 15-6-8.
 */
package com.etong.pt.db;

import com.etong.pt.utility.PtResult;

public interface DictHelper {
    PtResult getCategory(int category);
    PtResult getChildCategory(int category, int parent);
    PtResult getLeaveByValue(int category, int value, int parent);
}
