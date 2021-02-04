package vip.gameclub.lwlib.model.inventory;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import vip.gameclub.lwlib.listener.BaseListener;
import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.service.plugin.BasePlugin;
import vip.gameclub.lwlib.service.utils.BaseStringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有容器父类
 *
 * @author LW-MrWU
 * @date 创建时间 2021/1/30 13:58
 */
public abstract class BaseInventory<T extends BasePlugin> extends BaseListener {
    private T basePlugin;
    private String title;
    private Player player;
    private List<Inventory> inventoryList;

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @param player 玩家
     * @param title 标题
     * @return
     * @author LW-MrWU
     * @date 2021/2/4 14:44
     */
    public BaseInventory(T basePlugin, Player player, String title){
        this(basePlugin, player, title, 36);
    }

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @param player 玩家
     * @param title 标题
     * @param pageSize 实际容器容量（不包括头尾，最大36）
     * @return
     * @author LW-MrWU
     * @date 2021/2/4 14:42
     */
    public BaseInventory(T basePlugin, Player player, String title, int pageSize){
        this.basePlugin = basePlugin;
        this.player = player;
        this.title = title;

        this.inventoryList = Lists.newArrayList();

        initInventory(pageSize);

        basePlugin.registerListener(this);
    }

    /**
     * 容器初始化
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/2/4 11:20
     */
    public void initInventory(int pageSize){
        List<?> objectList = getResourceList();
        if(objectList == null || objectList.size() == 0){
            return;
        }

        List<List<?>> resultList = getPageList(objectList, pageSize);
        for (int i=0; i<resultList.size(); i++){
            List<?> valueList = resultList.get(i);
            int invSize = pageSize + 18;
            if(valueList.size() != pageSize){
                if(valueList.size()/9 == 0){
                    if(i == 0){
                        invSize = 18;
                    }else if(i == resultList.size()-1){
                        invSize = 27;
                    }
                }else{
                    if(i == 0){
                        invSize = (valueList.size()/9 + 2)*9;
                    }else if(i == resultList.size()-1){
                        invSize = (valueList.size()/9 + 3)*9;
                    }
                }
            }
            Inventory inventory = Bukkit.createInventory(null, invSize, BaseStringUtil.chatColorCodes(this.title));

            if(!headCustom()){
                ItemStack welcomeItemStack = welcome(i);
                ItemStack closeItemStack = close();
                inventory.setContents(new ItemStack[]{welcomeItemStack,welcomeItemStack,welcomeItemStack,welcomeItemStack,welcomeItemStack,welcomeItemStack,welcomeItemStack,welcomeItemStack,closeItemStack});
            }

            for (Object object : valueList){
                handleItem(inventory, object);
            }

            if(!footCustom()){
                ItemStack welcomeItemStack = welcome(i);
                ItemStack nextItemStack = next(i);
                ItemStack preItemStack = pre(i);
                //判断是否需要加上一页下一页
                if(i == 0){
                    if(valueList.size() == pageSize){
                        //加下一页
                        inventory.setItem(invSize - 1, nextItemStack);
                        for(int n=invSize-2; n>invSize-10; n--){
                            inventory.setItem(n, welcomeItemStack);
                        }
                    }
                }else if(i < resultList.size()-1){
                    //加上一页、下一页
                    inventory.setItem(invSize - 9, preItemStack);
                    inventory.setItem(invSize - 1, nextItemStack);
                    for(int n=invSize-8; n<invSize-1; n++){
                        inventory.setItem(n, welcomeItemStack);
                    }
                }else if(i == resultList.size()-1){
                    //加上一页
                    inventory.setItem(invSize - 9, preItemStack);
                    for(int n=invSize-8; n<invSize; n++){
                        inventory.setItem(n, welcomeItemStack);
                    }
                }
            }
            inventoryList.add(inventory);
        }
    }

    /**
     * 容器内容资源数据列表
     * @param
     * @return java.util.List<?>
     * @author LW-MrWU
     * @date 2021/2/4 11:20
     */
    public abstract List<?> getResourceList();

    /**
     * 容器内单个资源处理
     * @param inventory 容器
     * @param object item
     * @return void
     * @author LW-MrWU
     * @date 2021/2/4 11:21
     */
    public abstract void handleItem(Inventory inventory, Object object);

    /**
     * 自定义容器头一行内容
     * 修改头行内容时
     * 请返回invClickCustomListener方法为true
     * 并覆写关闭/分页事件
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/2/4 14:39
     */
    public abstract boolean headCustom();

    /**
     * 自定义容器末行内容
     * 修改尾行内容时
     * 请返回invClickCustomListener方法为true
     * 并覆写关闭/分页事件
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/2/4 14:39
     */
    public abstract boolean footCustom();

    /**
     * 自定义容器点击事件
     * 返回false表示使用默认分页点击功能
     * 返回true请确保重写分页点击事件
     * @param event 容器点击事件
     * @return boolean
     * @author LW-MrWU
     * @date 2021/2/4 15:38
     */
    public abstract boolean invClickCustomListener(InventoryClickEvent event);

