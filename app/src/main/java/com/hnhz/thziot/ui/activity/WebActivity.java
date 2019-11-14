package com.hnhz.thziot.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hnhz.thziot.R;
import com.hnhz.thziot.bean.login.LoginData;
import com.hnhz.thziot.event.EventFireMessage;
import com.hnhz.thziot.ui.webview.IWebMethod;
import com.hnhz.thziot.ui.webview.WVJBWebView;
import com.hnhz.thziot.zxing.activity.CaptureActivity;
import com.kq.platform.kq_common.ui.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Zhou jiaqi on 2019/8/30.
 */
public class WebActivity extends BaseActivity implements IWebMethod {

    public final static String TAG = WebActivity.class.getSimpleName();

    @BindView(R.id.wb_container)
    LinearLayout wbContainer;
    @BindView(R.id.web_progress_bar)
    ProgressBar webProgressBar;
    @BindView(R.id.web_error_container)
    LinearLayout webErrorContainer;
    @BindView(R.id.btn_restart_web)
    Button btnRestartWeb;

    private static final int REQUEST_CODE_SCAN = 16;
    private static final int REQUEST_CODE_FILE_CHOOSER = 111;
    private ValueCallback<Uri[]> mUploadCallBackAboveL;
    private ValueCallback<Uri> mUploadMessage;
    private static final int COMMON_ACTIVITY_REQUEST_CODE = 168;
    private final static int FILECHOOSER_RESULTCODE = 2;
    private String mCameraFilePath;
    private boolean isWebviewAlive = true;

    public WVJBWebView webView;
    //需要动态申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    //权限的requestCode
    private final int mRequestCode = 166;

    private JSONObject jsonObject;
    private String homeUrl;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initWidget() {
        webView = new WVJBWebView(this);
        webView.setLayoutParams(new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        wbContainer.addView(webView);
        getIntentData(webView);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void bindLogic() {
        webView.registerHandler("scanCode", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                checkPermission();
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    webProgressBar.setVisibility(View.GONE);
                }
            }

            protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
            }


            // For Lollipop 5.0+ Devices
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                mUploadCallBackAboveL = filePathCallback;
                showFileChooser();
                return true;
            }

