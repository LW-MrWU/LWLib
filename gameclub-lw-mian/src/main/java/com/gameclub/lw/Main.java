package com.gameclub.lw;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin implements Listener {

    /**
     * 当插件被Load(加载)时执行的代码
     * getLogger().info() -> 代表或其控制台Log并且发送一行info信息
     */
    @Override
    public void onLoad() {
        getLogger().info("onLoad has been invoked!");
    }

    /**
     * 当插件被Enable(开启)时执行的代码
     * getLogger().info() -> 代表或其控制台Log并且发送一行info信息
     */
    @Override
    public void onEnable() {
        getLogger().info("onEnable has been invoked!");
        // 注册本类为所有事件, 如果你将你的监听器类放在别的类里,
        // 这里假设该类是 ChatListener
        // 那传入的参数就改为 Bukkit.getPluginManager.registerEvents(new ChatListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        // 保存config.yml至插件文件夹
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        getLogger().info("name: " + config.getString("name"));
        getLogger().info("displayName: " + config.getString("displayName"));
        getLogger().info("age: " + config.getInt("age"));
        getLogger().info("health: " + config.getDouble("health"));

        File file = new File(getDataFolder(), "test/test.yml");
        FileConfiguration configOther = YamlConfiguration.loadConfiguration(file);

        getLogger().info("文件夹配置测试: " + configOther.getString("name"));
        getConfig().options().copyDefaults(true);
    }

    /**
     * 当插件被Disable(关闭)时执行的代码
     * getLogger().info() -> 代表或其控制台Log并且发送一行info信息
     */
    @Override
    public void onDisable() {
        getLogger().info("onDisable has been invoked!");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.getPlayer().sendMessage("chat!");
    }
}
