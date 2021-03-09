package com.sq.security.mapper;

import com.sq.pojo.Member;

public interface UserDetailMapper {

    Member selectByPrimaryKey(Long id);
}
