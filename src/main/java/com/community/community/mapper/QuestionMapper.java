package com.community.community.mapper;

import com.community.community.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO question (id,title,description,gmt_create,gmt_modify,creator,tag) values(#{id},#{title},#{description},#{gmtCreate},#{gmtModify},#{creator},#{tag})")
    public void Insert(Question question);

    @Select("SELECT * FROM question")
    public List<Question> findAll();

    @Select("SELECT * FROM question where creator = #{userId}")
    public List<Question> findByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM question where id = #{id}")
    Question findById(@Param("id")Integer id);

    @Update("UPDATE QUESTION SET title=#{title},description=#{description},gmt_modify=#{gmtModify},tag=#{tag} WHERE id=#{id}")
    void update(Question question);

    @Update("UPDATE QUESTION SET view_count = view_count + 1 where id=#{id}")
    void incrViewCount(@Param("id") Integer id);
}
