package com.sq.user.mapper;

import com.sq.pojo.Member;

public interface UserMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    Member selectByUserName(String username);
}
