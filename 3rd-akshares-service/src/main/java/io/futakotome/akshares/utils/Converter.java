package io.futakotome.akshares.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public final class Converter {
    public static Map<String, String> convertObjToMap(Object obj) {
        Map<String, String> map = new HashMap<>();
        Class<?> clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), (String) field.get(obj));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
}
