package com.example.ylf019.zlxandroid.http.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by yjx on 2018/4/19.
 */

public class PersonSpaceModel extends BaseModel implements MultiItemEntity {


    /**
     * id : 564
     * headurl : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0
     * nickname : /SunsHiNe/
     * add_time : 2018-04-04 05:03:54
     * content : 哈哈
     * open_id : o1-_h5BTGrctcR6Myf7LxbK1bK-g
     * img_url : 20180404/5ac3ec3a142bb.jpg
     * form_user_id : 191,89,89,89
     * comment_content : ....<&&&&>来啊<&&&&>来了来了<&&&&>哈哈，测试有问题啊
     * comment_id : 673,809,810,811
     * reg_time : 2018-03-29 15:10:01,2018-04-16 17:16:12,2018-04-16 17:24:36,2018-04-16 17:31:01
     * headimage : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eraFtqch2hlrDKE2vuAlujfZKWoYFwibAMHXtttrCkPsdoHEwd7KKHRXJXTic6KU9E3vibLFkSibg3nMw/0,https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0,https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0,https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0
     * nick_name : - Fino<&&>/SunsHiNe/<&&>/SunsHiNe/<&&>/SunsHiNe/
     * u_id :
     * add_time_state : 2018年04月04日
     * arr : [{"content":"....","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eraFtqch2hlrDKE2vuAlujfZKWoYFwibAMHXtttrCkPsdoHEwd7KKHRXJXTic6KU9E3vibLFkSibg3nMw/0","dynamic_date":"2018-03-29 15:10:01","comment_time_state":"2018年03月29日","nick_name":"- Fino","comment_id":"673","form_user_id":"191"},{"content":"来啊","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0","dynamic_date":"2018-04-16 17:16:12","comment_time_state":"2018年04月16日","nick_name":"/SunsHiNe/","comment_id":"809","form_user_id":"89"},{"content":"来了来了","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0","dynamic_date":"2018-04-16 17:24:36","comment_time_state":"2018年04月16日","nick_name":"/SunsHiNe/","comment_id":"810","form_user_id":"89"},{"content":"哈哈，测试有问题啊","avatarUrl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0","dynamic_date":"2018-04-16 17:31:01","comment_time_state":"2018年04月16日","nick_name":"/SunsHiNe/","comment_id":"811","form_user_id":"89"}]
     * img_url_arr : ["20180404/5ac3ec3a142bb.jpg"]
     * zan_arr : []
     */

    private String id;
    private String        headurl;
    private String        nickname;
    private String        add_time;
    private String        content;
    private String        open_id;
    private String        img_url;
    private String        form_user_id;
    private String        comment_content;
    private String        comment_id;
    private String        reg_time;
    private String        headimage;
    private String        nick_name;
    private String        u_id;
    private String        add_time_state;
    private List<ArrBean> arr;
    private List<String>  img_url_arr;
    private List<?>       zan_arr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadurl() {
        return headurl;
    }

    public void setHeadurl(String headurl) {
        this.headurl = headurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpen_id() {
        return open_id;
    }

    public void setOpen_id(String open_id) {
        this.open_id = open_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getForm_user_id() {
        return form_user_id;
    }

    public void setForm_user_id(String form_user_id) {
        this.form_user_id = form_user_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    public String getHeadimage() {
        return headimage;
    }

    public void setHeadimage(String headimage) {
        this.headimage = headimage;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getAdd_time_state() {
        return add_time_state;
    }

    public void setAdd_time_state(String add_time_state) {
        this.add_time_state = add_time_state;
    }

    public List<ArrBean> getArr() {
        return arr;
    }

    public void setArr(List<ArrBean> arr) {
        this.arr = arr;
    }

    public List<String> getImg_url_arr() {
        return img_url_arr;
    }

    public void setImg_url_arr(List<String> img_url_arr) {
        this.img_url_arr = img_url_arr;
    }

    public List<?> getZan_arr() {
        return zan_arr;
    }

    public void setZan_arr(List<?> zan_arr) {
        this.zan_arr = zan_arr;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class ArrBean {
        /**
         * content : ....
         * avatarUrl : https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eraFtqch2hlrDKE2vuAlujfZKWoYFwibAMHXtttrCkPsdoHEwd7KKHRXJXTic6KU9E3vibLFkSibg3nMw/0
         * dynamic_date : 2018-03-29 15:10:01
         * comment_time_state : 2018年03月29日
         * nick_name : - Fino
         * comment_id : 673
         * form_user_id : 191
         */

        private String content;
        private String avatarUrl;
        private String dynamic_date;
        private String comment_time_state;
        private String nick_name;
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
}
