package com.hnhz.thziot.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.MenuBean;
import com.hnhz.thziot.bean.MenuRightBean;
import com.hnhz.thziot.bean.ProductBean;
import com.hnhz.thziot.bean.SubItemBean;
import com.hnhz.thziot.global.Constant;
import com.hnhz.thziot.global.GlobalApplication;
import com.hnhz.thziot.presenter.ProductContentPresenter;
import com.hnhz.thziot.presenter.view.IProductContentView;
import com.hnhz.thziot.ui.fragment.WebFragment;
import com.hnhz.thziot.ui.widget.ListPopDownTitleBar;
import com.hnhz.thziot.ui.widget.dialog.ListPopDownDialog;
import com.hnhz.thziot.ui.widget.dialog.SubItemListDialog;
import com.kq.platform.kq_common.global.BaseConstant;
import com.kq.platform.kq_common.ui.BaseCompactActivity;
import com.kq.platform.kq_common.ui.widget.base.TitleBar;
import com.kq.platform.kq_common.utils.PrefsUtils;
import com.kq.platform.kq_common.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zhou jiaqi on 2019/8/6.
 */
public class ProductContentActivity extends BaseCompactActivity<ProductContentPresenter> implements IProductContentView {

    @BindView(R.id.title_bar)
    ListPopDownTitleBar titleBar;
    @BindView(R.id.tab_menu)
    TabLayout tabMenu;
    @BindView(R.id.vp_menu)
    ViewPager vpMenu;

    private ListPopDownDialog<SubItemBean> listPopDownDialog;

    private int currentSelectIndex;

    private List<SubItemBean> listSubItem;
    private ProductBean productBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_content);
        ButterKnife.bind(this);
        initIntentData();
        presenter = new ProductContentPresenter(this);

        initView();
    }

    private void initView() {
        initTitle();

    }

    private void initIntentData() {
        productBean = getIntent().getParcelableExtra(ProductBean.class.getSimpleName());
        listSubItem = getIntent().getParcelableArrayListExtra(SubItemBean.class.getSimpleName());

    }

    private void initTitle() {
        StatusBarUtil.setStatusBarColor(this, R.color.base_color, false);

        titleBar.setTitle(productBean.getProductName());
        titleBar.setTitleDesc("");
        titleBar.initVisible(true,false,false,false);
        titleBar.setImgBtnLeftRe(R.mipmap.icon_back);
        titleBar.setLeftListener(v -> finish());

        titleBar.setOnTitleClickListener(new ListPopDownTitleBar.OnTitleClickListener() {
            @Override
            public void onTitleClick(View v) {

            }
        });

        LinearLayout titleView = titleBar.getTitleView();

        listPopDownDialog = new SubItemListDialog(this);
        listPopDownDialog.setAnchor(titleView);

        listPopDownDialog.setDisplayOptions(listSubItem);
        currentSelectIndex = 0;
        listPopDownDialog.setSelectedOption(currentSelectIndex);

        listPopDownDialog.setOnDropdownItemSelectedListener(new ListPopDownDialog.setOnDropdownItemSelected<SubItemBean>() {
            @Override
            public void onDropdownItemSelected(int position, SubItemBean subItemBean) {
                //showMessage(subItemBean.getSubitemsName());
                currentSelectIndex = position;
                titleBar.setTitleDesc(subItemBean.getSubitemsName());
                refreshSubTitle();
            }
        });

        listPopDownDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        listPopDownDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

            }
        });

        titleBar.setOnTitleClickListener(v -> {
            if (listPopDownDialog.isShowing()) {
                listPopDownDialog.hide();
            } else {
                listPopDownDialog.show();
            }
        });

        refreshSubTitle();
    }

    private void refreshSubTitle() {
        if (currentSelectIndex >= 0) {
            titleBar.setTitleDesc(listSubItem.get(currentSelectIndex).getSubitemsName());
            presenter.getMenuList(listSubItem.get(currentSelectIndex).getSubitemsId(), productBean.getProductId());
        } else {
            titleBar.setTitleDesc("");
        }

    }

    @Override
    public void getMenu(MenuRightBean menuRightBean) {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        ArrayList<String> menuTitleList = new ArrayList<>();
        List<MenuBean> menuList = menuRightBean.getListProductAccess();
        if(menuList.size()==0){
            showMessage("未获取到菜单");
            return;
        }
        PrefsUtils prefsUtils = new PrefsUtils(GlobalApplication.getContext(), BaseConstant.PREF_TOKEN_FILE);
        String jSessionId = prefsUtils.get(BaseConstant.PREF_KEY_JSESSIONID, "");

        for(MenuBean menuBean: menuList){
            String mainUrl = menuBean.getHref()+"?jsessionid="+jSessionId + "&subitemsId=" + menuRightBean.getSubitemsId() + "&source=APP";

            fragmentList.add(WebFragment.newInstance(menuBean.getName(),mainUrl));

            menuTitleList.add(menuBean.getName());
        }

        vpMenu.setAdapter(new PagerFragmentAdapter(getSupportFragmentManager(), fragmentList,menuTitleList));
        vpMenu.setOffscreenPageLimit(menuList.size());
        tabMenu.setupWithViewPager(vpMenu);
    }


    // 自定义ViewPager页面适配器
    private class PagerFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        private List<String> title;

        public PagerFragmentAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList,List<String> title) {
            super(fragmentManager);
            this.fragmentList = fragmentList;
            this.title = title;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }

//    private List<SubItemBean> testData(){
//        listSubItem = new ArrayList<>();
//        SubItemBean subItemBean = new SubItemBean();
//        subItemBean.setSubitemsName("测试1");
//
//        SubItemBean subItemBean1 = new SubItemBean();
//        subItemBean1.setSubitemsName("测试2");
//
//
//        listSubItem.add(subItemBean);
//        listSubItem.add(subItemBean1);
//
//        return listSubItem;
//    }
}
