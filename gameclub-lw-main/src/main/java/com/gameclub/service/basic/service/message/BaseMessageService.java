package com.gameclub.service.basic.service.message;

import com.gameclub.service.basic.service.plugin.BasePlugin;
import org.bukkit.command.CommandSender;

/**
 * @author lw
 * @date 创建时间 2021/1/18 17:54
 * @description TODO
 */
public class BaseMessageService {

    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/18 17:55
     * @param [basePlugin]
     * @return
     */
    public BaseMessageService(BasePlugin basePlugin) {
        this.basePlugin = basePlugin;
    }

    /**
     * 根据语言发送消息
     * @author lw
     * @date 2021/1/18 17:55
     * @param [commandSender 接收消息的人, key 配置key, defualt 默认使用的, prms 需要替换的字符串]
     * @return void
     */
    public void sendMessageByLanguage(CommandSender commandSender, String key, String defualt, String ...prms) {
        String language = basePlugin.getBaseLanguageService().getLanguage(key, defualt, prms);
        sendMessage(commandSender,language);
    }

    /**
     * 发送消息
     * @author lw
     * @date 2021/1/18 17:55
     * @param [commandSender 接收消息的人, message 消息内容]
     * @return void
     */
    public void sendMessage(CommandSender commandSender,String message) {
        String remessage = this.basePlugin.getBaseUtilsService().chatColorCodes(message);
        commandSender.sendMessage(remessage);
    }
}
