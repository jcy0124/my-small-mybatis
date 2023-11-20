package com.jcy.dao;

import com.jcy.pojo.User;

import java.util.List;

public interface IUserDao {

    /**
     * 查询所有
     */
    List<User> findAll();

    /**
     * 根据多条件查询
     */
    User findByCondition(User user);
}
