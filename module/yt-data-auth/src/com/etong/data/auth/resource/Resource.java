package com.etong.data.auth.resource;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_id
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_rspath
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String rspath;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_itemid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String itemid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_pitemid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String pitemid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_name
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_vieworder
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private Integer vieworder;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_remark
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String remark;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_available
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private Boolean available;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_delete
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private Boolean delete;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_leaf
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private Boolean leaf;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_stid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String stid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_type
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private Byte type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_icon
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String icon;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_pm_resource.f_url
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    private String url;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_id
     *
     * @return the value of t_pm_resource.f_id
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_id
     *
     * @param id the value for t_pm_resource.f_id
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_rspath
     *
     * @return the value of t_pm_resource.f_rspath
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getRspath() {
        return rspath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_rspath
     *
     * @param rspath the value for t_pm_resource.f_rspath
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setRspath(String rspath) {
        this.rspath = rspath;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_itemid
     *
     * @return the value of t_pm_resource.f_itemid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_itemid
     *
     * @param itemid the value for t_pm_resource.f_itemid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_pitemid
     *
     * @return the value of t_pm_resource.f_pitemid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getPitemid() {
        return pitemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_pitemid
     *
     * @param pitemid the value for t_pm_resource.f_pitemid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setPitemid(String pitemid) {
        this.pitemid = pitemid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_name
     *
     * @return the value of t_pm_resource.f_name
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_name
     *
     * @param name the value for t_pm_resource.f_name
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_vieworder
     *
     * @return the value of t_pm_resource.f_vieworder
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public Integer getVieworder() {
        return vieworder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_vieworder
     *
     * @param vieworder the value for t_pm_resource.f_vieworder
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setVieworder(Integer vieworder) {
        this.vieworder = vieworder;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_remark
     *
     * @return the value of t_pm_resource.f_remark
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getRemark() {
        return remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_remark
     *
     * @param remark the value for t_pm_resource.f_remark
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_available
     *
     * @return the value of t_pm_resource.f_available
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_available
     *
     * @param available the value for t_pm_resource.f_available
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_delete
     *
     * @return the value of t_pm_resource.f_delete
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public Boolean getDelete() {
        return delete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_delete
     *
     * @param delete the value for t_pm_resource.f_delete
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_leaf
     *
     * @return the value of t_pm_resource.f_leaf
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public Boolean getLeaf() {
        return leaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_leaf
     *
     * @param leaf the value for t_pm_resource.f_leaf
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_stid
     *
     * @return the value of t_pm_resource.f_stid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getStid() {
        return stid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_stid
     *
     * @param stid the value for t_pm_resource.f_stid
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setStid(String stid) {
        this.stid = stid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_type
     *
     * @return the value of t_pm_resource.f_type
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public Byte getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_type
     *
     * @param type the value for t_pm_resource.f_type
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_icon
     *
     * @return the value of t_pm_resource.f_icon
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getIcon() {
        return icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_icon
     *
     * @param icon the value for t_pm_resource.f_icon
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_pm_resource.f_url
     *
     * @return the value of t_pm_resource.f_url
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public String getUrl() {
        return url;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_pm_resource.f_url
     *
     * @param url the value for t_pm_resource.f_url
     *
     * @mbggenerated Tue Dec 08 16:21:42 CST 2015
     */
    public void setUrl(String url) {
        this.url = url;
    }
}