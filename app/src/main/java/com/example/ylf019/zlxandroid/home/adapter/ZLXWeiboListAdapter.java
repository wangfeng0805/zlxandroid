package com.example.ylf019.zlxandroid.home.adapter;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.appevent.WeiBoLikeEvent;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.http.info.ZLXImageInfo;
import com.example.ylf019.zlxandroid.http.info.ZlxLoveInfo;
import com.example.ylf019.zlxandroid.utils.DateHelper;
import com.example.ylf019.zlxandroid.utils.SharedPreferencesHelper;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.utils.ViewHelper;
import com.example.ylf019.zlxandroid.view.WeiBoContentTextView;
import com.example.ylf019.zlxandroid.view.WeiBoPictureView;
import com.example.ylf019.zlxandroid.zlxfind.MySpaceActivity;
import com.example.ylf019.zlxandroid.zlxfind.OtherSpaceActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * @author guozhangyu  create by 2017/11/10 17:55
 * @Description
 */
public class ZLXWeiboListAdapter extends BaseQuickAdapter<WeiboInfo, BaseViewHolder> {
    private onItemViewClick onClick;
    private int mContextMenuPosition;
    public int menuGroupId = 999;//上下文菜单组Id
    public int groupId;

    public ZLXWeiboListAdapter(int layoutResId, List<WeiboInfo> data) {
        super(layoutResId, data);
    }

    //设置上下文菜单的位置
    public void setContextPosition(int position) {
        mContextMenuPosition = position;
    }

    //获取上下文菜单的位置(有头部)
    public int getContextPositionByHead() {
        return mContextMenuPosition - 1;//对header的处理
    }

    //获取上下文菜单的位置(无头部)
    public int getContextPosition() {
        return mContextMenuPosition;
    }

    public void changeItemLikeByPosition(int position, WeiBoLikeEvent event) {
        if (event.isLike) {
            mData.get(position).setIs_zan(1);
            mData.get(position).zan_count = mData.get(position).zan_count + 1;
        } else {
            mData.get(position).setIs_zan(0);
            mData.get(position).zan_count = mData.get(position).zan_count - 1;
        }
        setData(position, mData.get(position));
    }
//
//    /**
//     * 更新评论数量状态数据
//     */
//    public void updateCommentStatus(int position, WeiBoCommentEvent event) {
//        if (null != event) {
//            if (event.isAdd)
//                mData.get(position).comment_num = mData.get(position).comment_num + 1;
//            else
//                mData.get(position).comment_num = mData.get(position).comment_num - 1;
//            setData(position, mData.get(position));
//        }
//    }

    @Override
    protected void convert(final BaseViewHolder helper, final WeiboInfo item) {
        helper.setText(R.id.tv_user_name, item.getNickname())
                .setText(R.id.tv_time, DateHelper.getWeiBoDataTime(item.getAdd_time()))
                .setText(R.id.tv_comment, item.getArr().size()+"").setText(R.id.tv_like, item.getZan_count() +"");
//                setText(R.id.tv_comment, item.getComment_num() + "").setText(R.id.tv_like, item.getLove_num() + "");
        LinearLayout llItem = helper.getView(R.id.ll_item);
//        ImageView ivIsTop = helper.getView(R.id.iv_is_top);
//        ImageView mIvGuan = helper.getView(R.id.iv_guan);
//        UserAvatarImageView mIvAvatar = helper.getView(R.id.iv_avatar);
        CircleImageView mIvAvatar = helper.getView(R.id.iv_avatar);
//        TextView tvPhoneSource = helper.getView(R.id.tv_phone_source); //来自手机显示
        final ImageView mIvMore = helper.getView(R.id.iv_more);//todo 关注
//        FrameLayout mFlVideo = helper.getView(R.id.fl_audio);
//        final ImageView mIvAudio = helper.getView(R.id.iv_audio);
        WeiBoContentTextView mTvContent = helper.getView(R.id.tv_content);
//        LinearLayout mLlAudio = helper.getView(R.id.ll_audio);
//        TextView mTvAudioTime = helper.getView(R.id.tv_audio_time);
        WeiBoPictureView mPictureView = helper.getView(R.id.picture_view);
        LinearLayout mLlComment = helper.getView(R.id.ll_comment);
        LinearLayout mLlLike = helper.getView(R.id.ll_like);
        final TextView mTvLike = helper.getView(R.id.tv_like);

        if(item.getU_id().isEmpty()) {
            mIvMore.setImageResource(R.drawable.personal_follow_btn);
        }else {
            mIvMore.setImageResource(R.drawable.personal_followed_btn);
        }

        mIvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getU_id().isEmpty()) {
                    if (onClick != null) {
                        onClick.onFollowBtnClick(item.getLx_num(), helper.getPosition(), false);
                    }
                }else {
                    if (onClick != null) {
                        onClick.onFollowBtnClick(item.getDy_user_id(), helper.getPosition(),true);
                    }
                }
            }
        });

