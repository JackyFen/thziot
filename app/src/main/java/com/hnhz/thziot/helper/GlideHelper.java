package com.hnhz.thziot.helper;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hnhz.thziot.R;

/**
 * Created by Zhou jiaqi on 2019/8/29.
 */
public class GlideHelper {

    public static RequestOptions headImgRequestOptions(){
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .skipMemoryCache(true)
                .error(R.mipmap.icon_default_avatar)
                .placeholder(R.mipmap.icon_default_avatar)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);

        return options;
    }

    public static RequestOptions productImgOptions(){
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .skipMemoryCache(true)
                //.error(R.mipmap.icon_default_avatar)
                //.placeholder(R.mipmap.icon_default_avatar)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.HIGH);

        return options;
    }
}
