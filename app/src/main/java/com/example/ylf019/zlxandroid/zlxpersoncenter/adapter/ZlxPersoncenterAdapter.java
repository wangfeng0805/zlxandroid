package com.example.ylf019.zlxandroid.zlxpersoncenter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.home.adapter.ZLXWeiboListAdapter;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.view.RoyalSwitch;
import com.example.ylf019.zlxandroid.zlxpersoncenter.MyBaseAdapter;

import java.util.List;

/**
 * Created by yjx on 2018/4/16.
 */

public class ZlxPersoncenterAdapter extends MyBaseAdapter<PersonCenterModel.InfoBean> {


    private onItemViewClick onClick;

    public ZlxPersoncenterAdapter(Context context, List<PersonCenterModel.InfoBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        switch(position) {
            case 0:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter1, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    holder.tv_title2 = (TextView) convertView.findViewById(R.id.text2);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                PersonCenterModel.InfoBean newsBean = getItem(position);
                holder.tv_title1.setText("老乡号: "+ newsBean.getLx_num());
                holder.tv_title2.setText("性别: "+newsBean.getSex()+"  年龄: "+newsBean.getAge()+"  城市: "+newsBean.getCity());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClick != null) {
                            onClick.onStartClick(position);
                        }
                    }
                });
                break;

            case 1:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter4, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_title1.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.icon_love, 0, 0, 0);
                holder.tv_title1.setText("心动的她");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClick != null) {
                            onClick.onStartClick(position);
                        }
                    }
                });

                break;

            case 2:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter2, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_title1.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.speak, 0, 0, 0);
                holder.tv_title1.setText("进入我的空间");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClick != null) {
                            onClick.onStartClick(position);
                        }
                    }
                });
                break;
            case 3:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter2, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_title1.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.fensi, 0, 0, 0);
                holder.tv_title1.setText("关注我的老乡");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClick != null) {
                            onClick.onStartClick(position);
                        }
                    }
                });
                break;
            case 4:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter4, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_title1.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.guanzhu, 0, 0, 0);
                holder.tv_title1.setText("我关注的老乡");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClick != null) {
                            onClick.onStartClick(position);
                        }
                    }
                });
                break;

            case 5:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter2, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_title1.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.icon_help, 0, 0, 0);
                holder.tv_title1.setText("帮助");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClick != null) {
                            onClick.onStartClick(position);
                        }
                    }
                });

                break;

            case 6:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter3, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    holder.mRoyalSwitch = convertView.findViewById(R.id.switch_botton);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
//                holder.tv_title1.setCompoundDrawablesWithIntrinsicBounds
//                        (R.drawable.suo, 0, 0, 0);
                holder.tv_title1.setText("隐藏自己的位置");
                holder.mRoyalSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b) {
                            //todo 隐藏位置
                        }else {
                            //todo 不隐藏位置
                        }
                    }
                });
                break;

            case 7:
                if (convertView == null) {
                    convertView = View.inflate(mContext, R.layout.item_perosncenter2, null);
                    holder = new ViewHolder();
                    holder.tv_title1 = (TextView) convertView.findViewById(R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.tv_title1.setCompoundDrawablesWithIntrinsicBounds
                        (R.drawable.icon_item_close, 0, 0, 0);
                holder.tv_title1.setText("退出登录");
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(onClick != null) {
                            onClick.onStartClick(position);
                        }
                    }
                });

                break;
        }

        return convertView;
    }

    public void onItemViewClick(ZlxPersoncenterAdapter.onItemViewClick onClick) {
        this.onClick = onClick;
    }

    public interface onItemViewClick {
        void onStartClick(int position);
    }


    static class  ViewHolder{
        ImageView iv;
        TextView tv_title1;
        TextView tv_title2;
        RoyalSwitch mRoyalSwitch;
    }

}
