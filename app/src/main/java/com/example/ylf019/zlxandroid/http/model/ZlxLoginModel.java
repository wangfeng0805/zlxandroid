package com.example.ylf019.zlxandroid.http.model;

/**
 * Created by yjx on 2018/5/11.
 */

public class ZlxLoginModel extends BaseModel {


    /**
     * code : 0
     * data : {"id":"132","unionid":"oEme80UtowxCe1Rx2jpK28FrPPeE","open_id":"o1-_h5L6vkpvg6wlLbxijA44X1AM","province":"安徽省","city":"合肥市","area":"肥东县","address":"安徽省合肥市肥东县","longitude":"121.5041","latitude":"31.2396","headurl":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIDeujlOxcicMJTaicaawVpibdWKvTRbicriadjoiaaPToBqbx9IibhyDoBTAUEkKgOoAuP4FaDnl5BFBKDg/0","add_time":"2018-03-22 13:07:32","update_time":"2018-05-10 14:15:42","born_date":"1993-04-13","sex":"男","nickname":"水木年华","education":"本科","vocation":"其他","lx_num":"lx74488328","is_show":"0"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 132
         * unionid : oEme80UtowxCe1Rx2jpK28FrPPeE
         * open_id : o1-_h5L6vkpvg6wlLbxijA44X1AM
         * province : 安徽省
         * city : 合肥市
         * area : 肥东县
         * address : 安徽省合肥市肥东县
         * longitude : 121.5041
         * latitude : 31.2396
         * headurl : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIDeujlOxcicMJTaicaawVpibdWKvTRbicriadjoiaaPToBqbx9IibhyDoBTAUEkKgOoAuP4FaDnl5BFBKDg/0
         * add_time : 2018-03-22 13:07:32
         * update_time : 2018-05-10 14:15:42
         * born_date : 1993-04-13
         * sex : 男
         * nickname : 水木年华
         * education : 本科
         * vocation : 其他
         * lx_num : lx74488328
         * is_show : 0
         */

        private String id;
        private String unionid;
        private String open_id;
        private String province;
        private String city;
        private String area;
        private String address;
        private String longitude;
        private String latitude;
        private String headurl;
        private String add_time;
        private String update_time;
        private String born_date;
        private String sex;
        private String nickname;
        private String education;
        private String vocation;
        private String lx_num;
        private String is_show;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getHeadurl() {
            return headurl;
        }

        public void setHeadurl(String headurl) {
            this.headurl = headurl;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getBorn_date() {
            return born_date;
        }

        public void setBorn_date(String born_date) {
            this.born_date = born_date;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getVocation() {
            return vocation;
        }

        public void setVocation(String vocation) {
            this.vocation = vocation;
        }

        public String getLx_num() {
            return lx_num;
        }

        public void setLx_num(String lx_num) {
            this.lx_num = lx_num;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }
    }
}
