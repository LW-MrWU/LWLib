package com.gameclub.lwlib.service.basic.service.database.mysql;

import com.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import com.gameclub.lwlib.model.enumModel.MysqlDataTypeEnum;
import com.gameclub.lwlib.service.basic.service.plugin.BasePlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.Iterator;
import java.util.Map;

/**
 * @author lw
 * @date 创建时间 2021/1/25 14:18
 * @description 基础mysql数据库服务
 */
public class BaseMysqlService<T extends BasePlugin> {
    protected T basePlugin;

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String ip;
    private static String port;
    private static String database;
    private static String tablePrefix;
    private static String user;
    private static String password;

    private static Connection connection;
    private static PreparedStatement preparedStatement;
    private static Statement statement;
    private static ResultSet resultSet;

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
        String tablePrefix = fileConfiguration.getString("mysql.tablePrefix");
        String user = fileConfiguration.getString("mysql.user");
        String password = fileConfiguration.getString("mysql.password");

        this.ip = ip;
        this.port = port;
        this.database = database;
        this.tablePrefix = tablePrefix;
        this.user = user;
        this.password = password;

        return true;
    }

    /**
     * 连接数据库
     * @author lw
     * @date 2021/1/26 10:45
     * @param []
     * @return java.sql.Connection
     */
    private Connection getConnection(){
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
            connection = DriverManager.getConnection(url, user, password);

        }catch(SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
        }catch(Exception e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
        }
        return connection;
    }

    /**
     * 关闭连接
     * @author lw
     * @date 2021/1/25 18:55
     * @param []
     * @return void
     */
    public void closeConnection(){
        try{
            if(preparedStatement != null){
                preparedStatement.close();
            }

            if(statement != null){
                statement.close();
            }

            if(resultSet != null){
                resultSet.close();
            }

            if(connection != null){
                connection.close();
            }
        }catch (SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
        }
    }

    /**
     * 判断数据库是否联通
     * @author lw
     * @date 2021/1/26 9:59
     * @param []
     * @return boolean
     */
    public boolean isMysqlConnect(){
        if(!initProperty()){
            return false;
        }

        if(getConnection() == null){
            return false;
        }

        return true;
    }

    /**
     * 执行查询sql
     * @author lw
     * @date 2021/1/25 18:49
     * @param [plugin, sql]
     * @return java.sql.ResultSet
     */
    public ResultSet exeSqlSelect(String sql){
        if(!isMysqlConnect()){
            return null;
        }

        try{
            statement = connection.createStatement();
            return statement.executeQuery(sql);
        }catch (SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return null;
        }
    }

    /**
     * 执行insert/update/delete/创建表单等操作sql
     * @author lw
     * @date 2021/1/26 12:16
     * @param [sql]
     * @return java.lang.Boolean
     */
    public Integer exeSql(String sql){
        if(!isMysqlConnect()){
            return -1;
        }

        try{
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            closeConnection();
            return result;
        }catch (SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return -1;
        }
    }

    /**
     * 自动创建表单
     * @author lw
     * @date 2021/1/26 12:17
     * @param [table, map]
     * @return boolean
     */
    public boolean createTable(String table, Map<String, MysqlDataTypeEnum> map){
        if(!isMysqlConnect()){
            return false;
        }

        String sql = "CREATE TABLE `" + tablePrefix + table + "` ("
                + " `id` bigint(19) NOT NULL,"
                + " `CREATED_TIME` timestamp(6) NULL DEFAULT NULL,"
                + " `UPDATED_TIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),";

        Iterator<Map.Entry<String, MysqlDataTypeEnum>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, MysqlDataTypeEnum> entry = iterator.next();
            String key = entry.getKey();
            MysqlDataTypeEnum mysqlDataTypeEnum = entry.getValue();
            String typeName = mysqlDataTypeEnum.getTypeName();
            int typeLength = mysqlDataTypeEnum.getTypeLength();
            sql += " `" + key + "` " + typeName;
            if(typeLength != 0){
                sql += "(" + typeLength;
                //浮点类型处理
                if(mysqlDataTypeEnum == MysqlDataTypeEnum.FLOAT || mysqlDataTypeEnum == MysqlDataTypeEnum.DOUBLE || mysqlDataTypeEnum == MysqlDataTypeEnum.DECIMAL){
                    //添加小数点后2位精度
                    sql += ",2";
                }
                sql += ")";
            }
            sql += ",";
        }
        sql += " PRIMARY KEY (`id`) USING BTREE )ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8";

        try{
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            closeConnection();
            if(result == 0){
                return true;
            }
            return false;
        }catch (SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return false;
        }
    }
}
