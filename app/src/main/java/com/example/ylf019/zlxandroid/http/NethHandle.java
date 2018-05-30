package com.example.ylf019.zlxandroid.http;


import com.example.ylf019.zlxandroid.appconfig.URLConfig;
import com.example.ylf019.zlxandroid.http.info.LeaveMsgModel;
import com.example.ylf019.zlxandroid.http.info.WeiXinTokenInfo;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.MarketModel;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.http.model.PublishModel;
import com.example.ylf019.zlxandroid.http.model.WeiXinUserInfoModel;
import com.example.ylf019.zlxandroid.http.model.ZlxHomePageModel;
import com.example.ylf019.zlxandroid.http.model.ZlxLoginModel;
import com.example.ylf019.zlxandroid.http.model.ZlxMyFansModel;
import com.example.ylf019.zlxandroid.http.model.ZlxObjectListModel;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * @author guozhangyu  create by 2017/8/22 19:32
 */
public class NethHandle {
    //采取单例模式
    private static NethHandle nethHandle = new NethHandle();

    public static NethHandle getNethHandle() {
        if (nethHandle == null) {
            nethHandle = new NethHandle();
        }
        return nethHandle;
    }

    private NethHandle() {

    }

    //首页获取所有用户
    public void getUserInfo(MyCallBack<MarketModel> myCallBack) {
        HttpManager.getHttpManager().post(URLConfig.getBaseUrl() + "/user/getUserListNew", myCallBack);
    }

    /**
     * 上传用户定位的经纬度
     */
    public void updateMyLocation(String latitude, String longitude, String lx_num, MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("latitude", latitude)
                .addParam("longitude", longitude)
                .addParam("lx_num", lx_num)
                .post(URLConfig.getBaseUrl() + "/User/updatePersonalData", myCallBack);
    }


    /**
     * 发现附近的老乡
     */
    public void findUserList(String page, String page_size, String province, String lx_num, String label, MyCallBack<List<WeiboInfo>> myCallBack) {
        HttpManager.getHttpManager()
                   .addParam("page", page)
                   .addParam("page_size", page_size)
                   .addParam("province", province)
                   .addParam("lx_num", lx_num)
                   .addParam("label", label)
                   .post(URLConfig.getBaseUrl() + "/space/selectDynamicListNew", myCallBack);
    }

    /**
     * 个人信息
     */
    public void getPersonCenterInfo(String lx_num, MyCallBack<PersonCenterModel> myCallBack) {
        HttpManager.getHttpManager()
                   .addParam("lx_num", lx_num)
                   .post(URLConfig.getBaseUrl() + "/User/userSelectOneNew", myCallBack);
    }

    /**
     * 个人信息2
     */
    public void getPersonCenterInfo(String lx_num, String my_lx_num, MyCallBack<PersonCenterModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num", lx_num)
                .addParam("my_lx_num", my_lx_num)
                .post(URLConfig.getBaseUrl() + "/User/userSelectOneNew", myCallBack);
    }

    /**
     * 获取留言信息
     */
    public void getLeaveMsgList(String lx_num, String my_lx_num, MyCallBack<LeaveMsgModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num",lx_num)
                .addParam("my_lx_num",my_lx_num)
                .post(URLConfig.getBaseUrl() + "/Space/getMyLeaveMsgNew", myCallBack);
    }

    /**
     * 个人动态
     */
    public void getPersonSplaceBBList(String lx_num, String my_lx_num, MyCallBack<List<WeiboInfo>> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num",lx_num)
                .addParam("my_lx_num",my_lx_num)
                .post(URLConfig.getBaseUrl() + "/Space/selectDynamicOneNew", myCallBack);
    }

    /**
     * 我的粉丝列表
     */
    public void getMyFansList(String lx_num, MyCallBack<ZlxMyFansModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num",lx_num)
                .addParam("sel_type", "yourfans")
                .post(URLConfig.getBaseUrl() + "/Space/getMyfansNew", myCallBack);
    }

