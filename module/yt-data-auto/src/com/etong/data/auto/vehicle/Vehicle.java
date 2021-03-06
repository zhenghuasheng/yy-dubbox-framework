package com.etong.data.auto.vehicle;

import java.io.Serializable;

public class Vehicle implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.F_ID
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Short id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_pid
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Short pid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.F_TITLE
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String title;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_image
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String image;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_brand_desc
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String brandDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_letter
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String letter;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_level
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Byte level;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.country
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String country;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_minguide
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Float minguide;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_maxguide
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Float maxguide;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_out_vol
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String outVol;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_min_out
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Integer minOut;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_max_out
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Integer maxOut;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_website
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String website;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_models_level
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Short modelsLevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.carlevel
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private String carlevel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column v_cm_vehicle.f_trueorder
     *
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    private Short trueorder;

    private String pTitle;
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.F_ID
     *
     * @return the value of v_cm_vehicle.F_ID
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Short getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.F_ID
     *
     * @param id the value for v_cm_vehicle.F_ID
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_pid
     *
     * @return the value of v_cm_vehicle.f_pid
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Short getPid() {
        return pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_pid
     *
     * @param pid the value for v_cm_vehicle.f_pid
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setPid(Short pid) {
        this.pid = pid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.F_TITLE
     *
     * @return the value of v_cm_vehicle.F_TITLE
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.F_TITLE
     *
     * @param title the value for v_cm_vehicle.F_TITLE
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_image
     *
     * @return the value of v_cm_vehicle.f_image
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getImage() {
        return image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_image
     *
     * @param image the value for v_cm_vehicle.f_image
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_brand_desc
     *
     * @return the value of v_cm_vehicle.f_brand_desc
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getBrandDesc() {
        return brandDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_brand_desc
     *
     * @param brandDesc the value for v_cm_vehicle.f_brand_desc
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_letter
     *
     * @return the value of v_cm_vehicle.f_letter
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getLetter() {
        return letter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_letter
     *
     * @param letter the value for v_cm_vehicle.f_letter
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setLetter(String letter) {
        this.letter = letter;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_level
     *
     * @return the value of v_cm_vehicle.f_level
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Byte getLevel() {
        return level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_level
     *
     * @param level the value for v_cm_vehicle.f_level
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setLevel(Byte level) {
        this.level = level;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.country
     *
     * @return the value of v_cm_vehicle.country
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getCountry() {
        return country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.country
     *
     * @param country the value for v_cm_vehicle.country
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_minguide
     *
     * @return the value of v_cm_vehicle.f_minguide
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Float getMinguide() {
        return minguide;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_minguide
     *
     * @param minguide the value for v_cm_vehicle.f_minguide
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setMinguide(Float minguide) {
        this.minguide = minguide;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_maxguide
     *
     * @return the value of v_cm_vehicle.f_maxguide
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Float getMaxguide() {
        return maxguide;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_maxguide
     *
     * @param maxguide the value for v_cm_vehicle.f_maxguide
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setMaxguide(Float maxguide) {
        this.maxguide = maxguide;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_out_vol
     *
     * @return the value of v_cm_vehicle.f_out_vol
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getOutVol() {
        return outVol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_out_vol
     *
     * @param outVol the value for v_cm_vehicle.f_out_vol
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setOutVol(String outVol) {
        this.outVol = outVol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_min_out
     *
     * @return the value of v_cm_vehicle.f_min_out
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Integer getMinOut() {
        return minOut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_min_out
     *
     * @param minOut the value for v_cm_vehicle.f_min_out
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setMinOut(Integer minOut) {
        this.minOut = minOut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_max_out
     *
     * @return the value of v_cm_vehicle.f_max_out
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Integer getMaxOut() {
        return maxOut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_max_out
     *
     * @param maxOut the value for v_cm_vehicle.f_max_out
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setMaxOut(Integer maxOut) {
        this.maxOut = maxOut;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_website
     *
     * @return the value of v_cm_vehicle.f_website
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getWebsite() {
        return website;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_website
     *
     * @param website the value for v_cm_vehicle.f_website
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_models_level
     *
     * @return the value of v_cm_vehicle.f_models_level
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Short getModelsLevel() {
        return modelsLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_models_level
     *
     * @param modelsLevel the value for v_cm_vehicle.f_models_level
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setModelsLevel(Short modelsLevel) {
        this.modelsLevel = modelsLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.carlevel
     *
     * @return the value of v_cm_vehicle.carlevel
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public String getCarlevel() {
        return carlevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.carlevel
     *
     * @param carlevel the value for v_cm_vehicle.carlevel
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setCarlevel(String carlevel) {
        this.carlevel = carlevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column v_cm_vehicle.f_trueorder
     *
     * @return the value of v_cm_vehicle.f_trueorder
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public Short getTrueorder() {
        return trueorder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column v_cm_vehicle.f_trueorder
     *
     * @param trueorder the value for v_cm_vehicle.f_trueorder
     * @mbggenerated Fri Nov 13 10:18:06 CST 2015
     */
    public void setTrueorder(Short trueorder) {
        this.trueorder = trueorder;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getFullName() {
        if (pTitle != null) {
            return pTitle + title;
        } else {
            return title;
        }
    }
}