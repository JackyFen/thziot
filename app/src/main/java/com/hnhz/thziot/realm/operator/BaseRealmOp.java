package com.hnhz.thziot.realm.operator;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hnhz.thziot.realm.RealmConverter;
import com.hnhz.thziot.realm.RealmTool;

import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Fenrir on 2017/10/25 : 17:45.
 * TODO:  BaseRealmOp
 */

public class BaseRealmOp {
    /**
     * 主键已有  则更新
     *
     * @param realmObject
     */
    public static void saveOrUpdate(final @NonNull RealmObject realmObject) {
        RealmTool.tryExecuteTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(realmObject);
            }
        });
    }

    /**
     * 主键，已有的将不成功
     *
     * @param realmObject
     */
    public static void save(final @NonNull RealmObject realmObject) {
        RealmTool.tryExecuteTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(realmObject);
            }
        });
    }


    public static void saveCollections(final Collection<? extends RealmObject> objects) {
        if (objects == null || objects.size() == 0)
            return;
        RealmTool.tryExecuteTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(objects);
            }
        });
    }

    public static void saveCollectionsOrUpdate(final Collection<? extends RealmObject> objects) {
        if (objects == null || objects.size() == 0)
            return;
        RealmTool.tryExecuteTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(objects);
            }
        });
    }

    /**
     * 查找表默认的第一个
     *
     * @param realm
     * @param c     realm class
     * @param <T>   realm obj
     * @return realm obj
     */
    public static <T> T findFirst(Realm realm, final Class<? extends RealmObject> c) {
        return RealmTool.tryExecuteFetcher(realm, new RealmTool.Fetcher<T>() {
            @Override
            public T fetcher(Realm realm) {
                return (T) realm.where(c).findFirst();
            }
        });

    }

    /**
     * 查找表所有对象
     *
     * @param realm
     * @param c     realm class
     * @param <T>   realm obj
     * @return List<realm obj>
     */
    public static <T extends RealmObject> List<T> findAll(Realm realm, final Class<? extends RealmObject> c) {
        return RealmTool.tryExecuteFetcherList(realm, new RealmTool.FetcherList<T>() {
            @Override
            public List<T> fetcherList(Realm realm) {
                RealmResults r = realm.where(c).findAll();
                return RealmConverter.realmResultToList(r);
            }
        });
    }

    /**
     * 清除表数据，
     *
     * @param realm
     * @param c
     * @return
     */
    public static boolean clearAll(Realm realm, final Class<? extends RealmObject> c) {
        return RealmTool.tryExecuteDeleter(realm, new RealmTool.Deleter() {
            @Override
            public boolean delete(Realm realm) {
                boolean flag;
                realm.beginTransaction();
                flag = realm.where(c).findAll().deleteAllFromRealm();
                realm.commitTransaction();
                realm.close();
                return flag;
            }
        });
    }

    /**
     * 清除表数据，
     *
     * @param realm
     * @param c
     * @param id
     * @return
     */
    public static boolean deleteById(Realm realm, final Class<? extends RealmObject> c, final String id) {
        return RealmTool.tryExecuteDeleter(realm, new RealmTool.Deleter() {
            @Override
            public boolean delete(Realm realm) {
                boolean flag = false;
                realm.beginTransaction();
                flag = realm.where(c).equalTo("id", id).findAll().deleteAllFromRealm();
                realm.commitTransaction();
                return flag;
            }
        });
    }

    /**
     *
     */
    public static void createAllFromJson(final Class<? extends RealmObject> c, final String json) {
        if (json==null)
            return;
        RealmTool.tryExecuteTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                try {
                    realm.createOrUpdateAllFromJson(c, json);
                }catch (Exception e) {
                    Log.e("Realm", "createAllFromStream", e);
                }
            }
        });
    }
}
