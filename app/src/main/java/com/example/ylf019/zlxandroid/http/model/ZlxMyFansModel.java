package com.example.ylf019.zlxandroid.http.model;

import java.util.List;

/**
 * Created by yjx on 2018/4/20.
 */

public class ZlxMyFansModel extends BaseModel {


    private List<InfoBean> info;

    public List<InfoBean> getInfo() {
        return info;
    }

    public void setInfo(List<InfoBean> info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 299
         * headurl : https://wx.qlogo.cn/mmopen/vi_32/uQEkq0ibuL0DQFODVMFtcjreC6QskBj3dGn0ZSK8WQXib7icibZ3tQvqtXgCjUVgsDGs1fBXP3V9Lu4klkNiciaajkibg/0
         * nickname : 女皇
         * open_id : o1-_h5Gmpx03gkIEKHhz49KendxA
         * province : 广西壮族自治区
         * city : 桂林市
         * area : 七星区
         * lx_num : lx37102612
         */

        private String id;
        private String headurl;
        private String nickname;
        private String open_id;
        private String province;
        private String city;
        private String area;
        private String lx_num;

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

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getLx_num() {
            return lx_num;
        }

        public void setLx_num(String lx_num) {
            this.lx_num = lx_num;
        }
    }
}
