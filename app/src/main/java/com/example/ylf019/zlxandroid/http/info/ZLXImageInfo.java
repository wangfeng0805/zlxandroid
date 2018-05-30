package com.example.ylf019.zlxandroid.http.info;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author guozhangyu  create by 2017/9/25 13:00
 * @Description
 */
public class ZLXImageInfo implements Parcelable {

    private int    type;
    private String pics;

    public ZLXImageInfo(Parcel in) {
        type = in.readInt();
        pics = in.readString();
    }


    public static final Creator<ZLXImageInfo> CREATOR = new Creator<ZLXImageInfo>() {
        @Override
        public ZLXImageInfo createFromParcel(Parcel in) {
            return new ZLXImageInfo(in);
        }

        @Override
        public ZLXImageInfo[] newArray(int size) {
            return new ZLXImageInfo[size];
        }
    };

    public ZLXImageInfo() {

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPics() {
        return pics;
    }

    public void setPics(String pics) {
        this.pics = pics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(pics);
    }
}
