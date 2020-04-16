package com.mengxuegu.oauth2.resource.exception;

public enum ErrorInfo {

    PARAM_NOT_EXIST(1001, "请添加参数"),
    NAME_NOT_EXIST(1003, "姓名不能为空"),
    EDUCATIONAL_NOT_EXIST(1004, "学历不能为空"),
    PHONE_NOT_EXIST(1005, "电话不能为空"),
    AGE_NOT_EXIST(1007, "年龄不能为0"),
    DEPT_NOT_EXIST(1008, "部门不能为空"),
    GENDER_NOT_EXIST(1009, "性别不合法"),
    ID_NOT_EXIST(1010, "id不存在"),
    ADDRESS_NOT_EXIST(1002, "地址不能为空");

    public Integer code;
    public String message;

    private ErrorInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }



}
