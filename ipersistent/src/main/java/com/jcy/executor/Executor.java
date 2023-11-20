package com.jcy.executor;

import com.jcy.pojo.Configuration;
import com.jcy.pojo.MappedStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

    void close();

}























