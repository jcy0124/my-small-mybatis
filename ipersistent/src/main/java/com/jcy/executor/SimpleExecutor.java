package com.jcy.executor;

import com.jcy.config.BoundSql;
import com.jcy.pojo.Configuration;
import com.jcy.pojo.MappedStatement;
import com.jcy.utils.GenericTokenParser;
import com.jcy.utils.ParameterMapping;
import com.jcy.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor{

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {

        //1，加载驱动，获取数据库连接
        connection = configuration.getDataSource().getConnection();

        //2，获取preparedStatement预编译对象
        // 获取要执行的sql语句
        String sql = mappedStatement.getSql();
        //含有？的sql和#{}里面的值
        BoundSql boundSql = getBoundSql(sql);
        String finalSql = boundSql.getFinalSql();
        preparedStatement = connection.prepareStatement(finalSql);

        //3，设置参数
        // parameterType = com.jcy.pojo.User
        String parameterType = mappedStatement.getParameterType();

        if (parameterType != null) {
            Class<?> parameterTypeClass = Class.forName(parameterType);

            List<ParameterMapping> listParameterMapping = boundSql.getListParameterMapping();
            for (int i = 0; i < listParameterMapping.size(); i++) {
                ParameterMapping parameterMapping = listParameterMapping.get(i);
                // 第一次执行就是id 第二次执行是username
                String paramName = parameterMapping.getContent();
                // 反射
                Field declaredField = parameterTypeClass.getDeclaredField(paramName);
                declaredField.setAccessible(true);
                Object value = declaredField.get(param);
                // 赋值占位符
                preparedStatement.setObject(i + 1, value);
            }
        }

        //4， 执行sql，发起查询
        resultSet = preparedStatement.executeQuery();

        //5， 处理返回结果集
        ArrayList<E> list = new ArrayList<>();
        while (resultSet.next()){
            // 元数据信息  包含了   字段名 字段的值
            ResultSetMetaData metaData = resultSet.getMetaData();

            // resultType = com.jcy.pojo.User
            String resultType = mappedStatement.getResultType();
            Class<?> resultTypeClass = Class.forName(resultType);
            Object o = resultTypeClass.newInstance();

            for (int i = 0; i < metaData.getColumnCount(); i++) {

                //字段名 id username
                String columnName = metaData.getColumnName(i+1);
                //字段值 value
                Object value = resultSet.getObject(columnName);

                //问题：现在要封装到哪一个实体中
                //封装
                //PropertyDescriptor属性描述器:通过API方法获取某个属性的读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                //参数1：实例对象  参数2：要设置的值
                writeMethod.invoke(o,value);

            }
            list.add((E) o);
        }

        return list;
    }

    /**
     * 1,将#{}占位符替换成 ？ 2，解析替换的过程中，将#{}里面的值保存下来，用于获取param的对象
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {

        //1,创建标记处理器，配合标记解析器完成标记的处理解析工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        //2,创建标记解析器，
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{","}",parameterMappingTokenHandler);
        //3,#{}占位符替换成了？，在解析过程中把#{}里面的值保存在ParameterMapping中
        String finalSql = genericTokenParser.parse(sql);
        //#{}里面的值的一个集合，id，username
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        BoundSql boundSql = new BoundSql(finalSql, parameterMappings);
        return boundSql;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        if (resultSet != null){
            try {
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (preparedStatement != null){
            try {
                preparedStatement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if (connection != null){
            try {
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }


}
































