package vip.gameclub.lwlib.service.utils;

import org.bukkit.Bukkit;
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
public class BasePlayerUtil {
    /**
     * 获取在线玩家列表
     * @param
     * @return java.util.List<org.bukkit.entity.Player>
     * @author LW-MrWU
     * @date 2021/1/28 12:12
     */
    public static List<Player> getOnlinePlayerList(){
        List<Player> playerList = new ArrayList<>();
        Object[] objs = Bukkit.getOnlinePlayers().toArray();
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
    public static List<OfflinePlayer> getOfflinePlayerList(){
        List<OfflinePlayer> offlinePlayerList = new ArrayList<>();
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();

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
    public static String getID(Player player) {
        return player.getUniqueId().toString();
    }

    /**
     * 根据玩家ID获取玩家
     * @param playerID 玩家ID
     * @return org.bukkit.entity.Player
     * @author LW-MrWU
     * @date 2021/1/30 15:51
     */
    public static Player getPlayer(String playerID) {
        return Bukkit.getPlayer(UUID.fromString(playerID));
    }

    /**
     * 根据玩家名称获取玩家ID
     * @param name 玩家名称
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/30 15:53
     */
    public static String getID(String name) {
        return Bukkit.getOfflinePlayer(name).getUniqueId().toString();
    }

    /**
     * 根据玩家ID获取玩家名
     * @param playerID 1
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/30 15:53
     */
    public static String getName(String playerID) {
        return playerID == null ? null : Bukkit.getOfflinePlayer(UUID.fromString(playerID)).getName();
    }
}
