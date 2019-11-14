package com.hnhz.thziot.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.hnhz.thziot.presenter.ProductPresenter;
import com.hnhz.thziot.presenter.view.IProductView;
import com.hnhz.thziot.realm.operator.ProductRealmOp;
import com.hnhz.thziot.ui.adapter.ProductAdapter;
import com.hnhz.thziot.ui.widget.Circleview;
import com.hnhz.thziot.ui.widget.recyclerview.ItemDragHelperCallback;
import com.kq.platform.kq_common.ui.BaseCompactActivity;
import com.kq.platform.kq_common.ui.widget.base.TitleBar;
import com.kq.platform.kq_common.ui.widget.recyclerview.SpaceItemDecoration;
import com.kq.platform.kq_common.utils.SizeUtils;
import com.kq.platform.kq_common.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseCompactActivity<ProductPresenter> implements View.OnClickListener,IProductView {

    private final static int REQUEST_CODE_PRODUCT_MANAGE = 101;

    @BindView(R.id.rv_product)
    RecyclerView rvProduct;
    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.tv_alarm_count)
    TextView tvAlarmCount;
    @BindView(R.id.tv_available_product_count)
    TextView tvAvailableProductCount;
    @BindView(R.id.tv_my_work_order_count)
    TextView tvMyWorkOrderCount;
    @BindView(R.id.iv_user_photo)
    Circleview ivUserPhoto;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.nav)
    RelativeLayout navView;
    @BindView(R.id.base_title_bar)
    TitleBar titleBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private ProductAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new ProductPresenter(this);
        initWidget();
        bindLogic();
    }

    protected void initWidget() {
        initTitleBar();
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        rvProduct.setLayoutManager(manager);
        rvProduct.setHasFixedSize(true);
        rvProduct.addItemDecoration(new SpaceItemDecoration(4,SizeUtils.dp2px(this,1),false));

        int width = getResources().getDisplayMetrics().widthPixels/ 4 * 3;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) navView.getLayoutParams();
        params.width = width;
        navView.setLayoutParams(params);


        ItemDragHelperCallback callback = new ItemDragHelperCallback();
        final ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvProduct);

//        items = new ArrayList<>();
//        for (int i = 0; i < 18; i++) {
//            ProductBean entity = new ProductBean();
//            entity.setProductName("频道" + i);
//            if(i==1){
//                entity.setIsUse(1);
//            }
//            items.add(entity);
//        }

        adapter = new ProductAdapter(this, helper);
        rvProduct.setAdapter(adapter);

        presenter.getAllProduct();
    }

    protected void bindLogic() {
        tvEdit.setOnClickListener(this);
        adapter.setOnProductItemClickListener(new ProductAdapter.OnProductItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                ProductBean productBean = adapter.getData().get(position);
                //showMessage(position + ":" + productBean.getProductName());
//                Intent intent = new Intent();
//                intent.putExtra(ProductBean.class.getSimpleName(),productBean);
//                intent.setClass(MainActivity.this,ProductContentActivity.class);
//                startActivity(intent);

                presenter.getSubItem(productBean);
            }
        });

        adapter.setOnProductAddClickListener((v,position)->{

            Intent intent = new Intent();
            intent.setClass(MainActivity.this,ProductManageActivity.class);
            startActivityForResult(intent,REQUEST_CODE_PRODUCT_MANAGE);
        });

    }

    private void initTitleBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.base_head_color, false);
        titleBar.setTitle(getString(R.string.txt_main_title));
        titleBar.setImgBtnLeftRe(R.mipmap.icon_mine);
        titleBar.setLeftListener(v -> {
            if (drawerLayout.isDrawerOpen(navView)) {
                drawerLayout.closeDrawer(navView);
            } else {
                drawerLayout.openDrawer(navView);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_edit:
                if (adapter.isEditMode()) {
                    tvEdit.setText("编辑");
                } else {
                    tvEdit.setText("完成");
                }
                adapter.changeEditMode();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_PRODUCT_MANAGE:
                if(resultCode == RESULT_OK){
                    //List<ProductBean> followedProductList = data.getParcelableArrayListExtra(ProductBean.class.getSimpleName());
                    List<ProductBean> productBeanList = ProductRealmOp.getFollowedProductList();
                    adapter.setData(productBeanList);
                    //ProductRealmOp.updateFollowedProductList(followedProductList);

                }
                break;
        }
    }

    @Override
    public void getProductSuccess(List<ProductBean> followedProductList) {
        adapter.setData(followedProductList);
    }

    @Override
    public void getSubItemSuccess(List<SubItemBean> subItemList,ProductBean productBean) {
        if(subItemList==null || subItemList.size()==0){
            showMessage("你未拥有管理该产品的服务实例");
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(SubItemBean.class.getSimpleName(),(ArrayList)subItemList);
        intent.putExtra(ProductBean.class.getSimpleName(),productBean);
        intent.setClass(MainActivity.this,ProductContentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String flag, String msg) {
        super.onFailure(flag, msg);
        showToast(msg);
    }
}
