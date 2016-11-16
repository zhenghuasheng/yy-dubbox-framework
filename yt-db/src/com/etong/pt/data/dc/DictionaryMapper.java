package com.etong.pt.data.dc;

import com.etong.pt.data.dc.Dictionary;
import com.etong.pt.data.dc.DictionaryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DictionaryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int countByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int deleteByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int deleteByPrimaryKey(Short f_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int insert(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int insertSelective(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    List<Dictionary> selectByExample(DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    Dictionary selectByPrimaryKey(Short f_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int updateByExample(@Param("record") Dictionary record, @Param("example") DictionaryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int updateByPrimaryKeySelective(Dictionary record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cm_type
     *
     * @mbggenerated Mon Jun 08 15:32:53 CST 2015
     */
    int updateByPrimaryKey(Dictionary record);
}