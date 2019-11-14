package com.hnhz.thziot.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.facebook.stetho.Stetho;
import com.kq.platform.kq_common.global.BaseGlobalApplication;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by Zhou jiaqi on 2018/3/12.
 */

public class GlobalApplication extends BaseGlobalApplication {

    public static synchronized GlobalApplication getInstance() {
        return (GlobalApplication) mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("thziot.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
//
        Stetho.initialize(//Stetho初始化
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        );
//
//        String token = new PrefsUtils(this).get("token","");
//        LoginData.setToken(token);

    }


}
