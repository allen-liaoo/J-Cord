package org.alienideology.jcord.internal.gateway;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.JCord;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.IllegalFormatException;
import java.util.function.Consumer;

/**
 * Requester - A Http Requester for HttpPath.
 * @author AlienIdeology
 */
public final class Requester {

    private HttpPath path;
    private IdentityImpl identity;
    private HttpRequest request;

    public Requester(IdentityImpl identity, HttpPath path) {
        this.identity = identity;
        this.path = path;
    }

    /*
        ---------------
            Request
        ---------------
     */

    /**
     *  Chaining request method.
     */
    public Requester request(String... params) {
        requestHttp(params);
        return this;
    }

    /**
     * @return A GetRequest to perform further action.
     */
    public GetRequest requestGet(String... params) {
        HttpRequest request = requestHttp(params);
        return (GetRequest) request;
    }

    /**
     * @return A HttpRequestWithBody to perform further action.
     */
    public HttpRequestWithBody requestWithBody(String... params) {
        HttpRequest request = requestHttp(params);
        return (HttpRequestWithBody) request;
    }

    /**
     * @param consumer Specified actions to the HttpRequest.
     */
    public Requester updateRequest(Consumer<HttpRequest> consumer) {
        consumer.accept(request);
        return this;
    }

    /**
     * @param consumer Specified actions to the GetRequest. I.e. Adding headers.
     */
    public Requester updateGetRequest(Consumer<GetRequest> consumer) {
        consumer.accept((GetRequest) request);
        return this;
    }

    /**
     * @param consumer Specified actions to the HttpRequestWithBody. I.e. Adding json.
     */
    public Requester updateRequestWithBody(Consumer<HttpRequestWithBody> consumer) {
        consumer.accept((HttpRequestWithBody)request);
        return this;
    }

    /*
        -------------
            Result
        -------------
     */

    /**
     * Performs requests ignoring the returning values.
     */
    public void performRequest() {
        try {
            request.asString();
        } catch (UnirestException e) {
            throw new RuntimeException("Fail to perform http requestHttp!");
        }
    }

    /**
     * @return The json object get by performing request.
     */
    public JSONObject getAsJSONObject() {
        JSONObject json;
        try {
            HttpResponse<JsonNode> response = request.asJson();
            handleErrorCode(response);
            JsonNode node = response.getBody();

            if (node.isArray()) {
                throw new UnirestException("The request returns a JSON Array. Json: "+node.getArray().toString(4));
            } else {
                json = node.getObject();
            }
        } catch (UnirestException e) {
            throw new JSONException("Error Occurred while getting JSON Object: "+e.getLocalizedMessage());
        }
        handleErrorResponse(json);
        return json;
    }

    /**
     * @return The json array get by performing request.
     */
    public JSONArray getAsJSONArray() {
        JSONArray json;
        try {
            HttpResponse<JsonNode> response = request.asJson();
            handleErrorCode(response);
            JsonNode node = response.getBody();

            if (!node.isArray()) {
                handleErrorResponse(node.getObject());
                throw new UnirestException("The request returns a JSON Object. Json: "+node.getObject().toString(4));
            } else {
                json = node.getArray();
            }
        } catch (UnirestException e) {
            throw new JSONException("Error Occurred while getting JSON Array: "+e.getLocalizedMessage());
        }
        return json;
    }

    /*
        ----------------
            Internal
        ----------------
     */

    /**
     * Using the HttpMethod to return a http request.
     * @param params Parameters to be replaced.
     * @return The http request
     */
    private HttpRequest requestHttp(String... params) {
        String processedPath = processPath(params);

        HttpRequest request = null;
        switch (path.getMethod()) {
            case GET:
                request = Unirest.get(processedPath); break;
            case HEAD:
                request = Unirest.head(processedPath); break;
            case POST:
                request = Unirest.post(processedPath); break;
            case PUT:
                request = Unirest.put(processedPath); break;
            case PATCH:
                request = Unirest.patch(processedPath); break;
            case DELETE:
                request = Unirest.delete(processedPath); break;
            case OPTIONS:
                request = Unirest.options(processedPath); break;
        }
        processRequest(request, processedPath);
        this.request = request;
        return request;
    }

    private String processPath(String... params) {
        String processedPath;
        try {
            processedPath = path.getPath().replaceAll("\\{(.+?)}", "%s");
            processedPath = String.format(processedPath, (Object[]) params);
        } catch (IllegalFormatException ife) {
            throw new IllegalArgumentException("[INTERNAL] Cannot perform an HttpRequest due to unmatched parameters!");
        }
        return processedPath;
    }

    private void processRequest(HttpRequest request, String path) {
        request.header("Authorization", identity.getToken())
                .header("User-Agent", "DiscordBot ($"+path+", $"+ JCord.VERSION+")");
    }

    private void handleErrorResponse(JSONObject response) {
        if (response.has("code")) {
            ErrorResponse errorResponse = ErrorResponse.getByKey(response.getInt("code"));
            if (errorResponse != ErrorResponse.UNKNOWN) {
                throw new ErrorResponseException(errorResponse);
            } else {
                throw new ErrorResponseException(response.getInt("code"), response.getString("message"));
            }
        }
    }

    private void handleErrorCode(HttpResponse response) {
        ErrorCode error = ErrorCode.getByKey(response.getStatus());
        if (error.isServerError() || error.isFailure()) {
            throw new RuntimeException("[Error Code "+error.key+"] "+error.meaning);
        } else if (error == ErrorCode.UNKNOWN){
            throw new RuntimeException("[Unknown Error Code " + response.getStatus() +"] " + response.getStatusText());
        }
    }

}
