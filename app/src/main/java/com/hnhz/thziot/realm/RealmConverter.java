package com.hnhz.thziot.realm;

import android.support.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by Fenrir on 2017/10/23 : 14:51.
 * TODO: 存储数据与使用数据的转换（属性值一致（名称））
 */

public class RealmConverter {


    /**
     * 全能型(属性值一致（名称）要有同样的属性名)
     * 从realm 中解出，，不在是proxy 对象 不要再得到父类
     *
     * @param oldObj
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T realmObjToNormalAllGoodTwo(@NonNull RealmObject oldObj, Class<? super T> type) {
        if (oldObj == null) {
            return null;
        }
        Class oldObjClass = oldObj.getClass();
        Field[] targetFields = type.getDeclaredFields();
        try {
            Object o = type.newInstance();
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                int modifier = targetField.getModifiers();
                if (Modifier.isStatic(modifier)) {
                    continue;
                }

                String fieldName = targetField.getName();

                Field originalObjectField = oldObjClass.getDeclaredField(fieldName);
                originalObjectField.setAccessible(true);
                Class returnType=originalObjectField.getType();
                Class  needType=targetField.getType();
                if (returnType.getSuperclass() == RealmObject.class) {
                    Object object = realmObjToNormalAllGood((RealmObject) originalObjectField.get(oldObj), needType);
                    targetField.set(o, object);
                } else if (returnType == RealmList.class) {
                    Type genericFieldType = targetField.getGenericType();
                    if (genericFieldType instanceof ParameterizedType) {
                        ParameterizedType aType = (ParameterizedType) genericFieldType;
                        Type[] fieldArgTypes = aType.getActualTypeArguments();
                        Class fieldArgClass = (Class) fieldArgTypes[0];
                        Object object = realmListToList((RealmList<? extends RealmObject>) originalObjectField.get(oldObj), fieldArgClass);
                        targetField.set(o, object);
                    }
                } else {
                    targetField.set(o, originalObjectField.get(oldObj));
                }
            }
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 全能型(属性值一致（名称）要有同样的属性名)
     *
     * @param oldObj
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T realmObjToNormalAllGood(@NonNull RealmObject oldObj, Class<? super T> type) {
        if (oldObj == null) {
            return null;
        }
        Class oldObjClass = oldObj.getClass();
        Field[] targetFields = type.getDeclaredFields();
        try {
            Object o = type.newInstance();
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                int modifier = targetField.getModifiers();
                if (Modifier.isStatic(modifier)) {
                    continue;
                }

                String fieldName = targetField.getName();

                Field originalObjectField = oldObjClass.getDeclaredField(fieldName);
                originalObjectField.setAccessible(true);
                Class returnType=originalObjectField.getType();
                Class  needType=targetField.getType();
                if (returnType.getSuperclass() == RealmObject.class) {
                    Object object = realmObjToNormalAllGood((RealmObject) originalObjectField.get(oldObj), needType);
                    targetField.set(o, object);
                } else if (returnType == RealmList.class) {
                    Type genericFieldType = targetField.getGenericType();
                    if (genericFieldType instanceof ParameterizedType) {
                        ParameterizedType aType = (ParameterizedType) genericFieldType;
                        Type[] fieldArgTypes = aType.getActualTypeArguments();
                        Class fieldArgClass = (Class) fieldArgTypes[0];
                        Object object = realmListToList((RealmList<? extends RealmObject>) originalObjectField.get(oldObj), fieldArgClass);
                        targetField.set(o, object);
                    }
                } else {
                    targetField.set(o, originalObjectField.get(oldObj));
                }
            }
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 全能型
     *
     * @param originalObject
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T normalToRealmObjAllGood(@NonNull Object originalObject, Class<? super T> type, List<String> noNeedField) {
        if (originalObject == null)
            return null;
        Class originalClass = originalObject.getClass();
        Field[] targetFields = type.getDeclaredFields();
        try {
            Object o = type.newInstance();
            for (Field targetField : targetFields) {
                // match field and merge value.
                try {
                    int modifier = targetField.getModifiers();
                    if (Modifier.isStatic(modifier)) {
                        continue;
                    }
                    Class returnType = targetField.getType();

                    String fieldName = targetField.getName();
                    if (noNeedField != null && noNeedField.contains(fieldName)) {
                        continue;
                    }

                    Field originalObjectField = originalClass.getDeclaredField(fieldName);
                    originalObjectField.setAccessible(true);
                    targetField.setAccessible(true);
                    if (returnType.getSuperclass() == RealmObject.class) {

                        Object object = normalToRealmObjAllGood(originalObjectField.get(originalObject), returnType, noNeedField);
                        targetField.set(o, object);
                    } else if (returnType == RealmList.class) {
                        //listToListRealm
                        Type genericFieldType = targetField.getGenericType();
                        if (genericFieldType instanceof ParameterizedType) {
                            ParameterizedType aType = (ParameterizedType) genericFieldType;
                            Type[] fieldArgTypes = aType.getActualTypeArguments();
                            Class fieldArgClass = (Class) fieldArgTypes[0];
                            Object object = listToListRealm((List<? extends Object>) originalObjectField.get(originalObject), fieldArgClass, noNeedField);
                            targetField.set(o, object);
                        }
                    } else {
                        targetField.set(o, originalObjectField.get(originalObject));
                    }
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 单个转换，realm 表中不能 有realm List,也不能有其他realm obj 因为过滤了;
     *
     * @param oldObj
     * @param type
     * @param <T>
     * @return
     */

