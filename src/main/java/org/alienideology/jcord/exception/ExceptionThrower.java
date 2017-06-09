package org.alienideology.jcord.exception;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import org.alienideology.jcord.gateway.ErrorCode;
import org.alienideology.jcord.gateway.ErrorResponse;
import org.json.JSONObject;

/**
 * ExceptionThrower - Handles JSON Error Response and Http Error Codes.
 * @author AlienIdeology
 */
public class ExceptionThrower {

    public ExceptionThrower() {
    }

    public JSONObject handle(JSONObject response) {
        if (response.has("code")) {
            ErrorResponseException exception = new ErrorResponseException(ErrorResponse.getByKey(response.getInt("code")));
            exception.printStackTrace();
            throw exception;
        }
        return response;
    }

    public HttpResponse<JsonNode> handle(HttpResponse<JsonNode> response) {
        handle(response.getBody().getObject());
        ErrorCode error = ErrorCode.getByKey(response.getStatus());
        if (error != ErrorCode.UNKNOWN && (error.isServerError() || error.isFailure())) {
            RuntimeException exception = new RuntimeException(error.meaning);
            exception.printStackTrace();
            throw exception;
        }
        return response;
    }

}
