package com.gameclub.lwlib.test;

import com.gameclub.lwlib.llb.LwLibMainPlugin;
import com.gameclub.lwlib.service.basic.service.database.mysql.BaseMysqlService;
import com.gameclub.lwlib.utils.base.AbstractNoTransactionalService;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lw
 * @date 创建时间 2021/1/16 18:58
 * @description 测试demo
 */
public class TestDemo extends AbstractNoTransactionalService {

    @Test
    public void test(){
        log.info("测试demo");
        Map<Object,Object> map = new HashMap<Object,Object>();
        map.put(1,"三国");//值是字符串
        map.put("数组",new int[]{1,2,3});//值是数组
        map.put(null, null);//值是null
        map.put(map,map);//值是map自己
        map.put('A',2.8 );//值是浮点数

        Iterator<Map.Entry<Object,Object>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Object,Object> e = it.next();
            System.out.println(e.getKey()+","+e.getValue());
        }
    }

    @Test
    public void test2(){
        BaseMysqlService baseMysqlService = new BaseMysqlService(LwLibMainPlugin.getInstance());
        Map<String, Map<String, Integer>> map = new HashMap<>();

        Map<String, Integer> varcharMap = new HashMap<>();
        varcharMap.put("varchar", 32);
        map.put("name", varcharMap);

        map.put("class", varcharMap);

        baseMysqlService.createTable("test", map);
    }
}
