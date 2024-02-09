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
