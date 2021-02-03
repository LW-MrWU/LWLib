package vip.gameclub.lwlib.service.message;

import vip.gameclub.lwlib.service.plugin.BasePlugin;
import org.bukkit.command.CommandSender;
import vip.gameclub.lwlib.service.utils.BasePlayerUtil;
import vip.gameclub.lwlib.service.utils.BaseStringUtil;

/**
 * 基础消息服务
 * @author LW-MrWU
 * @date 创建时间 2021/1/18 17:54
 */
public class BaseMessageService {

    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 12:04
     */
    public BaseMessageService(BasePlugin basePlugin) {
        this.basePlugin = basePlugin;
    }

    /**
     * 根据语言发送消息
     * @param commandSender 发送对象
     * @param key 配置key
     * @param defualt 默认使用的
     * @param prms 需要替换的字符串
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:05
     */
    public void sendMessageByLanguage(CommandSender commandSender, String key, String defualt, String ...prms) {
        String language = basePlugin.getBaseLanguageService().getLanguage(key, defualt, prms);
        sendMessage(commandSender,language);
    }

    /**
     * 发送消息
     * @param commandSender 发送对象
     * @param message 消息内容
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:05
     */
    public void sendMessage(CommandSender commandSender,String message) {
        String remessage = BaseStringUtil.chatColorCodes(message);
        commandSender.sendMessage(remessage);
    }

    /**
     * 根据玩家ID和语言发送消息
     * @param playerId 玩家ID
     * @param key 配置key
     * @param defualt 默认使用的
     * @param prms 需要替换的字符串
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:05
     */
    public void sendMessageByLanguagePlayerId(String playerId, String key, String defualt, String ...prms) {
        String language = basePlugin.getBaseLanguageService().getLanguage(key, defualt, prms);
        sendMessage(BasePlayerUtil.getPlayer(playerId),language);
    }

    /**
     * 根据玩家ID发送消息
     * @param playerId 玩家ID
     * @param message 消息内容
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 12:05
     */
    public void sendMessageByPlayerId(String playerId, String message) {
        String remessage = BaseStringUtil.chatColorCodes(message);
        BasePlayerUtil.getPlayer(playerId).sendMessage(remessage);
    }
}
