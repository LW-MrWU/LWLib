package vip.gameclub.lwlib.service.utils;

import vip.gameclub.lwlib.service.plugin.BasePlugin;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lw
 * @date 创建时间 2021/1/24 16:03
 * @description 玩家公共服务
 */
public class BasePlayerService {
    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/19 13:59
     * @param [basePlugin]
     * @return
     */
    public BasePlayerService(BasePlugin basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 获取在线玩家列表
     * @author lw
     * @date 2021/1/24
     * @param []
     * @return java.util.List<org.bukkit.entity.Player>
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
     * @author lw
     * @date 2021/1/24
     * @param []
     * @return java.util.List<org.bukkit.OfflinePlayer>
     */
    public List<OfflinePlayer> getOfflinePlayerList(){
        List<OfflinePlayer> offlinePlayerList = new ArrayList<>();
        OfflinePlayer[] offlinePlayers = this.basePlugin.getServer().getOfflinePlayers();

        if(offlinePlayers != null && offlinePlayers.length > 0){
            offlinePlayerList = Arrays.asList(offlinePlayers);
        }

        return offlinePlayerList;
    }

}
