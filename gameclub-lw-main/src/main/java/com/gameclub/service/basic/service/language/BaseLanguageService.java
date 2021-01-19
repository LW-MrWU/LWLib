package com.gameclub.service.basic.service.language;

import com.gameclub.model.config.BaseLanguageConfig;
import com.gameclub.service.basic.service.plugin.BasePlugin;

/**
 * @author lw
 * @date 创建时间 2021/1/18 17:31
 * @description TODO
 */
public class BaseLanguageService {

    protected BasePlugin basePlugin;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/18 17:32
     * @param [basePlugin]
     * @return
     */
    public BaseLanguageService(BasePlugin basePlugin) {
        this.basePlugin = basePlugin;
    }

    /**
     * 返回字符串
     * @author lw
     * @date 2021/1/18 17:32
     * @param [str 需要替换的字符串, prms 需要填充的字符串]
     * @return java.lang.String
     */
    public String getString(String str, String... prms) {
        String tempString = str;
        if (tempString != null) {
            for (int i = 0; i < prms.length; i++) {
                String holder = "{" + i + "}";
                tempString = tempString.replace(holder, String.valueOf(prms[i]));
            }
        }
        return tempString;
    }

    /**
     * 返回语言配置
     * @author lw
     * @date 2021/1/18 17:32
     * @param [key 语言配置名称, defualt 默认语言, prms 需要替换的字符串]
     * @return java.lang.String
     */
    public String getLanguage(String key,String defualt,String ...prms) {
        BaseLanguageConfig baseLanguageConfig = this.basePlugin.getBaseConfigService().getConfig(BaseLanguageConfig.configName);

        String language = null;
        if(baseLanguageConfig!=null) {
            language = baseLanguageConfig.getConfig().getString(key, defualt);
            if(language==null) {
                language = defualt;
            }
        }else {
            language = defualt;
        }
        language = getString(language, prms);
        String reLanguage = this.basePlugin.getBaseUtilsService().translateColorCodes(language);
        return reLanguage;
    }

}
