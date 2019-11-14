package com.hnhz.thziot.realm;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Fenrir on 2017/10/23 : 15:01.
 */

public class RealmTool {

    public static Realm getRealm() {
        Realm realm = Realm.getDefaultInstance();
        realm.refresh();
        return realm;
    }

    public static void tryExecuteTransaction(Realm.Transaction transaction) {
        Realm realm = null;
        try {
            realm = getRealm();
            realm.executeTransaction(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //realm 关闭
            quitRealm(realm);
        }
    }


    public static void tryExecuteTransaction(Realm realm, Realm.Transaction transaction) {
        if (checkRealm(realm)) {
            return;
        }
        try {
            realm.executeTransaction(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //realm自行控制
    }

    /**
     * 得到单个对象，可加入转换， 自己得到realm 并关闭
     *
     * @param fetcher
     * @param <T>
     * @return
     */
    public static <T> T tryExecuteFetcher(Fetcher<T> fetcher) {
        Realm realm = null;
        try {
            realm = getRealm();
            return fetcher.fetcher(realm);
        } finally {
            quitRealm(realm);
        }
    }

    /**
     * 得到单个对象，可加入转换， realm自行控制
     *
     * @param realm
     * @param fetcher
     * @param <T>
     * @return
     */
    public static <T> T tryExecuteFetcher(Realm realm, Fetcher<T> fetcher) {
        if (checkRealm(realm)) {
            return null;
        }
        try {
            return fetcher.fetcher(realm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 删除要在事务中进行，realm自行控制
     *
     * @param realmd
     * @param deleter
     * @return
     */
    public static boolean tryExecuteDeleter(Realm realmd, final Deleter deleter) {
        if (checkRealm(realmd)) {
            return false;
        }
        try {
            return deleter.delete(realmd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除要在事务中进行
     *
     * @param deleter
     * @return
     */
    public static boolean tryExecuteDeleter(final Deleter deleter) {
        Realm realm = null;
        boolean flag = false;
        try {
            realm = getRealm();
            flag = deleter.delete(realm);
            return flag;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            realm.close();
        }
        return false;
    }


    public static <T> List<T> tryExecuteFetcherList(FetcherList<T> fetcher) {
        Realm realm = null;
        try {
            realm = getRealm();
            return fetcher.fetcherList(realm);
        } finally {
            quitRealm(realm);
        }
    }

    public static <T> List<T> tryExecuteFetcherList(Realm realm, FetcherList<T> fetcher) {
        if (checkRealm(realm)) {
            return null;
        }
        try {
            return fetcher.fetcherList(realm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void quitRealm(Realm realm) {
        if (realm == null) {
            return;
        }
        realm.close();

    }

    public static boolean checkRealm(Realm realm) {
        if (realm == null)
            return true;

        return false;
    }

    public interface Deleter {
        boolean delete(Realm realm);
    }

    public interface Fetcher<T> {
        T fetcher(Realm realm);
    }

    public interface FetcherList<T> {
        List<T> fetcherList(Realm realm);
    }
}
