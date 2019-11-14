package com.hnhz.thziot.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.helper.GlideHelper;
import com.hnhz.thziot.http.URLManager;
import com.hnhz.thziot.realm.operator.ProductRealmOp;
import com.hnhz.thziot.realm.realmobj.ProductRealmObj;
import com.hnhz.thziot.ui.widget.recyclerview.OnItemMoveListener;
import com.kq.platform.kq_common.ui.widget.SquareRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhou jiaqi on 2019/7/29.
 */
public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemMoveListener {


    private final int VIEW_TYPE_PRODUCT = 1;
    private final int VIEW_TYPE_ADD = 2;

    private Context context;
    private ItemTouchHelper itemTouchHelper;


    // touch 点击开始时间
    private long startTime;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long SPACE_TIME = 100;

    private boolean isEditMode;

    private OnProductItemClickListener onProductItemClickListener;
    private OnProductAddClickListener onProductAddClickListener;
    private OnProductDeleteClickListener onProductDeleteClickListener;

    private List<ProductBean> productList;

    public ProductAdapter(Context context, ItemTouchHelper itemTouchHelper, List<ProductBean> productList) {
        this.context = context;
        this.itemTouchHelper = itemTouchHelper;
        this.productList = productList;
    }

    public ProductAdapter(Context context, ItemTouchHelper itemTouchHelper) {
        this.context = context;
        this.itemTouchHelper = itemTouchHelper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_PRODUCT) {
            View view = LayoutInflater.from(context).inflate(R.layout.listitem_product, parent, false);
            return new ProductViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.listitem_product_add,parent,false);
            return new ProductAddViewHolder(view);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if(viewHolder instanceof  ProductViewHolder) {
            ProductViewHolder holder = (ProductViewHolder) viewHolder;
            ProductBean item = productList.get(position);

            holder.tvProductEntrance.setText(item.getProductName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEditMode) {
                        int position = holder.getAdapterPosition();
                        if(onProductItemClickListener!=null) {
                            onProductItemClickListener.onItemClick(v, position);
                        }
                    }
                }
            });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if(isEditMode){
//                    itemTouchHelper.startDrag(holder);
//                }
//                return true;
//            }
//        });

            viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (isEditMode) {
                        switch (MotionEventCompat.getActionMasked(event)) {
                            case MotionEvent.ACTION_DOWN:
                                startTime = System.currentTimeMillis();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (System.currentTimeMillis() - startTime > SPACE_TIME) {
                                    itemTouchHelper.startDrag(holder);
                                }
                                break;
                            case MotionEvent.ACTION_CANCEL:
                            case MotionEvent.ACTION_UP:
                                startTime = 0;
                                break;
                        }

                    }
                    return false;
                }
            });

            Glide.with(context).load(URLManager.FILE_URL+item.getImageUrl())
                    .apply(GlideHelper.productImgOptions())
                    .into(holder.ivProductEntrance);

            if (isEditMode) {
                holder.ivProductDel.setVisibility(View.VISIBLE);
            } else {
                holder.ivProductDel.setVisibility(View.GONE);
            }

            if(item.getUsable()==1){
                holder.ivHasManage.setVisibility(View.VISIBLE);
            }else{
                holder.ivHasManage.setVisibility(View.GONE);
            }

            holder.ivProductDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(holder.getAdapterPosition());
                    ProductRealmOp.unFollowProduct(item);
                    if(onProductDeleteClickListener!=null) {
                        onProductDeleteClickListener.onDeleteClick(v, position);
                    }
                }
            });
        }else if(viewHolder instanceof ProductAddViewHolder){
            ProductAddViewHolder holder = (ProductAddViewHolder) viewHolder;

            if(isEditMode){
                holder.itemView.setVisibility(View.GONE);
            }else{
                holder.itemView.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onProductAddClickListener!=null) {
                        onProductAddClickListener.onAddClick(v, position);
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return productList==null?0:productList.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==productList.size()){
            return VIEW_TYPE_ADD;
        }else{
            return VIEW_TYPE_PRODUCT;
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ProductBean item = productList.get(fromPosition);
        productList.remove(fromPosition);
        productList.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    private void removeItem(int position){
        productList.remove(position);
        notifyItemRemoved(position);
    }

    public void changeEditMode() {
        isEditMode = !isEditMode;
        if(!isEditMode){
            ProductRealmOp.updateFollowedProductListPosition(productList);
        }
        notifyDataSetChanged();
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setData(List<ProductBean> productFollowedList){
        this.productList = productFollowedList;
        notifyDataSetChanged();
    }

    public interface OnProductItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnProductAddClickListener {
        void onAddClick(View v, int position);
    }

    public interface OnProductDeleteClickListener{
        void onDeleteClick(View v, int position);
    }

    public void setOnProductItemClickListener(OnProductItemClickListener listener){
        this.onProductItemClickListener = listener;
    }

    public void setOnProductAddClickListener(OnProductAddClickListener listener){
        this.onProductAddClickListener = listener;
    }

    public void setOnProductDeleteClickListener(OnProductDeleteClickListener listener){
        this.onProductDeleteClickListener = listener;
    }


    public List<ProductBean> getData(){
        return productList;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_product_entrance)
        ImageView ivProductEntrance;
        @BindView(R.id.tv_product_entrance)
        TextView tvProductEntrance;
        @BindView(R.id.iv_product_del)
        ImageView ivProductDel;
        @BindView(R.id.rl_product_entrance)
        SquareRelativeLayout rlProductEntrance;
        @BindView(R.id.iv_has_manage)
        ImageView ivHasManage;

        public ProductViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ProductAddViewHolder extends RecyclerView.ViewHolder{

        public ProductAddViewHolder(View itemView){
            super(itemView);
        }
    }
}
