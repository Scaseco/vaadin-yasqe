/*
 * Copyright © 2024 AKSW Research Group (https://aksw.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.aksw.vaadin.yasqe;

import java.util.List;
import java.util.Map;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * The request config is used for creating the curl link.
 */
public class RequestConfig
    extends ViewOverJson
{
    private static final long serialVersionUID = 1L;

    public RequestConfig() {
        this(JsonUtils.createObject());
    }

    public RequestConfig(ObjectNode json) {
        super(json);
    }

    public String getEndpoint() {
        return JsonUtils.getString(json, "endpoint");
    }

    public RequestConfig setEndpoint(String value) {
        json.put("endpoint", value);
        return this;
    }

    public String getMethod() {
        return JsonUtils.getString(json, "method");
    }

    public RequestConfig setMethod(String value) {
        json.put("method", value);
        return this;
    }

    public String getAcceptHeaderGraph() {
        return JsonUtils.getString(json, "acceptHeaderGraph");
    }

    public RequestConfig setAcceptHeaderGraph(String value) {
        json.put("acceptHeaderGraph", value);
        return this;
    }

    public String getAcceptHeaderSelect() {
        return JsonUtils.getString(json, "acceptHeaderSelect");
    }

    public RequestConfig setAcceptHeaderSelect(String value) {
        json.put("acceptHeaderSelect", value);
        return this;
    }

    public String getAcceptHeaderUpdate() {
        return JsonUtils.getString(json, "acceptHeaderUpdate");
    }

    public RequestConfig setAcceptHeaderUpdate(String value) {
        json.put("acceptHeaderUpdate", value);
        return this;
    }

    public List<String> getNamedGraphs() {
        ArrayNode array = JsonUtils.getArray(json, "namedGraphs");
        List<String> result = array == null ? null : JsonUtils.toList(array, JsonNode::asString);
        return result;
    }

    public RequestConfig setNamedGraphs(List<String> list) {
        return setNamedGraphs(JsonUtils.toArray(list, JsonUtils::create));
    }

    public RequestConfig setNamedGraphs(ArrayNode array) {
        json.set("namedGraphs", array);
        return this;
    }

    public List<String> getDefaultGraphs() {
        ArrayNode array = JsonUtils.getArray(json, "defaultGraphs");
        List<String> result = array == null ? null : JsonUtils.toList(array, JsonNode::asString);
        return result;
    }

    public RequestConfig setDefaultGraphs(List<String> list) {
        return setDefaultGraphs(JsonUtils.toArray(list, JsonUtils::create));
    }

    public RequestConfig setDefaultGraphs(ArrayNode array) {
        json.set("defaultGraphs", array);
        return this;
    }

    public Map<String, String> getHeaders() {
        ObjectNode tmp = JsonUtils.getObject(json, "headers");
        return tmp == null ? null : JsonUtils.toMap(tmp, JsonNode::asString);
    }

    public RequestConfig setHeaders(Map<String, String> map) {
        return setHeaders(JsonUtils.toObject(map, JsonUtils::create));
    }

    public RequestConfig setHeaders(ObjectNode json) {
        json.set("headers", json);
        return this;
    }

    public RequestConfig addHeader(String key, String value) {
        JsonUtils.getOrCreateObject(json, "headers").put(key, value);
        return this;
    }

    public RequestConfig addDefaultGraph(String value) {
        JsonUtils.getOrCreateArrayAndAdd(json, "defaultGraphs", JsonUtils.create(value));
        return this;
    }

    public RequestConfig addNamedGraph(String value) {
        JsonUtils.getOrCreateArrayAndAdd(json, "namedGraphs", JsonUtils.create(value));
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
