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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.NullNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.node.ValueNode;

public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final JsonNodeFactory FACTORY = JsonNodeFactory.instance;

    public static NullNode createNull() {
        return FACTORY.nullNode();
    }

    public static ObjectNode createObject() {
        return FACTORY.objectNode();
    }

    public static ValueNode create(Boolean v) {
        return v == null ? createNull() : FACTORY.booleanNode(v);
    }

    public static ValueNode create(String v) {
        return v == null ? createNull() : FACTORY.stringNode(v);
    }

    public static ObjectNode getObject(ObjectNode src, String member) {
        return (ObjectNode)src.get(member);
    }

    public static ArrayNode getArray(ObjectNode src, String member) {
        return (ArrayNode)src.get(member);
    }

    public static String textOrNull(JsonNode node) {
        return node.isNull() ? null : node.asString();
    }

    public static Boolean booleanOrNull(JsonNode node) {
        return node.isNull() ? null : node.booleanValue();
    }

    public static String getString(ObjectNode obj, String member) {
        JsonNode val = obj.get(member);
        return textOrNull(val);
    }

    public static Boolean getBoolean(ObjectNode obj, String member) {
        JsonNode val = obj.get(member);
        return booleanOrNull(val);
    }

    /** Copy all entries from src into dst (not a deep copy) */
    public static ObjectNode putAll(ObjectNode dst, ObjectNode src) {
        src.properties().stream().forEach(entry -> dst.set(entry.getKey(), entry.getValue()));
        return dst;
    }

    public static ObjectNode clear(ObjectNode json) {
        json.removeAll();
        return json;
    }

    public static JsonNode clone(JsonNode json) {
        // Reliable deep copy using Jackson's conversion
        return MAPPER.valueToTree(MAPPER.convertValue(json, Object.class));
    }

    public static ObjectNode clone(ObjectNode json) {
        return (ObjectNode) clone((JsonNode) json);
    }

    /** Unsets the key if the value is null, otherwise sets it as usual. */
    public static ObjectNode putOrUnset(ObjectNode json, String key, String value) {
        if (value == null) {
            json.remove(key);
        } else {
            json.put(key, value);
        }
        return json;
    }

    public static <T> List<T> toList(ArrayNode array, Function<JsonNode, T> mapper) {
        List<T> result = new ArrayList<>(array.size());
        for (JsonNode value : array) {
            result.add(mapper.apply(value));
        }
        return result;
    }

    public static <T> ArrayNode toArray(List<T> list, Function<T, JsonNode> mapper) {
        ArrayNode result = FACTORY.arrayNode();
        list.forEach(item -> result.add(mapper.apply(item)));
        return result;
    }

    public static ArrayNode add(ArrayNode array, JsonNode item) {
        array.add(item);
        return array;
    }

    public static ArrayNode getOrCreateArrayAndAdd(ObjectNode owner, String key, JsonNode item) {
        ArrayNode array = getOrCreateArray(owner, key);
        add(array, item);
        return array;
    }

    public static ArrayNode getOrCreateArray(ObjectNode owner, String key) {
        return getOrCreate(owner, key, FACTORY::arrayNode);
    }

    public static ObjectNode getOrCreateObject(ObjectNode owner, String key) {
        return getOrCreate(owner, key, FACTORY::objectNode);
    }

    public static <T extends JsonNode> T getOrCreate(ObjectNode owner, String key, Supplier<T> newInstance) {
        JsonNode existing = owner.get(key);
        if (existing == null || !existing.getNodeType().equals(newInstance.get().getNodeType())) {
            T result = newInstance.get();
            owner.set(key, result);
            return result;
        }
        @SuppressWarnings("unchecked")
        T cast = (T) existing;
        return cast;
    }

    public static <T> Map<String, T> toMap(ObjectNode json, Function<JsonNode, T> mapper) {
        return json.properties().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> mapper.apply(e.getValue())
        ));
    }

    public static <T> ObjectNode toObject(Map<String, T> map, Function<T, JsonNode> mapper) {
        ObjectNode result = FACTORY.objectNode();
        map.forEach((key, value) -> result.set(key, mapper.apply(value)));
        return result;
    }
}
