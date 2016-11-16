/**
 * Created by wunan on 2015/4/21.
 */
package com.etong.pt.utility;

import java.io.Serializable;

public class PtResult implements Serializable {
    PtError ptError = PtCommonError.PT_ERROR_SUCCESS;
    String description;
    Object object;

    public PtResult() {
    }

    public PtResult(PtError ptError, String description, Object object) {
        this.description = description;
        this.object = object;
        this.ptError = ptError;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public <T> T getObject() {
        return (T) object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public PtError getPtError() {
        return ptError;
    }

    public void setPtError(PtError ptError) {
        this.ptError = ptError;
    }

    public boolean isSucceed() {
        return (ptError == PtCommonError.PT_ERROR_SUCCESS);
    }

    @Override
    public String toString() {
        String result = "PtResult{ PtError:" + ptError.toString();

        if (description != null) {
            result += ", Description:" + description;
        }

        if (object != null) {
            result += ", Object:" + object.toString();
        }

        result += "}";
        return result;
    }
}
