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


    /**
     * 生成代理对象
     *
     * @param mapperClass
     */
    @Override
    public <T> T getMapper(Class<?> mapperClass) {

        //使用JDK动态代理生成基于接口的代理对象
        Object proxy = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {

            /*
                Object：代理对象的引用，很少用
                Method：被调用的方法的字节码对象
                Object[]：调用的方法的参数
             */

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                // 具体的逻辑：执行底层JDBC

                // 通过调用sqlSession里面的方法来完成方法的调用

                // 参数的准备：1.statementId:com.jcy.dao.IUserDao.findAll 2.param=args

                // 问题1：无法获取现有的statementId

                //findAll
                String methodName = method.getName();
                // com.jcy.dao.IUserDao
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;

                //方法调用
                //问题2：要调用sqlSession中的增删改查的什么方法？
                //改造当前工程： sqlCommandType
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                //select update delete insert
                String sqlCommandType = mappedStatement.getSqlCommandType();
                switch (sqlCommandType){
                    case "select":
                        //执行查询方法的调用
                        // 问题3：该调用selectList还是selectOne？
                        Type genericReturnType = method.getGenericReturnType();
                        // 判断是否实现了 泛型类型参数化，
                        if (genericReturnType instanceof ParameterizedType) {
                            if (args != null){
                                return selectList(statementId, args[0]);
                            }
                            return selectList(statementId, null);
                        }
                        return selectOne(statementId,args[0]);
                    case "update":
                        //执行更新方法的调用
                        break;
                    case "delete":
                        //执行删除方法的调用
                        break;
                    case "insert":
                        //执行插入方法的调用
                }


                return null;

            }
        });

        return (T) proxy;
    }
}
































