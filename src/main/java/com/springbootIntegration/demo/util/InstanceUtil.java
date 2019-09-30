package com.springbootIntegration.demo.util;

import com.alibaba.fastjson.JSON;
import com.springbootIntegration.demo.support.exception.DataParseException;
import com.springbootIntegration.demo.support.exception.DataParseException;
import com.springbootIntegration.demo.support.exception.InstanceException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import com.esotericsoftware.reflectasm.MethodAccess;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListSet;

public final class InstanceUtil {
    protected static Logger logger = LogManager.getLogger();
   // private static final Map<String, MethodAccess> methodMap = newHashMap();
    private static final Map<String, Field> fieldMap = newHashMap();
    public static Map<String, Class<?>> clazzMap = new HashMap();

    private InstanceUtil() {
    }

    public static final <T> T to(Object orig, Class<T> clazz) {
        T bean = null;

        try {
            bean = clazz.newInstance();
            Class<?> cls = orig.getClass();
            BeanInfo orgInfo = Introspector.getBeanInfo(cls);
            PropertyDescriptor[] orgPty = orgInfo.getPropertyDescriptors();
            Map<String, PropertyDescriptor> propertyMap = newHashMap();
            PropertyDescriptor[] var7 = orgPty;
            int var8 = orgPty.length;

            for(int var9 = 0; var9 < var8; ++var9) {
                PropertyDescriptor property = var7[var9];
                propertyMap.put(property.getName(), property);
            }

            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var22 = propertyDescriptors;
            int var23 = propertyDescriptors.length;

            for(int var11 = 0; var11 < var23; ++var11) {
                PropertyDescriptor property = var22[var11];
                String key = property.getName();
                if (!"class".equals(key) && propertyMap.containsKey(key)) {
                    Method getter = ((PropertyDescriptor)propertyMap.get(key)).getReadMethod();
                    Object value = TypeParseUtil.convert(getter.invoke(orig), property.getPropertyType(), (String)null);

                    try {
                        String fieldName = clazz.getName() + "." + key;
                        Field field = (Field)fieldMap.get(fieldName);
                        if (field == null) {
                            field = clazz.getDeclaredField(key);
                            fieldMap.put(fieldName, field);
                        }

                        field.setAccessible(true);
                        field.set(bean, value);
                    } catch (Exception var18) {
                        PropertyUtils.setProperty(bean, key, value);
                    }
                }
            }
        } catch (Exception var19) {
            logger.error("to Error " + var19);
        }

        return bean;
    }

    public static final <T> T parse(String json, Class<T> clazz) {
        try {
            Map map = (Map)JSON.parseObject(json, Map.class);
            return (T)transMap2Bean(map, clazz);
        } catch (Exception var3) {
            logger.error("parse", var3);
            return null;
        }
    }

    public static <T> T transMap2Bean(Map<String, Object> map, Class<T> clazz) {
        T bean = null;

        try {
            bean = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            PropertyDescriptor[] var5 = propertyDescriptors;
            int var6 = propertyDescriptors.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                PropertyDescriptor property = var5[var7];

                try {
                    String key = property.getName();
                    if (map.containsKey(key)) {
                        Object value = TypeParseUtil.convert(map.get(key), property.getPropertyType(), (String)null);

                        try {
                            String fieldName = clazz.getName() + "." + key;
                            Field field = (Field)fieldMap.get(fieldName);
                            if (field == null) {
                                field = clazz.getDeclaredField(key);
                                fieldMap.put(fieldName, field);
                            }

                            field.setAccessible(true);
                            field.set(bean, value);
                        } catch (Exception var13) {
                            PropertyUtils.setProperty(bean, key, value);
                        }
                    }
                } catch (Exception var14) {
                    logger.error("transMap2Bean setter Error ", var14);
                }
            }
        } catch (Exception var15) {
            logger.error("transMap2Bean Error ", var15);
        }

