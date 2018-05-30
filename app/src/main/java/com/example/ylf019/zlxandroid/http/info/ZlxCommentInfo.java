package com.example.ylf019.zlxandroid.http.info;

import java.io.Serializable;

/**
 * Created by yjx on 2018/4/27.
 */

public class ZlxCommentInfo implements Serializable {


    /**
     * content : 十年树木，百年树人
     * avatarUrl : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK89699f0p2ac54yOwyibN5mk2qicN9JMiaAFaMtqfOVfqAia4ia69lVlpeHH7O7Nn8SUeL4Rary94EBmg/0
     * dynamic_date : 2018-04-18 12:26:12
     * comment_time_state : 2018年04月18日
     * nick_name : 大旺哥
     * lx_num :
     * comment_id : 840
     * form_user_id : 101
     */

    private String content;
    private String avatarUrl;
    private String dynamic_date;
    private String comment_time_state;
    private String nick_name;
    private String lx_num;
    private String comment_id;
    private String form_user_id;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getDynamic_date() {
        return dynamic_date;
    }

    public void setDynamic_date(String dynamic_date) {
        this.dynamic_date = dynamic_date;
    }

    public String getComment_time_state() {
        return comment_time_state;
    }

    public void setComment_time_state(String comment_time_state) {
        this.comment_time_state = comment_time_state;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getLx_num() {
        return lx_num;
    }

    public void setLx_num(String lx_num) {
        this.lx_num = lx_num;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getForm_user_id() {
        return form_user_id;
    }

    public void setForm_user_id(String form_user_id) {
        this.form_user_id = form_user_id;
    }
}
