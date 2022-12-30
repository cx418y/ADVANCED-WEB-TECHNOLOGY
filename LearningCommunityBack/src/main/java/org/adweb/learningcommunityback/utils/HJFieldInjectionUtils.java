package org.adweb.learningcommunityback.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class HJFieldInjectionUtils {

    private static Map<String, List<Method>> declaredMethodName2MethodList(Class<?> clazz) {
        HashMap<String, List<Method>> map = new HashMap<>();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        Arrays.stream(declaredMethods).forEach(method -> {
            map.putIfAbsent(method.getName(), new ArrayList<>());
            map.get(method.getName()).add(method);
        });

        return map;
    }

    /**
     * 将src中的同名属性，注入到dst中。如果dst中对应的属性已经有值了，就不注入了
     *
     * @param src 源
     * @param dst 目标
     */
    public static void inject(Object src, Object dst) {
        if (src == null || dst == null) {
            return;
        }
        Class<?> dstClass = dst.getClass();
        Class<?> srcClass = src.getClass();

        var srcMethodMap = declaredMethodName2MethodList(srcClass);
        var dstMethodMap = declaredMethodName2MethodList(dstClass);

        //对于src的getter，找到dst对应的setter，并在dst上调用setter
        srcMethodMap
                .keySet()
                .stream()
                .filter(mName -> mName.startsWith("get"))
                .filter(mName -> dstMethodMap.containsKey(mName.replaceFirst("get", "set")))
                .forEach(getterName -> {
                    String setterName = getterName.replaceFirst("get", "set");

                    Object value = null;

                    for (var getter : srcMethodMap.get(getterName)
                    ) {
                        try {
                            value = getter.invoke(src);
                        } catch (IllegalAccessException | InvocationTargetException ignored) {
                        }
                    }

                    for (var setter : dstMethodMap.get(setterName)) {
                        try {
                            setter.invoke(dst, value);
                        } catch (IllegalAccessException | InvocationTargetException ignored) {
                        }
                    }
                });
    }
}
