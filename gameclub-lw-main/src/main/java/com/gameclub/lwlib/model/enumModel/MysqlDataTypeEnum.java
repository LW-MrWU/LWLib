package com.gameclub.lwlib.model.enumModel;

/**
 * @author lw
 * @date 创建时间 2021/1/26 16:42
 * @description mysql数据类型枚举类
 */
public enum MysqlDataTypeEnum {
    TINYINT("TINYINT", 0),
    SMALLINT("SMALLINT", 0),
    MEDIUMINT("MEDIUMINT", 0),
    INT("INT", 0),
    BIGINT("BIGINT", 0),
    FLOAT("FLOAT", 4),
    DOUBLE("DOUBLE", 4),
    DECIMAL("DECIMAL", 10),

    DATE("DATE", 0),
    TIME("TIME", 0),
    YEAR("YEAR", 0),
    DATETIME("DATETIME", 0),
    TIMESTAMP("TIMESTAMP", 0),

    CHAR("CHAR", 0),
    VARCHAR("VARCHAR", 128),
    TINYBLOB("TINYBLOB", 0),
    TINYTEXT("TINYTEXT", 0),
    BLOB("BLOB", 0),
    TEXT("TEXT", 0),
    MEDIUMBLOB("MEDIUMBLOB", 0),
    MEDIUMTEXT("MEDIUMTEXT", 0),
    LONGBLOB("LONGBLOB", 0),
    LONGTEXT("LONGTEXT", 0);

    private String typeName;
    private int typeLength;

    MysqlDataTypeEnum(String typeName, int typeLength) {
        this.typeLength = typeLength;
        this.typeName = typeName;
    }

    public int getTypeLength(){
        return typeLength;
    }

    public String getTypeName() {
        return typeName;
    }

    public static MysqlDataTypeEnum getEnumFromString(String str){
        if(str != null){
            try {
                return Enum.valueOf(MysqlDataTypeEnum.class, str.trim());
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
        }
        return null;
    }
}
