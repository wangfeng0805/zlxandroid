package com.example.ylf019.zlxandroid.http.model;

/**
 * Created by yjx on 2018/4/16.
 */

public class PersonCenterModel extends BaseModel {


    /**
     * info : {"id":"790","unionid":"","open_id":"o1-_h5HsqkEUPahmNKpBOkjqN84I","province":"","city":"","area":"","address":"","longitude":"116.655036","latitude":"39.9009742","headurl":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIywDoPiaztn3Im4Ohey60Qc2TjPbAt7ttEH4s7xKI2cznXiaVdI4cOibVlONVBcbbtf2Jda37YedmDg/0","add_time":"2018-04-02 09:35:04","update_time":"2018-04-02 10:27:41","born_date":"0000-00-00","sex":"","nickname":"shaohui","object_age_low":"0","object_age_upper":"0","object_height_low":"0","object_height_upper":"0","education":null,"vocation":"","object_occupation":"","height":"0","label":"","object_label":"","lx_num":"lx87232217","is_show":"0","age":""}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * id : 790
         * unionid :
         * open_id : o1-_h5HsqkEUPahmNKpBOkjqN84I
         * province :
         * city :
         * area :
         * address :
         * longitude : 116.655036
         * latitude : 39.9009742
         * headurl : https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIywDoPiaztn3Im4Ohey60Qc2TjPbAt7ttEH4s7xKI2cznXiaVdI4cOibVlONVBcbbtf2Jda37YedmDg/0
         * add_time : 2018-04-02 09:35:04
         * update_time : 2018-04-02 10:27:41
         * born_date : 0000-00-00
         * sex :
         * nickname : shaohui
         * object_age_low : 0
         * object_age_upper : 0
         * object_height_low : 0
         * object_height_upper : 0
         * education : null
         * vocation :
         * object_occupation :
         * height : 0
         * label :
         * object_label :
         * lx_num : lx87232217
         * is_show : 0
         * age :
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
        private String object_age_low;
        private String object_age_upper;
        private String object_height_low;
        private String object_height_upper;
        private String education;
        private String vocation;
        private String object_occupation;
        private String height;
        private String label;
        private String object_label;
        private String lx_num;
        private String is_show;
        private String age;
        private String is_follow;

        public String getIs_follow() {
            return is_follow;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

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

        public String getObject_age_low() {
            return object_age_low;
        }

        public void setObject_age_low(String object_age_low) {
            this.object_age_low = object_age_low;
        }

        public String getObject_age_upper() {
            return object_age_upper;
        }

        public void setObject_age_upper(String object_age_upper) {
            this.object_age_upper = object_age_upper;
        }

        public String getObject_height_low() {
            return object_height_low;
        }

        public void setObject_height_low(String object_height_low) {
            this.object_height_low = object_height_low;
        }

        public String getObject_height_upper() {
            return object_height_upper;
        }

        public void setObject_height_upper(String object_height_upper) {
            this.object_height_upper = object_height_upper;
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

        public String getObject_occupation() {
            return object_occupation;
        }

        public void setObject_occupation(String object_occupation) {
            this.object_occupation = object_occupation;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getObject_label() {
            return object_label;
        }

        public void setObject_label(String object_label) {
            this.object_label = object_label;
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

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