            //For Android 4.1 only
            protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                mUploadMessage = uploadMsg;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
            }

            protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webErrorContainer.setVisibility(View.VISIBLE);
            }
        });
        btnRestartWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webErrorContainer.setVisibility(View.GONE);
                getIntentData(webView);
            }
        });
    }

    public void registerWebMethod(WVJBWebView webView) {
        webView.registerHandler("newNativePageHandler", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                receivedWebDataJump(data);
            }
        });

        webView.registerHandler("receiveData", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                if (jsonObject != null) {
                    callback.onResult(jsonObject);
                }
            }
        });

        webView.registerHandler("toMapApp", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                goToBaiduMap(data);
            }
        });

        webView.registerHandler("backToPrePage", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                Log.d(TAG, "backToPrePage");
                receivedWebDataBack();
            }
        });

        webView.registerHandler("checkWarn", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                Log.d(TAG, "checkWarn");
                receivedWebWarn();
            }
        });

        webView.registerHandler("login", new WVJBWebView.WVJBHandler<Object, Object>() {
            @Override
            public void handler(Object data, WVJBWebView.WVJBResponseCallback<Object> callback) {
                LoginData.setGlobalUserBean(null);
                Intent intent4 = new Intent(WebActivity.this, LoginActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
            }
        });
    }


    @Override
    public void receivedWebDataJump(Object data) {
        Intent intent = new Intent(WebActivity.this, WebActivity.class);
        intent.putExtra("webData", data.toString());
        startActivityForResult(intent, COMMON_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public void receivedWebDataBack() {
        WebActivity.this.setResult(RESULT_OK);
        WebActivity.this.finish();
    }

    @Override
    public void receivedWebWarn() {
        EventBus.getDefault().post(new EventFireMessage("checked"));
        WebActivity.this.setResult(RESULT_FIRST_USER);
        WebActivity.this.finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_SCAN) {
            String codeResult = data.getStringExtra("result");
            webView.callHandler("scanCodeResult", codeResult);
        } else if (resultCode == RESULT_OK && requestCode == COMMON_ACTIVITY_REQUEST_CODE) {
            getIntentData(webView);
        } else if (resultCode == RESULT_FIRST_USER && requestCode == COMMON_ACTIVITY_REQUEST_CODE) {
            this.finish();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode == REQUEST_CODE_FILE_CHOOSER) {
                if (mUploadCallBackAboveL == null)
                    return;
                Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
                if (result == null && !TextUtils.isEmpty(mCameraFilePath)) {
                    // 看是否从相机返回
                    File cameraFile = new File(mCameraFilePath);
                    if (cameraFile.exists()) {
                        result = Uri.fromFile(cameraFile);
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Uri newUri = getImageStreamFromExternal(mCameraFilePath);
                    Uri[] uri = new Uri[]{result};
                    mUploadCallBackAboveL.onReceiveValue(uri);
                } else {
                    Uri[] uri = WebChromeClient.FileChooserParams.parseResult(resultCode, data);
                    mUploadCallBackAboveL.onReceiveValue(uri);
                }
                mUploadCallBackAboveL = null;
            }
        } else if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage)
                return;
            Uri result = data == null || resultCode != MainActivity.RESULT_OK ? null : data.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;
        }
    }



    public void getIntentData(WVJBWebView webView) {

        String webData = getIntent().getStringExtra("webData");
        try {
            jsonObject = new JSONObject(webData);
            String url = jsonObject.getString("url");
            url += "&source=APP";
            webView.loadUrl(homeUrl + url);
            setCommonTitle(jsonObject.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        registerWebMethod(webView);
    }

    private boolean isInstalled(String packageName) {
        PackageManager manager = getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> installedPackages = manager.getInstalledPackages(0);
        if (installedPackages != null) {
            for (PackageInfo info : installedPackages) {
                if (info.packageName.equals(packageName))
                    return true;
            }
        }
        return false;
    }

    private void goToBaiduMap(Object data) {
        try {
            JSONObject jsonObject = new JSONObject(data.toString());
            JSONObject pointObject = jsonObject.getJSONObject("point");
            String latitude = pointObject.getString("latitude");
            String longitude = pointObject.getString("longitude");
            String addr = jsonObject.getString("addr");
            if (!isInstalled("com.baidu.BaiduMap")) {
                showToast("请先安装百度地图客户端");
                return;
            }
            Intent intent = new Intent();
            intent.setData(Uri.parse("baidumap://map/marker?location=" + latitude + "," + longitude + "&title=" + addr + "&content=makeamarker&traffic=on&src=andr.baidu.openAPIdemo"));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 打开选择图片/相机
     */
    private void showFileChooser() {

        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // android7.0注意uri的获取方式改变
            Uri photoOutputUri = FileProvider.getUriForFile(
                    WebActivity.this,
                    "com.hnhz.thziot.fileprovider",
                    new File(mCameraFilePath));
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
        } else {
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        }

        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
        chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
        chooser.putExtra(Intent.EXTRA_INTENT, intent1);
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        startActivityForResult(chooser, REQUEST_CODE_FILE_CHOOSER);
    }


    public static Uri getMediaUriFromPath(Context context, String path) {
        Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = context.getContentResolver().query(mediaUri,
                null,
                MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                new String[]{path.substring(path.lastIndexOf("/") + 1)},
                null);
        Uri uri = null;
        if (cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(mediaUri,
                    cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
        }
        cursor.close();
        return uri;
    }

    @Override
    protected void onResume() {
        if (webView != null) {
            webView.onResume();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventFireMessage event) {
        webView.reload();
    }

    @Override
    public void onBackPressed() {
        if (webView == null) {
            return;
        }

        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            webView.stopLoading();
            webView.loadUrl("about:blank");
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (isWebviewAlive) {
            try {
                wbContainer.removeView(webView);
                webView.removeAllViews();
                webView.setVisibility(View.GONE);
                webView.clearHistory();
                webView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                isWebviewAlive = false;
            }
        }
        super.onDestroy();
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        //创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPermissionList中
        List<String> mPermissionList = new ArrayList<>();
        mPermissionList.clear();//清空没有通过的权限
        //逐个判断你要的权限是否已经通过
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);//添加还未授予的权限
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {
            //有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            Intent intent = new Intent(WebActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case mRequestCode: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    Intent intent = new Intent(WebActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                } else {
                    // 权限被用户拒绝了。
                    showToast("请打开相关权限，否则无法使用该功能");
                }

            }
        }
    }

    private Uri getUri(String path){
        Uri uri = null;
        if (path != null) {
            path = Uri.decode(path);
            ContentResolver cr = this.getContentResolver();
            StringBuffer buff = new StringBuffer();
            buff.append("(")
                    .append(MediaStore.Images.ImageColumns.DATA)
                    .append("=")
                    .append("'" + path + "'")
                    .append(")");
            Cursor cur = cr.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new String[] { MediaStore.Images.ImageColumns._ID },
                    buff.toString(), null, null);
            int index = 0;
            for (cur.moveToFirst(); !cur.isAfterLast(); cur
                    .moveToNext()) {
                index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                // set _id value
                index = cur.getInt(index);
            }
            if (index == 0) {
                //do nothing
            } else {
                Uri uri_temp = Uri
                        .parse("content://media/external/images/media/"
                                + index);
                if (uri_temp != null) {
                    uri = uri_temp;
                }
            }
        }
        return uri;
    }


    public static Uri getImageStreamFromExternal(String imageName) {
        File externalPubPath = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
        );

        File picPath = new File(externalPubPath, imageName);
        Uri uri = null;
        if(picPath.exists()) {
            uri = Uri.fromFile(picPath);
        }

        return uri;
    }
}
