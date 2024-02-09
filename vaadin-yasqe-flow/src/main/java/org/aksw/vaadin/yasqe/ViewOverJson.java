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

import java.io.Serializable;
import java.util.Objects;

import elemental.json.JsonObject;

public class ViewOverJson
    implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected JsonObject json;

    public JsonObject getJson() {
        return json;
    }

    /**
     * Any Json object can serve as the 'backend' for a YasqeConfig.
     * It should be ensured that that all present values are of the appropriate type.
     */
    public ViewOverJson(JsonObject json) {
        super();
        this.json = Objects.requireNonNull(json);
    }

    @Override
    public String toString() {
        return json.toJson();
    }
}
