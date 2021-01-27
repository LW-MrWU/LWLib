package vip.gameclub.lwlib.model.config;

import vip.gameclub.lwlib.service.plugin.BasePlugin;
import vip.gameclub.lwlib.model.enumModel.BaseLanguageEnum;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author lw
 * @date 创建时间 2021/1/22 15:24
 * @description config文件父类
 */
public abstract class BaseDefaultConfig <T extends BasePlugin> extends BaseConfig {

    public BaseDefaultConfig(T basePlugin) {
        super(basePlugin, "config.yml");
    }

    @Override
    public void loadConfig() {
        FileConfiguration fileConfiguration = this.getFileConfiguration();
        if(fileConfiguration != null){
            String lang = fileConfiguration.getString("lang");
            if(StringUtils.isNotEmpty(lang)){
                BaseLanguageEnum baseLanguageEnum = BaseLanguageEnum.getEnumByName(lang);
                String fileName = baseLanguageEnum.getValue();

                BasePlugin basePlugin = this.getBasePlugin();
                BaseLanguageConfig baseLanguageConfig = new BaseLanguageConfig(basePlugin, fileName);
            }
        }

        loadDefaultConfig();
    }

    /**
     * 基础文件配置拓展
     * @author lw
     * @date 2021/1/22 16:52
     * @param []
     * @return void
     */
    protected abstract void loadDefaultConfig();

    @Override
    protected boolean createConfig() {
        return false;
    }
}
