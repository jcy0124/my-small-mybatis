package com.jcy.utils;

import java.util.ArrayList;
import java.util.List;


public  class ParameterMappingTokenHandler  implements TokenHandler {

    private List<ParameterMapping> parameterMappings = new ArrayList<>();


    /**
     * content 是参数名称 #{id} #{username}
     */
    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings) {
        this.parameterMappings = parameterMappings;
    }
}