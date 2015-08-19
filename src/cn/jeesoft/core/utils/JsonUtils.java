package cn.jeesoft.core.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import cn.jeesoft.core.exception.JsonException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;

/**
 * JSON处理工具类
 * @version v0.1.1 king 2015-01-15 JSON解析失败时，不返回null抛出异常
 * @version v0.1.0 king 2014-11-21 JSON序列/反序列
 */
public class JsonUtils {
	
	private JsonUtils() {
    }
    
    
    /**
     * 序列化
     * @param javaObject 要序列化的对象
     * @return JSON格式字符串
     */
    public static String toJsonString(Object javaObject) {
        return JSON.toJSONString(javaObject);
    }
    /**
     * 序列化为JSON对象
     * @param javaObject 要序列化的对象
     * @return 三种种可能值：JsonObject、JsonArray、null
     */
    public static Object toJson(Object javaObject) {
        if (javaObject == null) {
            return null;
        }
        if (javaObject instanceof JSON) {
            return null;
        }

        if (javaObject instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> map = (Map<Object, Object>) javaObject;

            JSONObject json = new JSONObject();

            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                Object key = entry.getKey();
                String jsonKey = TypeUtils.castToString(key);
                Object jsonValue = toJson(entry.getValue());
                json.put(jsonKey, jsonValue);
            }

            return json;
        }

        if (javaObject instanceof Collection) {
            @SuppressWarnings("unchecked")
            Collection<Object> collection = (Collection<Object>) javaObject;

            JSONArray array = new JSONArray();

            for (Object item : collection) {
                Object jsonValue = toJson(item);
                array.add(jsonValue);
            }

            return array;
        }

        Class<?> clazz = javaObject.getClass();

        if (clazz.isEnum()) {
            return null;
        }

        if (clazz.isArray()) {
            int len = Array.getLength(javaObject);

            JSONArray array = new JSONArray();

            for (int i = 0; i < len; ++i) {
                Object item = Array.get(javaObject, i);
                Object jsonValue = toJson(item);
                array.add(jsonValue);
            }

            return array;
        }

        if (ParserConfig.getGlobalInstance().isPrimitive(clazz)) {
            return null;
        }

        try {
            List<FieldInfo> getters = TypeUtils.computeGetters(clazz, null);

            JSONObject json = new JSONObject();

            for (FieldInfo field : getters) {
                Object value = field.get(javaObject);
                if (!ParserConfig.getGlobalInstance().isPrimitive(value.getClass())) {
                	value = toJson(value);
                }
                json.put(field.getName(), value);
            }

            return json;
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        }
        return null;
    }
    
    
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @return 两种种可能值：JsonObject、JsonArray
     */
    public static Object fromJson(String json) throws JsonException {
        if (json == null) {
            throw new NullPointerException("'json' can not NULL.");
        }
        
        try {
            Object value = new DefaultJSONParser(json).parse();
            if (value == null) {
                throw new JsonException("'json' parser Failed.");
            }
            
            if (value instanceof JSONObject) {
                return value;
            } else if (value instanceof JSONArray) {
                return value;
            } else {
                throw new JsonException("'"+value+"' cannot convert JSON.");
            }
        } catch (Exception e) {
            throw new JsonException(e.getMessage());
        }
    }
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @param type 反序列化的对象类型
     * @return 对象实例
     */
    public static <T>T fromJson(String json, TypeToken<T> type) throws JsonException {
        return fromJson(json, type.getType());
    }
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @param type 反序列化的对象类型
     * @return 对象实例
     */
    public static <T>T fromJson(String json, Class<T> type) throws JsonException {
        return fromJson(json, (Type)type);
    }
    /**
     * 反序列化
     * @param json JSON格式字符串
     * @param type 反序列化的对象类型
     * @return 对象实例
     */
    public static <T>T fromJson(String json, Type type) throws JsonException {
        try {
            return JSON.parseObject(json, type, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
        } catch (Throwable e) {
            throw new JsonException(e);
        }
    }
    
}
