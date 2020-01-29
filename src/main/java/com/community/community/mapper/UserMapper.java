package com.community.community.mapper;

import com.community.community.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (id,account_id,login,token,gmt_create,gmt_modify,bio,avatar_url) values(#{id},#{accountId},#{login},#{token},#{gmtCreate},#{gmtModify},#{bio},#{avatarUrl})")
    public void insert(User user);

    @Select("SELECT * FROM user WHERE token=#{token}")
    User findUserByToken(@Param("token") String token);

    @Select("SELECT * FROM user WHERE account_id=#{accountId}")
    User findUserByAccountId(@Param("accountId") String accountId);

    @Select("SELECT * FROM user WHERE id=#{id}")
    User findUserById(@Param("id") int id);

    @Update("UPDATE user SET token=#{token},gmt_modify=#{gmtModify},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User dbUser);
}
