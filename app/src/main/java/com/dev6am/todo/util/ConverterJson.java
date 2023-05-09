package com.dev6am.todo.util;

import com.dev6am.todo.model.Data;
import com.dev6am.todo.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConverterJson {

    public static String DataToJson(Data data){

        Gson gson = new Gson();
        String dataJson = gson.toJson(data);

        return dataJson;
    }

    public static Data jsonToData(String json){

        Gson gson = new Gson();

        JsonElement jsonElement = JsonParser.parseString(json);

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        User user = gson.fromJson(jsonObject.get("data"), User.class);

        return Data.builder()
                .data(user)
                .build();
    }
}
