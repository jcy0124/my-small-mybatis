package com.jcy.adaptor;

import com.jcy.pojo.MappedStatement;

public class MappedStatementAdaptor {

    public static MappedStatement buildMappedStatement(String statementId, String resultType, String parameterType, String sql){
        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.setStatementId(statementId);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParameterType(parameterType);
        mappedStatement.setSql(sql);
        return mappedStatement;
    }

}
