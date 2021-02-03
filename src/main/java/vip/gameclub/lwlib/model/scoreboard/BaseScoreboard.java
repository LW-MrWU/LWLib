package vip.gameclub.lwlib.model.scoreboard;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.service.plugin.BasePlugin;
import vip.gameclub.lwlib.service.utils.BaseStringUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 记分板父类
 * @author LW-MrWU
 * @date 创建时间 2021/2/1 11:54
 */
public abstract class BaseScoreboard<T extends BasePlugin> {
    protected T basePlugin;

    private Scoreboard scoreboard;
    private Objective objective;
    private String title;
    private Player player;
    private List<Map<String, List<String>>> moduleList;
    private boolean isShow;
    private DisplaySlot displaySlot;
    private int count;

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @param player 玩家
     * @param title 标题
     * @param displaySlot 显示方式
     * @return
     * @author LW-MrWU
     * @date 2021/2/1 16:20
     */
    public BaseScoreboard(T basePlugin, Player player, String title, DisplaySlot displaySlot, Integer count){
        this.basePlugin = basePlugin;
        this.player = player;
        this.title = title;
        this.displaySlot = displaySlot;
        this.count = count;

        this.isShow = false;
        this.moduleList = Lists.newArrayList();

        show();
    }

    /**
     * 注册一个scoreboard展示
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/2/1 16:21
     */
    private void show(){
        if(this.isShow){
            return;
        }

        if(this.player == null || !this.player.isOnline()){
            return;
        }

        this.scoreboard = basePlugin.getServer().getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(player.getName(), "dummy", BaseStringUtil.chatColorCodes(this.title));
        this.objective.setDisplaySlot(displaySlot);

        this.player.setScoreboard(scoreboard);
        this.isShow = true;
    }

    /**
     * 加载显示内容
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/2/1 16:22
     */
    public void reload(){
        this.objective.unregister();
        this.isShow = false;
        show();

        if(showCustom()){
            return;
        }

        int i = 0;
        for (Map<String, List<String>> module : this.moduleList){

            Iterator<Map.Entry<String, List<String>>> iterator = module.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, List<String>> entry = iterator.next();
                String key = entry.getKey();
                for (String str : entry.getValue()){
                    //判断长度是否超过40,自动换行
                    int length = str.length();
                    if(length > 40){
                        for(int n=0; n<=length/40; n++){
                            if(n == length/40){
                                getObjective().getScore(str.substring(n*39)).setScore(++i);
                                break;
                            }
                            getObjective().getScore(str.substring(n*39, n*39+39)).setScore(++i);
                        }
                    }else{
                        getObjective().getScore(str).setScore(++i);
                    }
                }
                getObjective().getScore(key).setScore(++i);
            }
        }

    }

    /**
     * 自定义显示内容
     * 返回true则覆盖默认显示方式
     * @param  1
     * @return boolean
     * @author LW-MrWU
     * @date 2021/2/1 16:23
     */
    protected abstract boolean showCustom();

    /**
     * 增加一个显示模块
     * @param moduleName 模块的名字
     * @param args 参数（展示用，多个参数默认换行展示）
     * @return boolean
     * @author LW-MrWU
     * @date 2021/2/1 16:24
     */
    public boolean addModule(String moduleName, String... args){
        if(this.player == null || !this.player.isOnline()){
            return false;
        }

        if(StringUtils.isEmpty(moduleName)){
            return false;
        }

        if(this.moduleList.size() >= this.count){
            this.basePlugin.getBaseMessageService().sendMessageByLanguage(this.player, BaseSysMsgEnum.SCOREBOARD_MODULE_COUNT_EOOR.name(), BaseSysMsgEnum.SCOREBOARD_MODULE_COUNT_EOOR.getValue(), String.valueOf(count));
            return false;
        }

        if(isContains(moduleName)){
            return false;
        }

        Map<String, List<String>> module = new HashMap<>();
        List<String> msgList = Lists.newArrayList();

        for (int i = args.length-1; i>=0; i--){
            String str = args[i].trim();
            msgList.add(str);
        }
        module.put(moduleName, msgList);
        this.moduleList.add(module);
        reload();
        return true;
    }

    /**
     * 删除一个模块
     * @param moduleName 模块名
     * @return void
     * @author LW-MrWU
     * @date 2021/2/1 16:25
     */
    public void delModule(String moduleName){
        for (Map<String, List<String>> module : this.moduleList) {
            for (String key : module.keySet()) {
                if(key.equalsIgnoreCase(moduleName)){
                    moduleList.remove(module);
                    reload();
                    return;
                }
            }
        }
    }

    /**
     * 判断模块是否已存在
     * @param moduleName 模块名
     * @return boolean
     * @author LW-MrWU
     * @date 2021/2/1 17:30
     */
    public boolean isContains(String moduleName){
        for (Map<String, List<String>> module : this.moduleList){
            for (String key : module.keySet()){
                if(key.equalsIgnoreCase(moduleName)){
                    return true;
                }
            }
        }
        return false;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public Player getPlayer() {
        return this.player;
    }

    public String getTitle() {
        return this.title;
    }

    public List<Map<String, List<String>>> getModuleList() {
        return this.moduleList;
    }
}
