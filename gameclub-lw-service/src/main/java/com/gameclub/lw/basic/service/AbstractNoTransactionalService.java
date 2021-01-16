package com.gameclub.lw.basic.service;

import com.gameclub.lw.utlis.dozer.DozerHelper;
import com.gameclub.lw.utlis.jackson.JsonMapper;
import com.gameclub.lw.utlis.jackson.JsonMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * creator by @author 2b 提供给Service，没有事物 提供 <code>DozerHelper</code> dozer 服务 <code>org.slf4j.Logger</code> log 服务
 * <code>JsonMapper</code> json 服务
 */
public abstract class AbstractNoTransactionalService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final JsonMapper jsonMapper = JsonMapperFactory.getJsonMapper();

    @Autowired
    protected DozerHelper dozerHelper;

    protected DozerHelper getDozerHelper() {
        return dozerHelper;
    }
}

