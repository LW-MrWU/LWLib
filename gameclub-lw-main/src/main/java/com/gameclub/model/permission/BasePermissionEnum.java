package com.gameclub.model.permission;

/**
 * @author lw
 * @date 创建时间 2021/1/18 18:44
 * @description 基础权限类型
 */
public enum BasePermissionEnum {
    ADMIN("admin");

    private String value;

    private BasePermissionEnum(String val) {
        this.value = val;
    }

    public String getValue() {
        return this.value;
    }
}
