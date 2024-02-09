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
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.function.SerializableSupplier;
import com.vaadin.flow.shared.Registration;

import elemental.json.Json;
import elemental.json.JsonObject;
import elemental.json.JsonValue;


/**
 * Component that contains a yasqe and all required client side dependencies for Vaadin.
 */
@Tag("yasqe")
@NpmPackage(value = "@zazuko/yasqe", version = "4.2.34")
@CssImport("@zazuko/yasqe/build/yasqe.min.css")
@JavaScript("./yasqe.vaadin.js")
public class Yasqe extends Div {
    private static final long serialVersionUID = 1L;

    protected SerializableSupplier<JsonObject> config;

    /**
     * Server side state.
     * Passed to the client upon init.
     * Updated when the client reports changes.
     */
    protected JsonObject mirror;

    public static void initDefaults(JsonObject json) {
        json.put("value", Json.createNull());
        json.put("enqueuedPrefixes", Json.createObject());
        // json.put("inputAreaWidth", );
    }

    public Yasqe() {
        this(new YasqeConfig());
    }

    /**
     * @param config The config is anything that can produce a JSON object.
     */
    public Yasqe(SerializableSupplier<JsonObject> config) {
        super();
        String id = "yasqe-" + UUID.randomUUID();
        setId(id);

        this.mirror = Json.createObject();
        initDefaults(mirror);

        this.config = Objects.requireNonNull(config);

        addAttachListener(ev -> {
            init();
        });
    }

    protected void init() {
        // We need to clone the json to avoid concurrent modifications
        JsonValue mirrorClone = JsonUtils.clone(mirror);

        this.getElement().executeJs("""
        setTimeout(function() {
            const id = $0;
            const self = document.getElementById(id);
            const yasqe = new Yasqe(self, $1);
            self.yasqe = yasqe;

            console.log('id', id);
            // console.log("yasqe", yasqe);

            // Catch query requests but do not let yasqe execute them
            // yasqe.query = config => {
            yasqe.queryBtn.onclick = () => {
                const config = yasqe.getValue();
                const isAbort = yasqe.req != null;
                self.$server.onQuery(config, isAbort);
                // return new Promise((resolve, reject) => {}); // Promise.resolve("done");
            };

            // Override abortQuery to not actually try to abort a request
            yasqe.abortQuery = () => {
                if (this.req) {
                    this.req = undefined;
                }
            };

            const mirror = $2;
            // If a value was set on the mirror then place it into yasqe
            // This overrides local storage!
            if (mirror.value) {
                yasqe.setValue(mirror.value);
            }

            const super_setValue = yasqe.setValue;
            yasqe.setValue = arg => {
                self.$server.onSetValue(arg);
                super_setValue(arg);
            };

            yasqe.addPrefixes(mirror.enqueuedPrefixes);
            yasqe.updateQueryButtonX = (isBusy, isError) => {
                // Hack: A non-undefined request is considered busy; so we set it to an empty object
                yasqe.req = isBusy ? {} : undefined;
                const status = isError === false ? "valid" : (isError === true ? "error" : undefined);
                yasqe.updateQueryButton(status);
            };

            yasqe.updateQueryButtonX(mirror.queryButtonBusy, mirror.queryButtonError);

            // yasqe.on("query", (inst, req) => {
            //     self.$server.onQuery(req);
            // });

        }, 100);
        """, getId().get(), config.get(), mirrorClone);

        JsonUtils.clear(mirror.getObject("enqueuedPrefixes"));
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
    }

    /** Returns the current value. If the component is not initialized the value will be null. */
    public String getValue() {
        return mirror.get("value").asString();
    }

    protected void executeJs(String expression, Serializable... parameters) {
        this.getElement().executeJs(expression, parameters);
    }

    protected void executeJsIfReady(String expression, Serializable... parameters) {
        if (isAttached()) {
            executeJs(expression, parameters);
        }
    }

    public Yasqe setValue(String value) {
        mirror.put("value", value);
        executeJsIfReady("document.getElementById($0).yasqe.setValue($1)", getId().get(), value);
        return this;
    }

