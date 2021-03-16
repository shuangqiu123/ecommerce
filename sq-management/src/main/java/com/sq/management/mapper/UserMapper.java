package com.sq.management.mapper;

import com.sq.pojo.Member;

public interface UserMapper {
    Member getUserByUserId(Long id);
}
