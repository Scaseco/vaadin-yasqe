package org.aksw.vaadin.yasqe;

import com.vaadin.flow.component.ComponentEvent;

/**
 * Event for when a yasqe requests a short link.
 */
public class ShortLinkRequestEvent
    extends ComponentEvent<Yasqe>
{
    private static final long serialVersionUID = 1L;

    protected String value;

    public ShortLinkRequestEvent(Yasqe source, boolean fromClient, String value) {
        super(source, fromClient);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
