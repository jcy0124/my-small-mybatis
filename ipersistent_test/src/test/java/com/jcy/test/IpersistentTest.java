package com.jcy.test;

import com.jcy.dao.IUserDao;
import com.jcy.io.Resources;
import com.jcy.pojo.User;
import com.jcy.sqlSession.SqlSession;
import com.jcy.sqlSession.SqlSessionFactory;
import com.jcy.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

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

        // 4, 调用sqlSession方法
        User user = new User();
        user.setId(1);
        user.setUsername("tom");

        User getUser = sqlSession.selectOne("user.selectOne", user);
        System.out.println(getUser);

        // 5, 释放资源
        sqlSession.close();
    }


    /**
     * 使用mapper代理测试
     */
    @Test
    public void test2() throws Exception {
        // 1, 根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");

        // 2, 解析配置文件，封装了Configuration（配置信息以及mapper的xml信息）对象，创建了sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);

        // 3, 生产sqlSession 创建了执行器对象
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4, 调用sqlSession方法
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        List<User> list = userDao.findAll();

        list.forEach(System.out::println);

        // 5, 释放资源
        sqlSession.close();

    }
}




































