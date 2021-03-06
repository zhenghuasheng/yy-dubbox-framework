package com.etong.data.auth.userresource;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserResourceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int countByExample(UserResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int deleteByExample(UserResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int insert(UserResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int insertSelective(UserResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    List<UserResource> selectByExample(UserResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    UserResource selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserResource record, @Param("example") UserResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int updateByExample(@Param("record") UserResource record, @Param("example") UserResourceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int updateByPrimaryKeySelective(UserResource record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_useresource
     *
     * @mbggenerated Mon Nov 23 10:25:11 CST 2015
     */
    int updateByPrimaryKey(UserResource record);
}