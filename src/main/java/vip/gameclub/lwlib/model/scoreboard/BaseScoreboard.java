package vip.gameclub.lwlib.model.scoreboard;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.service.plugin.BasePlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 记分板父类
 * @author LW-MrWU
 * @date 创建时间 2021/2/1 11:54
 */
public abstract class BaseScoreboard<T extends BasePlugin> {
    protected T basePlugin;

    private static Map<Player, BaseScoreboard> scoreboardData;
    private Scoreboard scoreboard;
    private Objective objective;
    private String title;
    private static Player player;
    private List<Team> teamList;
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
        this.title = title;
        this.displaySlot = displaySlot;
        this.count = count;

        this.player = player;
        this.basePlugin = basePlugin;
        this.isShow = false;
        teamList = Lists.newArrayList();

        show();

        getScoreboardData().put(player, this);
    }

    /**
     * 注册一个scoreboard展示
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/2/1 16:21
     */
    private void show(){
        if(isShow){
            return;
        }

        if(player == null || !player.isOnline()){
            return;
        }

        this.scoreboard = basePlugin.getServer().getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective(player.getName(), "dummy", basePlugin.getBaseStringService().chatColorCodes(this.title));
        objective.setDisplaySlot(displaySlot);

        player.setScoreboard(scoreboard);
        isShow = true;
    }

    /**
     * 加载显示内容
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/2/1 16:22
     */
    public void reload(){
        show();

        if(showCustom()){
            return;
        }

        int i = 0;
        for (Team team : teamList){
            for (String str : team.getEntries()){
                getObjective().getScore(str).setScore(++i);
            }
            getObjective().getScore(team.getName()).setScore(++i);
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
     * 增加一个Team
     * @param teamName team的名字
     * @param args 参数（展示用，多个参数默认换行展示）
     * @return boolean
     * @author LW-MrWU
     * @date 2021/2/1 16:24
     */
    public boolean addTeam(String teamName, String... args){
        if(player == null || !player.isOnline()){
            return false;
        }

        if(StringUtils.isEmpty(teamName)){
            return false;
        }

        if(teamList.size() >= count){
            basePlugin.getBaseMessageService().sendMessageByLanguage(player, BaseSysMsgEnum.SCOREBOARD_TEAM_COUNT_EOOR.name(), BaseSysMsgEnum.SCOREBOARD_TEAM_COUNT_EOOR.getValue(), String.valueOf(count));
            return false;
        }

        for (Team team : teamList){
            if(team.getName().equalsIgnoreCase(teamName)){
                return false;
            }
        }

        Team team = getScoreboard().registerNewTeam(teamName);
        for (int i = args.length-1; i>=0; i--){
            team.addEntry(args[i]);
        }

        teamList.add(team);
        reload();
        return true;
    }

    /**
     * 删除一个Team
     * @param teamName team名
     * @return void
     * @author LW-MrWU
     * @date 2021/2/1 16:25
     */
    public void delTeam(String teamName){
        for (Team team : teamList){
            if(team.getName().equalsIgnoreCase(teamName)){
                teamList.remove(team);
                objective.unregister();
                isShow = false;
                break;
            }
        }
        reload();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }

    public Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player p) {
        player = p;
    }

    public String getTitle() {
        return title;
    }

    public static Map<Player, BaseScoreboard> getScoreboardData() {
        if(scoreboardData == null){
            scoreboardData = new HashMap<>();
        }
        return scoreboardData;
    }

    public List<Team> getTeamList() {
        return teamList;
    }
}
