package com.jcy.config;

import com.jcy.adaptor.MappedStatementAdaptor;
import com.jcy.pojo.Configuration;
import com.jcy.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * parse():解析映射配置文件 -->对每一个标签封装到mappedStatement-->configuration里面的map集合中
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder() {
    }

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }


    public void parse(InputStream resourceAsSteam) throws DocumentException {

        Document document = new SAXReader().read(resourceAsSteam);
        Element rootElement = document.getRootElement();

        String namespace = rootElement.attributeValue("namespace");
        /**
         *     <select id="selectOne" resultType="com.jcy.pojo.User" parameterType="com.jcy.pojo.User">
         *         select * from user where id = #{id} and username = #{username}
         *     </select>
         */

        List<Element> selectList = rootElement.selectNodes("//select");
        for (Element element : selectList) {
            //id
            String id = element.attributeValue("id");
            //resultType
            String resultType = element.attributeValue("resultType");
            //parameterType
            String parameterType = element.attributeValue("parameterType");
            //sql
            String sql = element.getTextTrim();
            //namespace+id
            String statementId = namespace+"."+id;
            //封装mappedStatement对象
            MappedStatement mappedStatement = MappedStatementAdaptor.buildMappedStatement(statementId, resultType, parameterType, sql);
            //设置select标签
            mappedStatement.setSqlCommandType("select");
            //将封装好的mappedStatement对象封装到Configuration中的map集合中
            configuration.getMappedStatementMap().put(statementId,mappedStatement);
        }
    }
}


















































