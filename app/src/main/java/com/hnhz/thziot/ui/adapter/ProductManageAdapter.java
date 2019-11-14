package com.hnhz.thziot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.helper.GlideHelper;
import com.hnhz.thziot.http.URLManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhou jiaqi on 2019/8/14.
 */
public class ProductManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ProductBean> productAllList;


    public ProductManageAdapter(Context context, List<ProductBean> productAllList) {
        this.context = context;
        this.productAllList = productAllList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listitem_product_manage, parent, false);
        return new ProductManageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductBean item = productAllList.get(position);
        ProductManageViewHolder viewHolder = (ProductManageViewHolder) holder;

        viewHolder.tvProductEntrance.setText(item.getProductName());

        Glide.with(context).load(URLManager.FILE_URL+item.getImageUrl())
                .apply(GlideHelper.productImgOptions())
                .into(viewHolder.ivProductEntrance);

        if(item.isFollowed()){
            viewHolder.cbFollowed.setChecked(true);
        }else{
            viewHolder.cbFollowed.setChecked(false);
        }

        viewHolder.itemView.setOnClickListener(v ->{
            if(item.isFollowed()){
                item.setFollowed(false);
            }else{
                item.setFollowed(true);
            }
            notifyItemChanged(position);
        });

        if(item.getIsUse()==1) {
            viewHolder.ivHasManage.setVisibility(View.VISIBLE);
        }else{
            viewHolder.ivHasManage.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return productAllList ==null?0: productAllList.size();
    }

    public List<ProductBean> getData(){
        return productAllList;
    }


    public class ProductManageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_entrance)
        ImageView ivProductEntrance;
        @BindView(R.id.tv_product_entrance)
        TextView tvProductEntrance;
        @BindView(R.id.cb_followed)
        CheckBox cbFollowed;
        @BindView(R.id.iv_has_manage)
        ImageView ivHasManage;

        public ProductManageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
