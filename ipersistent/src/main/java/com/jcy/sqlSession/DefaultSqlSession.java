package com.jcy.sqlSession;

import com.jcy.executor.Executor;
import com.jcy.pojo.Configuration;
import com.jcy.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession() {
    }

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    /**
     * 查询多个结果
     * sqlSession.selectList() :定位到要执行的sql语句，从而执行
     *
     * @param statementId
     * @param param
     */
    @Override
    public <E> List<E> selectList(String statementId, Object param) throws Exception {
        // 将查询操作委派给底层的执行器
        // query():执行底层的JDBC 1，sql配置信息 2，sql配置信息
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list = executor.query(configuration,mappedStatement,param);

        return list;
    }

    /**
     * 查询单个结果
     *
     * @param statementId
     * @param param
     */
    @Override
    public <T> T selectOne(String statementId, Object param) throws Exception {
        //去调用selectList
        List<Object> list = this.selectList(statementId, param);
        if (list.size() == 1) {
            return (T) list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("返回结果过多");
        }else {
            return null;
        }
    }

    /**
     * 清除资源
     */
    @Override
    public void close() {
        executor.close();
    }


}
































