package com.gameclub.lw;

import com.gameclub.lw.biz.listenner.TestListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    /**
     * 当插件被Load(加载)时执行的代码
     * getLogger().info() -> 代表或其控制台Log并且发送一行infoja信息
     */
    @Override
    public void onLoad() {
        getLogger().info("插件正在加载中");
    }

    /**
     * 当插件被Enable(开启)时执行的代码
     * getLogger().info() -> 代表或其控制台Log并且发送一行info信息
     */
    @Override
    public void onEnable() {
        getLogger().info("插件成功启用");
        Bukkit.getPluginManager().registerEvents(new TestListener(),this);
    }

    /**
     * 当插件被Disable(关闭)时执行的代码
     * getLogger().info() -> 代表或其控制台Log并且发送一行info信息
     */
    @Override
    public void onDisable() {
        getLogger().info("再见");
    }

}
