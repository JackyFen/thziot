package com.kq.platform.kq_common.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import kq.platform.kq_common.R;

import com.kq.platform.kq_common.presenter.BasePresenter;
import com.kq.platform.kq_common.presenter.IBaseView;
import com.kq.platform.kq_common.ui.widget.base.BaseToast;
import com.kq.platform.kq_common.ui.widget.base.LoadDialog;

/**
 * Created by Zhou jiaqi on 2017/12/21.
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    private static final String TAG = BaseFragment.class.getSimpleName();

    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;

    protected View rootView;

    protected SparseArray<View> views = new SparseArray<>();
    protected LoadDialog loadDialog;

    protected P presenter;

    protected Unbinder unbinder;
    private PermissionListener mListener;
    private static final int PERMISSION_REQUESTCODE = 100;


    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    /**
     * 设置视图布局
     *
     * @return
     */
    abstract protected int getLayoutId();

    /**
     * 控件初始化
     */
    abstract protected void initWidget();

    /**
     * 绑定逻辑
     */
    abstract protected void bindLogic();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(view, savedInstanceState);
        initWidget();
        bindLogic();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    protected BaseCompactActivity getBaseActivity() {
        return (BaseCompactActivity) getActivity();
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFragmentFirstVisible() {

    }

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    @Override
    public void onDestroyView() {
        if(presenter!=null){
            presenter.clearDisposable();
        }
        super.onDestroyView();
        unbinder.unbind();
    }

    public void showCustomToastS(String mes) {
        Message message = Message.obtain();
        message.obj = mes;
        message.what = showCustomToastS;
        baseHandler.sendMessage(message);
    }

    public void showCustomToastL(String mes) {
        Message message = Message.obtain();
        message.obj = mes;
        message.what = showCustomToastL;
        baseHandler.sendMessage(message);
    }

    public void showToast(String mes) {
        Message message = Message.obtain();
        message.obj = mes;
        message.what = showToast;
        baseHandler.sendMessage(message);
    }

    @Override
    public void showWait(String mes) {
        Message message = Message.obtain();
        message.obj = mes;
        message.what = showWait;
        baseHandler.sendMessage(message);
    }


    @Override
    public void showWait() {
        Message message = Message.obtain();
        message.obj = getString(R.string.common_loading);
        message.what = showWait;
        baseHandler.sendMessage(message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onFailure(String flag, String msg) {
        getBaseActivity().onFailure(flag,msg);
    }

    public void showWait(String msg, boolean isCancel) {
        Message message = Message.obtain();
        message.obj = msg;
        if (isCancel) {
            message.what = showWaitCancelable;
        } else {
            message.what = showWait;
        }
        baseHandler.sendMessage(message);
    }


    @Override
    public void cancelWait() {
        Message message = Message.obtain();
        message.what = cancelWaitMes;
        baseHandler.sendMessage(message);

    }

    /**
     * 已授权、未授权的接口回调
     */
    public interface PermissionListener {

        void onGranted();//已授权

        void onDenied(List<String> deniedPermission);//未授权

    }

    protected static final int showCustomToastS = 2017;
    protected static final int showCustomToastL = 2018;
    protected static final int showToast = 2019;

    protected static final int showWait = 2022;
    protected static final int showWaitMes = 2023;
    protected static final int cancelWaitMes = 2024;
    protected static final int showWaitCancelable = 2025;

    protected BaseHandler baseHandler = new BaseHandler(this);

    private static class BaseHandler extends Handler {
        public WeakReference<BaseFragment> reference;

        public BaseHandler(BaseFragment fragment) {
            reference = new WeakReference<BaseFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseFragment instance = reference.get();
            if (instance != null) {
                switch (msg.what) {
                    case showCustomToastS:
                        BaseToast.makeToast(instance.getActivity().getApplicationContext(), String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
                        break;
                    case showCustomToastL:
                        BaseToast.makeToast(instance.getActivity().getApplicationContext(), String.valueOf(msg.obj), Toast.LENGTH_LONG).show();
                        break;
                    case showToast:
                        Toast.makeText(instance.getActivity().getApplicationContext(), String.valueOf(msg.obj), Toast.LENGTH_SHORT).show();
                        break;
                    case showWaitMes:
                    case showWait:
                        if (instance.loadDialog == null)
                            instance.loadDialog = new LoadDialog(instance.getActivity());
                        instance.loadDialog.setTextTip(String.valueOf(msg.obj));
                        instance.loadDialog.show();
                        break;
                    case showWaitCancelable:
                        if (instance.loadDialog == null)
                            instance.loadDialog = new LoadDialog(instance.getActivity());
                        instance.loadDialog.setTextTip(String.valueOf(msg.obj));
                        instance.loadDialog.setCancelable(true);
                        instance.loadDialog.show();
                        break;
                    case cancelWaitMes:
                        if (instance.loadDialog != null && instance.loadDialog.isShowing())
                            instance.loadDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void showMessage(String mes) {
        showToast(mes);
    }
}