//        final JCVideoPlayerStandard mVideoplayer = helper.getView(R.id.videoplayer);
//        //基本信息
//        if (item.user_type == 0) {
//            mIvAvatar.showUserAvatar2(item.avatar, false,item.pendant);
//            ViewHelper.setGone(mIvGuan, true);
//            mTvContent.setUrlEnable(false);
//        } else {
//            mIvAvatar.showUserAvatar2(item.avatar, true,"");
//            ViewHelper.setGone(mIvGuan, false);
//            mTvContent.setUrlEnable(true);
//        }
            Glide.with(mContext).load(item.getHeadurl()).placeholder(R.drawable.sidebar_default_head_icon).dontAnimate().into(mIvAvatar);
//        if (item.top == 1)
//            ViewHelper.setGone(ivIsTop, false);
//        else
//            ViewHelper.setGone(ivIsTop, true);
//        if (StringHelper.isEmpty(item.getSource())) {
//            tvPhoneSource.setText("");
//            ViewHelper.setGone(tvPhoneSource, true);
//        } else {
//            ViewHelper.setGone(tvPhoneSource, false);
//            tvPhoneSource.setText("来自" + item.getSource());
//        }
//        int textSize = (int) mTvContent.getTextSize();
//        SpannableString spannableString = ExpressionUtil.getSpannableString(item.content, mContext, textSize - 22);
//        mTvContent.setWeiBoContent(spannableString);
//        if (StringHelper.notEmpty(item.audio)) {
//            ViewHelper.setGone(mLlAudio, false);
//            if (StringHelper.notEmpty(item.getAudio_length()))
//                mTvAudioTime.setText(String.format(mContext.getString(R.string.tips_audio_time_format), item.getAudio_length()));
//        } else
//            ViewHelper.setGone(mLlAudio, true);
//        if (item.getPic() != null && item.getPic().size() > 0) {
//            ViewHelper.setGone(mPictureView, false);
//            mPictureView.bindViewData(item.getImages(), item.getSize());
//        } else {
//            ViewHelper.setGone(mPictureView, true);
//        }

//        if() {
//
//        }


        mTvContent.setWeiBoContent(item.getContent());
        if (item.getImg_url_arr() != null && item.getImg_url_arr().size() > 0) {
            ViewHelper.setGone(mPictureView, false);
            List<ZLXImageInfo> list = new ArrayList<>();
            for (String s : item.getImg_url_arr()) {
                ZLXImageInfo zlxImageInfo = new ZLXImageInfo();
                zlxImageInfo.setPics("https://waptest1.ylfcf.com/Data/upload/" + s);
                zlxImageInfo.setType(0);
                list.add(zlxImageInfo);
            }
            mPictureView.bindViewData(list, item.getImg_url_arr().size());
        } else {
            ViewHelper.setGone(mPictureView, true);
        }

        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getLx_num().equals(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LX_NUM))) {
                    Intent intent = new Intent(mContext, MySpaceActivity.class);
                    intent.putExtra("lx_num", item.getLx_num());
                    mContext.startActivity(intent);
                }else {
                    Intent intent = new Intent(mContext, OtherSpaceActivity.class);
                    intent.putExtra("lx_num", item.getLx_num());
                    mContext.startActivity(intent);
                }
            }
        });

        mTvLike.setCompoundDrawablesWithIntrinsicBounds
                (item.getIs_zan() == 1 ? R.drawable.icon_liked : R.drawable.icon_unlike, 0, 0, 0);
