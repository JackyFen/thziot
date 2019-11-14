package com.hnhz.thziot.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.realm.operator.ProductRealmOp;
import com.hnhz.thziot.ui.adapter.ProductManageAdapter;
import com.kq.platform.kq_common.ui.BaseActivity;
import com.kq.platform.kq_common.ui.widget.recyclerview.SpaceItemDecoration;
import com.kq.platform.kq_common.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Zhou jiaqi on 2019/8/14.
 */
public class ProductManageActivity extends BaseActivity {

    @BindView(R.id.rv_product)
    RecyclerView rvProduct;

    private ProductManageAdapter adapter;

    private List<ProductBean> productAllList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_manage;
    }

    @Override
    protected void initWidget() {
        initTitle();
        initData();

        GridLayoutManager manager = new GridLayoutManager(this, 4);
        rvProduct.setLayoutManager(manager);
        rvProduct.setHasFixedSize(true);
        rvProduct.addItemDecoration(new SpaceItemDecoration(4,SizeUtils.dp2px(this,1),false));


        adapter = new ProductManageAdapter(this,productAllList);
        rvProduct.setAdapter(adapter);
    }

    @Override
    protected void bindLogic() {

    }

    private void initTitle(){
        baseTitleBar.setTitle("所有产品");
        baseTitleBar.initVisible(true,false,false,true);
        baseTitleBar.setImgBtnLeftRe(R.mipmap.icon_back);
        baseTitleBar.setLeftListener(v -> finish());
        baseTitleBar.setRightTVText("完成");
        baseTitleBar.setRightTextViewListener(v -> complete());
    }

    private void initData(){
        //productAllList = getIntent().getParcelableArrayListExtra(ProductBean.class.getSimpleName());
        productAllList = ProductRealmOp.getAllProductList();
    }

    private void complete(){
        List<ProductBean> productAllList = adapter.getData();

//        List<ProductBean> productFollowedList = new ArrayList<>();
//        for(ProductBean  productBean : productAllList){
//            if(productBean.isFollowed()){
//                productFollowedList.add(productBean);
//            }
//        }

        ProductRealmOp.updateFollowedProductList(productAllList);
//        Intent intent = new Intent();
//        intent.putExtra(ProductBean.class.getSimpleName(),(ArrayList)productFollowedList);

        setResult(RESULT_OK);
        finish();
    }

}
