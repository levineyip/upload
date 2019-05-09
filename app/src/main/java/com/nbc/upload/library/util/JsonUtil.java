package com.nbc.upload.library.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class JsonUtil {

    public static String getJsonFromAsserts(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        /*try {
         *//*return new Gson().fromJson(getCorrectJson(json), clazz);*//*
            return new GsonBuilder().disableHtmlEscaping().create().fromJson(getCorrectJson(json), clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        /*try {
            JsonElement jsonElement = new JsonParser().parse(getCorrectJson(json));
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            List<T> beans = new ArrayList<>();
            for (JsonElement jsonElement2 : jsonArray) {
                *//*T bean = new Gson().fromJson(jsonElement2, clazz);*//*
                T bean = new GsonBuilder().disableHtmlEscaping().create().fromJson(jsonElement2, clazz);
                beans.add(bean);
            }
            return beans;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return null;
    }

    public static String getCorrectJson(String json) {
        return isJsonCorrect(json) ? json : "";
    }

    public static boolean isJsonCorrect(String s) {
        return s != null && !s.equals("[]")
                && !s.equals("{}") && !s.equals("") && !s.equals("[null]") && !s.equals("{null}") && !s.equals("null");
    }

    public static <T> String list2JSON(List<T> entities) {
        /*return new Gson().toJson(entities);*/
        /*return new GsonBuilder().disableHtmlEscaping().create().toJson(entities);*/
        return null;
    }

    public static <T> String object2JSON(T entity) {
        /*return new Gson().toJson(entity);*/
        /*return new GsonBuilder().disableHtmlEscaping().create().toJson(entity);*/
        return null;
    }

}
