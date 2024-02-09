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
