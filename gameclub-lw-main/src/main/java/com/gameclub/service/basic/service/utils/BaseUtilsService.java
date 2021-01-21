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
    public String chatColorCodes(String msg){
        String newStr = ChatColor.translateAlternateColorCodes('&', msg);
        return newStr;
    }

    /**
     * 字符替换 {i}替换
     * @author lw
     * @date 2021/1/18 17:32
     * @param [str 需要替换的字符串, prms 需要填充的字符串]
     * @return java.lang.String
     */
    public String substitutionPrms(String str, String... prms) {
        String tempString = str;
        if (tempString != null) {
            for (int i = 0; i < prms.length; i++) {
                String holder = "{" + i + "}";
                tempString = tempString.replace(holder, String.valueOf(prms[i]));
            }
        }
        String reTempString = this.basePlugin.getBaseUtilsService().chatColorCodes(tempString);
        return reTempString;
    }

}
