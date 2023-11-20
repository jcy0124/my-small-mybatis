package com.jcy.test;

import com.jcy.io.Resources;
import org.junit.Test;

import java.io.InputStream;

public class IpersistentTest {


    /**
     * 传统方式（不使用mapper代理）测试
     */
    @Test
    public void test1() throws Exception {
        // 1, 根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");


    }
}