    /**
     * 我的关注列表
     */
    public void getMyGuanzhuList(String lx_num, MyCallBack<ZlxMyFansModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num",lx_num)
                .addParam("sel_type", "myfans")
                .post(URLConfig.getBaseUrl() + "/Space/getMyfansNew", myCallBack);
    }

    /**
     * 图片上传
     */
    public void loadImage(File file, Map<String, String> maps, MyCallBack<PublishModel> myCallBack) {
        HttpManager.getHttpManager()
                .loadImage(URLConfig.getBaseUrl() + "/user/uploadFile", file, maps, myCallBack);
    }

    /**
     *发布说说
     */
    public void publishWeibo(String open_id, String content, String img_url, String label, String lx_num, MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("open_id",open_id)
                .addParam("content", content)
                .addParam("img_url", img_url)
                .addParam("label", label)
                .addParam("lx_num", lx_num)
                .post(URLConfig.getBaseUrl() + "/user/saveDynamic", myCallBack);
    }

    /**
     * 发布说说
     */

    public void publishWeibo(String open_id, String content, String label, String lx_num, MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("open_id",open_id)
                .addParam("content", content)
                .addParam("label", label)
                .addParam("lx_num", lx_num)
                .post(URLConfig.getBaseUrl() + "/user/saveDynamic", myCallBack);
    }

    /**
     *点赞微博
     */
    public void requestLoveWeibo(String lx_num, String dy_user, String dynamic_id, MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num", lx_num)
                .addParam("dynamic_id", dynamic_id)
                .addParam("dy_user", dy_user)
                .post(URLConfig.getBaseUrl() + "/space/addDynamicZanNew", myCallBack);
    }

    /**
     *微信登陆获取accessToken
     */
    public void getAccessToken(String appid, String secret, String code, MyCallBack<WeiXinTokenInfo> myCallBack) {
        HttpManager.getHttpManager()
//                .addParam("appid", appid)
//                .addParam("secret", secret)
//                .addParam("code", code)
//                .addParam("grant_type", "client_credential")
                .get("https://api.weixin.qq.com/sns/oauth2/access_token?"+"appid="+appid+""+"&secret="+secret+"&code="+code+"&grant_type=authorization_code", myCallBack);
    }

    /**
     *微信登陆获取用户信息
     */
    public void getWeiXinUserInfo(String access_token, String openid, MyCallBack<WeiXinUserInfoModel> myCallBack) {
        HttpManager.getHttpManager()
                .get("https://api.weixin.qq.com/sns/userinfo?"+"access_token="+access_token+
                        "&openid="+openid, myCallBack);
    }


    /**
     *对微博进行评论
     */
    public void addWeiboComment(String comment_id, String comment_content, String user_id, String to_uid, MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("comment_id", comment_id)
                .addParam("comment_content", comment_content)
                .addParam("user_id", user_id)
                .addParam("to_uid", to_uid)
                .post(URLConfig.getBaseUrl() + "/space/addCommentParamNew",myCallBack);
    }

    /**
     * 关注某个对象
     */
    public void followVillager(String u_lx_num, String follow_lx_num, MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("u_lx_num", u_lx_num)
                .addParam("follow_lx_num", follow_lx_num)
                .post(URLConfig.getBaseUrl() + "/user/followVillagerNew", myCallBack);
    }

    /**
     * 取消关注某个对象
     */
    public void cancelMyfans(String id, MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                .addParam("id", id)
                .post(URLConfig.getBaseUrl() + "/space/cancelMyfans", myCallBack);
    }

    /**
     * 说说详情页面 http://minipro.cms.com/space/getOneDynamic
     */
    public void getWeiboDetail(String lx_num, String id, MyCallBack<List<WeiboInfo>>myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num", lx_num)
                .addParam("id", id)
                .post(URLConfig.getBaseUrl() + "/space/getOneDynamicNew", myCallBack);
    }

