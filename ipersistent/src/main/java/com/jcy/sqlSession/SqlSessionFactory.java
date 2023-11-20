package com.jcy.sqlSession;

public interface SqlSessionFactory {

    /**
     * 1，生产sqlSession对象，2，创建执行器对象
     * @return
     */
    SqlSession openSession();
}