    public static <T> T realmObjToNormal(@NonNull RealmObject oldObj, Class<? super T> type) {
        if (oldObj == null) {
            return null;
        }
        Class oldObjClass = oldObj.getClass().getSuperclass();
        Field[] targetFields = type.getDeclaredFields();

        try {
            Object o = type.newInstance();
            for (Field targetField : targetFields) {
                targetField.setAccessible(true);
                int modifier = targetField.getModifiers();
                if (Modifier.isStatic(modifier)) {
                    continue;
                }
                Class returnType = targetField.getType();
                if (returnType == type) {
                    continue;
                }
                String methodName = null;
                if (returnType == boolean.class) {
                    methodName = "is" + firstUpper(targetField.getName());
                } else {
                    methodName = "get" + firstUpper(targetField.getName());
                }
                try {

                    Method getValueMethod = oldObjClass.getDeclaredMethod(methodName);
                    targetField.set(o, getValueMethod.invoke(oldObj));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String firstUpper(String text) {
        char first = Character.toUpperCase(text.charAt(0));
        return first + text.substring(1);
    }

    @SuppressWarnings("unchecked")
    public static void convertAll(Collection<? extends RealmObject> ts, Collection outContainer, Class toClass) {
        for (RealmObject t : ts) {
            outContainer.add(realmObjToNormal(t, toClass));
        }
    }

    /**
     * 单个转换，realm 表中不能 有realm List,也不能有其他realm obj 因为过滤了;
     *
     * @param originalObject
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T normalToRealmObj(@NonNull Object originalObject, Class<? super T> type) {
        if (originalObject == null)
            return null;
        Class originalClass = originalObject.getClass();
        Field[] targetFields = type.getDeclaredFields();
        try {
            Object o = type.newInstance();
            for (Field targetField : targetFields) {
                // match field and merge value.
                try {
                    int modifier = targetField.getModifiers();
                    if (Modifier.isStatic(modifier)) {
                        continue;
                    }
                    Class returnType = targetField.getType();
                    if (returnType == type) {
                        continue;
                    }
                    String fieldName = targetField.getName();
                    Field originalObjectField = originalClass.getDeclaredField(fieldName);
                    originalObjectField.setAccessible(true);
                    targetField.setAccessible(true);

                    targetField.set(o, originalObjectField.get(originalObject));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();

                }
            }
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <E, T extends RealmObject> RealmList<T> listToListRealm(List<E> list, Class<T> type, List<String> noNeedField) {
        if (list == null)
            return null;
        RealmList<T> listRealm = new RealmList<>();

        for (E e : list) {
            listRealm.add(normalToRealmObjAllGood(e, type, noNeedField));
        }
        return listRealm;
    }

    public static <T, E extends RealmObject> List<T> ListRealmToListNormal(List<E> list, Class<T> type) {
        if (list == null) {
            return null;
        }
        List<T> listNormal = new ArrayList<>();
        for (E e : list) {
            listNormal.add(realmObjToNormalAllGood(e, type));
        }
        return listNormal;
    }

    public static <E, T extends RealmObject> List<T> listNormalToListRealm(List<E> list, Class<T> type, List<String> noNeedField) {
        if (list == null) {
            return null;
        }
        List<T> listRealm = new ArrayList<>();
        for (E e : list) {
            listRealm.add(normalToRealmObjAllGood(e, type, noNeedField));
        }
        return listRealm;
    }


    public static <T> List<T> realmListToList(RealmList<T> realmList) {
        if (realmList == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T t : realmList) {
            list.add(t);
        }
        return list;
    }


    public static <T, E extends RealmObject> List<T> realmListToListTwo(RealmList<E> realmList, Class<? super T> type) {
        if (realmList == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (E e : realmList) {
            list.add(realmObjToNormalAllGoodTwo(e, type));
        }
        return list;
    }


    public static <T, E extends RealmObject> List<T> realmListToList(RealmList<E> realmList, Class<? super T> type) {
        if (realmList == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (E e : realmList) {
            list.add(realmObjToNormalAllGood(e, type));
        }
        return list;
    }


    public static <T> List<T> realmResultToList(RealmResults<T> realmResults) {
        if (realmResults == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (T t : realmResults) {
            list.add(t);
        }
        return list;
    }


    public static <T, E extends RealmObject> List<T> realmResultToList(RealmResults<E> realmResults, Class<? super T> type) {
        if (realmResults == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        for (E e : realmResults) {
            list.add(realmObjToNormalAllGood(e, type));
        }
        return list;
    }
}
