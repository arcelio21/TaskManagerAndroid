package com.dev6am.todo.util;

import com.dev6am.todo.model.Data;
import com.dev6am.todo.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConverterJson {

    public static String DataToJson(Data data){

        Gson gson = new Gson();
        String dataJson = gson.toJson(data);

        return dataJson;
    }

    public static <T> T  jsonToData(String json, Class<T> aClass){

        Gson gson = new Gson();

        JsonElement jsonElement = JsonParser.parseString(json);

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        return gson.fromJson(jsonObject.get("data"), aClass);

    }

    public static <T> List<T> jsonToDataList(String json, Class<T[]> clasList){

        Gson gson = new Gson();

        JsonElement jsonElement = JsonParser.parseString(json);

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        T[] dataArray = gson.fromJson(jsonObject.get("data"), clasList);


        return Arrays.stream(dataArray).collect(Collectors.toList());
    }
}
