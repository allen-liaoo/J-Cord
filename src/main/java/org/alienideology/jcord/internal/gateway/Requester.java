package org.alienideology.jcord.internal.gateway;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.alienideology.jcord.internal.exception.ErrorResponseException;
import org.alienideology.jcord.internal.exception.HttpErrorException;
import org.alienideology.jcord.internal.object.IdentityImpl;
import org.alienideology.jcord.util.log.Logger;
import org.alienideology.jcord.util.log.LogLevel;
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

    public Logger LOG;

    private HttpPath path;
    private String token;
    private HttpRequest request;
    private boolean useJson;

    /**
     * Constructor for non-identity tokens
     */
    public Requester(String token, HttpPath path) {
        this.token = token;
        this.path = path;
    }

    /**
     * Constructor for JSON requests
     */
    public Requester(IdentityImpl identity, HttpPath path) {
        this(identity, path, true);
    }

    /**
     * Constructor for JSON requests
     */
    public Requester(IdentityImpl identity, HttpPath path, boolean useJson) {
        this.LOG = identity.LOG;
        this.token = identity.getToken();
        this.path = path;
        this.useJson = useJson;
    }

    /*
        ---------------
            Request
        ---------------
     */

    /**
     *  Chaining request event.
     */
    public Requester request(String... params) {
        requestHttp((Object[]) params);
        return this;
    }

    /**
     * @return A GetRequest to perform further action.
     */
    public GetRequest requestGet(String... params) {
        HttpRequest request = requestHttp((Object[]) params);
        return (GetRequest) request;
    }

    /**
     * @return A HttpRequestWithBody to perform further action.
     */
    public HttpRequestWithBody requestWithBody(String... params) {
        HttpRequest request = requestHttp((Object[]) params);
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
        consumer.accept((HttpRequestWithBody) request);
        return this;
    }

    public Requester setRequest(HttpRequest request) {
        this.request = request;
        return this;
    }

    /*
        -------------
            Result
        -------------
     */

    /**
     * Performs requests
     * @return HttpCode, the response status
     */
    public HttpCode performRequest() {
        try {
            HttpResponse<JsonNode> response = request.asJson();
            checkRateLimit(response);
            handleErrorCode(response);
            JsonNode node = response.getBody();
            if (node != null && !node.isArray()) {
                handleErrorResponse(node.getObject());
            }
            return HttpCode.getByKey(response.getStatus());
        } catch (UnirestException e) {
            throw new RuntimeException("Fail to perform http request!");
        }
    }

    /**
     * @return The json object get by performing request.
     */
    public JSONObject getAsJSONObject() {
        JSONObject json;
        try {
            HttpResponse<JsonNode> response = request.asJson();
            checkRateLimit(response);
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
            checkRateLimit(response);
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
    private HttpRequest requestHttp(Object... params) {
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
        processRequest(request);
        this.request = request;
        return request;
    }

    private String processPath(Object... params) {
        String processedPath;
        try {
            processedPath = path.getPath().replaceAll("\\{(.+?)}", "%s");
            processedPath = String.format(processedPath, params);
        } catch (IllegalFormatException ife) {
            throw new IllegalArgumentException("[INTERNAL] Cannot perform an HttpRequest due to unmatched parameters!");
        }
        return processedPath;
    }

    private void processRequest(HttpRequest request) {
        request.header("Authorization", token);
        if (useJson) request.header("Content-Type", "application/json");
    }

    private void handleErrorResponse(JSONObject response) {
        if (response.has("code")) {
            if (!(response.get("code") instanceof Integer)) return;
            ErrorResponse errorResponse = ErrorResponse.getByKey(response.getInt("code"));
            if (errorResponse != ErrorResponse.UNKNOWN) {
                throw new ErrorResponseException(errorResponse);
            } else {
                throw new ErrorResponseException(response.getInt("code"), response.getString("message"));
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void handleErrorCode(HttpResponse response) {
        HttpCode error = HttpCode.getByKey(response.getStatus());
        if (error.isServerError() || error.isFailure()) {
            if (error.equals(HttpCode.TOO_MANY_REQUESTS)) { // Rate limited
                checkRateLimit(response);
            } else {
                throw new HttpErrorException(error);
            }
        } else if (error == HttpCode.UNKNOWN){
            throw new HttpErrorException(response.getStatus(), response.getStatusText());
        }
    }

    private void checkRateLimit(HttpResponse<JsonNode> response) {
        final Headers headers = response.getHeaders();
        if (headers.containsKey("Retry-After")) { // Rate limited
            Long retryAfter = Long.parseLong(headers.getFirst("Retry-After")); // In milliseconds
            LOG.log(LogLevel.ERROR, "You are being rate limited! Automatically blocked the thread.\n" +
                    "(Request: "+path.toString()+" | Retry after: "+retryAfter+" ms)");
            try {
                Thread.sleep(retryAfter);
            } catch (InterruptedException e) {
                LOG.log(LogLevel.ERROR, "Error when blocking thread for rate limit: ", null);
                e.printStackTrace();
            }
        }
    }

}
