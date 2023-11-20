package com.jcy.sqlSession;

import com.jcy.executor.Executor;
import com.jcy.executor.SimpleExecutor;
import com.jcy.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory() {
    }

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    /**
     * 1，生产sqlSession对象，2，创建执行器对象
     *
     * @return
     */
    @Override
    public SqlSession openSession() {
        // 1，创建执行器对象
        Executor simpleExecutor = new SimpleExecutor();

        // 2，生产sqlSession对象
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(configuration,simpleExecutor);

        return defaultSqlSession;
    }
}












