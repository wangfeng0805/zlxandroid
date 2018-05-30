package com.example.ylf019.zlxandroid.http.model;

import java.util.List;

/**
 * Created by yjx on 2018/5/15.
 */

public class ZlxHomePageModel extends BaseModel {

    /**
     * info : {"id":"133","unionid":"","open_id":"o1-_h5Fmnw6EKTTbGeEQeLO3Luok","province":"安徽省","city":"阜阳市","area":"颍上县","address":"安徽省阜阳市颍上县","longitude":"121.2206","latitude":"31.73506","headurl":"https://wx.qlogo.cn/mmopen/vi_32/giaL60wiaIOxbFwwPeoUrmepSr99KInlksibOKCYJibnElQNegn1xAnzXehhoeMzicp5fR8fWicjxEsPVQY5icKZzMfMQ/0","add_time":"2018-03-22 13:07:49","update_time":"2018-03-31 12:58:28","born_date":"1992-05-10","sex":"女","nickname":"刘露露","object_age_low":"0","object_age_upper":"0","object_height_low":"0","object_height_upper":"0","education":"","vocation":"","object_occupation":"","height":"0","label":"","object_label":"","lx_num":"lx14980602","is_show":"0","graduate_university":"","u_id":"0","dynamic_arr":["20180322/5ab33c0ad9f10.jpg"],"age":26,"my_fans":[],"follow_fans":[{"headurl":"","open_id":"o1-_h5KiVsD6SIK5aeaRQCBdQJq4","lx_num":"lx68924692"},{"headurl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erSN05ZeaPokVwMjUU6w5eiap5czpo6ySqibjQQQVB3LYvOoPIZRkmv2NjvuMEmQFdQibhBqUZBqR7uw/0","open_id":"o1-_h5PQQAYs-dM9KMTG5Jqp-e3k","lx_num":"lx93541466"},{"headurl":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIv8tYGXBSgaIB5PLn2sJwR3QZiaSQCyjyK8rsNL7q76wtDmNAV17By3oyZlxiav55C8bm74jRpIrIg/0","open_id":"o1-_h5HUFjxkeV1uLX3JALPprEkw","lx_num":"lx26766053"}]}
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
         * id : 133
         * unionid :
         * open_id : o1-_h5Fmnw6EKTTbGeEQeLO3Luok
         * province : 安徽省
         * city : 阜阳市
         * area : 颍上县
         * address : 安徽省阜阳市颍上县
         * longitude : 121.2206
         * latitude : 31.73506
         * headurl : https://wx.qlogo.cn/mmopen/vi_32/giaL60wiaIOxbFwwPeoUrmepSr99KInlksibOKCYJibnElQNegn1xAnzXehhoeMzicp5fR8fWicjxEsPVQY5icKZzMfMQ/0
         * add_time : 2018-03-22 13:07:49
         * update_time : 2018-03-31 12:58:28
         * born_date : 1992-05-10
         * sex : 女
         * nickname : 刘露露
         * object_age_low : 0
         * object_age_upper : 0
         * object_height_low : 0
         * object_height_upper : 0
         * education :
         * vocation :
         * object_occupation :
         * height : 0
         * label :
         * object_label :
         * lx_num : lx14980602
         * is_show : 0
         * graduate_university :
         * u_id : 0
         * dynamic_arr : ["20180322/5ab33c0ad9f10.jpg"]
         * age : 26
         * my_fans : []
         * follow_fans : [{"headurl":"","open_id":"o1-_h5KiVsD6SIK5aeaRQCBdQJq4","lx_num":"lx68924692"},{"headurl":"https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83erSN05ZeaPokVwMjUU6w5eiap5czpo6ySqibjQQQVB3LYvOoPIZRkmv2NjvuMEmQFdQibhBqUZBqR7uw/0","open_id":"o1-_h5PQQAYs-dM9KMTG5Jqp-e3k","lx_num":"lx93541466"},{"headurl":"https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIv8tYGXBSgaIB5PLn2sJwR3QZiaSQCyjyK8rsNL7q76wtDmNAV17By3oyZlxiav55C8bm74jRpIrIg/0","open_id":"o1-_h5HUFjxkeV1uLX3JALPprEkw","lx_num":"lx26766053"}]
         */

        private String               id;
        private String               unionid;
        private String               open_id;
        private String               province;
        private String               city;
        private String               area;
        private String               address;
        private String               longitude;
        private String               latitude;
        private String               headurl;
        private String               add_time;
        private String               update_time;
        private String               born_date;
        private String               sex;
        private String               nickname;
        private String               object_age_low;
        private String               object_age_upper;
        private String               object_height_low;
        private String               object_height_upper;
        private String               education;
        private String               vocation;
        private String               object_occupation;
        private String               height;
        private String               label;
        private String               object_label;
        private String               lx_num;
        private String               is_show;
        private String               graduate_university;
        private String               u_id;
        private int                  age;
        private List<String>         dynamic_arr;
        private List<My_FansBean>    my_fans;
        private List<FollowFansBean> follow_fans;

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

        public String getGraduate_university() {
            return graduate_university;
        }

        public void setGraduate_university(String graduate_university) {
            this.graduate_university = graduate_university;
        }

        public String getU_id() {
            return u_id;
        }

        public void setU_id(String u_id) {
            this.u_id = u_id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getDynamic_arr() {
            return dynamic_arr;
        }

        public void setDynamic_arr(List<String> dynamic_arr) {
            this.dynamic_arr = dynamic_arr;
        }

        public List<My_FansBean> getMy_fans() {
            return my_fans;
        }

        public void setMy_fans(List<My_FansBean> my_fans) {
            this.my_fans = my_fans;
        }

        public List<FollowFansBean> getFollow_fans() {
            return follow_fans;
        }

        public void setFollow_fans(List<FollowFansBean> follow_fans) {
            this.follow_fans = follow_fans;
        }

        public static class My_FansBean {
            /**
             * headurl :
             * open_id : o1-_h5KiVsD6SIK5aeaRQCBdQJq4
             * lx_num : lx68924692
             */

            private String headurl;
            private String open_id;
            private String lx_num;

            public String getHeadurl() {
                return headurl;
            }

            public void setHeadurl(String headurl) {
                this.headurl = headurl;
            }

            public String getOpen_id() {
                return open_id;
            }

            public void setOpen_id(String open_id) {
                this.open_id = open_id;
            }

            public String getLx_num() {
                return lx_num;
            }

            public void setLx_num(String lx_num) {
                this.lx_num = lx_num;
            }
        }

        public static class FollowFansBean {
            /**
             * headurl :
             * open_id : o1-_h5KiVsD6SIK5aeaRQCBdQJq4
             * lx_num : lx68924692
             */

            private String headurl;
            private String open_id;
            private String lx_num;

            public String getHeadurl() {
                return headurl;
            }

            public void setHeadurl(String headurl) {
                this.headurl = headurl;
            }

            public String getOpen_id() {
                return open_id;
            }

            public void setOpen_id(String open_id) {
                this.open_id = open_id;
            }

            public String getLx_num() {
                return lx_num;
            }

            public void setLx_num(String lx_num) {
                this.lx_num = lx_num;
            }
        }
    }
}

