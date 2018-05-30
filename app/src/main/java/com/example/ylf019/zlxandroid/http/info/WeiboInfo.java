package com.example.ylf019.zlxandroid.http.info;

import com.example.ylf019.zlxandroid.http.model.BaseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yjx on 2018/4/12.
 */

public class WeiboInfo  extends BaseModel implements Serializable {

    /**
     * id : 1023
     * headurl : https://wx.qlogo.cn/mmopen/vi_32/sdO71Mg9dtULib3ZG3FE3SiaHib89OhnQLP2ibxoVn6SEa4uG4JvjZyU59zHIS0c7CpDkrk8grmSTuibeGuHQ2flJAg/0
     * dy_user_id : 477
     * label : 民风民俗
     * nickname : Lee
     * add_time : 2018-04-28 15:28:00
     * content : 美食
     * open_id : o1-_h5GBmF30fHv_8Sl6Yoo_RSiw
     * img_url : 20180428/5ae422b3b09bb.jpg
     * phone :
     * form_user_id : null
     * comment_content : M<&&&&>你好呀
     * comment_id : 909,910
     * reg_time : 2018-05-02 15:25:20,2018-05-03 14:43:20
     * headimage : null
     * nick_name : null
     * u_id :
     * add_time_state : 2018年04月28日
     * arr : [{"content":"M","avatarUrl":"","dynamic_date":"2018-05-02 15:25:20","comment_time_state":"2018年05月02日","nick_name":"","comment_id":"909","form_user_id":""},{"content":"你好呀","avatarUrl":null,"dynamic_date":"2018-05-03 14:43:20","comment_time_state":"19小时前","nick_name":null,"comment_id":"910","form_user_id":null}]
     * img_url_arr : ["20180428/5ae422b3b09bb.jpg"]
     * zan_arr : [{"id":"259","u_id":"477","from_u_id":"89","zan_time":"2018-05-02 15:32:18","dynamic_id":"1023","zan_head_url":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eqia8icibkRy0xrWpQZR6VHGMicydlFKib5bnSiczUHAHKksiaR6VP2a3hBbWGM9jzgEN1GHkep217oiaLAtg/0","zan_nick_name":"/SunsHiNe/"},{"id":"263","u_id":"477","from_u_id":"127","zan_time":"2018-05-03 11:55:14","dynamic_id":"1023","zan_head_url":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eo3JzakPcEUs5FIzSHzdbCibW2wnWv9jibYr4uKuiaCichurqB7ptnaR2nHHys5z2hSnOxJBEfhrkO1Eg/0","zan_nick_name":"沈祎sy"}]
     * is_zan : 0
     */

    private String id;
    private String           headurl;
    private String           dy_user_id;
    private String           label;
    private String           nickname;
    private String           add_time;
    private String           content;
    private String           open_id;
    private String           img_url;
    private String           phone;
    private Object           form_user_id;
    private String           comment_content;
    private String           comment_id;
    private String           reg_time;
    private Object           headimage;
    private Object           nick_name;
    private String           u_id;
    private String           add_time_state;
    private int              is_zan;
    private String lx_num;
    private List<ZlxCommentInfo>     arr;
    private List<String>     img_url_arr;
    private List<ZlxLoveInfo> zan_arr;
    public int zan_count;

    public String getLx_num() {
        return lx_num;
    }

    public void setLx_num(String lx_num) {
        this.lx_num = lx_num;
    }

    public int getZan_count() {
        return zan_count;
    }

    public void setZan_count(int zan_count) {
        this.zan_count = zan_count;
    }

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

    public String getDy_user_id() {
        return dy_user_id;
    }

    public void setDy_user_id(String dy_user_id) {
        this.dy_user_id = dy_user_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getForm_user_id() {
        return form_user_id;
    }

    public void setForm_user_id(Object form_user_id) {
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

    public Object getHeadimage() {
        return headimage;
    }

    public void setHeadimage(Object headimage) {
        this.headimage = headimage;
    }

    public Object getNick_name() {
        return nick_name;
    }

    public void setNick_name(Object nick_name) {
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

    public int getIs_zan() {
        return is_zan;
    }

    public void setIs_zan(int is_zan) {
        this.is_zan = is_zan;
    }

    public List<ZlxCommentInfo> getArr() {
        return arr;
    }

    public void setArr(List<ZlxCommentInfo> arr) {
        this.arr = arr;
    }

    public List<String> getImg_url_arr() {
        return img_url_arr;
    }

    public void setImg_url_arr(List<String> img_url_arr) {
        this.img_url_arr = img_url_arr;
    }

    public List<ZlxLoveInfo> getZan_arr() {
        return zan_arr;
    }

    public void setZan_arr(List<ZlxLoveInfo> zan_arr) {
        this.zan_arr = zan_arr;
    }


}
