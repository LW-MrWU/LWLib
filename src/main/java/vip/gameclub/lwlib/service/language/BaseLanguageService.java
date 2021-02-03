package vip.gameclub.lwlib.service.language;

import vip.gameclub.lwlib.service.plugin.BasePlugin;
import vip.gameclub.lwlib.model.config.BaseConfig;
import vip.gameclub.lwlib.model.config.BaseLanguageConfig;
import vip.gameclub.lwlib.service.utils.BaseStringUtil;

/**
 * 基础语言服务
 * @author LW-MrWU
 * @date 创建时间 2021/1/18 17:31
 */
public class BaseLanguageService {

    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 12:00
     */
    public BaseLanguageService(BasePlugin basePlugin) {
        this.basePlugin = basePlugin;
    }

    /**
     * 返回语言配置
     * @param key 语言配置名称
     * @param defualt 默认语言
     * @param prms 需要替换的字符串
     * @return java.lang.String
     * @author LW-MrWU
     * @date 2021/1/28 12:00
     */
    public String getLanguage(String key,String defualt,String ...prms) {
        BaseConfig baseLanguageConfig = this.basePlugin.getBaseConfigService().getConfig(BaseLanguageConfig.getConfigName());

        String language = null;
        if(baseLanguageConfig!=null) {
            language = baseLanguageConfig.getFileConfiguration().getString(key, defualt);
            if(language==null) {
                language = defualt;
            }
        }else {
            language = defualt;
        }
        language = BaseStringUtil.substitutionPrms(language, prms);
        return language;
    }

}
