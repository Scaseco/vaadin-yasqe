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

import java.util.Objects;

import com.vaadin.flow.function.SerializableSupplier;

import elemental.json.Json;
import elemental.json.JsonObject;

/**
 * The YasqeConfig.
 *
 * A java domain view over its json model.
 */
public class YasqeConfig
    implements SerializableSupplier<JsonObject>
{
    private static final long serialVersionUID = 1L;

    protected JsonObject json;

    @Override
    public JsonObject get() {
        return json;
    }

    public YasqeConfig() {
        this(Json.createObject());
    }

    public YasqeConfig(JsonObject json) {
        super();
        this.json = Objects.requireNonNull(json);
    }

    public String getMode() {
        return json.getString("mode");
    }

    public YasqeConfig setMode(String value) {
        json.put("mode", value);
        return this;
    }

    public String getPersistenceId() {
        return json.getString("persistenceId");
    }

    public YasqeConfig setPersistenceId(String value) {
        json.put("persistenceId", value);
        return this;
    }

    public Boolean getCollapsePrefixesOnLoad() {
        return json.getBoolean("collapsePrefixesOnLoad");
    }

    public YasqeConfig setCollapsePrefixesOnLoad(Boolean value) {
        json.put("collapsePrefixesOnLoad", value);
        return this;
    }

    public Boolean getSyntaxErrorCheck() {
        return json.getBoolean("syntaxErrorCheck");
    }

    public YasqeConfig setSyntaxErrorCheck(Boolean value) {
        json.put("syntaxErrorCheck", value);
        return this;
    }

    public Boolean isResizeable() {
        return json.getBoolean("resizeable");
    }

    public YasqeConfig setResizeable(Boolean value) {
        json.put("resizeable", value);
        return this;
    }

    public String getPrefixCcApi() {
        return json.getString("prefixCcApi");
    }

    public YasqeConfig setPrefixCcApi(String value) {
        json.put("prefixCcApi", value);
        return this;
    }

    public Boolean getShowQueryButton() {
        return json.getBoolean("showQueryButton");
    }

    public YasqeConfig setMode(Boolean value) {
        json.put("showQueryButton", value);
        return this;
    }


//	  /**
//	   * Show a button with which users can create a link to this query. Set this value to null to disable this functionality.
//	   * By default, this feature is enabled, and the only the query value is appended to the link.
//	   * ps. This function should return an object which is parseable by jQuery.param (http://api.jquery.com/jQuery.param/)
//	   */
//	  createShareableLink: (yasqe: Yasqe) => string;
//	  createShortLink: ((yasqe: Yasqe, longLink: string) => Promise<string>) | undefined;
//	  consumeShareLink: ((yasqe: Yasqe) => void) | undefined | null;
//	  /**
//	   * Change persistency settings for the YASQE query value. Setting the values
//	   * to null, will disable persistancy: nothing is stored between browser
//	   * sessions Setting the values to a string (or a function which returns a
//	   * string), will store the query in localstorage using the specified string.
//	   * By default, the ID is dynamically generated using the closest dom ID, to avoid collissions when using multiple YASQE items on one
//	   * page
//	   */
//	  persistenceId: ((yasqe: Yasqe) => string) | string | undefined | null;
//	  persistencyExpire: number; //seconds
//	  showQueryButton: boolean;
//	  requestConfig: RequestConfig<Yasqe> | ((yasqe: Yasqe) => RequestConfig<Yasqe>);
//	  pluginButtons: (() => HTMLElement[] | HTMLElement) | undefined;
//	  //Addon specific addon ts defs, or missing props from codemirror conf
//	  highlightSelectionMatches: { showToken?: RegExp; annotateScrollbar?: boolean };
//	  tabMode: string;
//	  foldGutter: any; //This should be of type boolean, or an object. However, setting it to any to avoid
//	  //ts complaining about incorrectly extending, as the cm def only defined it has having a boolean type.
//	  matchBrackets: boolean;
//	  autocompleters: string[];
//	  hintConfig: Partial<HintConfig>;
//	  resizeable: boolean;
//	  editorHeight: string;
//	  queryingDisabled: string | undefined; // The string will be the message displayed when hovered
//	  prefixCcApi: string; // the suggested default prefixes URL API getter
}