    public Yasqe setInputAreaSize(Integer width, Integer height) {
        mirror.put("inputAreaWidth", width);
        mirror.put("inputAreaHeight", height);
        executeJsIfReady("document.getElementById($0).yasqe.setSize($1, $0)", getId().get(), width, height);
        return this;
    }

    /** See {@link #addPrefixes(JsonObject)} */
    public Yasqe addPrefixes(Map<String, String> prefixes) {
        JsonObject tmp = Json.createObject();
        prefixes.forEach(tmp::put);
        addPrefixes(tmp);
        return this;
    }

    /**
     * Adds the given prefixes to the current editor content.
     * Does not affect the auto completion.
     * Auto completion is API based and configurable in {@link YasqeConfig#getPrefixCcApi()}.
     */
    public Yasqe addPrefixes(JsonObject prefixes) {
        if (isAttached()) {
            executeJs("document.getElementById($0).yasqe.addPrefixes($1)", getId().get(), prefixes);
        } else {
            JsonUtils.putAll(mirror.getObject("enqueuedPrefixes"), prefixes);
        }
        return this;
    }

    public void updateQueryButton(boolean isBusy, Boolean isError) {
        JsonValue errValue = isError == null ? Json.createNull() : Json.create(isError);
        mirror.put("queryButtonError", errValue);
        mirror.put("queryButtonBusy", isBusy);
        executeJsIfReady("document.getElementById($0).yasqe.updateQueryButtonX($1, $2)", getId().get(), isBusy, errValue);

    }

    @ClientCallable
    protected void onClientChangeValue(String value) {
        mirror.put("value", value);
    }

    protected boolean status = false;

    @ClientCallable
    public void onQuery(String req, boolean isAbort) {
        fireEvent(new QueryButtonEvent(this, true, req, isAbort));
    }

    public Registration addQueryButtonListener(ComponentEventListener<QueryButtonEvent> listener) {
        return this.addListener(QueryButtonEvent.class, listener);
    }

//  private static <T> T await(PendingJavaScriptResult promise, Class<T> cls) {
//  try {
//      return promise.toCompletableFuture(cls).get();
//  } catch (InterruptedException | ExecutionException e) {
//      throw new RuntimeException(e);
//  }
//}

// // Fires when a query is executed
//    yasqe.on("query", (instance: Yasqe, req: superagent.SuperAgentRequest) => {});
//    // Fires when a query is finished
//    yasqe.on("queryResponse", (instance: Yasqe, req: superagent.SuperAgentRequest, duration: number) => {});

 // Set query value in editor
//    yasqe.setValue("select * where {...}");
//
//    // Get query value from editor
//    yasqe.getValue();
//
//    // execute a query
//    yasqe.query({
//      url: "https://dbpedia.org/sparql",
//      reqMethod: "POST", // or "GET"
//      headers: { Accept: "..." /*...*/ },
//      args: { arg1: "val1" /*...*/ },
//      withCredentials: false,
//    });
//
//    // get whether we're in query or update mode
//    yasqe.getQueryMode();
//
//    // get the query type (select, ask, construct, ...)
//    yasqe.getQueryType();
//
//    // get prefixes map from the query string
//    yasqe.getPrefixesFromQuery();
//
//    // Add prefixes to the query.
//    yasqe.addPrefixes({ dbo: "http://dbpedia.org/ontology/" });
//
//    // Remove prefixes to the query.
//    yasqe.removePrefixes({ dbo: "http://dbpedia.org/ontology/" });
//
//    // set size of input area
//    yasqe.setSize(500, 300);
//
//    // Collapsing prefixes if there are any. Use false to expand them.
//    yasqe.collapsePrefixes(true);

 // number of seconds to persist query input, stored in the browser
 // set to 0 to always load the default query on page load
// persistencyExpire // default: 30 days
//
// // default settings for how to query the endpoint
// requestOpts: {
//   endpoint: "http://dbpedia.org/sparql",
//   method: "POST",
//   headers: {}
// },
}
