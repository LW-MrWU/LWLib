package vip.gameclub.lwlib.service.database.mysql;

import vip.gameclub.lwlib.model.enumModel.BaseSysMsgEnum;
import vip.gameclub.lwlib.model.enumModel.MysqlDataTypeEnum;
import vip.gameclub.lwlib.service.plugin.BasePlugin;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * 基础mysql服务
 * @author LW-MrWU
 * @date 创建时间 2021/1/25 14:18
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

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 构造函数
     * @param basePlugin 启动主类
     * @return
     * @author LW-MrWU
     * @date 2021/1/28 11:52
     */
    public BaseMysqlService(T basePlugin){
        this.basePlugin = basePlugin;
    }

    /**
     * 初始化数据
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:53
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
     * @param
     * @return java.sql.Connection
     * @author LW-MrWU
     * @date 2021/1/28 11:53
     */
    private Connection getConnection(){
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            String url = "jdbc:mysql://" + ip + ":" + port + "/" + database + "?useSSL=false&characterEncoding=utf-8&allowPublicKeyRetrieval=true&serverTimezone=GMT%2B8";
            connection = DriverManager.getConnection(url, user, password);

        }catch(SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
        }catch(Exception e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
        }
        return connection;
    }

    /**
     * 关闭mysql连接
     * @param
     * @return void
     * @author LW-MrWU
     * @date 2021/1/28 11:53
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
     * 事务结束后请调用closeConnection()方法关闭数据库连接
     * 避免不必要的资源浪费
     * @param
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:53
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
     * 事务结束后请调用closeConnection()方法关闭数据库连接
     * 避免不必要的资源浪费
     * @param sql sql语句
     * @return java.sql.ResultSet
     * @author LW-MrWU
     * @date 2021/1/28 11:55
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
     * 执行insert/update/delete/创建表单
     * 等操作sql
     * @param sql sql语句
     * @return java.lang.Integer
     * @author LW-MrWU
     * @date 2021/1/28 11:55
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
     * 框架已自带id、createTime、updateTime
     * @param table 表名
     * @param paramMap 数据集
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:55
     */
    public boolean createTable(String table, Map<String, MysqlDataTypeEnum> paramMap){
        if(!isMysqlConnect()){
            return false;
        }

        String sql = "CREATE TABLE IF NOT EXISTS `" + tablePrefix + table + "` ("
                + " `id` bigint(19) UNSIGNED NOT NULL AUTO_INCREMENT,"
                + " `CREATED_TIME` timestamp(6) NULL DEFAULT NULL,"
                + " `UPDATED_TIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),";

        Iterator<Map.Entry<String, MysqlDataTypeEnum>> iterator = paramMap.entrySet().iterator();
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
        sql += " PRIMARY KEY (`id`) USING BTREE )ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";

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

    /**
     * 自动插入数据
     * 框架自带id、createTime、updateTime不用处理
     * @param table 表名
     * @param paramMap 数据集
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:56
     */
    public boolean insert(String table, Map<String, Object> paramMap){
        if(!isMysqlConnect() || paramMap.size() <= 0){
            return false;
        }

        String sql = "INSERT INTO `" + tablePrefix + table + "` (CREATED_TIME,";

        for (String str : paramMap.keySet()){
            sql += str + ",";
        }
        sql = sql.substring(0, sql.length()-1) + ") VALUES('" + Timestamp.valueOf(simpleDateFormat.format(new Date())) + "',";

        for (Object obj : paramMap.values()){
            if(obj instanceof String){
                sql += "'" + obj + "',";
            }else{
                sql += obj + ",";
            }
        }
        sql = sql.substring(0, sql.length()-1) + ")";

        try{
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            closeConnection();
            if(result > 0){
                return true;
            }
            return false;
        }catch (SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return false;
        }
    }

    /**
     * 根据条件查询数据表
     * map为null时整表搜索
     * 事务结束后请调用closeConnection()方法关闭数据库连接
     * 避免不必要的资源浪费
     * @param table 表名
     * @param paramMap 数据集
     * @return java.sql.ResultSet
     * @author LW-MrWU
     * @date 2021/1/28 11:57
     */
    public ResultSet select(String table, Map<String, Object> paramMap){
        if(!isMysqlConnect()){
            return null;
        }

        String sql = "SELECT * FROM `" + tablePrefix + table + "` ";

        if(paramMap != null && paramMap.size()>0){
            sql += "WHERE ";
            Iterator<Map.Entry<String,Object>> iterator = paramMap.entrySet().iterator();
            while (iterator.hasNext()){
                Map.Entry<String, Object> entry = iterator.next();
                String key = entry.getKey();
                Object value = entry.getValue();
                if(value instanceof String){
                    sql += " and " + key + "=" + "'" + value + "'";
                }else{
                    sql += " and " + key + "="+ value;
                }
            }
            sql = sql.replaceFirst(" and ", "");
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
     * 根据id删除数据
     * @param table 表名
     * @param id 数据在表中的id
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:58
     */
    public boolean deleteById(String table, Long id){
        if(!isMysqlConnect() || id<=0){
            return false;
        }
        String sql = "DELETE FROM `" + tablePrefix + table + "` WHERE id=" + id;

        try{
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            closeConnection();
            if(result > 0){
                return true;
            }
            return false;
        }catch (SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return false;
        }
    }

    /**
     * 根据id更新数据
     * @param table 表名
     * @param id 数据在表中的id
     * @param paramMap 数据集
     * @return boolean
     * @author LW-MrWU
     * @date 2021/1/28 11:59
     */
    public boolean updateById(String table, Long id, Map<String, Object> paramMap){
        if(!isMysqlConnect() || id<=0 || paramMap.size()<=0){
            return false;
        }

        String sql = "UPDATE `" + tablePrefix + table + "` SET ";
        Iterator<Map.Entry<String,Object>> iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if(value instanceof String){
                sql += key + "=" + "'" + value + "',";
            }else{
                sql += key + "="+ value + ",";
            }
        }
        sql = sql.substring(0, sql.length()-1);
        sql += " WHERE id=" + id;

        try{
            statement = connection.createStatement();
            int result = statement.executeUpdate(sql);
            closeConnection();
            if(result > 0){
                return true;
            }
            return false;
        }catch (SQLException e){
            basePlugin.getBaseLogService().warningByLanguage(BaseSysMsgEnum.MYSQL_EXCEPTION.name(), BaseSysMsgEnum.MYSQL_EXCEPTION.getValue(), e.getMessage());
            return false;
        }
    }
}
