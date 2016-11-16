/**
 * Created by wunan on 15-12-7.
 */
package com.etong.pt.utility;

public class PageLimit {
    protected String limitClause;

    public String getLimitClause() {
        return limitClause;
    }

    public void setLimitClause(Integer start, Integer count) {
        if ((start == null) || (count == null)) {
            return;
        }

        if (start < 0) {
            start = 0;
        }

        limitClause = String.format("limit %d, %d", start, count);
    }
}
