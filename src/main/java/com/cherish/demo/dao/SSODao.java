package com.cherish.demo.dao;

import com.cherish.demo.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface SSODao {

    User selectOneUser(User user);

}
