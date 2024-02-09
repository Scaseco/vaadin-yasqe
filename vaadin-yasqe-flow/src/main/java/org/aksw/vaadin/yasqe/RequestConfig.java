package org.aksw.vaadin.yasqe;

import java.util.List;
import java.util.Map;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

/**
 * The request config is used for creating the curl link.
 */
public class RequestConfig
    extends ViewOverJson
{
    private static final long serialVersionUID = 1L;

    public RequestConfig() {
        this(Json.createObject());
    }

    public RequestConfig(JsonObject json) {
        super(json);
    }

    public String getEndpoint() {
        return json.get("endpoint");
    }

    public RequestConfig setEndpoint(String value) {
        json.put("endpoint", value);
        return this;
    }

    public String getMethod() {
        return json.get("method");
    }

    public RequestConfig setMethod(String value) {
        json.put("method", value);
        return this;
    }

    public String getAcceptHeaderGraph() {
        return json.get("acceptHeaderGraph");
    }

    public RequestConfig setAcceptHeaderGraph(String value) {
        json.put("acceptHeaderGraph", value);
        return this;
    }

    public String getAcceptHeaderSelect() {
        return json.get("acceptHeaderSelect");
    }

    public RequestConfig setAcceptHeaderSelect(String value) {
        json.put("acceptHeaderSelect", value);
        return this;
    }

    public String getAcceptHeaderUpdate() {
        return json.get("acceptHeaderUpdate");
    }

    public RequestConfig setAcceptHeaderUpdate(String value) {
        json.put("acceptHeaderUpdate", value);
        return this;
    }

    public List<String> getNamedGraphs() {
        JsonArray array = json.getArray("namedGraphs");
        List<String> result = array == null ? null : JsonUtils.toList(array, JsonValue::asString);
        return result;
    }

    public RequestConfig setNamedGraphs(List<String> list) {
        return setNamedGraphs(JsonUtils.toArray(list, Json::create));
    }

    public RequestConfig setNamedGraphs(JsonArray array) {
        json.put("namedGraphs", array);
        return this;
    }

    public List<String> getDefaultGraphs() {
        JsonArray array = json.getArray("defaultGraphs");
        List<String> result = array == null ? null : JsonUtils.toList(array, JsonValue::asString);
        return result;
    }

    public RequestConfig setDefaultGraphs(List<String> list) {
        return setDefaultGraphs(JsonUtils.toArray(list, Json::create));
    }

    public RequestConfig setDefaultGraphs(JsonArray array) {
        json.put("defaultGraphs", array);
        return this;
    }

    public Map<String, String> getHeaders() {
        JsonObject tmp = json.getObject("headers");
        return tmp == null ? null : JsonUtils.toMap(tmp, JsonValue::asString);
    }

    public RequestConfig setHeaders(Map<String, String> map) {
        return setHeaders(JsonUtils.toObject(map, Json::create));
    }

    public RequestConfig setHeaders(JsonObject json) {
        json.put("headers", json);
        return this;
    }

    public RequestConfig addHeader(String key, String value) {
        JsonUtils.getOrCreateObject(json, "headers").put(key, value);
        return this;
    }

    public RequestConfig addDefaultGraph(String value) {
        JsonUtils.getOrCreateArrayAndAdd(json, "defaultGraphs", Json.create(value));
        return this;
    }

    public RequestConfig addNamedGraph(String value) {
        JsonUtils.getOrCreateArrayAndAdd(json, "namedGraphs", Json.create(value));
        return this;
    }

//    export interface RequestConfig<Y> {
//    	  queryArgument: string | ((yasqe: Y) => string) | undefined;
//    	  endpoint: string | ((yasqe: Y) => string);
//    	  method: "POST" | "GET" | ((yasqe: Y) => "POST" | "GET");
//    	  acceptHeaderGraph: string | ((yasqe: Y) => string);
//    	  acceptHeaderSelect: string | ((yasqe: Y) => string);
//    	  acceptHeaderUpdate: string | ((yasqe: Y) => string);
//    	  namedGraphs: string[] | ((yasqe: Y) => string[]);
//    	  defaultGraphs: string[] | ((yasqe: Y) => []);
//    	  args: Array<{ name: string; value: string }> | ((yasqe: Y) => Array<{ name: string; value: string }>);
//    	  headers: { [key: string]: string } | ((yasqe: Y) => { [key: string]: string });
//    	  withCredentials: boolean | ((yasqe: Y) => boolean);
//    	  adjustQueryBeforeRequest: ((yasqe: Y) => string) | false;
//    	}

}
