package com.abinj.clusters.utility;

import com.abinj.clusters.constants.ResponseConstants;
import io.vertx.core.json.JsonObject;

public class ResponseUtility {

    public static JsonObject bindRequestResponse(String status, String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put(ResponseConstants.STATUS, status).put(ResponseConstants.MESSAGE, message);
        return jsonObject;
    }
}
