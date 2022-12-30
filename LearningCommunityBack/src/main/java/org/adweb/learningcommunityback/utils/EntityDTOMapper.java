package org.adweb.learningcommunityback.utils;


import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 从数据库中的entity到DTO对象的映射器
 * 实现原理是利用反射，在entity上调用getter，并在dto上调用setter
 * 比如，在entity上调用getUsername，返回的结果作为参数，调用dto的setUsername
 */
public class EntityDTOMapper {

    private Class<?> entityClass;
    private Class<?> dtoClass;

    public static class EntityDTOMapperBuilder {

        private Class<?> entityClass;
        private Class<?> dtoClass;

        public EntityDTOMapperBuilder setEntityClass(Class<?> entityClass) {
            this.entityClass = entityClass;
            return this;
        }

        public EntityDTOMapperBuilder setDtoClass(Class<?> dtoClass) {
            this.dtoClass = dtoClass;
            return this;
        }

        public EntityDTOMapper build() {
            EntityDTOMapper mapper = new EntityDTOMapper();
            if (this.entityClass == null) {
                throw new RuntimeException("entityClass不能为空");
            }
            if (this.dtoClass == null) {
                throw new RuntimeException("dtoClass不能为空");
            }

            mapper.dtoClass = dtoClass;
            mapper.entityClass = entityClass;

            return mapper;
        }
    }

    private EntityDTOMapper() {
    }

    public static EntityDTOMapperBuilder builder() {
        return new EntityDTOMapperBuilder();
    }


    @SneakyThrows
    public Object map(Object entity) {
        if (entity == null) {
            return null;
        }
        Constructor<?> noArgsConstructor = dtoClass.getConstructor();
        Object result = noArgsConstructor.newInstance();

        //所有entityClass上的getter方法¬
        List<Method> entityGetters = Arrays.stream(entityClass.getDeclaredMethods()).filter(method -> method.getName().startsWith("get")).toList();

        entityGetters.forEach(getter -> {
            String setterName = getter.getName().replaceFirst("get", "set");
            try {
                //在entity上invoke getter方法，获得值
                Object value = getter.invoke(entity);

                if (value != null) {
                    Method[] possibleSetters = dtoClass.getDeclaredMethods();
                    Arrays.stream(possibleSetters).
                            filter(method -> method.getName().equals(setterName))
                            .forEach(method -> {
                                try {
                                    method.invoke(result, value);
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            });
                }

            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
        });

        return result;
    }


}
