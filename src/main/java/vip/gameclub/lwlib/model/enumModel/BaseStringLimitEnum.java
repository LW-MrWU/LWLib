package vip.gameclub.lwlib.model.enumModel;

/**
 * 特殊长度限制枚举类
 * @author LW-MrWU
 * @date 创建时间 2021/2/3 16:18
 */
public enum BaseStringLimitEnum {
    /**
     * 玩家名称限制
     */
    PLAYER_NAME_LIMIT(40),
    /**
     * 大箱子容器限制
     */
    INVENTORY_BIG_CHEST_LIMIT(54);

    private int length;

    BaseStringLimitEnum(int length){
        this.length = length;
    }

    /**
     * 获取length值
     * @param
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/2/3 11:39
     */
    public int getLength() {
        return length;
    }
}
