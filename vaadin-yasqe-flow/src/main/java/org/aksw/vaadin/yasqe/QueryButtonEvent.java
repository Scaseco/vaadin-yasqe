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

import com.vaadin.flow.component.ComponentEvent;

public class QueryButtonEvent
    extends ComponentEvent<Yasqe>
{
    private static final long serialVersionUID = 1L;

    protected String value;
    protected boolean isAbort;

    public QueryButtonEvent(Yasqe source, boolean fromClient, String value, boolean isAbort) {
        super(source, fromClient);
        this.value = value;
        this.isAbort = isAbort;
    }

    public String getValue() {
        return value;
    }

    public boolean isAbort() {
        return isAbort;
    }
}
