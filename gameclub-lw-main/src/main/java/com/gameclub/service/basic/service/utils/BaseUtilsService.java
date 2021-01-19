package com.gameclub.service.basic.service.utils;

import com.gameclub.service.basic.service.plugin.BasePlugin;
import org.bukkit.ChatColor;

/**
 * @author lw
 * @date 创建时间 2021/1/19 13:58
 * @description 公共方法服务
 */
public class BaseUtilsService {
    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/19 13:59
     * @param [basePlugin]
     * @return
     */
    public BaseUtilsService(BasePlugin basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 变更颜色代码前缀§->&
     * @author lw
     * @date 2021/1/19 14:01
     * @param [msg]
     * @return java.lang.String
     */
    public String translateColorCodes(String msg){
        String newStr = ChatColor.translateAlternateColorCodes('&', msg);
        return newStr;
    }
}
