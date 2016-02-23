package com.mahouttest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahouttest.domain.model.UserInfo;

@Repository
public interface UserInfoDAO extends JpaRepository<UserInfo, Long> {

}
