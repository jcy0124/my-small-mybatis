# Mybatis

## jdbc

### jdbc问题分析

* 数据库配置信息硬编码
    * 配置文件

* 频繁创建释放数据库连接
    * 连接池
* sql语句，参数，返回结果集获取均存在硬编码问题
    * 配置文件

* 需要手动封装返回结果集，较为繁琐
    * 反射，内省

## 手写持久层框架思路分析

### 框架使用端：引入自定义持久层框架的jar包

* 创建SqlMapConfig.xml的配置文件，数据库配置信息。存放mapper.xml全路径
* 创建mapper.xml配置文件：存放sql信息，参数类型，返回值类型



### 自定义持久层框架本身，本质就是对JDBC进行了封装

* 加载配置文件

    * 创建Resources类：负责加载配置文件，加载成字节输入流，存放到内存中
    * 定义方法：InputStream getResourceAsSteam(String path)

* 创建两个JavaBean(容器对象)

    * Configuration：全局配置类：存储sqlMapConfig.xml配置文件解析出来的内容
    * MappedStatement：映射配置类：存储mapper.xml配置文件解析出来的内容

* 解析配置文件，填充JavaBean(容器对象)

    * 创建SqlSessionFactoryBuilder类
    * 定义方法：SqlSessionFactory build(InputSteam)；
        * 解析配置文件（dom4j+xpath），封装Configuration
        * 创建SqlSessionFactory，并返回

* 创建SqlSessionFactory接口及DefaultSqlSessionFactory

    * 方法：SqlSession openSession(); 工厂模式（降低耦合，根据不同的需求生产不同的SqlSession）

* 创建SqlSession接口和DefaultSqlSession实现类

    * 方法：selectList(); 	 查询所有

      ​			selectOne();	查询单个

      ​			update();		 更新

      ​			delete();		   删除

* 创建Executor接口和实现类SimpleExecutor（具体jdbc的操作）

    * 方法：query(Configuration，MappedStatement，Object param(具体参数))；执行的就是底层的JDBC代码（数据库配置信息，sql配置信息） 

