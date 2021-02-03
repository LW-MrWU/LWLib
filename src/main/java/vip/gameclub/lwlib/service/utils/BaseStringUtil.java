package vip.gameclub.lwlib.service.utils;

import org.bukkit.ChatColor;

/**
 * 基础字符串公共服务
 * @author LW-MrWU
 * @date 创建时间 2021/1/19 13:58
 */
public class BaseStringUtil {

    /**
     * 变更颜色代码前缀§->&
     * @param msg 消息
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 12:13
     */
    public static String chatColorCodes(String msg){
        String newStr = ChatColor.translateAlternateColorCodes('&', msg);
        return newStr;
    }

    /**
     * 字符替换 {i}替换
     * @param str 需要替换的字符串
     * @param prms 需要填充的字符串
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 12:13
     */
    public static String substitutionPrms(String str, String... prms) {
        String tempString = str;
        if (tempString != null) {
            for (int i = 0; i < prms.length; i++) {
                String holder = "{" + i + "}";
                tempString = tempString.replace(holder, String.valueOf(prms[i]));
            }
        }
        String reTempString = chatColorCodes(tempString);
        return reTempString;
    }

}
