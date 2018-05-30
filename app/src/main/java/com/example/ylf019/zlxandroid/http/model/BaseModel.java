package com.example.ylf019.zlxandroid.http.model;

/**
 * Created by yjx on 2018/4/12.
 */

public class BaseModel {

    /**
     * error_id : -1
     * msg : 传递的参数有误
     */

    private int error_id;
    private String msg;

    public int getError_id() {
        return error_id;
    }

    public void setError_id(int error_id) {
        this.error_id = error_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
