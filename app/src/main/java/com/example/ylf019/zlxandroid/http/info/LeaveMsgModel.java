package com.example.ylf019.zlxandroid.http.info;

import com.example.ylf019.zlxandroid.http.model.BaseModel;

import java.util.List;

/**
 * Created by yjx on 2018/4/20.
 */

public class LeaveMsgModel extends BaseModel {

    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 169
         * content : 踩了
         * leave_time : 2018-04-17 21:10:00
         * nick_name : 女皇
         * avatarurl : https://wx.qlogo.cn/mmopen/vi_32/uQEkq0ibuL0DQFODVMFtcjreC6QskBj3dGn0ZSK8WQXib7icibZ3tQvqtXgCjUVgsDGs1fBXP3V9Lu4klkNiciaajkibg/0
         * open_id : o1-_h5Gmpx03gkIEKHhz49KendxA
         */

        private String id;
        private String content;
        private String leave_time;
        private String nick_name;
        private String avatarurl;
        private String open_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getLeave_time() {
            return leave_time;
        }

        public void setLeave_time(String leave_time) {
            this.leave_time = leave_time;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAvatarurl() {
            return avatarurl;
        }

        public void setAvatarurl(String avatarurl) {
            this.avatarurl = avatarurl;
        }

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }
    }
}
