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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class JsonUtils {
    /** Copy all entries from src into dst (not a deep copy) */
    public static JsonObject putAll(JsonObject dst, JsonObject src) {
        for (String key : src.keys()) {
            JsonValue value = src.get(key);
            dst.put(key, value);
        }
        return dst;
    }

    public static JsonObject clear(JsonObject json) {
        Arrays.asList(json.keys()).forEach(json::remove);
        return json;
    }

    public static JsonValue clone(JsonValue json) {
        // Print-parse is a slow approach for cloning...
        JsonValue result = Json.parse(json.toJson());
        return result;
    }

    public static JsonObject clone(JsonObject json) {
        return (JsonObject)clone((JsonValue)json);
    }

    /** Unsets the key if the value is null, otherwise sets it as usual. */
    public static JsonObject putOrUnset(JsonObject json, String key, String value) {
        if (value == null) {
            json.remove(key);
        } else {
            json.put(key, value);
        }
        return json;
    }

    public static <T> List<T> toList(JsonArray array, Function<JsonValue, T> mapper) {
        int n = array.length();
        List<T> result = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) {
            JsonValue value = array.getArray(i);
            T t = mapper.apply(value);
            result.add(t);
        }
        return result;
    }

    public static <T> JsonArray toArray(List<T> list, Function<T, JsonValue> mapper) {
        JsonArray result = Json.createArray();
        Iterator<T> it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            T item = it.next();
            JsonValue value = mapper.apply(item);
            result.set(i, value);
            ++i;
        }
        return result;
    }

    public static JsonArray add(JsonArray array, JsonValue item) {
        int n = array.length();
        array.set(n, item);
        return array;
    }

    public static JsonArray getOrCreateArrayAndAdd(JsonObject owner, String key, JsonValue item) {
        JsonArray array = getOrCreateArray(owner, key);
        add(array, item);
        return array;
    }

    public static JsonArray getOrCreateArray(JsonObject owner, String key) {
        return getOrCreate(owner, key, Json::createArray);
    }

    public static JsonObject getOrCreateObject(JsonObject owner, String key) {
        return getOrCreate(owner, key, Json::createObject);
    }

    public static <T extends JsonValue> T getOrCreate(JsonObject owner, String key, Supplier<T> newInstance) {
        T result = owner.get(key);
        if (result == null) {
            result = newInstance.get();
            owner.put(key, result);
        }
        return result;
    }

    public static <T> Map<String, T> toMap(JsonObject json, Function<JsonValue, T> mapper) {
        Map<String, T> result = Arrays.asList(json.keys()).stream().collect(Collectors.toMap(
                k -> k,
                k -> {
                    T r = mapper.apply(json.get(k));
                    return r;
                }));
        return result;
    }

    public static <T> JsonObject toObject(Map<String, T> map, Function<T, JsonValue> mapper) {
        JsonObject result = Json.createObject();
        map.entrySet().stream().forEach(e -> {
            JsonValue v = mapper.apply(e.getValue());
            result.put(e.getKey(), v);
        });
        return result;
    }

}
