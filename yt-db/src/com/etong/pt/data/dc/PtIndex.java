package com.etong.pt.data.dc;

import com.google.code.ssm.api.CacheKeyMethod;

import java.io.Serializable;

public class PtIndex implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_bm_indexnum.f_index
     *
     * @mbggenerated Wed Apr 22 08:48:41 CST 2015
     */
    private Long f_index;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_bm_indexnum.f_type
     *
     * @mbggenerated Wed Apr 22 08:48:41 CST 2015
     */
    private String f_type;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bm_indexnum.f_index
     *
     * @return the value of t_bm_indexnum.f_index
     *
     * @mbggenerated Wed Apr 22 08:48:41 CST 2015
     */
    public Long getF_index() {
        return f_index;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bm_indexnum.f_index
     *
     * @param f_index the value for t_bm_indexnum.f_index
     *
     * @mbggenerated Wed Apr 22 08:48:41 CST 2015
     */
    public void setF_index(Long f_index) {
        this.f_index = f_index;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_bm_indexnum.f_type
     *
     * @return the value of t_bm_indexnum.f_type
     *
     * @mbggenerated Wed Apr 22 08:48:41 CST 2015
     */
    @CacheKeyMethod
    public String getF_type() {
        return f_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_bm_indexnum.f_type
     *
     * @param f_type the value for t_bm_indexnum.f_type
     *
     * @mbggenerated Wed Apr 22 08:48:41 CST 2015
     */
    public void setF_type(String f_type) {
        this.f_type = f_type == null ? null : f_type.trim();
    }
}