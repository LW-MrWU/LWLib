package vip.gameclub.lwlib.service.scoreboard;

import org.bukkit.entity.Player;
import vip.gameclub.lwlib.model.scoreboard.BaseScoreboard;
import vip.gameclub.lwlib.service.plugin.BasePlugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 基础计分板服务
 * @author LW-MrWU
 * @date 创建时间 2021/2/3 14:56
 */
public class BaseScoreboardService<T extends BaseScoreboard> {
    protected BasePlugin basePlugin;

    //记分板数据
    private Map<String, BaseScoreboard> scoreboardData;

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/2/3 15:04
     */
    public BaseScoreboardService(BasePlugin basePlugin) {
        this.basePlugin = basePlugin;
    }

    /**
     * 注册记分板
     * @param  1
     * @return java.util.Map<org.bukkit.entity.Player,vip.gameclub.lwlib.model.scoreboard.BaseScoreboard>
     * @author LW-MrWU
     * @date 2021/2/2 22:52
     */
    public Map<String, BaseScoreboard> addScoreboard(T scoreboard) {
        if(scoreboardData == null){
            scoreboardData = new HashMap<>();
        }
        scoreboardData.put(scoreboard.getPlayer()+scoreboard.getClass().getName(), scoreboard);
        return scoreboardData;
    }

    /**
     *
     * @param player 玩家
     * @param cls 计分板子类
     * @return T
     * @author LW-MrWU
     * @date 2021/2/3 15:16
     */
    public T getScoreBoard(Player player, Class<T> cls){
        if(scoreboardData == null){
            scoreboardData = new HashMap<>();
        }
        return (T) scoreboardData.get(player+cls.getName());
    }
}
