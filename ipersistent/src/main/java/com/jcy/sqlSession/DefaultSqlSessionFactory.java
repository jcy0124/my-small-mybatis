package com.jcy.sqlSession;

import com.jcy.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory() {
    }

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


}












