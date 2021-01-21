package com.gameclub.model.command;

import com.gameclub.model.language.BaseLanguageEnum;
import com.gameclub.service.basic.service.plugin.BasePlugin;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;

/**
 * @author lw
 * @date 创建时间 2021/1/20 16:41
 * @description 命令父类
 */
public abstract class BaseCommand implements TabExecutor {
    protected BasePlugin basePlugin;

    /**
     * 命令名
     */
    private String commandName;

    /**
     * 命令别名
     */
    private String commandLabel;

    /**
     * 子命令
     */
    private Map<String, BaseCommand> subCommands = new HashMap<String, BaseCommand>();

    /**
     * 子命令(别名对应)
     */
    private Map<String, BaseCommand> subCommandAliases = new HashMap<String, BaseCommand>();

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/20 18:30
     * @param [commandName 名称]
     * @return
     */
    public BaseCommand(String commandName){
        this.commandName = commandName;
    }

    /**
     * 主命令构造函数
     * @author lw
     * @date 2021/1/20 18:30
     * @param [commandName, basePlugin]
     * @return
     */
    public BaseCommand(String commandName, BasePlugin basePlugin){
        this.commandName = commandName;
        this.basePlugin = basePlugin;
    }

    /**
     * 命令执行
     * @author lw
     * @date 2021/1/21 13:46
     * @param [commandSender 发送命令的对象, command 被执行的指令, label 被执行指令的别名, args 该指令的自变量数组]
     * @return boolean
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        //判断权限节点
        if (getPermissionNode() != null && !commandSender.hasPermission(getPermissionNode())) {
            BaseLanguageEnum languageEnum = BaseLanguageEnum.COMMAND_PERMISSIONDENIEDMESSAGE;
            this.basePlugin.getBaseMessageService().sendMessageByLanguage(commandSender, languageEnum.name(), languageEnum.getValue(), command.getName());
            return true;
        }

        //判断命令对象
        boolean isCommandSenderType = checkCommandSenderType(commandSender, commandSenderType());
        if(!isCommandSenderType){
            BaseLanguageEnum languageEnum = BaseLanguageEnum.COMMAND_NOCOMMANDSENDERTYPEMESSAGE;
            this.basePlugin.getBaseMessageService().sendMessageByLanguage(commandSender, languageEnum.name(), languageEnum.getValue());
            return true;
        }

        Integer length = args.length;
        if(length == null || length < 0){
            return false;
        }

        if(length == 0){
            return onCommand(commandSender, args);
        }

        String arg = args[0].toLowerCase();
        //检测参数有效性
        if (length > 0 && this.subCommands.get(arg) != null) {
            BaseCommand sub = this.subCommands.get(arg);
            return sub.onCommand(commandSender, command, label, Arrays.<String>copyOfRange(args, 1, args.length));
        }

        //检测（别名）参数有效性
        if (length > 0 && this.subCommandAliases.get(arg) != null) {
            BaseCommand sub = this.subCommandAliases.get(arg);
            return sub.onCommand(commandSender, command, label, Arrays.<String>copyOfRange(args, 1, args.length));
        }

        return onCommand(commandSender, args);
    }

    /**
     * 命令补全
     * @author lw
     * @date 2021/1/21 13:48
     * @param [commandSender 发送命令的对象, command 被执行的指令, label 被执行指令的别名, args 该指令的自变量数组]
     * @return java.util.List<java.lang.String>
     */
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] args) {

        //判断权限节点
        if (getPermissionNode() != null && !commandSender.hasPermission(getPermissionNode())) {
            return null;
        }

        Integer length = args.length;
        if(length == null || length < 1){
            return null;
        }
        String arg = args[0].toLowerCase();

        //检测参数有效性
        if (length > 0 && this.subCommands.get(arg) != null) {
            BaseCommand sub = this.subCommands.get(arg);
            return sub.onTabComplete(commandSender, command, label, Arrays.<String>copyOfRange(args, 1, args.length));
        }

        //检测（别名）参数有效性
        if (length > 0 && this.subCommandAliases.get(arg) != null) {
            BaseCommand sub = this.subCommandAliases.get(arg);
            return sub.onTabComplete(commandSender, command, label, Arrays.<String>copyOfRange(args, 1, args.length));
        }

        //获取自定义tab列表
        List<String> result = onTabComplete(commandSender, args);
        //默认tab列表
        if (result == null && length == 1) {
            List<String> subCommandsKeyList = getSubCommandsKeyList();
            List<String> subCommandAliasesKeyList = getSubCommandAliasesKeyList();
            result = new ArrayList<>();
            result.addAll(subCommandsKeyList);
            result.addAll(subCommandAliasesKeyList);
        }

        return result;
    }

    /**
     * 自定义命令执行
     * 已包含判断权限、命令执行对象、子命令、别名
     * @author lw
     * @date 2021/1/21 15:16
     * @param [commandSender, args]
     * @return boolean
     */
    public abstract boolean onCommand(CommandSender commandSender, String[] args);

    /**
     * 自定义命令补全 返回null则默认补全该命令subCommands、subCommandAliases
     * @author lw
     * @date 2021/1/21 13:49
     * @param [sender, args]
     * @return java.util.List<java.lang.String>
     */
    public abstract List<String> onTabComplete(CommandSender commandSender, String[] args);

    /**
     * 自定义权限节点
     * @author lw
     * @date 2021/1/21 14:53
     * @param []
     * @return java.lang.String
     */
    public abstract String getPermissionNode();

    public abstract BaseCommandSenderType commandSenderType();

    /**
     * 获取所有子命令list列表
     * @author lw
     * @date 2021/1/20 13:57
     * @param []
     * @return java.util.List<java.lang.String>
     */
    public List<String> getSubCommandsKeyList(){
        List<String> mapKeyList = new ArrayList<>(subCommands.keySet());
        return mapKeyList;
    }

    /**
     * 获取所有子命令(别名)list列表
     * @author lw
     * @date 2021/1/20 13:57
     * @param []
     * @return java.util.List<java.lang.String>
     */
    public List<String> getSubCommandAliasesKeyList(){
        List<String> mapKeyList = new ArrayList<>(subCommandAliases.keySet());
        return mapKeyList;
    }

    /**
     * 判断命令是否是命令发送方可使用的
     * @author lw
     * @date 2021/1/21 14:04
     * @param [sender 命令发送方, baseCommandSenderType 命令可用对象]
     * @return boolean
     */
    public boolean checkCommandSenderType(CommandSender sender, BaseCommandSenderType commandSenderType) {
        if(commandSenderType == null){
            return true;
        }

        boolean isCommandSenderType = false;

        BaseCommandSenderType senderCommandSenderType = getCommandSenderType(sender);

        //如果是任意
        if(commandSenderType.equals(BaseCommandSenderType.ARBITRARLIY)) {
            isCommandSenderType = true;
        }else {
            //命令预设类型是否与命令发送方一致
            if(commandSenderType.equals(senderCommandSenderType)) {
                isCommandSenderType = true;
            }
        }
        return isCommandSenderType;
    }

    /**
     * 辨别命令发送方类型
     * @author lw
     * @date 2021/1/21 14:05
     * @param [sender]
     * @return com.gameclub.model.command.BaseCommandSenderType
     */
    public BaseCommandSenderType getCommandSenderType(CommandSender sender) {
        BaseCommandSenderType senderCommandSenderType = BaseCommandSenderType.PLAYER;
        //获取命令发送方类型
        if(sender instanceof ConsoleCommandSender) {
            senderCommandSenderType = BaseCommandSenderType.CONSOLE;
        }
        return senderCommandSenderType;
    }

    /**
     * 添加子命令
     * @author lw
     * @date 2021/1/20 17:09
     * @param [commands]
     * @return void
     */
    public void addSubCommands(BaseCommand... commands) {
        for (BaseCommand baseCommand : commands){
            String commandName = baseCommand.commandName;
            if(StringUtils.isNotEmpty(commandName)){
                this.subCommands.put(commandName, baseCommand);
            }

            String commandLabel = baseCommand.getCommandLabel();
            if(StringUtils.isNotEmpty(commandLabel)){
                this.subCommandAliases.put(commandLabel, baseCommand);
            }
        }
    }

    public Map<String, BaseCommand> getSubCommands() {
        return subCommands;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandLabel() {
        return commandLabel;
    }

    /**
     * 设置命令别名
     * @author lw
     * @date 2021/1/21 13:56
     * @param [commandLabel]
     * @return void
     */
    public void setCommandLabel(String commandLabel) {
        this.commandLabel = commandLabel;
    }
}
