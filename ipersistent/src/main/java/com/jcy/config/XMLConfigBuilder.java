package com.jcy.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.jcy.io.Resources;
import com.jcy.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }

    /**
     *  使用 dom4j+path解析配置文件，封装Configuration对象
     * @param inputStream
     * @return
     */
    public Configuration parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);

        //获取根节点 <configuration>
        Element rootElement = document.getRootElement();

        //对应        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"></property>
        List<Element> list = rootElement.selectNodes("//property");

        Properties properties = new Properties();
        //获取每一个name和value
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        //创建数据源对象
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("driverClassName"));
        druidDataSource.setUrl(properties.getProperty("url"));
        druidDataSource.setUsername(properties.getProperty("username"));
        druidDataSource.setPassword(properties.getProperty("password"));

        //创建好的数据源对象封装到Configuration对象中
        configuration.setDataSource(druidDataSource);


        // -------------------解析映射配置文件----
        //1，获取映射配置文件路径  2，根据路径进行映射配置文件的加载解析  3，封装到MappedStatement
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");

            // 2，根据路径进行映射配置文件的加载解析
            InputStream resourceAsSteam = Resources.getResourceAsSteam(mapperPath);
            // XMLMapperBuilder：专门解析映射配置文件对象
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsSteam);
        }


        return configuration;
    }
}