//        //普通视频处理
//        if(item.getVideo() != null && item.getVideo_thumb() != null) {
//            mVideoplayer.setVisibility(View.VISIBLE);
//            mVideoplayer.widthRatio = 9;
//            mVideoplayer.heightRatio = 13;
//            mVideoplayer.setUp(item.getVideo(), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "");
//            Glide.with(mContext).load(item.getVideo_thumb()).asBitmap().into(mVideoplayer.thumbImageView);
////            mVideoplayer.startButton.performClick();
////            mVideoplayer.bottomProgressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_horizontal));
////            mVideoplayer.progressBar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.progress_horizontal));
//        }else {
//            mVideoplayer.setVisibility(View.GONE);
//        }
//
        llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClick)
                    onClick.onLlitemClick(helper.getPosition(), item);
            }
        });
        mTvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClick)
                    onClick.onLlitemClick(helper.getPosition(), item);
            }
        });
//        ((Activity) mContext).registerForContextMenu(mIvMore);
//        mIvMore.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                menu.clear();
//                menuGroupId = Utils.generateViewId();
//                groupId = menuGroupId;
//                if (SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LOGIN_ID).equals(item.getUser_id())) {
//                    menu.add(menuGroupId, 3, 3, "删除");
//                } else {
//                    menu.add(menuGroupId, 1, 1, item.had_follow == 1 ? "取消关注" : "关注");
//                    menu.add(menuGroupId, 2, 2, "举报");
//                }
//                menu.add(menuGroupId, 4, 4, "取消");
//                setContextPosition(helper.getPosition());
//            }
//        });
//        mIvMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mIvMore.showContextMenu();
//            }
//        });
//        mIvAvatar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (item != null) {
//                    if (StringHelper.notEmpty(item.getUser_id())) {
//                        Intent intent = new Intent();
//                        if (SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LOGIN_ID).equals(item.getUser_id()))
//                            intent.setClass(mContext, RNGPersonCenterActivity.class);
//                        else
//                            intent.setClass(mContext, RNGPersonCenterDetailActivity.class);
//                        intent.putExtra(AppContact.PARAM_KEY_ID, item.getUser_id());
//                        mContext.startActivity(intent);
//                    }
//                }
//            }
//        });
        mLlComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onClick)
                    onClick.onLlitemClick(helper.getPosition(), item);
            }
        });
        mLlLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != item && StringHelper.notEmpty(item.getOpen_id())) {
                    boolean isLike = (item != null && item.getIs_zan() == 1);
                    if (isLike) {//已点赞,取消点赞
                        if (onClick != null)
                            onClick.onLikeClick(helper.getPosition(), item.getOpen_id(), item.getId(),item.getDy_user_id()+"",true);
                    } else {//没有点赞，点赞
                        if (onClick != null)
                            onClick.onLikeClick(helper.getPosition(), item.getOpen_id(), item.getId(),item.getDy_user_id()+"", false);
                    }
                }
            }
        });
    }

    public void onItemViewClick(onItemViewClick onClick) {
        this.onClick = onClick;
    }

    public interface onItemViewClick {
        void onLikeClick(int position, String openId, String id, String dy_user,boolean isLike);

        void onLlitemClick(int position, WeiboInfo item);

        void onFollowBtnClick(String follow_u_id, int pos, boolean isFollowed);
    }

}
