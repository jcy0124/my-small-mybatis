package com.jcy.pojo;


/**
 * 映射配置类：存放mapper.xml解析内容
 */
public class MappedStatement {

    // 唯一标识   statementId： namespace.id
    private String statementId;

    // 返回值类型
    private String resultType;

    // 参数值类型
    private String parameterType;

    // sql语句
    private String sql;

    // sqlCommandType： 判断当前是什么操作的一个属性
    private String sqlCommandType;

    public String getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(String sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
