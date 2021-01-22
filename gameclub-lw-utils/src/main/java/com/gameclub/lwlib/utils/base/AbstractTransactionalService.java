package com.gameclub.lwlib.utils.base;

import com.gameclub.lwlib.utils.utlis.dozer.DozerHelper;
import com.gameclub.lwlib.utils.utlis.jackson.JsonMapper;
import com.gameclub.lwlib.utils.utlis.jackson.JsonMapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lw
 * @date 创建时间 2021/1/18 14:02
 * @description 基础的抽象servie实现 提供 <code>Transactional</code> 事务 <code>DozerHelper</code> dozer 服务 <code>org.slf4j.Logger</code> <code>JsonMapper</code> json 服务
 */
public class AbstractTransactionalService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final JsonMapper jsonMapper = JsonMapperFactory.getJsonMapper();

    @Autowired
    protected DozerHelper dozer;

    protected DozerHelper getDozer() {
        return dozer;
    }
}
