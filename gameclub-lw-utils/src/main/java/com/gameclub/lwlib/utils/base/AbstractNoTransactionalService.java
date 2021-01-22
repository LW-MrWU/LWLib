package com.gameclub.lwlib.utils.base;

import com.gameclub.lwlib.utils.utlis.dozer.DozerHelper;
import com.gameclub.lwlib.utils.utlis.jackson.JsonMapper;
import com.gameclub.lwlib.utils.utlis.jackson.JsonMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lw
 * @date 创建时间 2021/1/18 14:00
 * @description 提供给Service，没有事物 提供 <code>DozerHelper</code> dozer 服务 <code>org.slf4j.Logger</code> log 服务 <code>JsonMapper</code> json 服务
 */
public class AbstractNoTransactionalService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final JsonMapper jsonMapper = JsonMapperFactory.getJsonMapper();

    @Autowired
    protected DozerHelper dozerHelper;

    protected DozerHelper getDozerHelper() {
        return dozerHelper;
    }
}