        return bean;
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        Map<String, Object> map = newHashMap();
        if (obj == null) {
            return map;
        } else {
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var4 = propertyDescriptors;
                int var5 = propertyDescriptors.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor property = var4[var6];
                    String key = property.getName();
                    if (!"class".equals(key)) {
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(obj);
                        map.put(key, value);
                    }
                }
            } catch (Exception var11) {
                logger.error("transBean2Map Error " + var11);
            }

            return map;
        }
    }

    public static <T> T getDiff(T oldBean, T newBean) {
        if (oldBean == null && newBean != null) {
            return newBean;
        } else if (newBean == null) {
            return null;
        } else {
            Class cls1 = oldBean.getClass();

            try {
                T object = (T)cls1.newInstance();
                BeanInfo beanInfo = Introspector.getBeanInfo(cls1);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var6 = propertyDescriptors;
                int var7 = propertyDescriptors.length;

                for(int var8 = 0; var8 < var7; ++var8) {
                    PropertyDescriptor property = var6[var8];
                    String key = property.getName();
                    if (!"class".equals(key)) {
                        Method getter = property.getReadMethod();
                        Object oldValue = getter.invoke(oldBean);
                        Object newValue = getter.invoke(newBean);
                        if (newValue != null && !newValue.equals(oldValue)) {
                            Object value = TypeParseUtil.convert(newValue, property.getPropertyType(), (String)null);

                            try {
                                String fieldName = cls1.getName() + "." + key;
                                Field field = (Field)fieldMap.get(fieldName);
                                if (field == null) {
                                    field = cls1.getDeclaredField(key);
                                    fieldMap.put(fieldName, field);
                                }

                                field.setAccessible(true);
                                field.set(object, value);
                            } catch (Exception var17) {
                                PropertyUtils.setProperty(object, key, value);
                            }
                        }
                    }
                }

                return object;
            } catch (Exception var18) {
                throw new DataParseException(var18);
            }
        }
    }

    public static final Class<?> getClass(String clazz) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            return loader != null ? Class.forName(clazz, true, loader) : Class.forName(clazz);
        } catch (ClassNotFoundException var3) {
            throw new InstanceException(var3);
        }
    }

    public static final <E> List<E> getInstanceList(Class<E> cls, List<?> list) {
        List<E> resultList = newArrayList();
        E object = null;
        Iterator var4 = list.iterator();

        while(var4.hasNext()) {
            Object name = var4.next();
            Map<?, ?> map = (Map)name;
            object = newInstance(cls, map);
            resultList.add(object);
        }

        return resultList;
    }

    public static final <E> List<E> getInstanceList(Class<E> cls, ResultSet rs) {
        ArrayList resultList = newArrayList();

        try {
            E object = cls.newInstance();
            Field[] fields = cls.getDeclaredFields();

            while(rs.next()) {
                object = cls.newInstance();
                Field[] var5 = fields;
                int var6 = fields.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    Field field = var5[var7];
                    String fieldName = field.getName();
                    PropertyUtils.setProperty(object, fieldName, rs.getObject(fieldName));
                }

                resultList.add(object);
            }

            return resultList;
        } catch (Exception var10) {
            throw new InstanceException(var10);
        }
    }

    public static final <E> E newInstance(Class<E> cls, Map<String, ?> map) {
        E object = null;

        try {
            object = cls.newInstance();
            BeanUtils.populate(object, map);
            return object;
        } catch (Exception var4) {
            throw new InstanceException(var4);
        }
    }

    public static final Object newInstance(String clazz) {
        try {
            return getClass(clazz).newInstance();
        } catch (Exception var2) {
            throw new InstanceException(var2);
        }
    }

    public static final <K> K newInstance(Class<K> cls, Object... args) {
        try {
            Class<?>[] argsClass = null;
            if (args != null) {
                argsClass = new Class[args.length];
                int i = 0;

                for(int j = args.length; i < j; ++i) {
                    argsClass[i] = args[i].getClass();
                }
            }

            Constructor<K> cons = cls.getConstructor(argsClass);
            return cons.newInstance(args);
        } catch (Exception var5) {
            throw new InstanceException(var5);
        }
    }

    public static final Object newInstance(String className, Object... args) {
        try {
            Class<?> newoneClass = (Class)clazzMap.get(className);
            if (newoneClass == null) {
                newoneClass = Class.forName(className);
                clazzMap.put(className, newoneClass);
            }

            return newInstance(newoneClass, args);
        } catch (Exception var3) {
            throw new InstanceException(var3);
        }
    }

