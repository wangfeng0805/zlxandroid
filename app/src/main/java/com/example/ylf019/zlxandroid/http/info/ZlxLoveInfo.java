package com.example.ylf019.zlxandroid.http.info;

import java.io.Serializable;

/**
 * Created by yjx on 2018/4/27.
 */

public class ZlxLoveInfo implements Serializable {


    /**
     * lx_num : lx64878489
     * id : 34
     * u_id : 130
     * from_u_id : 130
     * zan_time : 2018-04-20 10:57:08
     * dynamic_id : 666
     * zan_head_url : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLkva9ibvBZ8OuVcM0LUqibEcTdX8MDA3M6WjiaENGzlpOY0nXmuVAQPEsgtmuicQqLuZvdpbyQpCodKg/0
     * zan_nick_name : Asher Yang
     */

    private String lx_num;
    private String id;
    private String u_id;
    private String from_u_id;
    private String zan_time;
    private String dynamic_id;
    private String zan_head_url;
    private String zan_nick_name;

    public String getLx_num() {
        return lx_num;
    }

    public void setLx_num(String lx_num) {
        this.lx_num = lx_num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getFrom_u_id() {
        return from_u_id;
    }

    public void setFrom_u_id(String from_u_id) {
        this.from_u_id = from_u_id;
    }

    public String getZan_time() {
        return zan_time;
    }

    public void setZan_time(String zan_time) {
        this.zan_time = zan_time;
    }

    public String getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(String dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    public String getZan_head_url() {
        return zan_head_url;
    }

    public void setZan_head_url(String zan_head_url) {
        this.zan_head_url = zan_head_url;
    }

    public String getZan_nick_name() {
        return zan_nick_name;
    }

    public void setZan_nick_name(String zan_nick_name) {
        this.zan_nick_name = zan_nick_name;
    }
}
