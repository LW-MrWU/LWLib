package vip.gameclub.lwlib.service.utils;

import org.bukkit.Bukkit;
import vip.gameclub.lwlib.service.plugin.BasePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 玩家公共服务
 * @author LW-MrWU
 * @date 创建时间 2021/1/24 16:03
 */
public class BasePlayerService {
    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 12:12
     */
    public BasePlayerService(BasePlugin basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 获取在线玩家列表
     * @param
     * @return java.util.List<org.bukkit.entity.Player>
     * @author LW-MrWU
     * @date 2021/1/28 12:12
     */
    public List<Player> getOnlinePlayerList(){
        List<Player> playerList = new ArrayList<>();
        Object[] objs = this.basePlugin.getServer().getOnlinePlayers().toArray();
        for (Object obj : objs){
            Player player = (Player)obj;
            playerList.add(player);
        }
        return playerList;
    }

    /**
     * 获取离线玩家列表
     * @param
     * @return java.util.List<org.bukkit.OfflinePlayer>
     * @author LW-MrWU
     * @date 2021/1/28 12:12
     */
    public List<OfflinePlayer> getOfflinePlayerList(){
        List<OfflinePlayer> offlinePlayerList = new ArrayList<>();
        OfflinePlayer[] offlinePlayers = this.basePlugin.getServer().getOfflinePlayers();

        if(offlinePlayers != null && offlinePlayers.length > 0){
            offlinePlayerList = Arrays.asList(offlinePlayers);
        }

        return offlinePlayerList;
    }

    /**
     * 获取玩家ID
     * @param player 玩家
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/30 15:49
     */
    public String getID(Player player) {
        return player.getUniqueId().toString();
    }

    /**
     * 根据玩家ID获取玩家
     * @param playerID 玩家ID
     * @return org.bukkit.entity.Player
     * @author LW-MrWU
     * @date 2021/1/30 15:51
     */
    public Player getPlayer(String playerID) {
        return this.basePlugin.getServer().getPlayer(UUID.fromString(playerID));
    }

    /**
     * 根据玩家名称获取玩家ID
     * @param name 玩家名称
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/30 15:53
     */
    public String getID(String name) {
        return this.basePlugin.getServer().getOfflinePlayer(name).getUniqueId().toString();
    }

    /**
     * 根据玩家ID获取玩家名
     * @param playerID 1
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/30 15:53
     */
    public String getName(String playerID) {
        return playerID == null ? null : this.basePlugin.getServer().getOfflinePlayer(UUID.fromString(playerID)).getName();
    }
}
