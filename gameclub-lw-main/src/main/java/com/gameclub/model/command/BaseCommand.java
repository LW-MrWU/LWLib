package com.gameclub.model.command;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lw
 * @date 创建时间 2021/1/18 18:05
 * @description 命令行父类
 */
public abstract class BaseCommand {
    /**
     * 所有人权限的构造
     * @author lw
     * @date 2021/1/18 18:05
     * @param [command 命令, baseCommandSenderType 接收命令相应的人群, minArgs最小支持的参数列表]
     * @return
     */
    public BaseCommand(String command,BaseCommandSenderType baseCommandSenderType,int minArgs) {
        this(command,baseCommandSenderType,minArgs,null);
    }

    /**
     * 所有人权限的构造
     * @author lw
     * @date 2021/1/18 18:06
     * @param [command 命令, baseCommandSenderType 接收命令相应的人群, minArgs 最小支持的参数列表, hasPermissions 所需要的权限]
     * @return
     */
    public BaseCommand(String command, BaseCommandSenderType baseCommandSenderType, int minArgs, List<String> hasPermissions) {
        this.command = command;
        this.commandSenderType = baseCommandSenderType;
        this.minArgs = minArgs;
        if(hasPermissions!=null) {
            this.hasPermissions = hasPermissions;
        }
    }

    /**
     * 执行该命令
     * @author lw
     * @date 2021/1/18 18:08
     * @param [commandSender 命令发送人, args 命令参数, commandSenderType 发送人类型]
     * @return void
     */
    public abstract void run(CommandSender commandSender, String[] args, BaseCommandSenderType commandSenderType);

    /**
     * 命令名称
     */
    private String command;

    /**
     * 最小要使用的参数个数
     */
    private int minArgs = 0;

    /**
     * 执行命令需要的权限
     */
    private List<String> hasPermissions = new ArrayList<String>();

    /**
     * 命令帮助
     */
    private String usage;

    /**
     * 命令别名
     */
    private String lable;

    /**
     * 是不是默认执行的命令
     */
    private boolean isDefault = false;

    /**
     * 允许谁来执行命令
     */
    private BaseCommandSenderType commandSenderType = BaseCommandSenderType.ARBITRARLIY;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public List<String> getHasPermissions() {
        return hasPermissions;
    }

    public void setHasPermissions(List<String> hasPermissions) {
        this.hasPermissions = hasPermissions;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public BaseCommandSenderType getCommandSenderType() {
        return commandSenderType;
    }

    public void setCommandSenderType(BaseCommandSenderType commandSenderType) {
        this.commandSenderType = commandSenderType;
    }
}
