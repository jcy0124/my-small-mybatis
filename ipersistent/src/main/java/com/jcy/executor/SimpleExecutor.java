package com.jcy.executor;

import com.jcy.pojo.Configuration;
import com.jcy.pojo.MappedStatement;

import java.util.List;

public class SimpleExecutor implements Executor{


    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {
        return null;
    }

    @Override
    public void close() {

    }
}
































