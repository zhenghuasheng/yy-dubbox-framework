package com.etong.pt.data.user.system;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserSystemMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int countByExample(UserSystemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int deleteByExample(UserSystemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int deleteByPrimaryKey(Long usid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int insert(UserSystem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int insertSelective(UserSystem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    List<UserSystem> selectByExample(UserSystemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    UserSystem selectByPrimaryKey(Long usid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserSystem record, @Param("example") UserSystemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int updateByExample(@Param("record") UserSystem record, @Param("example") UserSystemExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int updateByPrimaryKeySelective(UserSystem record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_um_usersystem
     *
     * @mbggenerated Wed Dec 16 16:56:37 CST 2015
     */
    int updateByPrimaryKey(UserSystem record);
}