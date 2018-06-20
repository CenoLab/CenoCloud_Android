package cenocloud.nero.com.cenocloud.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cenocloud.nero.com.cenocloud.entity.IotList;
import cenocloud.nero.com.cenocloud.entity.ResponseResult;

/**
 * Created by ny on 2018/3/7.
 */

public class TGJson {
    static ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
    public static IotList fromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(IotList.class, clazz);
        return gson.fromJson(json, objectType);
    }
    public static ResponseResult resultFromJson(String json, Class clazz) {
        Gson gson = new Gson();
        Type objectType = type(ResponseResult.class, clazz);
        return gson.fromJson(json, objectType);
    }
}
