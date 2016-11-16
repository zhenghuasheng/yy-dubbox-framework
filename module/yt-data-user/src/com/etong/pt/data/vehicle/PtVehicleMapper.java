package com.etong.pt.data.vehicle;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PtVehicleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int countByExample(PtVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int deleteByExample(PtVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int deleteByPrimaryKey(Long f_dvid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int insert(PtVehicle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int insertSelective(PtVehicle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    List<PtVehicle> selectByExample(PtVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    PtVehicle selectByPrimaryKey(Long f_dvid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int updateByExampleSelective(@Param("record") PtVehicle record, @Param("example") PtVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int updateByExample(@Param("record") PtVehicle record, @Param("example") PtVehicleExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int updateByPrimaryKeySelective(PtVehicle record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_dm_vehicle
     *
     * @mbggenerated Thu Apr 23 09:57:15 CST 2015
     */
    int updateByPrimaryKey(PtVehicle record);
}