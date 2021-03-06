package com.etong.data.auth.userrole;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int countByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int deleteByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int insert(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int insertSelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    List<UserRole> selectByExample(UserRoleExample example);

    List<UserRoleDetail> selectDetailByExample(UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    UserRole selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int updateByPrimaryKeySelective(UserRole record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_pm_userrole
     *
     * @mbggenerated Mon Nov 23 10:27:17 CST 2015
     */
    int updateByPrimaryKey(UserRole record);
}