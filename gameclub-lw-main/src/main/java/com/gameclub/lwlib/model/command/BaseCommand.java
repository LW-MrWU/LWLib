package com.gameclub.lwlib.model.command;

import com.gameclub.lwlib.llb.LwLibMainPlugin;
import com.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import com.gameclub.lwlib.service.basic.service.plugin.BasePlugin;
import com.gameclub.lwlib.model.enumModel.BaseCommandSenderType;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author lw
 * @date 创建时间 2021/1/20 16:41
 * @description 命令父类
 */
public abstract class BaseCommand implements TabExecutor {
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
        this(commandName, null);
    }

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/20 18:30
     * @param [commandName, basePlugin]
     * @return
     */
    public BaseCommand(String commandName, String commandLabel){
        this.commandName = commandName;
        this.commandLabel = commandLabel;
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
            BasePlugin basePlugin = getBasePlugin();
            if(getBasePlugin() == null){
                LwLibMainPlugin.getInstance().getBaseMessageService().sendMessageByLanguage(commandSender, BaseSysMsgEnum.COMMAND_NO_PERMISSION.name(), BaseSysMsgEnum.COMMAND_NO_PERMISSION.getValue());
            }else{
                basePlugin.getBaseMessageService().sendMessageByLanguage(commandSender, BaseSysMsgEnum.COMMAND_NO_PERMISSION.name(), BaseSysMsgEnum.COMMAND_NO_PERMISSION.getValue());
            }
            return true;
        }

        //判断命令对象
        if(getCommandSenderType() != null && !checkCommandSenderType(commandSender, getCommandSenderType())){
            BasePlugin basePlugin = getBasePlugin();
            if(getBasePlugin() == null){
                LwLibMainPlugin.getInstance().getBaseMessageService().sendMessageByLanguage(commandSender, BaseSysMsgEnum.COMMAND_SENDERTYPE_ERROR.name(), BaseSysMsgEnum.COMMAND_SENDERTYPE_ERROR.getValue());
            }else{
                basePlugin.getBaseMessageService().sendMessageByLanguage(commandSender, BaseSysMsgEnum.COMMAND_SENDERTYPE_ERROR.name(), BaseSysMsgEnum.COMMAND_SENDERTYPE_ERROR.getValue());
            }
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
            List<String> subCommandsKeyList = getPermissionSubCommandsKeyList(commandSender);
            List<String> subCommandAliasesKeyList = getPermissionSubCommandAliasesKeyList(commandSender);
            result = new ArrayList<>();
            if(subCommandsKeyList != null && subCommandsKeyList.size() > 0){
                result.addAll(subCommandsKeyList);
            }
            if(subCommandAliasesKeyList != null && subCommandAliasesKeyList.size() > 0){
                result.addAll(subCommandAliasesKeyList);
            }
            //若无输出则输出在线玩家列表
            if(result == null || result.size() <= 0){
                List<String> userNames = new ArrayList<>();
                List<Player> playerList = LwLibMainPlugin.getInstance().getBasePlayerService().getOnlinePlayerList();
                playerList.forEach(player -> {
                    userNames.add(player.getName());
                });
                return userNames;
            }
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
     * 获取主函数对象
     * @author lw
     * @date 2021/1/24
     * @param []
     * @return java.lang.String
     */
    public abstract BasePlugin getBasePlugin();

    /**
     * 自定义权限节点
     * @author lw
     * @date 2021/1/21 14:53
     * @param []
     * @return java.lang.String
     */
    public abstract String getPermissionNode();

    /**
     * 返回命令可用对象 null为ARBITRARLIY任意
     * @author lw
     * @date 2021/1/21 18:23
     * @param []
     * @return com.gameclub.model.command.BaseCommandSenderType
     */
    public abstract BaseCommandSenderType getCommandSenderType();

    /**
     * 自定义帮助说明
     * @author lw
     * @date 2021/1/24
     * @param []
     * @return java.lang.String
     */
    public abstract String getUsageHelp();

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
     * 获取所有有权限的子命令list列表
     * @author lw
     * @date 2021/1/21 17:17
     * @param [commandSender]
     * @return java.util.List<java.lang.String>
     */
    public List<String> getPermissionSubCommandsKeyList(CommandSender commandSender){
        List<String> list = new ArrayList<>();
        for (BaseCommand command : subCommands.values()){
            if(checkPermission(commandSender, command)){
                list.add(command.getCommandName());
            }
        }
        return list;
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
     * 获取所有有权限的子命令(别名)list列表
     * @author lw
     * @date 2021/1/21 17:17
     * @param [commandSender]
     * @return java.util.List<java.lang.String>
     */
    public List<String> getPermissionSubCommandAliasesKeyList(CommandSender commandSender){
        List<String> list = new ArrayList<>();
        for (BaseCommand command : subCommandAliases.values()){
            if(checkPermission(commandSender, command)){
                list.add(command.getCommandLabel());
            }
        }
        return list;
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

        BaseCommandSenderType senderCommandSenderType = getSenderCommandSenderType(sender);

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
    public BaseCommandSenderType getSenderCommandSenderType(CommandSender sender) {
        BaseCommandSenderType senderCommandSenderType = BaseCommandSenderType.PLAYER;
        //获取命令发送方类型
        if(sender instanceof ConsoleCommandSender) {
            senderCommandSenderType = BaseCommandSenderType.CONSOLE;
        }
        return senderCommandSenderType;
    }

    /**
     * 权限检查
     * @author lw
     * @date 2021/1/21 16:54
     * @param [sender, baseCommand]
     * @return boolean
     */
    public boolean checkPermission(CommandSender sender, BaseCommand baseCommand) {
        String permission = baseCommand.getPermissionNode();
        if(StringUtils.isEmpty(permission) || sender.hasPermission(permission)){
            return true;
        }
        return false;
    }

    /**
     * 帮助命令(主命令下所有子命令的帮助提示)
     * @author lw
     * @date 2021/1/21 18:10
     * @param [sender, mainCommand]
     * @return void
     */
    public <T extends BaseCommand> void showAllHelp(CommandSender sender, T mainCommand, BasePlugin basePlugin){
        List<String> helps = new ArrayList<String>();

        Map<String, BaseCommand> cmds = mainCommand.getSubCommands();
        Set<String> keys = cmds.keySet();
        for(String key : keys) {
            BaseCommand baseCommand = cmds.get(key);
            if(this.checkPermission(sender, baseCommand) && mainCommand.checkCommandSenderType(sender, baseCommand.getCommandSenderType())) {
                String uesAge = baseCommand.getUsageHelp();
                if(!helps.contains(uesAge)) {
                    helps.add(uesAge);
                }
            }
        }

        for(String help : helps) {
            if(StringUtils.isEmpty(help)){
                continue;
            }
            basePlugin.getBaseMessageService().sendMessage(sender, help);
        }
    }

    /**
     * 帮助命令(当前命令下所有子命令的帮助提示)
     * @author lw
     * @date 2021/1/21 17:01
     * @param [sender]
     * @return void
     */
    public <T extends BasePlugin> void showHelp(CommandSender sender, T mainPlugin) {
        List<String> helps = new ArrayList<String>();

        Map<String, BaseCommand> cmds = this.getSubCommands();
        Set<String> keys = cmds.keySet();
        for(String key : keys) {
            BaseCommand baseCommand = cmds.get(key);
            if(this.checkPermission(sender, baseCommand) && this.checkCommandSenderType(sender, baseCommand.getCommandSenderType())) {
                String uesAge = baseCommand.getUsageHelp();
                if(!helps.contains(uesAge)) {
                    helps.add(uesAge);
                }
            }
        }

        for(String help : helps) {
            if(StringUtils.isEmpty(help)){
                continue;
            }
            mainPlugin.getBaseMessageService().sendMessage(sender, help);
        }
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