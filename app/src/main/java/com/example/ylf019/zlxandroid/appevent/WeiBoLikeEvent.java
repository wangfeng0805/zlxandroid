package com.example.ylf019.zlxandroid.appevent;

/**
 * Author:    guozhangyu
 * Description: weibo点赞事件
 */
public class WeiBoLikeEvent {
    public int id;//微博ID
    public boolean isLike;//是否点赞

    public WeiBoLikeEvent(int id, boolean isLike) {
        this.id = id;
        this.isLike = isLike;
    }
}
