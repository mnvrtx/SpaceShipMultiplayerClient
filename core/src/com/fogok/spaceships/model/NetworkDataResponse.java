package com.fogok.spaceships.model;


import com.fogok.dataobjects.utils.JsonReader;
import com.fogok.dataobjects.utils.libgdxexternals.JsonValue;

public class NetworkDataResponse {

    private JsonReader jsonReader;
    private JsonValue jsonResponse;
    private JsonValue oldJsonResponse;


    public NetworkDataResponse() {
        jsonReader = new JsonReader();
        oldJsonResponse = new JsonValue("");
    }

    public void handle(String json){
        if (isNormalJson(json)) {
            try {
                final JsonValue jsonValue = jsonReader.parse(json);
                oldJsonResponse = jsonResponse != null ? jsonResponse : jsonValue;
                jsonResponse = jsonValue;
            }catch (Exception e){
                System.out.println("Err parse json");
            }
        } else {
            jsonResponse = null;
        }
    }

    public JsonValue getJsonResponse() {
        return jsonResponse == null ? null : jsonResponse;
    }

    public JsonValue getOldJsonResponse() {
        return oldJsonResponse;
    }

    private boolean isNormalJson(String jsonValue){
        if (jsonValue == null) return false;
        if (jsonValue.equals("")) return false;
//        if (jsonValue.length() <= 3) return false;
        return true;
    }
}
