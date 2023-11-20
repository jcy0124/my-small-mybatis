package com.jcy.config;

import com.jcy.utils.ParameterMapping;

import java.util.List;

public class BoundSql {

    private String finalSql;

    private List<ParameterMapping> list;

    public BoundSql() {
    }

    public BoundSql(String finalSql, List<ParameterMapping> list) {
        this.finalSql = finalSql;
        this.list = list;
    }

    public String getFinalSql() {
        return finalSql;
    }

    public void setFinalSql(String finalSql) {
        this.finalSql = finalSql;
    }

    public List<ParameterMapping> getListParameterMapping() {
        return list;
    }

    public void setListParameterMapping(List<ParameterMapping> list) {
        this.list = list;
    }
}
