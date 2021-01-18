package com.gameclub.service.basic.service.command;

import com.gameclub.model.command.BaseCommand;
import com.gameclub.model.command.BaseCommandSenderType;
import com.gameclub.model.language.BaseLanguageEnum;
import com.gameclub.model.permission.BasePermissionEnum;
import com.gameclub.service.basic.service.plugin.BasePlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lw
 * @date 创建时间 2021/1/18 18:03
 * @description 基础命令服务
 */
public abstract class BaseCommandeSercvice implements CommandExecutor {

    protected BasePlugin basePlugin;

    /**
     * 需要执行的命令
     */
    private String exeCommand;

    /**
     * 所有的子命令
     */
    private Map<String, BaseCommand> commands = new HashMap<String, BaseCommand>();

    /**
     * 默认执行命令
     */
    private BaseCommand defualtCommand = null;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/18 18:13
     * @param [execCommand 命令名字, plugin 插件]
     * @return
     */
    public BaseCommandeSercvice(String execCommand,BasePlugin plugin) {
        this.basePlugin = plugin;
        this.exeCommand = execCommand;
    }

    /**
     * 命令执行
     * @author lw
     * @date 2021/1/18 18:14
     * @param [commandSender 发送命令的对象, command 被执行的指令, label 被执行指令的别名, args 该指令的自变量数组]
     * @return boolean 如果返回值是true，你不会看到什么明显的事情发生，但如果返回值是false，则会返回你的plugin.yml里的'usage:property'然后发送给命令使用者.
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String commandName = getCommandName(args);
        String[] commandArgs = getCommandArgs(args);

        BaseCommand commandBean = getCommand(commandName);

         //是否找到了命令，找不到就用默认命令
        if(commandBean == null) {
            if(defualtCommand != null) {
                commandBean = defualtCommand;
            }else {
                return notFoundCommand(commandSender, command, commandArgs);
            }
        }

        //验证是否是属于发送命令的分组
        boolean isCommandSenderType = checkCommandSenderType(commandSender, commandBean);
        if(!isCommandSenderType) {
            BaseLanguageEnum languageEnum = BaseLanguageEnum.COMMAND_NOCOMMANDSENDERTYPEMESSAGE;
            this.basePlugin.getBaseMessageService().sendMessageByLanguage(commandSender, languageEnum.name(), languageEnum.getValue());
            return true;
        }

        //判断权限是否符合
        boolean isPermissions = checkPermission(commandSender,commandBean);
        if(!isPermissions) {
            BaseLanguageEnum languageEnum = BaseLanguageEnum.COMMAND_PERMISSIONDENIEDMESSAGE;
            this.basePlugin.getBaseMessageService().sendMessageByLanguage(commandSender, languageEnum.name(), languageEnum.getValue(),command.getName());
            return true;
        }

        //判断最小参数是否符合要求
        if(commandBean.getMinArgs() < commandArgs.length) {
            BaseLanguageEnum languageEnum = BaseLanguageEnum.COMMAND_ARGS_ERROR;
            this.basePlugin.getBaseMessageService().sendMessageByLanguage(commandSender, languageEnum.name(), languageEnum.getValue(),command.getName());
            return true;
        }

        BaseCommandSenderType senderCommandSenderType = getCommandSenderType(commandSender);

        boolean flag = execCommand(commandSender,commandBean,commandArgs,senderCommandSenderType);
        return flag;
    }

    /**
     * 返回子参数的名字
     * @author lw
     * @date 2021/1/18 18:17
     * @param [args 参数]
     * @return java.lang.String
     */
    public String getCommandName(String[] args) {
        if(args.length<1) {
            return "";
        }
        return args[0];
    }

    /**
     * 提供参数列表
     * @author lw
     * @date 2021/1/18 18:18
     * @param [args 原始参数列表]
     * @return java.lang.String[]
     */
    public String[] getCommandArgs(String[] args) {
        List<String> newArgs = new ArrayList<String>();
        for(int i=1;i<args.length;i++) {
            newArgs.add(args[i]);
        }

        String[] result = new String[newArgs.size()];
        result = newArgs.toArray(result);
        return result;
    }

