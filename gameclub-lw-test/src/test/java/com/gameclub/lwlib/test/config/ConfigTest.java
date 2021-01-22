package com.gameclub.lwlib.test.config;

import com.gameclub.lwlib.utils.base.AbstractNoTransactionalService;
import org.junit.Test;

/**
 * @author lw
 * @date 创建时间 2021/1/18 15:08
 * @description TODO
 */
public class ConfigTest extends AbstractNoTransactionalService {

    @Test
    public void TestConfig(){
        String path = System.getProperty("file.separator");
        log.info("path:"+path);
    }
}