//    public static final Object invokeMethod(Object owner, String methodName, Object... args) {
//        Class<?> ownerClass = owner.getClass();
//        String key = null;
//        if (args != null) {
//            Class<?>[] argsClass = new Class[args.length];
//            int i = 0;
//
//            for(int j = args.length; i < j; ++i) {
//                if (args[i] != null) {
//                    argsClass[i] = args[i].getClass();
//                }
//            }
//
//            key = ownerClass + "_" + methodName + "_" + StringUtils.join(argsClass, ",");
//        } else {
//            key = ownerClass + "_" + methodName;
//        }
//
//        MethodAccess methodAccess = (MethodAccess)methodMap.get(key);
//        if (methodAccess == null) {
//            methodAccess = MethodAccess.get(ownerClass);
//            methodMap.put(key, methodAccess);
//        }
//
//        return methodAccess.invoke(owner, methodName, args);
//    }

    public static final <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    public static final <E> ArrayList<E> newArrayList(E... e) {
        ArrayList<E> list = newArrayList();
        Collections.addAll(list, e);
        return list;
    }

    public static final <k, v> HashMap<k, v> newHashMap() {
        return new HashMap();
    }

    public static final <E> HashSet<E> newHashSet() {
        return new HashSet();
    }

    public static final <k, v> Hashtable<k, v> newHashtable() {
        return new Hashtable();
    }

    public static final <k, v> LinkedHashMap<k, v> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static final <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet();
    }

    public static final <E> LinkedList<E> newLinkedList() {
        return new LinkedList();
    }

    public static final <k, v> TreeMap<k, v> newTreeMap() {
        return new TreeMap();
    }

    public static final <E> TreeSet<E> newTreeSet() {
        return new TreeSet();
    }

    public static final <E> Vector<E> newVector() {
        return new Vector();
    }

    public static final <k, v> WeakHashMap<k, v> newWeakHashMap() {
        return new WeakHashMap();
    }

    public static final <k, v> HashMap<k, v> newHashMap(k key, v value) {
        HashMap<k, v> map = newHashMap();
        map.put(key, value);
        return map;
    }

    public static final <k, v> HashMap<k, v> newHashMap(k[] key, v[] value) {
        HashMap<k, v> map = newHashMap();

        for(int i = 0; i < key.length; ++i) {
            map.put(key[i], value[i]);
        }

        return map;
    }

    public static final <k, v> LinkedHashMap<k, v> newLinkedHashMap(k key, v value) {
        LinkedHashMap<k, v> map = newLinkedHashMap();
        map.put(key, value);
        return map;
    }

    public static final <k, v> ConcurrentHashMap<k, v> newConcurrentHashMap() {
        return new ConcurrentHashMap();
    }

    public static final <e> ConcurrentLinkedDeque<e> newConcurrentLinkedDeque() {
        return new ConcurrentLinkedDeque();
    }

    public static final <e> ConcurrentLinkedQueue<e> newConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue();
    }

    public static final <e> ConcurrentSkipListSet<e> newConcurrentSkipListSet() {
        return new ConcurrentSkipListSet();
    }

    public static <E> Set<E> newHashSet(E[] e) {
        Set<E> set = newHashSet();
        Collections.addAll(set, e);
        return set;
    }
}