    /**
     * 完善个人资料
     */
    public void saveMyInfo(String sex, String area, String city, String province, String address, String born_date, String education,
                           String vocation, String lx_num, String height, String label,MyCallBack<BaseModel> myCallBack) {
        HttpManager.getHttpManager()
                    .addParam("sex", sex)
                    .addParam("area", area)
                    .addParam("city", city)
                    .addParam("province", province)
                    .addParam("address", address)
                    .addParam("born_date", born_date)
                    .addParam("education", education)
                    .addParam("vocation", vocation)
                    .addParam("lx_num", lx_num)
                    .addParam("height", height)
                    .addParam("label", label)
                    .post(URLConfig.getBaseUrl() + "/User/updatePersonalData", myCallBack);
    }

    /**
     * 获取找对象,推荐页面数据
     */
    public void getUserObjectList(String province, String sex, String object_age_upper, String object_age_low, String object_height_upper,
                                  String object_height_low, String page, MyCallBack<ZlxObjectListModel>myCallBack) {
        HttpManager.getHttpManager()
                    .addParam("province", province)
                    .addParam("sex", sex)
                    .addParam("object_age_upper", object_age_upper)
                    .addParam("object_age_low", object_age_low)
                    .addParam("object_height_upper", object_height_upper)
                    .addParam("object_height_low", object_height_low)
                    .addParam("page", page)
                    .post(URLConfig.getBaseUrl() + "/user/selectObjectUserList", myCallBack);
    }

    /**
     * 查询数据根据unionid
     */
    public void selectUserByUnionid(String union_id, MyCallBack<ZlxLoginModel>myCallBack) {
        HttpManager.getHttpManager()
                .addParam("union_id", union_id)
                .post(URLConfig.getBaseUrl() + "/user/selectUserByUnionid", myCallBack);
    }

    /**
     * 更新心动的她数据
     */
    public void updateUserInfo(String object_height_upper, String object_height_low, String object_age_upper, String object_age_low,
                               String object_occupation, String object_label, String lx_num, MyCallBack<BaseModel>myCallBack) {
        HttpManager.getHttpManager()
                .addParam("object_height_upper", object_height_upper)
                .addParam("object_height_low", object_height_low)
                .addParam("object_age_upper", object_age_upper)
                .addParam("object_age_low", object_age_low)
                .addParam("object_occupation", object_occupation)
                .addParam("object_label", object_label)
                .addParam("lx_num", lx_num)
                .post(URLConfig.getBaseUrl() + "/user/updateUserInfo", myCallBack);
    }

    /**
     * 个人主页
     */
    public void getNewSpaceInfoNew(String lx_num, String other_lx_num, MyCallBack<ZlxHomePageModel>myCallBack) {
        HttpManager.getHttpManager()
                .addParam("lx_num", lx_num)
                .addParam("other_lx_num", other_lx_num)
                .post(URLConfig.getBaseUrl() + "/user/getNewSpaceInfoNew", myCallBack);
    }

    /**
     * 个人空间留言
     */
    public void leaveMessage(String content, String to_lx_num, String from_lx_num, MyCallBack<BaseModel>myCallBack) {
        HttpManager.getHttpManager()
                .addParam("content", content)
                .addParam("to_lx_num", to_lx_num)
                .addParam("from_lx_num", from_lx_num)
                .post(URLConfig.getBaseUrl() + "/space/leaveMsgNew", myCallBack);
    }

    /**
     * 找对象模糊查询
     */
    public void searchObjectList(String nickname, MyCallBack<ZlxObjectListModel>myCallBack) {
        HttpManager.getHttpManager()
                .addParam("nickname", nickname)
                .post(URLConfig.getBaseUrl() + "/user/selectObjectUserList", myCallBack);
    }

    /**
     * 新用户数据更新
     */
    public void newUserInfo(String nickname, String sex, String province, String city, String open_id,
                            String headurl, String lx_num, MyCallBack<ZlxObjectListModel>myCallBack) {
        HttpManager.getHttpManager()
                .addParam("nickname", nickname)
                .addParam("sex", sex)
                .addParam("province", province)
                .addParam("city", city)
                .addParam("open_id", open_id)
                .addParam("headurl", headurl)
                .addParam("lx_num", lx_num)
                .post(URLConfig.getBaseUrl() + "/user/updateUserInfo", myCallBack);
    }


    public void removeAllMessage() {
        HttpManager.getHttpManager().removeAllMessage();
    }

}
