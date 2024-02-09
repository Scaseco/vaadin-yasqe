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

import java.util.Arrays;

import elemental.json.JsonObject;
import elemental.json.JsonValue;

public class JsonObjectUtils {
    /** Copy all entries from src into dst (not a deep copy) */
    public static JsonObject putAll(JsonObject dst, JsonObject src) {
        for (String key : src.keys()) {
            JsonValue value = src.get(key);
            dst.put(key, value);
        }
        return dst;
    }

    public static JsonObject clear(JsonObject obj) {
        Arrays.asList(obj.keys()).forEach(obj::remove);
        return obj;
    }
}
