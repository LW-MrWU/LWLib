package com.gameclub.lw.biz.listenner;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements Listener {
    private List<String> playerNameList = new ArrayList<String>();

    /*功能一：刚进入服务器的玩家都记录到“小本本”playerNameList上，他们是没登录的玩家*/
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){ //玩家登录服务器就会调用这个方法
        //先判断这个玩家的名是不是记过了
        if(!playerNameList.contains(e.getPlayer().getName())){
            //玩家一登录就给他记上名, 代表他没登录
            playerNameList.add(e.getPlayer().getName());
        }
    }

    /*功能二：没登录的玩家不让移动*/
    @EventHandler //这个注解告诉Bukkit这个方法正在监听某个事件
    public void onPlayerMove(PlayerMoveEvent e){ //玩家移动时Bukkit就会调用这个方法
        if(playerNameList.contains(e.getPlayer().getName())){
            e.setCancelled(true); //判断玩家是不是没登录, 是则取消事件
        }
        System.out.println("PLAYER MOVE!");
    }

    /*功能三：右击空气登录（本质就是从playerNameList把他删了）*/
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){ //玩家交互时会调用这个方法(这个下面会解释)
        if(e.getAction()== Action.LEFT_CLICK_AIR){ //判断是不是右键空气
            playerNameList.remove(e.getPlayer().getName());
        }
    }
}