    /**
     * 按数量分组
     * @param list 列表
     * @param groupNum 每组数量
     * @return java.util.List<java.util.List<?>>
     * @author LW-MrWU
     * @date 2021/2/4 11:21
     */
    public List<List<?>> getPageList(List<?> list, int groupNum){
        List<List<?>> resultList = new ArrayList<>();
        int current = 0;
        while (current < list.size()) {
            resultList.add(new ArrayList<>(list.subList(current, Math.min((current + groupNum), list.size()))));
            current += groupNum;
        }

        return resultList;
    }

    /**
     * 加载第一页
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/2/4 11:22
     */
    public void load(){
        if(inventoryList == null || inventoryList.size() == 0){
            return;
        }
        load(0);
    }

    /**
     * 加载第n页
     * 按0开始算，0为第一页
     * @param page 页码按
     * @return void
     * @author LW-MrWU
     * @date 2021/2/4 11:22
     */
    public void load(int page){
        if(inventoryList == null || inventoryList.size() == 0){
            return;
        }
        Inventory inventory = inventoryList.get(page);
        this.player.openInventory(inventory);
    }
    
    private ItemStack welcome(int i){
        ItemStack welcomeItemStack = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
        ItemMeta welcomeItemMeta = welcomeItemStack.getItemMeta();
        welcomeItemMeta.setDisplayName(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_WELCOME.name(), BaseSysMsgEnum.INVENTORY_MSG_WELCOME.getValue()));
        welcomeItemMeta.setLore(Lists.newArrayList(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_PAGE.name(), BaseSysMsgEnum.INVENTORY_MSG_PAGE.getValue(), String.valueOf(i+1))));
        welcomeItemStack.setItemMeta(welcomeItemMeta);
        return welcomeItemStack;
    }
    
    private ItemStack close(){
        ItemStack closeItemStack = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta closeItemMeta = closeItemStack.getItemMeta();
        closeItemMeta.setDisplayName(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_CLOSE.name(), BaseSysMsgEnum.INVENTORY_MSG_CLOSE.getValue()));
        closeItemStack.setItemMeta(closeItemMeta);
        return closeItemStack;
    }

    private ItemStack next(int i){
        ItemStack nextItemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
        ItemMeta nextItemMeta = nextItemStack.getItemMeta();
        nextItemMeta.setDisplayName(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_NEXT.name(), BaseSysMsgEnum.INVENTORY_MSG_NEXT.getValue()));
        nextItemMeta.setLore(Lists.newArrayList(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_PAGE.name(), BaseSysMsgEnum.INVENTORY_MSG_PAGE.getValue(), String.valueOf(i+2))));
        nextItemMeta.setLocalizedName(String.valueOf(i+1));
        nextItemStack.setItemMeta(nextItemMeta);
        return nextItemStack;
    }

    private ItemStack pre(int i){
        ItemStack preItemStack = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemMeta preItemMeta = preItemStack.getItemMeta();
        preItemMeta.setDisplayName(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_PRE.name(), BaseSysMsgEnum.INVENTORY_MSG_PRE.getValue()));
        preItemMeta.setLore(Lists.newArrayList(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_PAGE.name(), BaseSysMsgEnum.INVENTORY_MSG_PAGE.getValue(), String.valueOf(i))));
        preItemMeta.setLocalizedName(String.valueOf(i-1));
        preItemStack.setItemMeta(preItemMeta);
        return preItemStack;
    }

    public String getTitle() {
        return title;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Inventory> getInventoryList() {
        return inventoryList;
    }

    @EventHandler
    public void inventoryClickListener(InventoryClickEvent event){
        if(!invClickCustomListener(event)){
            if(event.getWhoClicked() instanceof Player == false) { return;}
            ItemStack itemStack = event.getCurrentItem();
            if(itemStack == null){
                return;
            }
            ItemMeta itemMeta = itemStack.getItemMeta();
            if(itemMeta == null){
                return;
            }
            String displayName = itemMeta.getDisplayName();
            if(StringUtils.isEmpty(displayName)){
                return;
            }
            if(displayName.equalsIgnoreCase(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_CLOSE.name(), BaseSysMsgEnum.INVENTORY_MSG_CLOSE.getValue()))){
                event.getWhoClicked().closeInventory();
            }else if(displayName.equalsIgnoreCase(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_PRE.name(), BaseSysMsgEnum.INVENTORY_MSG_PRE.getValue()))
                    || displayName.equalsIgnoreCase(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_NEXT.name(), BaseSysMsgEnum.INVENTORY_MSG_NEXT.getValue()))){
                String localizedName = itemStack.getItemMeta().getLocalizedName();
                load(Integer.parseInt(localizedName));
            }else if(displayName.equalsIgnoreCase(this.basePlugin.getBaseLanguageService().getLanguage(BaseSysMsgEnum.INVENTORY_MSG_WELCOME.name(), BaseSysMsgEnum.INVENTORY_MSG_WELCOME.getValue()))){
                event.setCancelled(true);
            }
        }
    }
}
