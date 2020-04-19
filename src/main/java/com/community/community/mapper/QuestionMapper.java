package com.community.community.mapper;

import com.community.community.dto.QuestionDTO;
import com.community.community.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Select("SELECT * FROM question WHEWE id=#{id}")
    public Question findBuId(@Param("id") Integer id);

    @Insert("INSERT INTO question (id,title,description,gmt_create,gmt_modify,creator,tag) values(#{id},#{title},#{description},#{gmtCreate},#{gmtModify},#{creator},#{tag})")
    public void Insert(Question question);

    @Select("SELECT * FROM question ORDER BY gmt_create desc")
    public List<Question> findAll();

    public List<Question> selectBySearch(@Param("search") String search);

    @Select("SELECT * FROM question where creator = #{userId}")
    public List<Question> findByUserId(@Param("userId") Integer userId);

    @Select("SELECT * FROM question where id = #{id}")
    Question findById(@Param("id")Integer id);

    @Update("UPDATE QUESTION SET title=#{title},description=#{description},gmt_modify=#{gmtModify},tag=#{tag} WHERE id=#{id}")
    void update(Question question);

    @Update("UPDATE QUESTION SET view_count = view_count + 1 where id=#{id}")
    void incrViewCount(@Param("id") Integer id);

    @Update("UPDATE QUESTION SET comment_count = comment_count + 1 where id=#{id}")
    void incrCommentCount(@Param("id") Integer id);

    @Select("select id,title,tag from question where tag REGEXP #{tag} AND id != #{id} ORDER BY gmt_create desc limit 5")
    List<Question> findRelatedBytag(QuestionDTO questionDTO);

    @Select("select id,title from question ORDER BY view_count desc limit 10")
    List<Question> selectHot();

    @Delete("DELETE FROM QUESTION WHERE id=#{id}")
    void deleteByid(@Param("id") Integer id);
}
