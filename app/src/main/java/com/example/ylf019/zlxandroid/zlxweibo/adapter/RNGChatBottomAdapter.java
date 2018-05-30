package com.example.ylf019.zlxandroid.zlxweibo.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ylf019.zlxandroid.utils.DisplayRules;
import com.example.ylf019.zlxandroid.utils.Utils;


/**
 * Description ${TODO}
 * Created by yangjinxin on 2017/9/5.
 */

public class RNGChatBottomAdapter extends RecyclerView.Adapter<RNGChatBottomAdapter.MyViewHolder> {


    private EditText       mEditContent;
    private Context        mContext;
    private DisplayRules[] values;

    public RNGChatBottomAdapter(Context context, EditText etmsg) {
        mContext = context;
        mEditContent = etmsg;
        values = DisplayRules.values();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
//        GridLayoutManager.LayoutParams layoutParams = new GridLayoutManager.LayoutParams(Utils.dip2px(mContext,28),Utils.dip2px(mContext,28));
        GridLayoutManager.LayoutParams layoutParams = new GridLayoutManager.LayoutParams(Utils.dip2px(mContext,52), Utils.dip2px(mContext,40));
//        layoutParams.setMargins(Utils.dip2px(mContext,12),Utils.dip2px(mContext,9),Utils.dip2px(mContext,12),Utils.dip2px(mContext,3));
        imageView.setPadding(34,9,34,3);
        imageView.setLayoutParams(layoutParams);
        return new MyViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        ImageView itemView = (ImageView) holder.itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (position == 0) {
//                    //删除功能
//                    KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_BACK, 0, 0, 0,
//                            0, KeyEvent.KEYCODE_ENDCALL);
//                    mEditContent.dispatchKeyEvent(event);
//                } else {
//                    String remote = values[position].getRemote();
                    if(mEditContent != null) {
                        String remote = values[position].getEmojiStr();
                        SpannableString spannableString = new SpannableString(remote);
                        Drawable drawable = mContext.getResources().getDrawable(values[position].getResId());
                        int textSize = (int) mEditContent.getTextSize();
                        drawable.setBounds(0, 0, textSize, textSize);
                        ImageSpan imageSpane = new ImageSpan(drawable);
                        spannableString.setSpan(imageSpane, 0, remote.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Editable editableText = mEditContent.getEditableText();
                        int selectionStart = mEditContent.getSelectionStart();
                        editableText.insert(selectionStart, spannableString);
                    }
//                }
            }
        });
        itemView.setImageResource(values[position].getResId());
    }

    @Override
    public int getItemCount() {
        return values.length;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
