package com.sq.security.service;

import com.sq.pojo.Member;

public interface UserDetailService {

    Member getMemberByUid(String uid);
}
