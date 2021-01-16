package com.gameclub.lw.basic.service.base;

import com.gameclub.lw.utlis.dozer.DozerHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * creator by @author 2b 基础的抽象servie实现 提供 <code>Transactional</code> 事务 <code>DozerHelper</code> dozer 服务
 * <code>org.slf4j.Logger</code> <code>JsonMapper</code> json 服务
 */
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractTransactionalService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected DozerHelper dozer;

    protected DozerHelper getDozer() {
        return dozer;
    }
}