    /**
     * 提供注册的命令
     * @author lw
     * @date 2021/1/18 18:19
     * @param [commandName 命令名字]
     * @return T
     */
    @SuppressWarnings("unchecked")
    public <T extends BaseCommand> T getCommand(String commandName){
        T tempresult = null;
        BaseCommand tempcommand = commands.get(commandName);
        if(tempcommand!=null) {
            tempresult = (T)tempcommand;
        }
        return tempresult;
    }

    /**
     * TODO
     * @author lw
     * @date 2021/1/18 18:26
     * @param sender 发送命令的对象
     * @param command 被执行的指令
     * @param args 该指令的自变量数组
     * @return 如果返回值是true，你不会看到什么明显的事情发生，但如果返回值是false，则会返回你的plugin.yml里的'usage:property'然后发送给命令使用者.
     */
    public abstract boolean notFoundCommand(CommandSender sender,Command command,String[] args);

    /**
     * 判断命令是否是命令发送方可使用的
     * @author lw
     * @date 2021/1/18 18:27
     * @param [sender 命令发送方, commandBean 命令实体]
     * @return boolean
     */
    public boolean checkCommandSenderType(CommandSender sender,BaseCommand commandBean) {
        boolean isCommandSenderType = false;
        BaseCommandSenderType commandSenderType = commandBean.getCommandSenderType();

        //如果是任意
        if(commandSenderType.equals(BaseCommandSenderType.ARBITRARLIY)) {
            isCommandSenderType = true;
        }else {
            BaseCommandSenderType senderCommandSenderType = getCommandSenderType(sender);
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
     * @date 2021/1/18 18:30
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
     * 检查是否有权限
     * @author lw
     * @date 2021/1/18 18:43
     * @param [sender 命令发送者, commandBean 命令]
     * @return boolean
     */
    public boolean checkPermission(CommandSender sender,BaseCommand commandBean) {
        boolean isPermissions = false;
        List<String> commandPermissions = commandBean.getHasPermissions();
        if(commandPermissions==null||commandPermissions.size()==0) {
            isPermissions = true;
        }else {
            //如果拥有管理员权限
            if(sender.hasPermission(BasePermissionEnum.ADMIN.getValue())) {
                isPermissions = true;
            }else {
                //如果拥有指定权限
                for(String permission : commandPermissions) {
                    if(sender.hasPermission(permission)) {
                        isPermissions = true;
                        break;
                    }
                }
            }
        }
        return isPermissions;
    }

    /**
     * 执行命令
     * @param sender 发送命令的对象
     * @param command 被执行的指令
     * @param args 该指令的自变量数组
     * @param commandSenderType 命令发送者
     * @return 如果返回值是true，你不会看到什么明显的事情发生，但如果返回值是false，则会返回你的plugin.yml里的'usage:property'然后发送给命令使用者.
     */
    public abstract boolean execCommand(CommandSender sender,BaseCommand command,String[] args,BaseCommandSenderType commandSenderType);

    /**
     * 注册命令
     * @author lw
     * @date 2021/1/18 18:21
     * @param [commandStr 子命令名称, commandBean 命令的实体]
     * @return void
     */
    public <T extends BaseCommand> void registerCommand(String commandStr,T commandBean) {
        commands.put(commandStr, commandBean);
        if(commandBean.isDefault()) {
            defualtCommand = commandBean;
        }
    }

    /**
     * 将命令注册进来
     * @author lw
     * @date 2021/1/18 18:21
     * @param [command]
     * @return void
     */
    public <T extends BaseCommand> void registerCommand(T command) {
        registerCommand(command.getCommand(),command);
    }

    public String getExeCommand() {
        return exeCommand;
    }

    public void setExeCommand(String exeCommand) {
        this.exeCommand = exeCommand;
    }

    public Map<String, BaseCommand> getCommands() {
        return commands;
    }

    public void setCommands(Map<String, BaseCommand> commands) {
        this.commands = commands;
    }

    public BaseCommand getDefualtCommand() {
        return defualtCommand;
    }

    public void setDefualtCommand(BaseCommand defualtCommand) {
        this.defualtCommand = defualtCommand;
    }
}
