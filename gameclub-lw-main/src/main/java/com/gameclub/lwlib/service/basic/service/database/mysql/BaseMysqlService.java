package com.gameclub.lwlib.service.basic.service.database.mysql;

import com.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import com.gameclub.lwlib.service.basic.service.plugin.BasePlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

/**
 * @author lw
 * @date 创建时间 2021/1/25 14:18
 * @description TODO
 */
public class BaseMysqlService<T extends BasePlugin> {
    protected T basePlugin;

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String ip;
    private String port;
    static String database;
    static String table;
    static String user;
    static String password;

    Connection connection = null;
    Statement statement = null;

    /**
     * 构造函数
     * @author lw
     * @date 2021/1/25 18:52
     * @param [basePlugin]
     * @return
     */
    public BaseMysqlService(T basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 数据库连接
     * @author lw
     * @date 2021/1/25 18:51
     * @param []
     * @return boolean
     */
    private boolean connect(){
        try{
            // 注册 JDBC 驱动
            Class.forName(this.JDBC_DRIVER);

            String url = "jdbc:mysql://" + this.ip + ":" + this.port + "/" + this.database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
            this.connection = DriverManager.getConnection(url, this.user, this.password);

            this.statement = connection.createStatement();
        }catch(SQLException e){
            this.basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return false;
        }catch(Exception e){
            this.basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 初始化数据
     * @author lw
     * @date 2021/1/25 18:52
     * @param []
     * @return boolean
     */
    private boolean initProperty(){
        FileConfiguration fileConfiguration = basePlugin.getConfig();
        Boolean enable = fileConfiguration.getBoolean("mysql.enable");
        if(enable == null || !enable){
            return false;
        }

        String ip = fileConfiguration.getString("mysql.ip");
        String port = fileConfiguration.getString("mysql.port");
        String database = fileConfiguration.getString("mysql.database");
        String table = fileConfiguration.getString("mysql.table");
        String user = fileConfiguration.getString("mysql.user");
        String password = fileConfiguration.getString("mysql.password");

        this.ip = ip;
        this.port = port;
        this.database = database;
        this.table = table;
        this.user = user;
        this.password = password;

        return true;
    }

    /**
     * 关闭连接
     * @author lw
     * @date 2021/1/25 18:55
     * @param []
     * @return void
     */
    public void close(){
        try{
            this.connection.close();
            this.statement.close();
        }catch (SQLException e){
            this.basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
        }
    }

    /**
     * 执行sql
     * @author lw
     * @date 2021/1/25 18:49
     * @param [plugin, sql]
     * @return java.sql.ResultSet
     */
    public ResultSet exeSql(String sql){
        if(!initProperty()){
            return null;
        }

        if(!connect()){
            return null;
        }

        try{
            return this.statement.executeQuery(sql);
        }catch (SQLException e){
            this.basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return null;
        }
    }
}
