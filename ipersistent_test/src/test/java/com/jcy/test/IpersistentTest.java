package com.jcy.test;

import com.jcy.io.Resources;
import com.jcy.sqlSession.SqlSession;
import com.jcy.sqlSession.SqlSessionFactory;
import com.jcy.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class IpersistentTest {


    /**
     * 传统方式（不使用mapper代理）测试
     */
    @Test
    public void test1() throws Exception {
        // 1, 根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");

        // 2, 解析配置文件，封装了Configuration（配置信息以及mapper的xml信息）对象，创建了sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        // 3, 生产sqlSession 创建了执行器对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

    }
}
