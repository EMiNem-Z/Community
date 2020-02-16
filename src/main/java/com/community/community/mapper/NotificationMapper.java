package com.community.community.mapper;

import com.community.community.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NotificationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated
     */
    int insert(Notification record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated
     */
    Notification selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated
     */
    List<Notification> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notification
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(Notification record);

    @Select("SELECT * FROM notification WHERE receiver = #{userId} and status = 0")
    List<Notification> listByStatus(@Param("userId") Integer userId);

    @Update("update notification SET status = 1 WHERE id = #{id}")
    void updateStatusById(Integer id);
}